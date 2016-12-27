package com.david.easyimagepicker.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.david.easyimagepicker.EasyImagePicker;
import com.david.easyimagepicker.entity.ImageInfo;
import com.david.easyimagepicker.imageloader.ImageLoader;
import com.david.easyimagepicker.util.LogUtil;
import com.david.easyimagepicker.util.PixelUtil;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Administrator on 2016/12/26.
 */

public class ImagePagerAdapter extends PagerAdapter {

    private ImageLoader imageLoader;
    private ArrayList<ImageInfo> images = new ArrayList<>();
    private Activity activity;
    private int screenWidth;
    private int screenHeight;
    private PhotoViewOnClick onClickListener;

    public ImagePagerAdapter(Activity activity, ArrayList<ImageInfo> images) {
        this.images = images;
        this.activity = activity;
        this.imageLoader = EasyImagePicker.getInstance().getPickerConfig().getImageLoader();
        this.screenWidth = PixelUtil.getScreenWidth(activity);
        this.screenHeight = PixelUtil.getScreenHeight(activity);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(activity);
        ImageInfo imageBean = images.get(position);
        imageLoader.displayImage(activity, imageBean.getImagePath(), photoView, screenWidth, screenHeight);
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                if(onClickListener != null){
                    onClickListener.photoViewOnClickListener();
                }
            }
        });
        container.addView(photoView);
        return photoView;
    }

    @Override
    public int getCount() {
        return images == null ? 0 : images.size();
    }

    @Override
    public int getItemPosition(Object object) {
        LogUtil.e(EasyImagePicker.getInstance().getPickerConfig().getLog(), " getItemPosition");
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void setPhotoViewOnClickListener(PhotoViewOnClick onClickListener){
        this.onClickListener = onClickListener;
    }

    public interface PhotoViewOnClick{
        void photoViewOnClickListener();
    }
}
