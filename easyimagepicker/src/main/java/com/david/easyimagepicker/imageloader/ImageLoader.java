package com.david.easyimagepicker.imageloader;

import android.app.Activity;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by david on 2016/12/22.
 * 构造ImageLoader抽象类，外部在使用EasyImagePicker时自己传入对应Loader即可，从而可以不在easyimageloader中依赖第三方图片加载库
 */

public interface ImageLoader extends Serializable {
    void displayImage(Activity activity, String fielPath, ImageView imageView, int width, int height);

    void clearMemoryCache();
}
