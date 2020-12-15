package com.example.custom.widget.dialog;

import android.content.Context;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import androidx.appcompat.app.AlertDialog;

/**
 * DialogBuilder와 AlertDialog를 합친 Class
 * 기존 Factory
 */
public class CustomMaterialAlertDialogBuilder extends MaterialAlertDialogBuilder {
    private AlertDialog mAlertDialog;
    public CustomMaterialAlertDialogBuilder(Context context) {
        super(context);
    }

    public CustomMaterialAlertDialogBuilder(Context context, int overrideThemeResId) {
        super(context, overrideThemeResId);
    }

    public CustomMaterialAlertDialogBuilder(Context context, AlertDialog dialog) {
        super(context);

    }

    @Override
    public AlertDialog show() {
        if(isShowing()){
            this.mAlertDialog.dismiss();
        }
        this.mAlertDialog = super.show();
        return this.mAlertDialog;
    }

    public boolean isShowing(){
        if(this.mAlertDialog == null){
            return false;
        }
        return this.mAlertDialog.isShowing();
    }

    public void dismiss(){
        if(this.mAlertDialog == null){
            return;
        }
        this.mAlertDialog.dismiss();
    }

    public void onDestory(){
        this.dismiss();
        this.mAlertDialog = null;
    }
}
