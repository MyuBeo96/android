package com.fss.mobiletrading.function.pastplaceorder;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fss.mobiletrading.function.placeorder.PlaceOrder;
import com.fscuat.mobiletrading.R;

public class PastPlaceOrderItemView extends LinearLayout {
    TextView tv_Gia;
    TextView tv_MaCk;
    TextView tv_Ngay;
    TextView tv_Side;
    TextView tv_Soluong;
    TextView tv_Gio;
    TextView tv_FloorTrade;
    TextView tv_LoaiLenh;
    TextView tv_ChannelTrade;
    TextView tv_Status;
    TextView tv_Via;
    TextView tv_Status1;
    ViewGroup vg_More;
    int buycolor;
    int sellcolor;
    public boolean isexpand = false;

    public PastPlaceOrderItemView(Context paramContext) {
        super(paramContext);
        ((LayoutInflater) paramContext
                .getSystemService(Service.LAYOUT_INFLATER_SERVICE)).inflate(
                R.layout.pastplaceorder_item, this);
        tv_Ngay = ((TextView) findViewById(R.id.text_PastPlaceOrder_item_Ngay));
        tv_MaCk = ((TextView) findViewById(R.id.text_PastPlaceOrder_item_MaCK));
        tv_Soluong = ((TextView) findViewById(R.id.text_PastPlaceOrder_item_SoLuong));
        tv_Side = ((TextView) findViewById(R.id.text_PastPlaceOrder_item_Side));
        tv_Gia = ((TextView) findViewById(R.id.text_PastPlaceOrder_item_Gia));
        tv_Gio = ((TextView) findViewById(R.id.text_PastPlaceOrder_item_Gio));
        tv_FloorTrade = ((TextView) findViewById(R.id.text_PastPlaceOrder_item_FloorTrade));
        tv_LoaiLenh = ((TextView) findViewById(R.id.text_PastPlaceOrder_item_LoaiLenh));
        tv_ChannelTrade = ((TextView) findViewById(R.id.text_PastPlaceOrder_item_KenhGiaoDich));
        tv_Status = ((TextView) findViewById(R.id.text_PastPlaceOrder_item_TrangThaiLenh));

        tv_Status1 = ((TextView) findViewById(R.id.text_PastPlaceOrder_item_Status));
        tv_Via = ((TextView) findViewById(R.id.text_PastPlaceOrder_item_via));

        vg_More = (ViewGroup) findViewById(R.id.layout_more);
        buycolor = getResources().getColor(R.color.color_Mua);
        sellcolor = getResources().getColor(R.color.color_Ban);

    }

    public void setListView(PastPlaceOrderItem item, boolean isexpand) {
        tv_MaCk.setText(item.getSYMBOL());
        tv_Side.setText(item.getExType());
        if (item.getEXECTYPE().equals(PlaceOrder.SIDE_NB)) {
            tv_Side.setTextColor(buycolor);
        } else {
            tv_Side.setTextColor(sellcolor);
        }
        tv_Ngay.setText(item.getTDATE());
        tv_Soluong.setText(item.getOQTTY());
        tv_Gia.setText(item.getQPRICE());
        tv_Status.setText(item.getORSTATUS());
        tv_Status1.setText(item.getORSTATUS());
        tv_Via.setText(item.getVIA());
        tv_Gio.setText(item.getTXTIME());
        tv_FloorTrade.setText(item.getTRADEPLACE());
        tv_LoaiLenh.setText(item.getPRICETYPE());
        tv_ChannelTrade.setText(item.getVIA());
        if (isexpand) {
            expand();
        } else {
            collapse();
        }
    }

    public void expand() {
        vg_More.setVisibility(View.VISIBLE);
        isexpand = true;
    }

    public void collapse() {
        vg_More.setVisibility(View.GONE);
        isexpand = false;
    }
}
