package com.david.easyimagepicker.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;

import com.david.easyimagepicker.R;
import com.david.easyimagepicker.adapter.FolderListAdapter;
import com.david.easyimagepicker.util.PixelUtil;

/**
 * Created by david on 2016/12/22.
 */

public class FolderPopWindow extends PopupWindow {


    private RecyclerView rv_folderview;

    public FolderPopWindow(Context context, FolderListAdapter folderListAdapter) {
        super(context);

        View view = View.inflate(context, R.layout.pop_folders_list, null);
        rv_folderview = (RecyclerView) view.findViewById(R.id.rv_folderview);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_folderview.setLayoutManager(manager);
        rv_folderview.setAdapter(folderListAdapter);

        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);  //如果不设置，就是 AnchorView 的宽度
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(0));
        setAnimationStyle(0);
        view.setAnimation(AnimationUtils.loadAnimation(context, R.anim.gf_flip_horizontal_in));

        //ListView 高度不满屏幕高度5/8时 高度为listView高度
    /*    ViewGroup.LayoutParams listParams = rv_folderview.getLayoutParams();
        listParams.height = rv_folderview.getHeight() > PixelUtil.getScreenHeight((Activity) context) * 3 / 8  ? PixelUtil.getScreenHeight((Activity) context) * 3 / 8 : rv_folderview.getHeight();
        listParams.width = RecyclerView.LayoutParams.MATCH_PARENT;
        rv_folderview.setLayoutParams(listParams);*/


    }
}
