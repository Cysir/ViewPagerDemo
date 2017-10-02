package com.zcy.hnkjxy.viewpagerdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by zhang chen yang on 2017/9/29 0029.
 */

public class PhotoAdapter extends PagerAdapter {
    /**
     * Fragment虽然不是上下文对象但是他有个方法getContext()对象
     * 我们使用Fragment是因为前面说到使用Glide加载图片时，Glide会根据传入的
     * Fragment或者Activity自行关联它们的生命周期，达到优化内存的效果。
     */
    private Fragment mContext;
    /**
     * 图片地址集合
     */
    private List<String> mList;
    /**
     * PhotoView集合，有多少个图片就创建多少个PhotoView。
     */
    private List<PhotoView> mPhoto = new ArrayList<>();

    /**
     * 构造方法，初始化适配器
     * @param mContext
     * @param mList
     */
    public PhotoAdapter(Fragment mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
        initPhoto();
    }
    private void initPhoto(){
        List<PhotoView> photos = new ArrayList<>();
        PhotoView v;
        //这个LayoutParams可以理解为java代码中的布局参数，相当于XML文件中的属性。
        ViewPager.LayoutParams params = new ViewPager.LayoutParams();
        //设置宽度填充满父布局
        params.width = ViewPager.LayoutParams.MATCH_PARENT;
        //高度为自身大小
        params.height = ViewPager.LayoutParams.WRAP_CONTENT;
//        一次性创建需要的PhotoView
        for (int i = mPhoto.size(); i < mList.size(); i++) {
            v = new PhotoView(mContext.getContext());
            //将布局参数设置进PhotoView
            v.setLayoutParams(params);
//            添加到集合中去
            photos.add(v);
            //使用Glide加载图片
            Glide.with(mContext).load(mList.get(i)).into(v);
        }
        mPhoto.addAll(photos);
        photos.clear();
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public void notifyDataSetChanged() {
        initPhoto();
        super.notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(mPhoto.get(position));
        Log.i("TAG", "instantiateItem: "+mPhoto.get(position).getScaleType());
        return mPhoto.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mPhoto.get(position));
    }

}
