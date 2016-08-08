package com.gosuncn.cu.module.image;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gosuncn.core.base.BaseActivity;
import com.gosuncn.cu.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.senab.photoview.PhotoView;

public class ImageActivity extends BaseActivity {

    @BindView(R.id.pv_main_show_img)
    PhotoView pvMainShowImg;
    @BindView(R.id.civ_img)
    CircleImageView civImg;
    @BindView(R.id.iv_glide)
    ImageView ivGlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        testGlide();
    }

    public void testGlide() {
        //详细请看http://www.cnblogs.com/whoislcj/p/5558168.html
        Glide.with(this)
                .load("http://img1.imgtn.bdimg.com/it/u=4097220334,1720642513&fm=21&gp=0.jpg")
                .asGif()//加载动态图
                //.asBitmap()//加载静态图
                //.centerCrop()
                .fitCenter()
                .placeholder(R.drawable.ic_launcher)//占位
                .error(R.mipmap.ic_launcher)//加载失败
                //.skipMemoryCache(true)//设置跳过内存缓存
                //.priority(Priority.NORMAL)//设置下载优先级
                //.diskCacheStrategy(DiskCacheStrategy.ALL)//all:缓存源资源和转换后的资源 none:不作任何磁盘缓存source:缓存源资源 result：缓存转换后的资源
                //.animate(android.R.anim.fade_in)
                 .thumbnail(0.1f)//先加载缩略图 然后在加载全图
                //.override(300, 300)//设置加载尺寸
                .crossFade()//淡入效果
                .into(ivGlide);

        //最简单的用法：
        //Glide.with(this).load("").into(ivGlide);

        // Glide.get(this).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
        // Glide.get(this).clearMemory();//清理内存缓存  可以在UI主线程中进行
    }

}
