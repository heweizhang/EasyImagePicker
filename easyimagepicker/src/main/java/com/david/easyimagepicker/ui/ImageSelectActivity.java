package com.david.easyimagepicker.ui;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.david.easyimagepicker.BaseImageActivity;
import com.david.easyimagepicker.EasyImagePicker;
import com.david.easyimagepicker.ImageSourceHelper;
import com.david.easyimagepicker.R;
import com.david.easyimagepicker.adapter.FolderListAdapter;
import com.david.easyimagepicker.adapter.ImageGridAdapter;
import com.david.easyimagepicker.view.FolderPopWindow;


/**
 * Created by david on 2016/12/21.
 * GitHub:
 */

public class ImageSelectActivity extends BaseImageActivity implements ImageSourceHelper.ImagesLoaderListener, View.OnClickListener, EasyImagePicker.OnImageSelectedChangedListener {
    private ImageGridAdapter adapter;
    private RecyclerView rv_photoviews;
    private Button btn_preview, btn_ok;
    private RelativeLayout footer_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageselect);

        initView();

        new ImageSourceHelper(this, null, this);//TODO: 获取所有图片资源，读取速度非常快，不算耗时操作，因此直接在主线程中获取
        EasyImagePicker.getInstance().setImageSelectedChangedListener(this);//设置图片选中未选中回调
    }

    private void initView() {
        rv_photoviews = (RecyclerView) findViewById(R.id.rv_photoviews);
        GridLayoutManager layoutManager = new GridLayoutManager(this, EasyImagePicker.getInstance().getImageWidthSize());
        rv_photoviews.setLayoutManager(layoutManager);
        adapter = new ImageGridAdapter(this, null);
        rv_photoviews.setAdapter(adapter);
        btn_preview = (Button) findViewById(R.id.btn_preview);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        footer_bar = (RelativeLayout) findViewById(R.id.footer_bar);

        onImageSelectedChanged();

        findViewById(R.id.btn_dir).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
        btn_preview.setOnClickListener(this);
    }

    @Override
    public void onImagesLoaded() {

        //设置数据
        adapter.setImages(EasyImagePicker.getInstance().getImageFolderList().get(0).getImageInfoList());
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_dir) {
            FolderPopWindow folderPopWindow = new FolderPopWindow(ImageSelectActivity.this, new FolderListAdapter(null, ImageSelectActivity.this));
            folderPopWindow.showAtLocation(footer_bar, Gravity.NO_GRAVITY, 0, 0);
        } else if (id == R.id.btn_preview) {

        } else if (id == R.id.btn_back) {
            finish();
        }

    }

    @Override
    public void onImageSelectedChanged() {
        btn_ok.setText("完成(" + EasyImagePicker.getInstance().getSelectedImagesList().size() + "/" + EasyImagePicker.getInstance().getMultipleLimit() + ")");
        if (EasyImagePicker.getInstance().getSelectedImagesList().size() == 0) {
            btn_preview.setVisibility(View.GONE);
        } else {
            btn_preview.setVisibility(View.VISIBLE);
            btn_preview.setText("预览(" + EasyImagePicker.getInstance().getSelectedImagesList().size() + ")");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO 如何回收内存
        EasyImagePicker.getInstance().getSelectedImagesList().clear();
    }
}
