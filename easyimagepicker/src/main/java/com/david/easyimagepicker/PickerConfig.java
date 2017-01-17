package com.david.easyimagepicker;

import android.content.Context;

import com.david.easyimagepicker.imageloader.ImageLoader;

/**
 * Created by david on 2016/12/23.
 * EasyImagPicker 的配置文件
 * 使用建造者模式
 */

public class PickerConfig {

    private Context context;
    private PickerThemeConfig pickerThemeConfig;
    private ImageLoader imageLoader;//外部传入图片加载器
    private int imageWidthSize;//gridview显示图片列数,默认为3
    private int animRes;//动画效果:禁止动画请传入0
    private String log;//默认log,传入null 为不打印

    private PickerConfig(Builder builder) {
        this.context = builder.context;
        this.imageLoader = builder.imageLoader;
        this.imageWidthSize = builder.imageWidthSize;
        this.animRes = builder.animRes;
        this.log = builder.log;
        this.pickerThemeConfig = builder.pickerThemeConfig;
    }

    public PickerThemeConfig getPickerThemeConfig() {
        return pickerThemeConfig;
    }

    public String getLog() {
        return log;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public int getImageWidthSize() {
        return imageWidthSize;
    }

    public int getAnimRes() {
        return animRes;
    }

    public Context getContext() {
        return context;
    }

    public static class Builder {
        public PickerConfig build() {
            return new PickerConfig(this);
        }

        private Context context;
        private ImageLoader imageLoader;
        private int imageWidthSize = 3;//gridview显示图片列数
        private int animRes = R.anim.gf_flip_horizontal_in;
        private String log = "easyimagepicker";//log tag,传入null 为不打印
        private PickerThemeConfig pickerThemeConfig = new PickerThemeConfig.Builder().build();


        public Builder(Context context, ImageLoader imageLoader) {
            this.context = context;
            this.imageLoader = imageLoader;
        }

        public Builder setPickerThemeConfig(PickerThemeConfig pickerThemeConfig) {
            this.pickerThemeConfig = pickerThemeConfig;
            return this;
        }

        public Builder setLog(String tag) {
            this.log = tag;
            return this;
        }

        public Builder setImageWidthSize(int imageWidthSize) {
            this.imageWidthSize = imageWidthSize;
            return this;
        }

        public Builder setAnimRes(int animRes) {
            this.animRes = animRes;
            return this;
        }
    }

}
