package com.android.fragments;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.mytest.AlarmReceiver;
import com.android.mytest.R;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiagFragment extends Fragment {
    private final static String TAG = DiagFragment.class.getSimpleName();
    AlertDialog alert = null;
    AlertDialog.Builder builder = null;
    RadioGroup alertMode, processMode;
    TextView tvAlarmTime;
    public DiagFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diag, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        alertMode = getView().findViewById(R.id.rbgrp_alertdiag);
        getView().findViewById(R.id.btn_show_alert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDiag(alertMode.getCheckedRadioButtonId(), getActivity());
            }
        });

        processMode = getView().findViewById(R.id.rbgrp_processdiag);
        getView().findViewById(R.id.btn_show_pick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPickDiag(processMode.getCheckedRadioButtonId(), getActivity());
            }
        });

        tvAlarmTime = getView().findViewById(R.id.tv_alarm_time);
        String str = getAlarmTime();
        if (!str.equals(""))
            tvAlarmTime.setText(str);
        else
            tvAlarmTime.setText("");
    }

    private String getAlarmTime(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String strAlarmTime = sharedPreferences.getString("time","");
        return strAlarmTime;
    }

    private void setTvAlarmTime(int hour, int minute){
        String strTmp = String.format("%02d:%02d", hour, minute);
        //步骤1：创建一个SharedPreferences对象
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("data",Context.MODE_PRIVATE);
        //步骤2： 实例化SharedPreferences.Editor对象
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //步骤3：将获取过来的值放入文件
        editor.putString("time", strTmp);
/*        editor.putInt("age", 28);
        editor.putBoolean("marrid",false);*/
        //步骤4：提交
        editor.commit();
    }

    private void showAlertDiag(final int radioButtonId, final Context mContext){
        switch (radioButtonId) {
            //普通对话框
            case R.id.rb_com_alert:
                //alert = null;
                builder = new AlertDialog.Builder(mContext);
                builder.setIcon(R.mipmap.ic_launcher)
                        .setTitle("系统提示：")
                        .setMessage("这是一个最普通的AlertDialog,\n带有三个按钮，分别是取消，中立和确定")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(mContext, "你点击了取消按钮~", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(mContext, "你点击了确定按钮~", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNeutralButton("中立", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(mContext, "你点击了中立按钮~", Toast.LENGTH_SHORT).show();
                            }
                        });             //创建AlertDialog对象
                builder.setCancelable(true);   //点击对话框以外的区域是否让对话框消失，缺省值true消失
                builder.show();
                //alert.show();                    //显示对话框
                break;
            //普通列表对话框
            case R.id.rb_list_alert:
                final String[] lesson = new String[]{"语文", "数学", "英语", "化学", "生物", "物理", "体育"};
                //alert = null;
                builder = new AlertDialog.Builder(mContext);
                builder.setIcon(R.mipmap.ic_launcher)
                        .setTitle("选择你喜欢的课程")
                        .setItems(lesson, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(mContext, "你选择了" + lesson[which], Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.setCancelable(false);   //点击对话框以外的区域是否让对话框消失，false为不消失
                builder.show();
                break;
            //单选列表对话框
            case R.id.rb_radio_alert:
                final String[] fruits = new String[]{"苹果", "雪梨", "香蕉", "葡萄", "西瓜"};
                alert = null;
                builder = new AlertDialog.Builder(mContext);
                builder.setIcon(R.mipmap.ic_launcher)
                        .setTitle("选择你喜欢的水果，只能选一个哦~")
                        .setSingleChoiceItems(fruits, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alert.dismiss();
                                Toast.makeText(getActivity(), "你选择了" + fruits[which], Toast.LENGTH_SHORT).show();
                            }
                        });
/*                //设置正面按钮
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //alert.dismiss();
                    }
                });*/
                alert = builder.create();
                alert.show();
                break;
            //多选列表对话框
            case R.id.rb_check_alert:
                final String[] menu = new String[]{"水煮豆腐", "萝卜牛腩", "酱油鸡", "胡椒猪肚鸡"};
                //定义一个用来记录个列表项状态的boolean数组
                final boolean[] checkItems = new boolean[]{false, false, false, false};
                //alert = null;
                builder = new AlertDialog.Builder(mContext);
                alert = builder.setIcon(R.mipmap.ic_launcher)
                        .setMultiChoiceItems(menu, checkItems, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                checkItems[which] = isChecked;
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String result = "";
                                for (int i = 0; i < checkItems.length; i++) {
                                    if (checkItems[i])
                                        result += menu[i] + " ";
                                }
                                Toast.makeText(getActivity(), "客官你点了:" + result, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();
                alert.show();
                break;
        }
    }
    private void showPickDiag(final int radioButtonId, final Context mContext){
        switch (radioButtonId) {
            // 日期选择框
            case R.id.rb_date_pick:
                Calendar cale1 = Calendar.getInstance();  // 获取当前时间
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            String result = "";
                            //这里获取到的月份需要加上1哦~
                            result += "你选择的是" + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日";
                            Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                        }
                    }
                    ,cale1.get(Calendar.YEAR), cale1.get(Calendar.MONTH), cale1.get(Calendar.DAY_OF_MONTH)).show();
                break;
            // 时间选择框
            case R.id.rb_time_pick:
                Calendar cale2 = Calendar.getInstance();  // 获取当前时间;
                new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    Calendar calendar = Calendar.getInstance();  // 获取当前时间
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String result = "";
                        result += "您选择的时间是:"+hourOfDay+"时"+minute+"分";
                        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                        // TODO Auto-generated method stub
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        // 建立Intent和PendingIntent来调用目标组件
                        //Intent intent = new Intent(getActivity(), AlarmReceiver.class);  //显式Intent
                        Intent intent = new Intent("MY_BROADCAST");  //隐式Intent
                        // Android 8.0之后仅在Manifest.xml中写receiver还不够，还要明确指明接收器所在的package和接收器的class
                        intent.setComponent(new ComponentName("com.android.mytest","com.android.mytest.AlarmReceiver"));
                        //getActivity().sendBroadcast(intent);
                        //Toast.makeText(getActivity(),"发送广播", Toast.LENGTH_LONG).show();
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);
                        // 获取闹钟管理的实例
                        AlarmManager am = (AlarmManager)getActivity().getSystemService(ALARM_SERVICE);
                        // 设置闹钟
                        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        tvAlarmTime.setText(String.format("%02d:%02d", hourOfDay, minute));
                        setTvAlarmTime(hourOfDay, minute);
                    }
                }
                ,cale2.get(Calendar.HOUR_OF_DAY), cale2.get(Calendar.MINUTE), true).show();
                break;
            default:
                break;
        }
    }
}
