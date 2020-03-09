package com.android.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import jp.wasabeef.glide.transformations.BlurTransformation;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.mytest.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class PictureFragment extends Fragment {

    public PictureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_picture, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().findViewById(R.id.btn_glide_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGlidePic();
            }
        });
    }

    private void showGlidePic(){
        ImageView ivGlidePic = getActivity().findViewById(R.id.iv_glide_pic);
        EditText edMode = getActivity().findViewById(R.id.et_mode);
        int mode = Integer.parseInt(edMode.getText().toString());
        String url;

        switch(mode) {
            case 0:
                // 加载应用内的图片资源
                Glide.with(this).load(R.mipmap.nami).into(ivGlidePic);
                break;
            case 1:
                // 加载网络图片资源
                url = "https://dss0.bdstatic.com/-0U0bnSm1A5BphGlnYG/tam-ogel/299c55e31d7f50ae4dc85faa90d6f445_121_121.jpg";
                Glide.with(this).load(url).into(ivGlidePic);
                break;
            case 2:
                // 加载gif动图资源
                Glide.with(this).asGif().load(R.mipmap.dance).into(ivGlidePic);
                break;
            case 3:
                // 加载本地图片，如SD卡中的
                File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/AX7.jpg");
                Glide.with(this).load(file).into(ivGlidePic);
                break;
            case 4:
                //图片圆形显示
                RequestOptions options1 = new RequestOptions().transform(new CircleCrop());
                Glide.with(this).load(R.mipmap.nami).apply(options1).into(ivGlidePic);
                //Glide.with(this).load(R.mipmap.nami).transform(new CircleCrop()).into(ivGlide);
                break;
            case 5:
                //图片圆角矩形显示
                //RequestOptions options2 = new RequestOptions().transform(new RoundedCorners(20));
                //Glide.with(this).load(R.mipmap.luff).apply(options2).into(ivGlide);
                Glide.with(this).load(R.mipmap.luff).transform(new RoundedCorners(20)).into(ivGlidePic);
                break;
            case 6:
                //根据图片的加载状态显示不同的图片
                RequestOptions options3 = new RequestOptions()
                        .error(R.drawable.ic_launcher_background)
                        .placeholder(R.drawable.ic_launcher_foreground);
                url = "https://upload-images.jianshu.io/upload_images/3720645-751647c1fa5d0b0f.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp";
                Glide.with(this).load(url).apply(options3).into(ivGlidePic);
                break;
            case 7:
                //高斯模糊
                //RequestOptions options4 = new RequestOptions().transform(new CircleCrop(), new BlurTransformation(3,3));
                //RequestOptions options4 = new RequestOptions().transform(new BlurTransformation(3,3));
                //Glide.with(this).load(R.mipmap.nami).apply(options4).into(ivGlide);
                Glide.with(this).load(R.mipmap.nami).transform(new CircleCrop(), new BlurTransformation(5,3)).into(ivGlidePic);
                break;

            default:
                break;
        }
    }
}
