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

    private List<OnImageSelectedChangedListener> onImageSelectedChangedListenerList = new ArrayList<>();//图片选中情况回调集合，多处需要此回调，不用集合会导致监听被覆盖

    public int getImagePickRequestCode() {
        return imagePickRequestCode;
    }

    public ImagePickerResultCallBack getResultCallBackListener() {
        return resultCallBackListener;
    }

    private ImagePickerResultCallBack resultCallBackListener;

    private int imagePickRequestCode;

    private static int multipleLimit = 1;//多选最大值,传入1：代表单选

    public static int getMultipleLimit() {
        return multipleLimit;
    }


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

    public void openPicker(int requestCallBackCode, int multipleLimit, ArrayList<ImageInfo> selectedImagesList, ImagePickerResultCallBack resultCallBack) {
        if (null == resultCallBack) {
            LogUtil.e(getPickerConfig().getLog(), "please init the resultCallBack");
            return;
        } else
            resultCallBackListener = resultCallBack;

        if (null == getPickerConfig()) {
            LogUtil.e(getPickerConfig().getLog(), "pickerConfig is null,please init it in the first");
            resultCallBack.onHanlderFailure(requestCallBackCode, "please init easyImagePicker!");
            return;
        }

        if (multipleLimit < 1) {
            resultCallBack.onHanlderFailure(requestCallBackCode, "multipleLimit must not less than 1");
            return;
        } else
            this.multipleLimit = multipleLimit;

        if (null != selectedImagesList) {
            if (selectedImagesList.size() > multipleLimit) {
                LogUtil.e(getPickerConfig().getLog(), "the input selectedImagesList.size() must not less than multipleLimit");
            } else {
                this.selectedImagesList.clear();
                this.selectedImagesList.addAll(selectedImagesList);
                LogUtil.e(getPickerConfig().getLog(), "the selectedImagesList init succeed" + selectedImagesList.size());
            }

        }
        imagePickRequestCode = requestCallBackCode;

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

    public void addSelectedImagesList(ImageInfo item, boolean isAdd) {
        if (isAdd)
            selectedImagesList.add(item);
        else
            selectedImagesList.remove(item);
        if (onImageSelectedChangedListenerList != null && onImageSelectedChangedListenerList.size() != 0) {
            for (OnImageSelectedChangedListener listener : onImageSelectedChangedListenerList) {
                listener.onImageSelectedChanged();
            }
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
        this.onImageSelectedChangedListenerList.add(onImageSelectedChangedListener);
    }

    /**
     * 图片选择结果回调
     */
    public static interface ImagePickerResultCallBack {
        /**
         * 处理成功
         *
         * @param requestCode
         * @param resultList
         */
        public void onHanlderSuccess(int requestCode, ArrayList<ImageInfo> resultList);

        /**
         * 处理失败或异常
         *
         * @param requestCode
         * @param errorMsg
         */
        public void onHanlderFailure(int requestCode, String errorMsg);
    }
}
