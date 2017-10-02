package com.zcy.hnkjxy.viewpagerdemo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.zcy.hnkjxy.viewpagerdemo.adapter.PhotoAdapter;

/**
 * Created by zhang chen yang on 2017/10/2 0002.
 */

public class ZoomPhotoView extends DialogFragment implements ViewPager.OnPageChangeListener {
    //ViewPager的适配器
    private PhotoAdapter adapter;
    //图片地址集合
    private List<String> mList = new ArrayList<>();
    //界面中心下方的当前第N张图片的指示器
    private TextView tvIndia;
    //ViewPager对象
    private ViewPager vpPhoto;
    //显示第currentItem图片，指示器的字体颜色，背景颜色
    private int currentItem, textColor, bacColor;
    //指示器的字体大小
    private float textSize;
    /**
     * 保持和AlertDialog使用方式的一致，我们也使用生成器模式和链式编程来构建对象
     * 然后我们还会使用单例模式
     */
    private static ZoomPhotoCreator zoomPhotoCreator;

    /*
    *静态代码块，当我们第一引用ZoomPhotoView时，创建一个生成器，它用来辅助我们生成ZoomPhotoView对象
     */
    static {
        zoomPhotoCreator = new ZoomPhotoCreator();
    }
    /*
     *典型的单例模式
     */
    static class Instance {
        static ZoomPhotoView Instance = new ZoomPhotoView();
    }
    /*
     * 构造函数私有化，典型的单例模式套路
     */
    private ZoomPhotoView() {

    }
//      重写DialogFragment的onCreateView方法
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //去掉标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //帧布局用于存放ViewPager和下标指示器(TextView)
        FrameLayout flay = new FrameLayout(getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        下标显示
        FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvIndia = new TextView(getContext());
        param.gravity = Gravity.CENTER | Gravity.BOTTOM;
        param.setMargins(50, 50, 50, 50);
        tvIndia.setLayoutParams(param);

        //图片显示位置
        vpPhoto = new ViewPager(getContext());
        vpPhoto.setLayoutParams(params);
        adapter = new PhotoAdapter(this, mList);
        vpPhoto.setAdapter(adapter);
        //设置ViewPager页面改变的监听器
        vpPhoto.addOnPageChangeListener(this);

        //加入flay布局
        flay.addView(vpPhoto);
        flay.addView(tvIndia);
        return flay;
    }

    //设置相关属性
    public static ZoomPhotoCreator size(float size) {
        zoomPhotoCreator.size(size);
        return zoomPhotoCreator;
    }

    public static ZoomPhotoCreator color(int color) {
        zoomPhotoCreator.color(color);
        return zoomPhotoCreator;
    }

    public static ZoomPhotoCreator bag(int color) {
        zoomPhotoCreator.bacColor(color);
        return zoomPhotoCreator;
    }

    public static ZoomPhotoView build() {

        return zoomPhotoCreator.build();
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }
//    添加图片数据集合
    public void addImages(ArrayList<String> images) {
        mList.clear();
        mList.addAll(images);
    }
//  初始化控件
    private void initView() {
        //获得我们创建对象时设置的属性
        currentItem = zoomPhotoCreator.currentItem;
        textColor = zoomPhotoCreator.color;
        bacColor = zoomPhotoCreator.bacColor;
        textSize = zoomPhotoCreator.size;
        //设置相关属性
        tvIndia.setTextSize(textSize);
        tvIndia.setTextColor(textColor);
        vpPhoto.setBackgroundColor(bacColor);
        tvIndia.setText(1 + currentItem + "/" + mList.size());
        adapter.notifyDataSetChanged();
        vpPhoto.setCurrentItem(currentItem);
    }

    @Override
    public void onStart() {
        super.onStart();
        //把Dialog设置为全屏
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    private static final String TAG = "ZoomPhotoView";

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: ");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
//  当页面切换时更改指示器指示的页面
    @Override
    public void onPageSelected(int position) {
        tvIndia.setText(1 + position + "/" + mList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
//      生成器模式辅助类
    public static class ZoomPhotoCreator {
        int color;
        float size;
        int bacColor;
        int currentItem;

        public ZoomPhotoCreator color(int textColor) {
            this.color = textColor;
            return this;
        }

        public ZoomPhotoCreator size(float textSize) {
            this.size = textSize;
            return this;
        }

        public ZoomPhotoCreator bacColor(int backgroundColor) {
            this.bacColor = backgroundColor;
            return this;
        }

        public ZoomPhotoCreator current(int currentItem) {
            this.currentItem = currentItem;
            return this;
        }

        public ZoomPhotoView build() {
            return Instance.Instance;
        }
    }
}
