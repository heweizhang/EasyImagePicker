package com.david.easyimagepicker;

import android.graphics.Color;

/**
 * Created by david on 2016/12/29.
 * 主题配置类
 */

public class PickerThmeConfig {

    private int textColor;//文字颜色
    private int topBarBgColor;//顶部标题栏背景颜色
    private int bottomBarBgColor;//bottomBar背景默认颜色
    private int backBtnIcon;//标题栏返回按钮图标
    private int backBtnBg;//标题栏返回按钮图标背景
    private int okBtnBg;//标题栏完成按钮图标背景

    private PickerThmeConfig(Builder builder) {
        this.textColor = builder.textColor;
        this.topBarBgColor = builder.topBarBgColor;
        this.backBtnIcon = builder.backBtnIcon;
        this.backBtnBg = builder.backBtnBg;
        this.okBtnBg = builder.okBtnBg;
        this.bottomBarBgColor = builder.bottomBarBgColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getTopBarBgColor() {
        return topBarBgColor;
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

        public PickerThmeConfig build() {
            return new PickerThmeConfig(this);
        }

        private int textColor = Color.parseColor("#FFFFFF");//文字颜色
        private int topBarBgColor = R.color.theme_color;//顶部标题栏背景默认颜色
        private int bottomBarBgColor = R.color.theme_color_footbar;//bottomBar背景默认颜色
        private int backBtnIcon = R.drawable.ic_back;//标题栏返回按钮图标
        private int backBtnBg = R.drawable.selector_image_select_back;//标题栏返回按钮图标背景
        private int okBtnBg = R.drawable.selector_image_ok;//标题完栏成按钮图标背景

        public Builder setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder setTopBarBgColor(int topBarBgColor) {
            this.topBarBgColor = topBarBgColor;
            return this;
        }

        public Builder setBottomBarBgColor(int bottomBarBgColor) {
            this.bottomBarBgColor = bottomBarBgColor;
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
