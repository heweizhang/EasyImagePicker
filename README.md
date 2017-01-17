# EasyImagePicker
网上开源的图片选择器已经很多了，用起来方便，但直接使用却不利于新手的学习，并且用第三方项目你怎么知道哪天会出现一个莫名其妙的bug，是不是该尝试着自己写一个？  

#### 先来看看我们可能碰到的需求：
1、 项目中需要做一个多图上传的功能，需要用到多图选择器，上传原图/压缩后，可以预览大图  
2、 单选:上传用户头像：大部分的头像功能应该都带两种方式：1、拍照，2、图库选择，用于头像的应该都需要裁剪（圆形裁剪，矩形裁剪），压缩成指定大小  
3、 产品经理脑袋一热，我们平台的图片应该带上自家的水印？这样才是高大上  

###### TODO： 
PreViewActivity viewpager 滑动至边界后，图片大小缩放至初始化大小  
EasyImagePicker如何回收？  

#### 已完成功能：
使用建造者模式，可以轻松配置主题  
单图，多图选择，预览  
沉浸式顶栏  
6.0权限动态申请


#### 如何使用
###### 初始化图片选择器：  
使用建造者模式来编写PickerConfig，配置起来，更加方便  
```
//初始化EasyImagePicker,必须调用一次，可在AppLication中初始化,也可以在普通Activity或fragment中
//多次初始化，配置为覆盖操作，即最后一次的配置生效
//自定义EasyImagePicker主题
PickerThemeConfig themeConfig = new PickerThemeConfig.Builder()
    .setTitleBarBgColor(R.color.theme_color)//标题栏，底部背景
    .setBackBtnIcon(R.mipmap.ic_launcher)//标题栏返回按钮图标
    .setBackBtnBg(R.drawable.selector_image_select_back)//标题栏返回按钮图标背景
    .setOkBtnBg(R.drawable.selector_image_ok)//标题栏确定按钮背景
    .setTextColor(Color.parseColor("#ffffff"))//不能使用R.color...的方式
    .setPartingLineColor(Color.parseColor("#ffffff"))//标题栏分竖直割线颜色
    .build();

PickerConfig config = new PickerConfig.Builder(this, new GlideImageLoader())//传入ImageLoader
    .setAnimRes(0)//传入0为不显示动画，不传显示默认动画，用户也可以传入自定义的动画id
    .setLog("test") //默认显示调试log，传入null为不打印
    .setImageWidthSize(3) //图片选择器显示列数，默认为3列
    .setPickerTehmeConfig(themeConfig)//传入自定义主题,不传入使用默认主题（仿微信）
    .build();
//初始化EasyImagePicker
EasyImagePicker.getInstance().init(config);

```

###### 调起图片选择器：
```

 //多选，需要显示初始化已选中图片，需要传入list,或直接传入null
EasyImagePicker.getInstance().openPicker(IMAGEPICKERREQUESTCODE, 8, list, new EasyImagePicker.ImagePickerResultCallBack() {
     @Override
     public void onHanlderSuccess(int requestCode, ArrayList<ImageInfo> resultList) {
         LogUtil.e("info", resultList.size());
     }

    @Override
    public void onHanlderFailure(int requestCode, String errorMsg) {
          LogUtil.e("info", errorMsg);
    }
});

//单选
    private void openEasyImagePickerSelectOne(){
        //不区分单选多选，传入1即为单选,需要显示初始化已选中图片，需要传入list,或直接传入null
        EasyImagePicker.getInstance().openPicker(IMAGEPICKERREQUESTCODE, new EasyImagePicker.ImagePickerResultCallBack() {
            @Override
            public void onHanlderSuccess(int requestCode, ArrayList<ImageInfo> resultList) {
                if (requestCode == IMAGEPICKERREQUESTCODE) {
                    tv_show_result.setText(resultList.get(0).getImagePath().toString());
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                LogUtil.e("info", errorMsg);
            }
        });
    }
```
