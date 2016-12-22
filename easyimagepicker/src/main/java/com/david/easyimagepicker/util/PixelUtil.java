package com.david.easyimagepicker.util;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by Administrator on 2016/7/26.
 */
public class PixelUtil {

    //获取运行屏幕宽度
    public static int getScreenWidth(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        //宽度 dm.widthPixels
        //高度 dm.heightPixels
//        LogUtil.i("info", "getScreenWidth" + dm.widthPixels);
        return dm.widthPixels;
    }

    /**
     * 获取控件宽
     */
    public static int getWidth(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return (view.getMeasuredWidth());
    }


    //DP转PX
    public static int dp2px(Activity context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //PX转DP
    public static int px2dp(Activity context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
