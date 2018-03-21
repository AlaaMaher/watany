package com.magdsoft.watany.helpers;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public interface Pagination {
    RecyclerView.ViewHolder getVHInstance(View view);

    void NotifyChanges();
}
