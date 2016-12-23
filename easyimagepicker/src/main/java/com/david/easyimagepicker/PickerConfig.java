package com.david.easyimagepicker;

/**
 * Created by david on 2016/12/23.
 * EasyImagPicker 的配置文件
 * 使用建造者模式
 */

public class PickerConfig {
    protected int imageWidthSize;//gridview显示图片列数
    protected int multipleLimit;//多选张数，1：单选


    public static class Builder{
        private int imageWidthSize;//gridview显示图片列数
        private int multipleLimit;//多选张数，1：单选

    }

}
