package com.fss.designcontrol.keyboardhook;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fss.designcontrol.R;

import java.util.List;


/**
 * Created by giang.ngo on 09-12-2015.
 */
public class KeyboardHookAdapter extends RecyclerView.Adapter<KeyboardHookItemView> {

    List<String> listSyms;

    public KeyboardHookAdapter(List<String> listSyms) {
        this.listSyms = listSyms;
    }

    @Override
    public KeyboardHookItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View sView = mInflater.inflate(R.layout.keyboardhookitem, parent, false);
        KeyboardHookItemView view =new KeyboardHookItemView(sView);
        view.setOnItemClickListener(onItemClickListener);
        return view;
    }

    @Override
    public void onBindViewHolder(KeyboardHookItemView holder, int position) {
        holder.setData(listSyms.get(position));
    }


    @Override
    public int getItemCount() {
        return listSyms == null ? 0 : listSyms.size();
    }

    KeyboardHookNew.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(KeyboardHookNew.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
