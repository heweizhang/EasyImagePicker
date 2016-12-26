package com.david.easyimagepickerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.david.easyimagepicker.EasyImagePicker;
import com.david.easyimagepicker.PickerConfig;
import com.david.easyimagepicker.entity.ImageInfo;
import com.david.easyimagepicker.util.LogUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ImageInfo> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化EasyImagePicker
        PickerConfig config = new PickerConfig.Builder(MainActivity.this, new GlideImageLoader())//传入ImageLoader
//                .setAnimRes(0)//传入0为不显示动画，不传显示默认动画，用户也可以传入自定义的动画
                .setLog("test") //默认显示调试log，传入null为不打印
//                .setImageWidthSize(3) //图片选择器显示列数，默认为3列
                .build();
        EasyImagePicker.getInstance().init(config);

        findViewById(R.id.btn_view_images).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEasyImagePicker();
            }
        });
    }

    private final int IMAGEPICKERREQUESTCODE = 100;

    private void openEasyImagePicker() {

        LogUtil.e("info", "list.size():" + list.size());
        //不区分单选多选，传入1即为单选,需要显示初始化已选中图片，需要传入list,或直接传入null
        EasyImagePicker.getInstance().openPicker(IMAGEPICKERREQUESTCODE, 8, list, new EasyImagePicker.ImagePickerResultCallBack() {
            @Override
            public void onHanlderSuccess(int requestCode, ArrayList<ImageInfo> resultList) {
                if (requestCode == IMAGEPICKERREQUESTCODE) {
                    list.clear();
                    list.addAll(resultList);
                    LogUtil.e("info", "list.size():" + list.size());
                    for (ImageInfo i : resultList) {
                        LogUtil.e("info", i.toString());
                    }
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                LogUtil.e("info", errorMsg);
            }
        });
    }

}
