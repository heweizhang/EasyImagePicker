package com.david.easyimagepicker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.david.easyimagepicker.R;
import com.david.easyimagepicker.entity.ImageFolder;

import java.util.List;

/**
 * Created by Administrator on 2016/12/22.
 */

public class FolderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ImageFolder> imageFolders;
    private Context context;

    public FolderListAdapter(List<ImageFolder> imageFolders, Context context) {
        this.imageFolders = imageFolders;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv_folder_listview,parent,false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 7;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
