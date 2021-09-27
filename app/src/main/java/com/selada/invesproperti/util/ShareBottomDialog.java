package com.selada.invesproperti.util;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.selada.invesproperti.R;

import me.shaohui.bottomdialog.BaseBottomDialog;

public class ShareBottomDialog extends BaseBottomDialog {

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_bottom_home_layout;
    }

    @Override
    public void bindView(View v) {
        // do any thing you want
        RadioGroup radioGroup = v.findViewById(R.id.radio_group);
        RadioButton rbSemua = v.findViewById(R.id.rb_1);
        RadioButton rbBaru = v.findViewById(R.id.rb_2);
        RadioButton rbSedangBerjalan = v.findViewById(R.id.rb_3);
        RadioButton rbTerdanai = v.findViewById(R.id.rb_4);

        rbSemua.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                rbSemua.setChecked(true);
                rbBaru.setChecked(false);
                rbSedangBerjalan.setChecked(false);
                rbTerdanai.setChecked(false);
                dismiss();
            }
        });

        rbBaru.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                rbSemua.setChecked(false);
                rbBaru.setChecked(true);
                rbSedangBerjalan.setChecked(false);
                rbTerdanai.setChecked(false);
                dismiss();
            }
        });

        rbSedangBerjalan.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                rbSemua.setChecked(false);
                rbBaru.setChecked(false);
                rbSedangBerjalan.setChecked(true);
                rbTerdanai.setChecked(false);
                dismiss();
            }
        });

        rbTerdanai.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                rbSemua.setChecked(false);
                rbBaru.setChecked(false);
                rbSedangBerjalan.setChecked(false);
                rbTerdanai.setChecked(true);
                dismiss();
            }
        });
        }

//        radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {
//            switch (i){
//                case R.id.rbSemua:
//                    //do something
//                    rbSemua.setChecked(true);
//                    rbBaru.setChecked(false);
//                    rbSedangBerjalan.setChecked(false);
//                    rbTerdanai.setChecked(false);
//                    dismiss();
//                    break;
//                case R.id.rbBaru:
//                    //do something
//                    rbSemua.setChecked(false);
//                    rbBaru.setChecked(true);
//                    rbSedangBerjalan.setChecked(false);
//                    rbTerdanai.setChecked(false);
//                    dismiss();
//                    break;
//                case R.id.rbSedangBerjalan:
//                    //do something
//                    rbSemua.setChecked(false);
//                    rbBaru.setChecked(false);
//                    rbSedangBerjalan.setChecked(true);
//                    rbTerdanai.setChecked(false);
//                    dismiss();
//                    break;
//                case R.id.rbTerdanai:
//                    //do something
//                    rbSemua.setChecked(false);
//                    rbBaru.setChecked(false);
//                    rbSedangBerjalan.setChecked(false);
//                    rbTerdanai.setChecked(true);
//                    dismiss();
//                    break;
//            }
//        });

    @Override
    public void dismiss() {
        super.dismiss();
    }


}