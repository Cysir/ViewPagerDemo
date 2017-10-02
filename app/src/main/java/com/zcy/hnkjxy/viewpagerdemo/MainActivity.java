package com.zcy.hnkjxy.viewpagerdemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> images;
    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置图片加载的地址
        images = new ArrayList<>();
        images.add("http://d.hiphotos.baidu.com/image/pic/item/f31fbe096b63f624f0d3ad2e8c44ebf81a4ca315.jpg");
        images.add("http://d.hiphotos.baidu.com/image/pic/item/5fdf8db1cb134954b2fe01a75f4e9258d0094a15.jpg");
        images.add("http://c.hiphotos.baidu.com/image/pic/item/9825bc315c6034a87ccffc42c2134954082376c7.jpg");
        images.add("http://b.hiphotos.baidu.com/image/pic/item/f3d3572c11dfa9ecf333c4d46bd0f703908fc1d0.jpg");
        images.add("http://upload.365jilin.com/2016/0820/1471686023587.jpg");
        images.add("http://imgs.aixifan.com/content/2016_07_11/1468254644.gif");
        images.add("http://i1.hdslb.com/bfs/archive/1d6f6483d6aab106dcf011c0be3243ab197505e3.jpg");
        images.add("http://img0.imgtn.bdimg.com/it/u=393555490,627047088&fm=214&gp=0.jpg");
        images.add("http://i2.hdslb.com/bfs/archive/0f2afafebf13691b3bdcbf5b81cf6f50c699f8d1.jpg");
        images.add("http://imgsrc.baidu.com/forum/w=580/sign=3e2d233f982397ddd679980c6983b216/214a8f025aafa40f10eed405a264034f79f01973.jpg");
        images.add("http://i1.hdslb.com/bfs/archive/aded071aa1479ac7302bd57d6cbcef761419e740.jpg");
        gridView = (GridView) findViewById(R.id.gvImages);
        //设置适配器
        gridView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return images.size();
            }

            @Override
            public Object getItem(int i) {
                return images.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                ImageView imageView = new ImageView(MainActivity.this);
                Glide.with(MainActivity.this).load(images.get(i)).into(imageView);
                return imageView;
            }
        });
        //给GridView添加单击事件
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /**
                 * 生成器模式获得一个ZoomPhotoView对象
                 * 设置指示器的大小，背景颜色，和指示器颜色
                 * 最后通过build方法创建
                 * 这里我们不用担心每一次单击都会创建一个新对象造成内存浪费
                 * 因为我们使用的是单例模式，即第一次使用会创建新对象，后面获取的
                 * 其实是对象的引用并非重新创建对象
                 */
                ZoomPhotoView zoomPhotoView = ZoomPhotoView
                        .size(12)
                        .bacColor(Color.BLACK)
                        .color(Color.WHITE)
                        .current(i)
                        .build();
                /**
                 * 添加图片数据进去
                 */
                zoomPhotoView.addImages((ArrayList<String>) images);
                /**
                 * 弹出Dialog
                 */
                zoomPhotoView.show(getSupportFragmentManager(),"tag");
            }
        });
    }

}
