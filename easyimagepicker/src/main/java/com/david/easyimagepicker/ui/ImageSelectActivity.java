package com.david.easyimagepicker.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.david.easyimagepicker.BaseImageActivity;
import com.david.easyimagepicker.EasyImagePicker;
import com.david.easyimagepicker.ImageSourceHelper;
import com.david.easyimagepicker.R;
import com.david.easyimagepicker.adapter.ImageGridAdapter;


/**
 * Created by david on 2016/12/21.
 * GitHub:
 */

public class ImageSelectActivity extends BaseImageActivity implements ImageSourceHelper.ImagesLoaderListener, View.OnClickListener {
    private ImageGridAdapter adapter;
    private RecyclerView rv_photoviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageselect);

        initView();
        new ImageSourceHelper(this, null, this);//TODO: 获取所有图片资源，读取速度非常快，不算耗时操作，因此直接在主线程中获取

    }

    private void initView() {
        rv_photoviews = (RecyclerView) findViewById(R.id.rv_photoviews);
        GridLayoutManager layoutManager = new GridLayoutManager(this, EasyImagePicker.getInstance().getImageWidthSize());
        rv_photoviews.setLayoutManager(layoutManager);
        adapter = new ImageGridAdapter(this, null);
        rv_photoviews.setAdapter(adapter);

        findViewById(R.id.btn_dir).setOnClickListener(this);
    }

    @Override
    public void onImagesLoaded() {

        adapter.setImages(EasyImagePicker.getInstance().getImageFolderList().get(0).getImageInfoList());
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_dir) {

        }

    }
}
