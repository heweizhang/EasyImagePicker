package com.david.easyimagepicker;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.david.easyimagepicker.entity.ImageFolder;
import com.david.easyimagepicker.entity.ImageInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 2016/12/21.
 * 加载本地图库
 */

public class ImageSourceHelper implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int LOAD_ALL = 0; //默认项：加载所有图片
    public static final int LOAD_CATEGORY = 1;//分类加载图片
    private final String[] IMAGE_PROJECTION = {//对应系统图库数据库的数据列
            MediaStore.Images.Media.DISPLAY_NAME,   //imageName
            MediaStore.Images.Media.DATA,           //imagePath  /storage/emulated/0/...
            MediaStore.Images.Media.SIZE,           //imageSize，long型
            MediaStore.Images.Media.WIDTH,          //imageWidth，int型
            MediaStore.Images.Media.HEIGHT,         //imageHeight，int型
            MediaStore.Images.Media.MIME_TYPE,      //imageMimeType  image/jpeg
            MediaStore.Images.Media.DATE_ADDED
    };

    private FragmentActivity activity;
    private ImagesLoaderListener imagesLoaderListener;
    private ArrayList<ImageFolder> imageFolders = new ArrayList<>();   //所有的图片文件夹

    /**
     * @param activity             兼容低版本，所以使用v4包
     * @param folderPath           默认为null:即加载所有图片
     * @param imagesLoaderListener 图片加载回调
     */
    public ImageSourceHelper(FragmentActivity activity, String folderPath, ImagesLoaderListener imagesLoaderListener) {
        this.activity = activity;
        this.imagesLoaderListener = imagesLoaderListener;

        //使用Loader来获取图片信息
        LoaderManager loaderManager = activity.getSupportLoaderManager();
        if (folderPath == null) {
            loaderManager.initLoader(LOAD_ALL, null, this);//通过调用LoaderManagr的initLoader()方法，创建一个Loader
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("path", folderPath);
            loaderManager.initLoader(LOAD_CATEGORY, null, this);
        }
    }


    /**
     * 我们要在onCreateLoader()方法内返回一个Loader的实例对象。很多情况下，我们需要查询ContentProvider里面的内容，
     * 那么我们就需要在onCreateLoader中返回一个CursorLoader的实例，CursorLoader继承自Loader。当然，如果CursorLoader
     * 不能满足我们的需求，我们可以自己编写自己的Loader然后在此onCreateLoader方法中返回
     *
     * @param id
     * @param args
     * @return
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = null;
        if (id == LOAD_ALL)
            loader = new CursorLoader(activity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION, null, null, IMAGE_PROJECTION[6] + " DESC");
        else
            loader = new CursorLoader(activity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION, IMAGE_PROJECTION[1] + " like '%" + args.getString("path") + "%'", null, IMAGE_PROJECTION[6] + " DESC");

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            imageFolders.clear();
            ArrayList<ImageInfo> imageInfos = new ArrayList<>();//用来存放所有图片，构造默认文件夹：所有图片
            while (data.moveToNext()) {

                // String imageName, int imageWidth, int iamgeHeidht, String imagePath, long imageCreatedTime, String imageMimeType, long imageSize)
                ImageInfo imageInfo = new ImageInfo(
                        data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0])),
                        data.getInt(data.getColumnIndexOrThrow(IMAGE_PROJECTION[3])),
                        data.getInt(data.getColumnIndexOrThrow(IMAGE_PROJECTION[4])),
                        data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1])),
                        data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[6])),
                        data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[5])),
                        data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]))
                );
                imageInfos.add(imageInfo);

                //根据父路径分类存放图片
                File imageParentFile = new File(data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]))).getParentFile();
                ImageFolder imageFolder = new ImageFolder();
                imageFolder.setName(imageParentFile.getName());
                imageFolder.setPath(imageParentFile.getAbsolutePath());//获取真实路径
                if (imageFolders.contains(imageFolder)) {//如果已经添加了该文件夹，则直接将图片写入该文件夹的图片集合中
                    imageFolders.get(imageFolders.indexOf(imageFolder)).getImageInfoList().add(imageInfo);
                } else {//没有则将文件夹添加到文件夹集合中,其中每个文件夹包含一个  ArrayList<ImageInfo>
                    ArrayList<ImageInfo> infos = new ArrayList<>();
                    infos.add(imageInfo);
                    imageFolder.setImageInfoList(infos);
                    imageFolder.setFirstImage(imageInfo);
                    imageFolders.add(imageFolder);
                }
            }

            if(data.getCount() > 0 ){
                //构造默认文件夹集合，即：所有图片
                ImageFolder folderAll = new ImageFolder();
                folderAll.setName(activity.getResources().getString(R.string.all_images));
                folderAll.setPath("/");
                folderAll.setFirstImage(imageInfos.get(0));
                folderAll.setImageInfoList(imageInfos);
                imageFolders.add(0,folderAll);//放在首位
            }

            //完成加载任务，开始回调
            //TODO:将数据存到内存中
            imagesLoaderListener.onImagesLoaded(imageFolders);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    public interface ImagesLoaderListener {
        void onImagesLoaded(List<ImageFolder> imageInfoList);
    }
}
