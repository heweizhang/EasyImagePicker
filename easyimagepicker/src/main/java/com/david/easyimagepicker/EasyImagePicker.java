package com.david.easyimagepicker;

import com.david.easyimagepicker.entity.ImageInfo;

import java.util.List;

/**
 * Created by david on 2016/12/21.
 * 图片选择器的入口，配置文件
 */

public class EasyImagePicker {


    /**
     * 图片选择结果回调
     */
    public static interface ImagePickerResultCallBack{
        /**
         * 处理成功
         * @param reqeustCode
         * @param resultList
         */
        public void onHanlderSuccess(int reqeustCode, List<ImageInfo> resultList);

        /**
         * 处理失败或异常
         * @param requestCode
         * @param errorMsg
         */
        public void onHanlderFailure(int requestCode, String errorMsg);
    }
}
