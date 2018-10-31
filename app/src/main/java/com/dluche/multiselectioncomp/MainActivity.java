package com.dluche.multiselectioncomp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dluche.multiselectioncomp.ctrls.MultipleChoiceCtrl;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private MultipleChoiceCtrl choiceCtrl;
    //private MultipleChoiceCtrl choiceCtr2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        iniVars();
        //
        iniActions();
    }

    private void iniVars() {
        context = this;
        //
        choiceCtrl = findViewById(R.id.main_mc_opt);
        //
        choiceCtrl.setmOption(generateOptList(15));
        //
        choiceCtrl.setExibitionMode(MultipleChoiceCtrl.SHOW_AS_LIST);
        //
        addMultCrtl();
    }

    private void addMultCrtl() {
        MultipleChoiceCtrl choiceCtrl1 = new MultipleChoiceCtrl(context);
        choiceCtrl1.setmOption(generateOptList(10));
        choiceCtrl1.setExibitionMode(MultipleChoiceCtrl.SHOW_AS_COMBO);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //
        ((ViewGroup) choiceCtrl.getParent()).addView(choiceCtrl1);


        //this.addContentView(choiceCtrl1,params);
    }

    private List<MultipleChoiceCtrl.MultipleChoiceCtrlObj> generateOptList(int tamList) {
        List<MultipleChoiceCtrl.MultipleChoiceCtrlObj> optList = new ArrayList<>();

        for(int i = 0; i < tamList; i++){
            MultipleChoiceCtrl.MultipleChoiceCtrlObj ctrlObj = new MultipleChoiceCtrl.MultipleChoiceCtrlObj();
            ctrlObj.setCode(String.valueOf(i));
            ctrlObj.setDesc("Desc:  Item" +( i + 1));
            ctrlObj.setSelected(false);
            //
            optList.add(ctrlObj);
        }
        //
        return optList;
    }

    private void iniActions() {

    }
}
