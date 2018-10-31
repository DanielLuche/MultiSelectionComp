package com.dluche.multiselectioncomp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.dluche.multiselectioncomp.ctrls.MultipleChoiceCtrl;

import java.util.List;

public class MultipleChoiceAdapter extends BaseAdapter {

    private Context context;
    private int resource;
    private List<MultipleChoiceCtrl.MultipleChoiceCtrlObj> source;
    private OnAdapterItemSelected onAdapterItemSelected;

    public interface OnAdapterItemSelected{
        void adapterItemSelected(MultipleChoiceCtrl.MultipleChoiceCtrlObj item);
    }

    public void setOnAdapterItemSelected(OnAdapterItemSelected onAdapterItemSelected) {
        this.onAdapterItemSelected = onAdapterItemSelected;
    }

    public MultipleChoiceAdapter(Context context, List<MultipleChoiceCtrl.MultipleChoiceCtrlObj> source) {
        this.context = context;
        this.source = source;
        this.resource = android.R.layout.simple_list_item_multiple_choice;
    }

    @Override
    public int getCount() {
        return source.size();
    }

    @Override
    public Object getItem(int position) {
        return source.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0L;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(resource,parent,false);
        }
        //
        final MultipleChoiceCtrl.MultipleChoiceCtrlObj item = source.get(position);
        //
        final CheckedTextView chtv_item = convertView.findViewById(android.R.id.text1);
        chtv_item.setText(item.getDesc());
        chtv_item.setChecked(item.isSelected());
        //
        chtv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chtv_item.toggle();
                item.setSelected(chtv_item.isChecked());
                if(onAdapterItemSelected != null){
                    onAdapterItemSelected.adapterItemSelected(item);
                }
            }
        });
        //
        return convertView;
    }
}
