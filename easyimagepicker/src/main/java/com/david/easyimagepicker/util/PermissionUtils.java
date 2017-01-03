package com.david.easyimagepicker.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by david on 2017/1/3.
 */

public class PermissionUtils {

    private static Context mContext;

    private static PermissionUtils instance;

    private int requestCode = -1;

    public static PermissionUtils getInstance(Context context) {

        mContext = context;
        synchronized (PermissionUtils.class) {
            if (instance == null) {
                instance = new PermissionUtils();
            }
        }
        return instance;
    }

    private OnPermissionRequestListener onPermissionRequestListener;

    public void requestPermissions(int requestCode, OnPermissionRequestListener permissionRequestListener, String... permissions) {
        if (mContext instanceof Activity) {
            if (permissionRequestListener != null) {
                this.requestCode = requestCode;
                this.onPermissionRequestListener = permissionRequestListener;
                if (hasPermission(permissions)) {//已经获取对应权限
                    if (onPermissionRequestListener != null)
                        onPermissionRequestListener.onPermissionGranted();
                } else
                    ActivityCompat.requestPermissions((Activity) mContext, permissions, requestCode);//权限申请

            } else {
                throw new RuntimeException("Context must instanceof Activity");
            }
        }

    }

    /**
     * 请求权限结果，对应BaseActivity中onRequestPermissionsResult()方法。
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != -1 && requestCode == this.requestCode) {
            if (onPermissionRequestListener != null) {
                if (verifyPermissions(grantResults)) {
                    onPermissionRequestListener.onPermissionGranted();
                } else {
                    onPermissionRequestListener.onPermissionDenied();
                }
            }
        }
    }

    /**
     * 权限检查方法
     *
     * @param permissions
     * @return
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    /**
     * 验证回调的权限是否已经授权
     * 回调中包含授权与未授权
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public interface OnPermissionRequestListener {
        void onPermissionGranted();

        void onPermissionDenied();
    }
}
