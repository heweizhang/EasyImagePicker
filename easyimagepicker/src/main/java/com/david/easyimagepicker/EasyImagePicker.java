package com.david.easyimagepicker;

import com.david.easyimagepicker.entity.ImageFolder;
import com.david.easyimagepicker.entity.ImageInfo;
import com.david.easyimagepicker.imageloader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 2016/12/21.
 * 图片选择器的入口，配置文件
 */

public class EasyImagePicker {

    private ArrayList<ImageFolder> imageFolderList = new ArrayList<>();//存放所有文件夹

    private ArrayList<ImageInfo> selectedImagesList = new ArrayList<>();//存放已选中的图片

    private int imageWidthSize = 3;//默认gridview显示图片为3列

    private ImageLoader imageLoader;     //外部传入图片加载器

    private boolean loadAnima = true; //默认加入图片加载动画

    public int getMultipleLimit() {
        return multipleLimit;
    }

    public void setMultipleLimit(int multipleLimit) {
        this.multipleLimit = multipleLimit;
    }

    private int multipleLimit = 0;//多选最大值,传入0：代表单选

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


    public boolean isLoadAnima() {
        return loadAnima;
    }

    public void setLoadAnima(boolean loadAnima) {
        this.loadAnima = loadAnima;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public ArrayList<ImageInfo> getSelectedImagesList() {
        return selectedImagesList;
    }

    public void setSelectedImagesList(ArrayList<ImageInfo> selectedImagesList) {
        this.selectedImagesList = selectedImagesList;
    }


    public int getImageWidthSize() {
        return imageWidthSize;
    }

    public void setImageWidthSize(int imageWidthSize) {
        this.imageWidthSize = imageWidthSize;
    }

    public ArrayList<ImageFolder> getImageFolderList() {
        return imageFolderList;
    }

    public void setImageFolderList(ArrayList<ImageFolder> imageFolderList) {
        this.imageFolderList = imageFolderList;
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
