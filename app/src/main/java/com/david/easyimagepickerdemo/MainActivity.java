package com.david.easyimagepickerdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.david.easyimagepicker.EasyImagePicker;
import com.david.easyimagepicker.PickerConfig;
import com.david.easyimagepicker.PickerThmeConfig;
import com.david.easyimagepicker.entity.ImageInfo;
import com.david.easyimagepicker.util.LogUtil;
import com.david.easyimagepicker.util.PermissionUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private ArrayList<ImageInfo> list = new ArrayList<>();
    private TextView tv_show_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_show_result = (TextView) findViewById(R.id.tv_show_result);
        findViewById(R.id.btn_view_images).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEasyImagePicker();
            }
        });

        initEasyImagePicker();
    }

    private void initEasyImagePicker() {
        //初始化EasyImagePicker,必须调用一次，可在AppLication中初始化,也可以在普通Activity或fragment中
        //多次初始化，配置为覆盖操作，即最后一次的配置生效
        // 自定义EasyImagePicker主题
        PickerThmeConfig thmeConfig = new PickerThmeConfig.Builder()
                .setTitleBarBgColor(R.color.theme_color)//标题栏，底部背景
                .setBackBtnIcon(R.mipmap.ic_launcher)//标题栏返回按钮图标
                .setOkBtnBg(R.drawable.selector_image_ok)//标题栏确定按钮背景
                .setTextColor(Color.parseColor("#ffffff"))//不能使用R.color...的方式
                .setPartingLineColor(Color.parseColor("#ffffff"))//标题栏分竖直割线颜色
                .build();

        PickerConfig config = new PickerConfig.Builder(this, new GlideImageLoader())//传入ImageLoader
//                .setAnimRes(0)//传入0为不显示动画，不传显示默认动画，用户也可以传入自定义的动画id
                .setLog("test") //默认显示调试log，传入null为不打印
                .setImageWidthSize(3) //图片选择器显示列数，默认为3列
//                .setPickerThmeConfig(thmeConfig)//传入自定义主题,不传入使用默认主题（仿微信）
                .build();
        //初始化EasyImagePicker
        EasyImagePicker.getInstance().init(config);
    }

    private final int IMAGEPICKERREQUESTCODE = 100;

    private void openEasyImagePicker() {

        //不区分单选多选，传入1即为单选,需要显示初始化已选中图片，需要传入list,或直接传入null
        EasyImagePicker.getInstance().openPicker(IMAGEPICKERREQUESTCODE, 1, null, new EasyImagePicker.ImagePickerResultCallBack() {
            @Override
            public void onHanlderSuccess(int requestCode, ArrayList<ImageInfo> resultList) {
                if (requestCode == IMAGEPICKERREQUESTCODE) {
                    list.clear();
                    list.addAll(resultList);
                    StringBuilder sb = new StringBuilder();

                    for (ImageInfo i : resultList) {
                        sb.append(i.getImagePath() + "\n");
                    }
                    tv_show_result.setText(sb.toString());
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                LogUtil.e("info", errorMsg);
            }
        });
    }


}
