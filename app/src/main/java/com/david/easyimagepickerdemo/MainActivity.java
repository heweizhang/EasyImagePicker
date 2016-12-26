package com.david.easyimagepickerdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.david.easyimagepicker.EasyImagePicker;
import com.david.easyimagepicker.PickerConfig;
import com.david.easyimagepicker.entity.ImageInfo;
import com.david.easyimagepicker.util.LogUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btn_view_images).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEasyImagePicker();
            }
        });
    }

    private final int IMAGEPICKERREQUESTCODE = 100;
    private void openEasyImagePicker() {
        //初始化EasyImagePicker
        PickerConfig config = new PickerConfig.Builder(MainActivity.this, new GlideImageLoader())//传入ImageLoader
//                .setAnimRes(0)//传入0为不显示动画，不传显示默认动画，用户也可以传入自定义的动画
//                .setLog(null) //默认显示调试log，传入null为不打印
//                .setImageWidthSize(3) //图片选择器显示列数，默认为3列
                .build();
        EasyImagePicker.getInstance().init(config);

        //不区分单选多选，传入1即为单选
        EasyImagePicker.getInstance().openPicker(IMAGEPICKERREQUESTCODE,10, new EasyImagePicker.ImagePickerResultCallBack() {
            @Override
            public void onHanlderSuccess(int requestCode, List<ImageInfo> resultList) {
                if (requestCode == IMAGEPICKERREQUESTCODE ){
                    for(ImageInfo i:resultList){
                        LogUtil.e("info",i.toString());
                    }
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        });
    }

}
