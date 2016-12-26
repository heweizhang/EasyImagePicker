package com.david.easyimagepicker.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.david.easyimagepicker.BaseImageActivity;
import com.david.easyimagepicker.EasyImagePicker;
import com.david.easyimagepicker.R;
import com.david.easyimagepicker.adapter.ImagePagerAdapter;
import com.david.easyimagepicker.entity.ImageInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/26.
 */

public class ImagePreViewActivity extends BaseImageActivity {
    private ImagePagerAdapter pagerAdapter;
    private ViewPager view_pager;
    private int currentPos;//当前显示图片位置
    private int folderIndex;//当前文件夹index
    private ArrayList<ImageInfo> imageInfos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagepreview);
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        if (getIntent() != null) {
            currentPos = getIntent().getIntExtra("currentPos", 0);
            imageInfos = EasyImagePicker.getInstance().getImageFolderList().get(getIntent().getIntExtra("folderIndex", 0)).getImageInfoList();
            pagerAdapter = new ImagePagerAdapter(this,imageInfos);
            view_pager.setAdapter(pagerAdapter);
        }
    }
}
