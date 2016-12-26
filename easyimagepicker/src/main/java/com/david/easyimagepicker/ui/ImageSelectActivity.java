package com.david.easyimagepicker.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.david.easyimagepicker.BaseImageActivity;
import com.david.easyimagepicker.EasyImagePicker;
import com.david.easyimagepicker.ImageSourceHelper;
import com.david.easyimagepicker.R;
import com.david.easyimagepicker.adapter.FolderListAdapter;
import com.david.easyimagepicker.adapter.ImageGridAdapter;
import com.david.easyimagepicker.util.LogUtil;
import com.david.easyimagepicker.view.FolderPopWindow;


/**
 * Created by david on 2016/12/21.
 * GitHub:
 */

public class ImageSelectActivity extends BaseImageActivity implements ImageSourceHelper.ImagesLoaderListener, View.OnClickListener, EasyImagePicker.OnImageSelectedChangedListener {
    private RecyclerView rv_photoviews;
    private Button btn_preview, btn_ok, btn_dir;
    private RelativeLayout footer_bar;
    private FolderPopWindow folderPopWindow;
    private ImageGridAdapter adapter;
    private FolderListAdapter foldersAdapter;

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
        GridLayoutManager layoutManager = new GridLayoutManager(this, EasyImagePicker.getInstance().getPickerConfig().getImageWidthSize());
        rv_photoviews.setLayoutManager(layoutManager);
        adapter = new ImageGridAdapter(this, null);
        rv_photoviews.setAdapter(adapter);
        foldersAdapter = new FolderListAdapter(this, null);

        btn_preview = (Button) findViewById(R.id.btn_preview);
        btn_dir = (Button) findViewById(R.id.btn_dir);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        footer_bar = (RelativeLayout) findViewById(R.id.footer_bar);

        onImageSelectedChanged();

        findViewById(R.id.btn_back).setOnClickListener(this);
        btn_preview.setOnClickListener(this);
        btn_dir.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
    }

    @Override
    public void onImagesLoaded() {

        //设置数据
        adapter.setImages(EasyImagePicker.getInstance().getImageFolderList().get(0).getImageInfoList());//默认第一个文件夹即：全部图片
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_dir) {
            if (!isFirstInitPop) {
                initPop();
                isFirstInitPop = true;
            }
            if (folderPopWindow != null && !folderPopWindow.isShowing()) {
                int[] location = new int[2];
                footer_bar.getLocationOnScreen(location);
                LogUtil.e("info", "footer_bar:" + location[0]);
                LogUtil.e("info", "footer_bar:" + location[1]);
                folderPopWindow.showAtLocation(footer_bar, Gravity.NO_GRAVITY, 0,
                        0);
            }

        } else if (id == R.id.btn_preview) {

        } else if (id == R.id.btn_back) {
            finish();
        } else if(id == R.id.btn_ok){
            LogUtil.e(EasyImagePicker.getInstance().getPickerConfig().getLog(),"btn_ok");
            if(EasyImagePicker.getInstance().getResultCallBackListener() != null){
                EasyImagePicker.getInstance().getResultCallBackListener().onHanlderSuccess(EasyImagePicker.getInstance().getImagePickRequestCode(),EasyImagePicker.getInstance().getSelectedImagesList());
                finish();
            }else
                LogUtil.e(EasyImagePicker.getInstance().getPickerConfig().getLog(),"--------------- 未设置回调 ----------------");
        }


    }


    private boolean isFirstInitPop;

    private void initPop() {
        folderPopWindow = new FolderPopWindow(ImageSelectActivity.this, foldersAdapter);

        foldersAdapter.setImageFolders(EasyImagePicker.getInstance().getImageFolderList());
        foldersAdapter.notifyDataSetChanged();
        foldersAdapter.setOnItemClickListener(new FolderListAdapter.CustomItemOnClick() {
            @Override
            public void onItemClick(int pos) {
                foldersAdapter.setCurrentFolderIndex(pos);
                adapter.setImages(EasyImagePicker.getInstance().getImageFolderList().get(pos).getImageInfoList());
                adapter.notifyDataSetChanged();
                btn_dir.setText(EasyImagePicker.getInstance().getImageFolderList().get(pos).getName());
                if (folderPopWindow != null && folderPopWindow.isShowing()) {
                    folderPopWindow.dismiss();
                }
            }
        });
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
