package com.david.easyimagepicker.ui;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.david.easyimagepicker.BaseImageActivity;
import com.david.easyimagepicker.EasyImagePicker;
import com.david.easyimagepicker.R;
import com.david.easyimagepicker.adapter.ImagePagerAdapter;
import com.david.easyimagepicker.entity.ImageInfo;
import com.david.easyimagepicker.util.LogUtil;
import com.david.easyimagepicker.view.ViewPagerFixed;

import java.util.ArrayList;

/**
 * Created by david on 2016/12/26.
 */

public class ImagePreViewActivity extends BaseImageActivity implements EasyImagePicker.OnImageSelectedChangedListener {
    private ImagePagerAdapter pagerAdapter;
    private ViewPagerFixed view_pager;
    private int currentPos;//当前显示图片位置
    private ArrayList<ImageInfo> imageInfos;
    private TextView tv_index, tv_selected;
    private CheckBox cb_check;
    private EasyImagePicker imagePicker;
    private ImageView btn_back;
    private LinearLayout topBar, bottomBar;
    private RelativeLayout view_root;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_imagepreview);
        imagePicker = EasyImagePicker.getInstance();

        view_root = (RelativeLayout) findViewById(R.id.view_root);
        view_pager = (ViewPagerFixed) findViewById(R.id.view_pager);
        tv_index = (TextView) findViewById(R.id.tv_index);
        tv_selected = (TextView) findViewById(R.id.tv_selected);
        cb_check = (CheckBox) findViewById(R.id.cb_check);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        topBar = (LinearLayout) findViewById(R.id.ll_topbar);
        bottomBar = (LinearLayout) findViewById(R.id.ll_bottombar);

        if (getIntent() != null) {
            currentPos = getIntent().getIntExtra("currentPos", 0);
            imageInfos = imagePicker.getImageFolderList().get(getIntent().getIntExtra("folderIndex", 0)).getImageInfoList();
            tv_index.setText((currentPos + 1) + "/" + imageInfos.size());
            setCheckBoxStatus(currentPos);
            pagerAdapter = new ImagePagerAdapter(this, imageInfos);
            view_pager.setAdapter(pagerAdapter);
            view_pager.setCurrentItem(currentPos);
        } else {
            finish();
        }
        initListener();
        onImageSelectedChanged();
    }

    private void initListener() {
        imagePicker.setImageSelectedChangedListener(this);//设置图片选中未选中回调

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK, getIntent());
                finish();
            }
        });

        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPos = position;
                setCheckBoxStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        cb_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.e(imagePicker.getPickerConfig().getLog(), "check onCheckedChanged ");
                if (cb_check.isChecked() && imagePicker.getSelectedImagesList().size() >= imagePicker.getMultipleLimit()) {
                    Toast.makeText(ImagePreViewActivity.this, getString(R.string.select_limit, imagePicker.getMultipleLimit() + ""), Toast.LENGTH_SHORT).show();
                    cb_check.setChecked(false);
                } else {
                    imagePicker.addSelectedImagesList(imageInfos.get(currentPos), cb_check.isChecked());
                }
            }
        });


        pagerAdapter.setPhotoViewOnClickListener(new ImagePagerAdapter.PhotoViewOnClick() {
            @Override
            public void photoViewOnClickListener() {
                showOrHideBars();
            }
        });

    }

    //点击图片，将topBar跟bottomBar隐藏或显示
    private void showOrHideBars() {
        if (topBar.getVisibility() == View.VISIBLE) {
            topBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
            bottomBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
            topBar.setVisibility(View.GONE);
            bottomBar.setVisibility(View.GONE);
//            tintManager.setStatusBarTintResource(R.color.transparent);//通知栏所需颜色
            //给最外层布局加上这个属性表示，Activity全屏显示，且状态栏被隐藏覆盖掉。
/*            if (Build.VERSION.SDK_INT >= 16)
                view_root.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);*/


        } else {
            topBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
            bottomBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
            topBar.setVisibility(View.VISIBLE);
            bottomBar.setVisibility(View.VISIBLE);
//            tintManager.setStatusBarTintResource(R.color.status_bar);//通知栏所需颜色
            //Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住
/*            if (Build.VERSION.SDK_INT >= 16)
                view_root.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);*/
        }

    }

    /**
     * 使状态栏透明 * <p> * 适用于图片作为背景的界面,此时需要图片填充到状态栏 * * @param activity 需要设置的activity
     */
    public static void setTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    private void setCheckBoxStatus(int index) {
        tv_index.setText((index + 1) + "/" + imageInfos.size());
        boolean checked = imagePicker.getSelectedImagesList().contains(imageInfos.get(index));
        if (checked) {
            cb_check.setChecked(true);
        } else {
            cb_check.setChecked(false);
        }
    }

    @Override
    public void onImageSelectedChanged() {
        if (imagePicker.getMultipleLimit() <= 1) {
            tv_selected.setVisibility(View.GONE);
        } else
            tv_selected.setText("已选(" + imagePicker.getSelectedImagesList().size() + "/" + imagePicker.getMultipleLimit() + ")");

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(RESULT_OK, getIntent());
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
