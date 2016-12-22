package com.david.easyimagepicker.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.david.easyimagepicker.EasyImagePicker;
import com.david.easyimagepicker.R;
import com.david.easyimagepicker.entity.ImageInfo;
import com.david.easyimagepicker.util.PixelUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/22.
 */

public class ImageGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private EasyImagePicker imagePicker;
    private Activity activity;
    private ArrayList<ImageInfo> images;       //当前需要显示的图片数据
    private ArrayList<ImageInfo> mSelectedImages; //全局保存的已经选中的图片数据
    private int diviceWidth; //设备宽度
    private int multipleLimit;//多选最大值,0 为单选

    //构造adapter时只需要传入上下文以及需要显示的数据，其余的数据通过EasyImagePicker中获取
    public ImageGridAdapter(Activity activity, ArrayList<ImageInfo> images) {
        this.images = images;
        this.activity = activity;
        this.imagePicker = EasyImagePicker.getInstance();
        this.mSelectedImages = imagePicker.getSelectedImagesList();
        this.diviceWidth = PixelUtil.getScreenWidth(activity);
        this.multipleLimit = imagePicker.getMultipleLimit();
    }

    public void setImages(ArrayList<ImageInfo> images) {
        this.images = images;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_rv_image_gridview, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //保证gridview显示的图片为正方形
        ViewGroup.LayoutParams params = ((ItemViewHolder) (holder)).iv_thumbnail.getLayoutParams();
        params.width = RecyclerView.LayoutParams.MATCH_PARENT;
        params.height = diviceWidth / imagePicker.getImageWidthSize();
        ((ItemViewHolder) (holder)).iv_thumbnail.setLayoutParams(params);

        imagePicker.getImageLoader().displayImage(activity, images.get(position).getImagePath(),
                ((ItemViewHolder) (holder)).iv_thumbnail, images.get(position).getImageWidth(),
                images.get(position).getIamgeHeidht());

        if (imagePicker.isLoadAnima()) {//读取配置文件，判断是否加载动画
            ((ItemViewHolder) (holder)).iv_thumbnail.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.gf_flip_horizontal_in));
        }

        ((ItemViewHolder) holder).cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return images == null ? 0 : images.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_thumbnail;
        CheckBox cb_check;

        public ItemViewHolder(View itemView) {
            super(itemView);
            iv_thumbnail = (ImageView) itemView.findViewById(R.id.iv_thumbnail);
            cb_check = (CheckBox) itemView.findViewById(R.id.cb_check);
        }
    }
}
