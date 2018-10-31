package com.dluche.multiselectioncomp.ctrls;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dluche.multiselectioncomp.R;
import com.dluche.multiselectioncomp.adapters.MultipleChoiceAdapter;
import com.dluche.multiselectioncomp.adapters.MultipleChoiceRecycleAdapter;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceCtrl extends LinearLayout implements View.OnClickListener {

    public static final String SHOW_AS_COMBO = "SHOW_AS_COMBO";
    public static final String SHOW_AS_LIST = "SHOW_AS_LIST";

    private Context context;
    private TextView tv_lbl;
    private List<MultipleChoiceCtrlObj> mOption = new ArrayList<>();
    private String exibitionMode = SHOW_AS_COMBO;
    private android.support.v7.widget.RecyclerView lv_opt;
    private MultipleChoiceAdapter mAdapter;
    private MultipleChoiceRecycleAdapter mAdapter2;


    public MultipleChoiceCtrl(Context context) {
        super(context);
        //
        initialize(context,null);
    }

    public MultipleChoiceCtrl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //
        initialize(context,attrs);
    }



    public MultipleChoiceCtrl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //
        initialize(context,attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        this.context = context;

        LayoutInflater inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View vv = inflater.inflate(R.layout.multiple_choice_layout, ll_holder, true);
        View view = inflater.inflate(R.layout.multiple_choice_layout,this,true);
        //
        tv_lbl =  view.findViewById(R.id.multiple_choice_ctrl_tv_lbl);
        tv_lbl.setOnClickListener(this);
        tv_lbl.setText(getSelectedText());
        lv_opt = findViewById(R.id.multiple_choice_ctrl_lv_opt);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        lv_opt.setLayoutManager(layoutManager);
        configExibition();
    }

    private void configExibition() {
        if(exibitionMode.equalsIgnoreCase(SHOW_AS_LIST)){
            lv_opt.setVisibility(VISIBLE);
            lv_opt.setAdapter(configDialogOptionListV2());
            //ViewCompat.setNestedScrollingEnabled(lv_opt, false);
        }else{
            lv_opt.setVisibility(GONE);
            lv_opt.setAdapter(null);
        }

    }

    public List<MultipleChoiceCtrlObj> getmOption() {
        return mOption;
    }

    public void setmOption(List<MultipleChoiceCtrlObj> mOption) {
        this.mOption = mOption;
    }

    public void setExibitionMode(String exibitionMode) {
        this.exibitionMode = exibitionMode;
        //
        configExibition();
    }

    @Override
    public void onClick(View v) {
        if(exibitionMode.equalsIgnoreCase(SHOW_AS_COMBO)) {
            openSelectionDialog();
        }
    }

    private void openSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //
        View dialogView = LayoutInflater.from(context).inflate(R.layout.multiple_choice_selection_dialog,null);
        //
        TextView tv_dialog_lbl = dialogView.findViewById(R.id.multiple_choice_dialog_tv_lbl);
        ListView lv_dialog_opt = dialogView.findViewById(R.id.multiple_choice_dialog_lv_option);
        ImageView iv_dialog_cancel = dialogView.findViewById(R.id.multiple_choice_dialog_iv_cancel);
        ImageView iv_dialog_selection = dialogView.findViewById(R.id.multiple_choice_dialog_iv_select);
        //
        tv_dialog_lbl.setText("Selecione 1 ou mais itens");
        lv_dialog_opt.setAdapter(configDialogOptionList());
        ViewCompat.setNestedScrollingEnabled(lv_dialog_opt, false);
        //
        builder
                .setTitle("Seleção")
                .setView(dialogView)
                .setCancelable(false)
        ;

        final AlertDialog alertDialog = builder.create();

        //
        iv_dialog_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        //
        iv_dialog_selection.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_lbl.setText(getSelectedText());
                alertDialog.dismiss();
                //Toast.makeText(context,u + " Item(s) selecionado(s)",Toast.LENGTH_LONG).show();
            }
        });
        //
        alertDialog.show();
    }

    private int getSelectedCount() {
//        int counter = 0;
//        if(mOption != null){
//
//            for(MultipleChoiceCtrlObj obj: mOption){
//                if(obj.isSelected()){
//                    counter ++;
//                }
//            }
//        }
        //
        return getSelectedList().size();
    }

    private String getSelectedText(){
        String msg = "";
        List<MultipleChoiceCtrlObj> list = getSelectedList();
        //
        if(list.size() == 0) {
            msg = "Nenhum item selecionado ";
        }else if(list.size() == 1){
            msg = list.get(0).getDesc();
        }else{
            msg = list.size() + " Item(s) selecionado(s)";
        }
        //
        return msg;
    }

    private List<MultipleChoiceCtrlObj> getSelectedList(){
        List<MultipleChoiceCtrlObj> selectedList = new ArrayList<>();
        if(mOption != null) {
            //
            for (MultipleChoiceCtrlObj obj : mOption) {
                if (obj.isSelected()) {
                    selectedList.add(obj);
                }
            }
        }
        //
        return selectedList;
    }

    private MultipleChoiceAdapter configDialogOptionList() {
        if(mOption != null && mOption.size() > 0){
            mAdapter = new MultipleChoiceAdapter(
                    context,
                    mOption
            );
            //
            if(exibitionMode.equalsIgnoreCase(SHOW_AS_LIST)){
                mAdapter.setOnAdapterItemSelected(new MultipleChoiceAdapter.OnAdapterItemSelected() {
                    @Override
                    public void adapterItemSelected(MultipleChoiceCtrlObj item) {
                        tv_lbl.setText(getSelectedText());
                    }
                });
            }
        }
        //
        return mAdapter;
    }

    private MultipleChoiceRecycleAdapter configDialogOptionListV2() {
        if(mOption != null && mOption.size() > 0){
            mAdapter2 = new MultipleChoiceRecycleAdapter(
                    context,
                    mOption
            );
            //
            if(exibitionMode.equalsIgnoreCase(SHOW_AS_LIST)){
                mAdapter2.setOnAdapterItemSelected(new MultipleChoiceRecycleAdapter.OnAdapterItemSelected() {
                    @Override
                    public void adapterItemSelected(MultipleChoiceCtrlObj item) {
                        tv_lbl.setText(getSelectedText());
                    }
                });
            }
        }
        //
        return mAdapter2;
    }
    public static class MultipleChoiceCtrlObj{
        private String code;
        private String desc;
        private boolean selected;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }
}
