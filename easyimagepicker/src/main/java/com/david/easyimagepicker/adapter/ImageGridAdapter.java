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
import android.widget.Toast;

import com.david.easyimagepicker.EasyImagePicker;
import com.david.easyimagepicker.R;
import com.david.easyimagepicker.entity.ImageInfo;
import com.david.easyimagepicker.util.LogUtil;
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
    public int multipleLimit;//多选最大值,1 为单选
    private CustomItemOnClick customItemOnClick;

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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
         View view = LayoutInflater.from(activity).inflate(R.layout.item_rv_image_gridview, parent, false);
        final ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        if(customItemOnClick != null){
            itemViewHolder.view_mask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customItemOnClick.onItemClick(itemViewHolder.getLayoutPosition());
                }
            });
            itemViewHolder.iv_thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customItemOnClick.onItemClick(itemViewHolder.getLayoutPosition());
                }
            });
        }
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //保证gridview显示的图片为正方形
        ViewGroup.LayoutParams params = ((ItemViewHolder) (holder)).iv_thumbnail.getLayoutParams();
        params.width = RecyclerView.LayoutParams.MATCH_PARENT;
        params.height = diviceWidth / imagePicker.getPickerConfig().getImageWidthSize();
        ((ItemViewHolder) (holder)).iv_thumbnail.setLayoutParams(params);


        if (imagePicker.getPickerConfig().getAnimRes() != 0) {//读取配置文件，判断是否加载动画
            ((ItemViewHolder) (holder)).iv_thumbnail.setAnimation(AnimationUtils.loadAnimation(activity, imagePicker.getPickerConfig().getAnimRes() ));
        }

        ((ItemViewHolder) holder).cb_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (((ItemViewHolder) holder).cb_check.isChecked() && mSelectedImages.size() >= multipleLimit) {
                    Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.select_limit, multipleLimit + ""), Toast.LENGTH_SHORT).show();
                    ((ItemViewHolder) holder).cb_check.setChecked(false);
                    ((ItemViewHolder) holder).view_mask.setVisibility(View.GONE);
                } else {
                    imagePicker.addSelectedImagesList(position, images.get(position), ((ItemViewHolder) holder).cb_check.isChecked());
                }
                if (((ItemViewHolder) holder).cb_check.isChecked())
                    ((ItemViewHolder) holder).view_mask.setVisibility(View.VISIBLE);
                else
                    ((ItemViewHolder) holder).view_mask.setVisibility(View.GONE);
            }
        });

        //单选则隐藏checkBox
        if (multipleLimit == 1) {
            ((ItemViewHolder) holder).cb_check.setVisibility(View.GONE);
        } else {
            ((ItemViewHolder) holder).cb_check.setVisibility(View.VISIBLE);
            boolean checked = mSelectedImages.contains(images.get(position));
            if (checked) {
                ((ItemViewHolder) holder).view_mask.setVisibility(View.VISIBLE);
                ((ItemViewHolder) holder).cb_check.setChecked(true);
            } else {
                ((ItemViewHolder) holder).view_mask.setVisibility(View.GONE);
                ((ItemViewHolder) holder).cb_check.setChecked(false);
            }
        }

        imagePicker.getPickerConfig().getImageLoader().displayImage(activity, images.get(position).getImagePath(),
                ((ItemViewHolder) (holder)).iv_thumbnail, images.get(position).getImageWidth(),
                images.get(position).getIamgeHeidht());
    }

    @Override
    public int getItemCount() {
        return images == null ? 0 : images.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_thumbnail;
        CheckBox cb_check;
        View view_mask;

        public ItemViewHolder(View itemView) {
            super(itemView);
            iv_thumbnail = (ImageView) itemView.findViewById(R.id.iv_thumbnail);
            cb_check = (CheckBox) itemView.findViewById(R.id.cb_check);
            view_mask = (View) itemView.findViewById(R.id.view_mask);
        }
    }

    public void setItemOnClickListener(CustomItemOnClick itemOnClick){
        this.customItemOnClick = itemOnClick;
    }

    public interface CustomItemOnClick{
        void onItemClick(int pos);
    }
}
