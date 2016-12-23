package com.david.easyimagepicker.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.david.easyimagepicker.EasyImagePicker;
import com.david.easyimagepicker.R;
import com.david.easyimagepicker.entity.ImageFolder;

import java.util.List;

/**
 * Created by Administrator on 2016/12/22.
 */

public class FolderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ImageFolder> imageFolders;
    private Context context;
    private CustomItemOnClick itemClickListener;
    private EasyImagePicker imagePicker;
    private int currentFolderIndex;//默认选中的是全部图片

    public void setCurrentFolderIndex(int currentFolderIndex) {
        this.currentFolderIndex = currentFolderIndex;
        imagePicker.setCurrentFolderIndex(currentFolderIndex);
        notifyDataSetChanged();
    }


    public FolderListAdapter(Context context, List<ImageFolder> imageFolders) {
        this.imageFolders = imageFolders;
        this.context = context;
        imagePicker = EasyImagePicker.getInstance();
        currentFolderIndex = imagePicker.getCurrentFolderIndex();
    }

    public void setImageFolders(List<ImageFolder> imageFolders) {
        this.imageFolders = imageFolders;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv_folder_listview, parent, false);
        final ItemViewHolder viewHolder = new ItemViewHolder(view);
        if (itemClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(viewHolder.getLayoutPosition());
                }
            });
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageFolder folderBean = imageFolders.get(position);
        imagePicker.getImageLoader().displayImage((Activity) context, folderBean.getFirstImage().getImagePath(),
                ((ItemViewHolder) (holder)).iv_folders_icon, folderBean.getFirstImage().getImageWidth(), folderBean.getFirstImage().getIamgeHeidht());
        ((ItemViewHolder) holder).tv_folder_name.setText(folderBean.getName());
        ((ItemViewHolder) holder).tv_image_count.setText(String.format("共%d张", folderBean.getImageInfoList().size()));
        if (position == currentFolderIndex)
            ((ItemViewHolder) holder).iv_folder_check.setVisibility(View.VISIBLE);
        else
            ((ItemViewHolder) holder).iv_folder_check.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return imageFolders == null ? 0 : imageFolders.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_folders_icon;
        ImageView iv_folder_check;
        TextView tv_folder_name;
        TextView tv_image_count;

        public ItemViewHolder(View itemView) {
            super(itemView);
            iv_folders_icon = (ImageView) itemView.findViewById(R.id.iv_folders_icon);
            iv_folder_check = (ImageView) itemView.findViewById(R.id.iv_folder_check);
            tv_folder_name = (TextView) itemView.findViewById(R.id.tv_folder_name);
            tv_image_count = (TextView) itemView.findViewById(R.id.tv_image_count);
        }
    }

    public void setOnItemClickListener(CustomItemOnClick itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface CustomItemOnClick {
        void onItemClick(int pos);
    }
}
