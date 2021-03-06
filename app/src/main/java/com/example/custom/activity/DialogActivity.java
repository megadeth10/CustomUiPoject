package com.example.custom.activity;

import android.app.Dialog;
import android.os.Bundle;
import com.example.custom.widget.dialog.CustomMaterialAlertDialogBuilder;
import androidx.annotation.Nullable;

public class DialogActivity extends BaseActivity {
    private CustomMaterialAlertDialogBuilder mMaterialDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mMaterialDialog = new CustomMaterialAlertDialogBuilder(this.mContext);
    }

    @Override
    protected void onDestroy() {
        if(this.mMaterialDialog != null){
            this.mMaterialDialog.onDestory();
        }
        this.mMaterialDialog = null;
        super.onDestroy();
    }

    protected CustomMaterialAlertDialogBuilder getDialogBuilder(){
        return this.mMaterialDialog;
    }
}
