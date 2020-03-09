package com.android.mytest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.fragments.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    private final int VIEW_FRAGMENT_ID = 1;
    private final int DIAG_FRAGMENT_ID = 2;
    private final int PIC_FRAGMENT_ID = 3;
    private final int ANIM_FRAGMENT_ID = 4;
    private ArrayList<String> mCategoryTxt = new ArrayList<>();
    private int[] mCategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    @SuppressLint("ResourceType")
    private void setRadioButton(final RadioButton radioButton, final int idx){
        radioButton.setButtonDrawable(null);//去掉前面的单选圆形按钮
        radioButton.setGravity(Gravity.CENTER);
        radioButton.setText(mCategoryTxt.get(idx));
        radioButton.setTextSize(30f);
        radioButton.setTextColor(getResources().getColorStateList(R.drawable.selector_category_button_color, null));
//        radioButton.setTextColor(getResources().getColor(R.color.colorPrimaryDark,null));
        radioButton.setId(idx+1);
        radioButton.setChecked(idx == 0);
        radioButton.setOnClickListener(new RadioButtonClick());
    }

    private void initButtonTxt(){
        mCategoryTxt.add("视图");
        mCategoryTxt.add("弹框");
        mCategoryTxt.add("图片");
        mCategoryTxt.add("动画");
    }

    private void initView(){
        initButtonTxt();
        RadioGroup rbgrpCategory = findViewById(R.id.rbgrp_category);
        // 为RadioGroup添加RadioButton
        for (int i = 0; i < mCategoryTxt.size(); i++) {
            RadioButton rbTmp = new RadioButton(this);
            setRadioButton(rbTmp, i);
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
            rbgrpCategory.addView(rbTmp, lp);
        }
        rbgrpCategory.setOrientation(RadioGroup.HORIZONTAL);  // button水平布置
    }

    class RadioButtonClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case VIEW_FRAGMENT_ID:
                    Log.d(TAG, "onClick: ViewFragment");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new ViewFragment()).commit();
                    break;
                case DIAG_FRAGMENT_ID:
                    Log.d(TAG, "onClick: DiagFragment");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new DiagFragment()).commit();
                    break;
                case PIC_FRAGMENT_ID:
                    Log.d(TAG, "onClick: PictureFragment");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new PictureFragment()).commit();
                    break;
                case ANIM_FRAGMENT_ID:
                    Log.d(TAG, "onClick: AnimFragment");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new AnimFragment()).commit();
                    break;
                default:
                    break;
            }
        }
    }
}
