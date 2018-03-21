package com.magdsoft.watany.helpers;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.magdsoft.watany.R;

public class AdapterPaginationHelper {
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_ITEM_LOAD = 1;
    private static final int VIEW_TYPE_ITEM_CLICK = 2;
    public boolean mIsClicked = true;
    public boolean mLoadMore = true;

    public RecyclerView.ViewHolder getHolderView(ViewGroup parent, int viewType, View mainView, Pagination adapter) {
        if (viewType == VIEW_TYPE_ITEM) {
            return adapter.getVHInstance(mainView);
        } else if (viewType == VIEW_TYPE_ITEM_LOAD) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_progress, parent, false);
            return new ProgressLoading(itemView);
        } else if (viewType == VIEW_TYPE_ITEM_CLICK) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading_click, parent, false);
            return new LoadClickedHolder(itemView, adapter);
        }
        return null;

    }

    public int getItemCount(int page, int ListSize) {
        if (page == 0) return ListSize + VIEW_TYPE_ITEM_LOAD;
        return ListSize + (mLoadMore ? VIEW_TYPE_ITEM_LOAD : VIEW_TYPE_ITEM);

    }

    public int getItemViewType(int page, int ListSize, int position) {
        if (page == 0) return position >= ListSize ? VIEW_TYPE_ITEM_LOAD : VIEW_TYPE_ITEM;
        return position >= ListSize ? (mIsClicked ? VIEW_TYPE_ITEM_CLICK : VIEW_TYPE_ITEM_LOAD) : VIEW_TYPE_ITEM;
    }

    public static class ProgressLoading extends RecyclerView.ViewHolder {
        public final ProgressBar progressBar;

        public ProgressLoading(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

    public class LoadClickedHolder extends RecyclerView.ViewHolder {

        public TextView load;

        private LoadClickedHolder(View itemView, final Pagination mParent) {
            super(itemView);
            load = (TextView) itemView.findViewById(R.id.tv_loadmore);
            load.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIsClicked = false;
                    mParent.NotifyChanges();
                }
            });
        }

    }


}

