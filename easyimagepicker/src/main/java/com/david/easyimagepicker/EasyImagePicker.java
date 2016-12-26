package com.david.easyimagepicker;

import android.content.Intent;

import com.david.easyimagepicker.entity.ImageFolder;
import com.david.easyimagepicker.entity.ImageInfo;
import com.david.easyimagepicker.imageloader.ImageLoader;
import com.david.easyimagepicker.ui.ImageSelectActivity;
import com.david.easyimagepicker.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 2016/12/21.
 * 图片选择器的核心类
 */

public class EasyImagePicker {

    private PickerConfig pickerConfig;

    public PickerConfig getPickerConfig() {
        return pickerConfig;
    }

    private ArrayList<ImageFolder> imageFolderList = new ArrayList<>();//存放所有文件夹

    private ArrayList<ImageInfo> selectedImagesList = new ArrayList<>();//存放已选中的图片

    private int currentFolderIndex = 0;//默认选中文件夹：全部文件

    private String log = "easyimagepicker";//默认log

    private OnImageSelectedChangedListener onImageSelectedChangedListener;//图片选中情况回调

    public int getImagePickRequestCode() {
        return imagePickRequestCode;
    }

    public ImagePickerResultCallBack getResultCallBackListener() {
        return resultCallBackListener;
    }

    private ImagePickerResultCallBack resultCallBackListener;

    private int imagePickRequestCode;

    public void setMultipleLimit(int multipleLimit) {
        this.multipleLimit = multipleLimit;
    }

    public static int getMultipleLimit() {
        return multipleLimit;
    }

    private static int multipleLimit = 5;//多选最大值,传入1：代表单选

    private static int animRes = R.anim.gf_flip_horizontal_in;

    private static EasyImagePicker instance;

    public static EasyImagePicker getInstance() {
        if (instance == null) {
            synchronized (EasyImagePicker.class) {
                if (instance == null) {
                    instance = new EasyImagePicker();
                }
            }
        }
        return instance;
    }

    public void init(PickerConfig config) {

        pickerConfig = config;

    }

    public void openPicker(int requestCallBackCode, int multipleLimit, ImagePickerResultCallBack resultCallBack) {
        this.multipleLimit = multipleLimit;
        resultCallBackListener = resultCallBack;
        imagePickRequestCode = requestCallBackCode;
        if (getPickerConfig().getImageLoader() == null) {
            LogUtil.e(log, "imageLoader == null");
            resultCallBack.onHanlderFailure(requestCallBackCode, "please init easyImagePicker!");
            return;
        }
        //TODO:6.0权限动态申请
        getPickerConfig().getContext().startActivity(new Intent(getPickerConfig().getContext(), ImageSelectActivity.class));

    }

    public ArrayList<ImageInfo> getSelectedImagesList() {
        return selectedImagesList;
    }

    public int getCurrentFolderIndex() {
        return currentFolderIndex;
    }

    public void setCurrentFolderIndex(int currentFolderIndex) {
        this.currentFolderIndex = currentFolderIndex;
    }

    public void setSelectedImagesList(ArrayList<ImageInfo> selectedImagesList) {
        this.selectedImagesList = selectedImagesList;
    }

    public void addSelectedImagesList(int position, ImageInfo item, boolean isAdd) {
        if (isAdd)
            selectedImagesList.add(item);
        else
            selectedImagesList.remove(item);
        if (onImageSelectedChangedListener != null) {
            onImageSelectedChangedListener.onImageSelectedChanged();
        }
    }

    public ArrayList<ImageFolder> getImageFolderList() {
        return imageFolderList;
    }

    public void setImageFolderList(ArrayList<ImageFolder> imageFolderList) {
        this.imageFolderList = imageFolderList;
    }

    //图片选中回调
    public interface OnImageSelectedChangedListener {
        void onImageSelectedChanged();
    }

    public void setImageSelectedChangedListener(OnImageSelectedChangedListener onImageSelectedChangedListener) {
        this.onImageSelectedChangedListener = onImageSelectedChangedListener;
    }

    /**
     * 图片选择结果回调
     */
    public static interface ImagePickerResultCallBack {
        /**
         * 处理成功
         *
         * @param reqeustCode
         * @param resultList
         */
        public void onHanlderSuccess(int reqeustCode, List<ImageInfo> resultList);

        /**
         * 处理失败或异常
         *
         * @param requestCode
         * @param errorMsg
         */
        public void onHanlderFailure(int requestCode, String errorMsg);
    }
}
