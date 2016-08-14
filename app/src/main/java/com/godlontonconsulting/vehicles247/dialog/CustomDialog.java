package com.godlontonconsulting.vehicles247.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;

import com.godlontonconsulting.vehicles247.R;



public class CustomDialog extends Dialog {

    public CustomDialog(Context context) {
        super(context, R.style.CustomDialog);
    }


    public void setContentView(int resID, String diaTitle) {
        super.setContentView(resID);
        this.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }
}
