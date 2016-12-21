package com.david.easyimagepicker.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/21.
 */

public class ImageFolder implements Serializable {
    private String name;//文件夹名字
    private String path;//文件夹路径
    private ImageInfo firstImage;//文件夹图标;默认该文件夹下首张
    private ArrayList<ImageInfo> imageInfoList;//该文件夹下图片集合

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ImageInfo getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(ImageInfo firstImage) {
        this.firstImage = firstImage;
    }

    public ArrayList<ImageInfo> getImageInfoList() {
        return imageInfoList;
    }

    public void setImageInfoList(ArrayList<ImageInfo> imageInfoList) {
        this.imageInfoList = imageInfoList;
    }

    /** 文件夹的路径和名字都相同，就认为是相同的文件夹 */
    @Override
    public boolean equals(Object o) {
        try {
            ImageFolder other = (ImageFolder) o;
            return this.path.equalsIgnoreCase(other.path) && this.name.equalsIgnoreCase(other.name);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}
