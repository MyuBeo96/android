package com.fss.mobiletrading.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.fss.mobiletrading.function.pastplaceorder.PastPlaceOrderItem;
import com.fss.mobiletrading.function.pastplaceorder.PastPlaceOrderItemView;
import com.tcscuat.mobiletrading.design.FilterArrayAdapter;


import java.util.List;

public class PastPlaceOrderAdapter extends
        FilterArrayAdapter<PastPlaceOrderItem> {
    Context context;
    List<PastPlaceOrderItem> list;
    final static int NO_ITEM_EXPANDED = -1;
    private int expandPosition = NO_ITEM_EXPANDED;

    public PastPlaceOrderAdapter(Context paramContext, int paramInt,
                                 List<PastPlaceOrderItem> paramList) {
        super(paramContext, paramInt, paramList);
        this.context = paramContext;
        this.list = paramList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new PastPlaceOrderItemView(context);
        }
        boolean expand = (position == expandPosition);
        ((PastPlaceOrderItemView) convertView).setListView(
                (PastPlaceOrderItem) filteredData.get(position), expand);

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        expandPosition = NO_ITEM_EXPANDED;
    }

    public void setExpandPosition(int expandPosition) {
        if (this.expandPosition != expandPosition) {
            this.expandPosition = expandPosition;
        } else {
            this.expandPosition = NO_ITEM_EXPANDED;
        }
        super.notifyDataSetChanged();
    }
}
