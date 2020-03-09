package com.android.mytest;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.android.mytest.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {
    private final static String ACTION_BOOT = "MY_BROADCAST";   //系统启动完成的广播
//    AlertDialog.Builder builder;
    @Override
    public void onReceive(final Context context, Intent intent) {
        Toast.makeText(context, intent.toString(), Toast.LENGTH_SHORT).show();
        if (ACTION_BOOT.equals(intent.getAction())) {
            Toast.makeText(context, "您设置的时间到了！", Toast.LENGTH_SHORT).show();
            Intent intentNew = new Intent();
            intentNew.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            ComponentName cn = new ComponentName("com.example.demo0224",  "com.example.demo0224.SplashActivity");
            intentNew.setComponent(cn);

            context.startActivity(intentNew);
        }
/*        builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_launcher)
                .setTitle("系统提示：")
                .setMessage("这是一个最普通的AlertDialog,\n带有三个按钮，分别是取消，中立和确定")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "你点击了确定按钮~", Toast.LENGTH_SHORT).show();
                    }
                });             //创建AlertDialog对象
        builder.setCancelable(true);   //点击对话框以外的区域是否让对话框消失，缺省值true消失
        builder.show();*/
    }
}
