package com.david.easyimagepicker.view;

import android.os.Bundle;
import android.util.Log;

import com.david.easyimagepicker.BaseImageActivity;
import com.david.easyimagepicker.ImageSourceHelper;
import com.david.easyimagepicker.R;
import com.david.easyimagepicker.entity.ImageFolder;
import com.david.easyimagepicker.entity.ImageInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/12/21.
 */

public class ImageSelectActivity extends BaseImageActivity implements ImageSourceHelper.ImagesLoaderListener {
    private long initBegin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageselect);
        initBegin = System.currentTimeMillis();
        new ImageSourceHelper(this, null, this);

    }

    @Override
    public void onImagesLoaded(List<ImageFolder> imageInfoList) {
        Log.e("info", "加载完成，耗时：" + (System.currentTimeMillis() - initBegin));

        for (ImageFolder i : imageInfoList) {
        Log.e("info", "文件夹：" + i.getPath());
            for (ImageInfo info : i.getImageInfoList()) {
                Log.e("info", info.getImageName() + " : " + info.getImagePath());
            }
        }
    }
}
