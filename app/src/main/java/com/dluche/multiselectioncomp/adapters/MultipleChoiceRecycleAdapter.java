package com.dluche.multiselectioncomp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.dluche.multiselectioncomp.ctrls.MultipleChoiceCtrl;

import java.util.List;

public class MultipleChoiceRecycleAdapter extends RecyclerView.Adapter<MultipleChoiceRecycleAdapter.MyViewHolder> {

    private Context context;
    private int resource;
    private List<MultipleChoiceCtrl.MultipleChoiceCtrlObj> source;
    private OnAdapterItemSelected onAdapterItemSelected;

    public void setOnAdapterItemSelected(OnAdapterItemSelected onAdapterItemSelected) {
        this.onAdapterItemSelected = onAdapterItemSelected;
    }

    public MultipleChoiceRecycleAdapter(Context context, List<MultipleChoiceCtrl.MultipleChoiceCtrlObj> source) {
        this.context = context;
        this.source = source;
        this.resource = android.R.layout.simple_list_item_multiple_choice;
    }

    public interface OnAdapterItemSelected{
        void adapterItemSelected(MultipleChoiceCtrl.MultipleChoiceCtrlObj item);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public CheckedTextView chtv_item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //
            chtv_item = itemView.findViewById(android.R.id.text1);
            String tst ="";
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(resource,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int position) {
        final MultipleChoiceCtrl.MultipleChoiceCtrlObj item = source.get(position);
        //
        myViewHolder.chtv_item.setText(item.getDesc());
        myViewHolder.chtv_item.setChecked(item.isSelected());
        //
        myViewHolder.chtv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewHolder.chtv_item.toggle();
                item.setSelected(myViewHolder.chtv_item.isChecked());
                if(onAdapterItemSelected != null){
                    onAdapterItemSelected.adapterItemSelected(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return source.size();
    }

}
