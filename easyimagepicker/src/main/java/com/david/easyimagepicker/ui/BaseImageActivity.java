package com.david.easyimagepicker.ui;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.david.easyimagepicker.EasyImagePicker;
import com.david.easyimagepicker.view.SystemBarTintManager;

/**
 * Created by Administrator on 2016/12/21.
 * 沉浸式状态栏：https://github.com/jgilfelt/SystemBarTint
 */

public class BaseImageActivity extends AppCompatActivity {
    protected SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);// 启用状态栏渐变
        tintManager.setStatusBarTintResource(EasyImagePicker.getInstance().getPickerConfig().getPickerThmeConfig().getTitleBarBgColor());//设置状态栏颜色与ActionBar颜色相连

    }

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
