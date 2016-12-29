package com.david.easyimagepicker.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
 * GitHub:https://github.com/HeweiZhang/EasyImagePicker
 * email:17505926606@163.com
 */

public class ImageSelectActivity extends BaseImageActivity implements ImageSourceHelper.ImagesLoaderListener, View.OnClickListener, EasyImagePicker.OnImageSelectedChangedListener {
    private RecyclerView rv_photoviews;
    private Button btn_preview, btn_ok, btn_dir;
    private RelativeLayout footer_bar,topbar;
    private FolderPopWindow folderPopWindow;
    private ImageGridAdapter adapter;
    private FolderListAdapter foldersAdapter;
    private final int REQUEST_GO_PREVIEW = 1111;
    private EasyImagePicker imagePicker;
    private TextView image_loading,tv_des;
    private ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageselect);
        imagePicker = EasyImagePicker.getInstance();
        initView();
        setPickerTheme();

        new ImageSourceHelper(this, null, this);//TODO: 获取所有图片资源，读取速度非常快，不算耗时操作，因此直接在主线程中获取
        imagePicker.setImageSelectedChangedListener(this);//设置图片选中未选中回调
    }

    private void initView() {

        rv_photoviews = (RecyclerView) findViewById(R.id.rv_photoviews);
        GridLayoutManager layoutManager = new GridLayoutManager(this, imagePicker.getPickerConfig().getImageWidthSize());
        rv_photoviews.setLayoutManager(layoutManager);
        adapter = new ImageGridAdapter(this, null);
        rv_photoviews.setAdapter(adapter);
        foldersAdapter = new FolderListAdapter(this, null);

        btn_preview = (Button) findViewById(R.id.btn_preview);
        btn_dir = (Button) findViewById(R.id.btn_dir);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_back = (ImageView)findViewById(R.id.btn_back);
        footer_bar = (RelativeLayout) findViewById(R.id.footer_bar);
        topbar = (RelativeLayout) findViewById(R.id.rl_topbar);
        image_loading = (TextView) findViewById(R.id.tv_image_loading);
        tv_des = (TextView) findViewById(R.id.tv_des);

        onImageSelectedChanged();

        findViewById(R.id.btn_back).setOnClickListener(this);
        btn_preview.setOnClickListener(this);
        btn_dir.setOnClickListener(this);
        btn_ok.setOnClickListener(this);

        adapter.setItemOnClickListener(new ImageGridAdapter.CustomItemOnClick() {
            @Override
            public void onItemClick(int pos) {
                Intent intent = new Intent(ImageSelectActivity.this, ImagePreViewActivity.class);
                intent.putExtra("images", imagePicker.getImageFolderList().get(imagePicker.getCurrentFolderIndex()).getImageInfoList());//当前选中文件夹
                intent.putExtra("currentPos", pos);//当前item位置
                startActivityForResult(intent, REQUEST_GO_PREVIEW);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_GO_PREVIEW && adapter != null)
            adapter.notifyDataSetChanged();
    }

    /**
     * 图片信息加载完成
     */
    @Override
    public void onImagesLoaded() {
        if(imagePicker.getImageFolderList() != null && imagePicker.getImageFolderList().size() != 0){
            image_loading.setVisibility(View.GONE);
            //设置数据
            adapter.setImages(imagePicker.getImageFolderList().get(0).getImageInfoList());//默认第一个文件夹即：全部图片
            adapter.notifyDataSetChanged();
        }else{
            image_loading.setText("抱歉，没有找到照片~");
        }
    }

    /**
     * 根据配置设置主题
     * //TODO
     */
    private void setPickerTheme(){
        btn_back.setImageResource(imagePicker.getPickerConfig().getPickerThmeConfig().getBackBtnIcon());

        tv_des.setTextColor(imagePicker.getPickerConfig().getPickerThmeConfig().getTextColor());
        btn_ok.setTextColor(imagePicker.getPickerConfig().getPickerThmeConfig().getTextColor());
        btn_preview.setTextColor(imagePicker.getPickerConfig().getPickerThmeConfig().getTextColor());
        btn_dir.setTextColor(imagePicker.getPickerConfig().getPickerThmeConfig().getTextColor());

        topbar.setBackgroundResource(imagePicker.getPickerConfig().getPickerThmeConfig().getTopBarBgColor());

        btn_back.setBackgroundResource(imagePicker.getPickerConfig().getPickerThmeConfig().getBackBtnBg());
        btn_ok.setBackgroundResource(imagePicker.getPickerConfig().getPickerThmeConfig().getOkBtnBg());

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
                folderPopWindow.showAtLocation(footer_bar, Gravity.NO_GRAVITY, 0,
                        0);
            }

        } else if (id == R.id.btn_preview) {
            Intent intent = new Intent(ImageSelectActivity.this, ImagePreViewActivity.class);
            intent.putExtra("images", imagePicker.getSelectedImagesList());//预览已选中文件
            intent.putExtra("currentPos", 0);//当前item位置
            startActivityForResult(intent, REQUEST_GO_PREVIEW);
        } else if (id == R.id.btn_back) {
            finish();
        } else if (id == R.id.btn_ok) {
            LogUtil.e(imagePicker.getPickerConfig().getLog(), "btn_ok");
            if (imagePicker.getResultCallBackListener() != null) {
                imagePicker.getResultCallBackListener().onHanlderSuccess(imagePicker.getImagePickRequestCode(), imagePicker.getSelectedImagesList());
                finish();
            } else
                LogUtil.e(imagePicker.getPickerConfig().getLog(), "--------------- 未设置回调 ----------------");
        }


    }


    private boolean isFirstInitPop;

    private void initPop() {
        folderPopWindow = new FolderPopWindow(ImageSelectActivity.this, foldersAdapter);

        foldersAdapter.setImageFolders(imagePicker.getImageFolderList());
        foldersAdapter.notifyDataSetChanged();
        foldersAdapter.setOnItemClickListener(new FolderListAdapter.CustomItemOnClick() {
            @Override
            public void onItemClick(int pos) {
                foldersAdapter.setCurrentFolderIndex(pos);
                adapter.setImages(imagePicker.getImageFolderList().get(pos).getImageInfoList());
                adapter.notifyDataSetChanged();
                btn_dir.setText(imagePicker.getImageFolderList().get(pos).getName());
                if (folderPopWindow != null && folderPopWindow.isShowing()) {
                    folderPopWindow.dismiss();
                }
            }
        });
    }


    @Override
    public void onImageSelectedChanged() {
        btn_ok.setText("完成(" + imagePicker.getSelectedImagesList().size() + "/" + imagePicker.getMultipleLimit() + ")");
        if (imagePicker.getSelectedImagesList().size() == 0) {
            btn_preview.setVisibility(View.GONE);
        } else {
            btn_preview.setVisibility(View.VISIBLE);
            btn_preview.setText("预览(" + imagePicker.getSelectedImagesList().size() + ")");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //TODO 如何回收内存
        imagePicker.getSelectedImagesList().clear();
        imagePicker.setCurrentFolderIndex(0);
    }
}
