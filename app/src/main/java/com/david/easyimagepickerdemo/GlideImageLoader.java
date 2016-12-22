package com.david.easyimagepickerdemo;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.david.easyimagepicker.imageloader.ImageLoader;

import java.io.File;

/**
 * 构造ImageLoader
 * 推荐Glide学习博客：https://mrfu.me/2016/02/27/Glide_Advanced_Loading/
 */
public class GlideImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity)                             //配置上下文
                .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .error(R.drawable.default_image)           //设置错误图片
                .placeholder(R.drawable.default_image)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {
    }
}
