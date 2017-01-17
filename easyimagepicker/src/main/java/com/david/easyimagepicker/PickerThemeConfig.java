package com.david.easyimagepicker;

import android.graphics.Color;

/**
 * Created by david on 2016/12/29.
 * 主题配置类
 */

public class PickerThemeConfig {

    private int textColor;//文字颜色
    private int titleBarBgColor;//顶部标题栏背景颜色
    private int backBtnIcon;//标题栏返回按钮图标
    private int backBtnBg;//标题栏返回按钮图标背景
    private int okBtnBg;//标题栏完成按钮图标背景
    private int partingLineColor;//标题分竖直割线颜色

    private PickerThemeConfig(Builder builder) {
        this.textColor = builder.textColor;
        this.titleBarBgColor = builder.titleBarBgColor;
        this.backBtnIcon = builder.backBtnIcon;
        this.backBtnBg = builder.backBtnBg;
        this.okBtnBg = builder.okBtnBg;
        this.partingLineColor = builder.partingLineColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getTitleBarBgColor() {
        return titleBarBgColor;
    }

    public int getPartingLineColor() {
        return partingLineColor;
    }

    public int getBackBtnIcon() {
        return backBtnIcon;
    }

    public int getBackBtnBg() {
        return backBtnBg;
    }

    public int getOkBtnBg() {
        return okBtnBg;
    }

    public static class Builder {

        public PickerThemeConfig build() {
            return new PickerThemeConfig(this);
        }

        private int textColor = Color.parseColor("#FFFFFF");//文字颜色
        private int titleBarBgColor = R.color.theme_color;//顶部标题栏背景默认颜色
        private int backBtnIcon = R.drawable.ic_back;//标题栏返回按钮图标
        private int backBtnBg = R.drawable.selector_image_select_back;//标题栏返回按钮图标背景
        private int okBtnBg = R.drawable.selector_image_ok;//标题完栏成按钮图标背景
        private int partingLineColor = R.color.partinLine;//标题分竖直割线颜色

        public Builder setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder setTitleBarBgColor(int titleBarBgColor) {
            this.titleBarBgColor = titleBarBgColor;
            return this;
        }

        public Builder setPartingLineColor(int partingLineColor) {
            this.partingLineColor = partingLineColor;
            return this;
        }

        public Builder setBackBtnIcon(int backBtnIcon) {
            this.backBtnIcon = backBtnIcon;
            return this;
        }

        public Builder setBackBtnBg(int backBtnBg) {
            this.backBtnBg = backBtnBg;
            return this;
        }

        public Builder setOkBtnBg(int okBtnBg) {
            this.okBtnBg = okBtnBg;
            return this;
        }
    }

}
