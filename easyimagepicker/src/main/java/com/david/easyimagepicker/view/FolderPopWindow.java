package com.david.easyimagepicker.view;

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

/**
 * Created by david on 2016/12/22.
 */

public class FolderPopWindow extends PopupWindow {
    private View view;
    private Context context;

    public FolderPopWindow(Context context, FolderListAdapter folderListAdapter) {
        super(context);
        this.context = context;
        view = View.inflate(context, R.layout.pop_folders_list, null);
        RecyclerView rv_folderview = (RecyclerView) view.findViewById(R.id.rv_folderview);
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

        view.findViewById(R.id.view_empty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        view.setAnimation(AnimationUtils.loadAnimation(context,R.anim.pop_enter_anim));
    }

    @Override
    public void dismiss() {
        view.setAnimation(AnimationUtils.loadAnimation(context,R.anim.pop_exist_anim));
        super.dismiss();
    }
}
