package com.david.easyimagepicker.entity;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/21.
 */

public class ImageInfo implements Serializable {
    private String imageName;//图片名字
    private int imageWidth;//图片宽度px
    private int iamgeHeidht;//图片高度px
    private String imagePath;//图片路径
    private long imageCreatedTime;//图片创建时间
    private String imageMimeType;   //图片的类型
    private long imageSize;// 图片大小

    public ImageInfo(String imageName, int imageWidth, int iamgeHeidht, String imagePath, long imageCreatedTime, String imageMimeType, long imageSize) {
        this.imageName = imageName;
        this.imageWidth = imageWidth;
        this.iamgeHeidht = iamgeHeidht;
        this.imagePath = imagePath;
        this.imageCreatedTime = imageCreatedTime;
        this.imageMimeType = imageMimeType;
        this.imageSize = imageSize;
    }

    public long getImageSize() {
        return imageSize;
    }

    public void setImageSize(long imageSize) {
        this.imageSize = imageSize;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public long getImageCreatedTime() {
        return imageCreatedTime;
    }

    public void setImageCreatedTime(long imageCreatedTime) {
        this.imageCreatedTime = imageCreatedTime;
    }

    public String getImageMimeType() {
        return imageMimeType;
    }

    public void setImageMimeType(String imageMimeType) {
        this.imageMimeType = imageMimeType;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getIamgeHeidht() {
        return iamgeHeidht;
    }

    public void setIamgeHeidht(int iamgeHeidht) {
        this.iamgeHeidht = iamgeHeidht;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    //TODO: 重写equals方法，为了进入图库时匹对已选择图片
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof ImageInfo)) {
            return false;
        }
        ImageInfo info = (ImageInfo) o;
        if (info == null) {
            return false;
        }
        return TextUtils.equals(info.getImagePath(), getImagePath());
    }
}
