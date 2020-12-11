package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.network.IApiCallback;
import com.example.network.networkLayer;
import com.example.network.response.appInfoResponse;
import com.example.network.response.errorResponse;
import com.example.network.response.resultResponse;
import com.example.utils.Log;

public class SecondActivity extends AppCompatActivity {
    TextView resultTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // transition code
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//            Slide startSlide = new Slide(Gravity.START);
//            startSlide.setDuration(100);
//            Slide endSlide = new Slide(Gravity.END);
//            startSlide.setDuration(100);
//            getWindow().setExitTransition(startSlide);
//            getWindow().setEnterTransition(endSlide);
//        }
        // transition code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(SecondActivity.class.getSimpleName(), "onClick");
                finish();
            }
        });
        this.resultTv = findViewById(R.id.resultTV);
        Button btn3 = findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn2 = findViewById(R.id.button2);
                btn2.setEnabled(!btn2.isEnabled());
            }
        });

        Button getAppInfoButton = findViewById(R.id.getAppInfoButton);
        getAppInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkLayer.getAppInfo(new IApiCallback() {
                    @Override
                    public void onFailure(errorResponse obj) {
                        String str = String.format("오류 발생\ncode: %d\nmessage: %s\nerror: %s", obj.getStatusCode(), obj.getMessage(), obj.getError());
                        printText(str);
                    }

                    @Override
                    public void onSuccess(resultResponse obj) {
                        appInfoResponse res = (appInfoResponse) obj;
                        printText(res.getAppType().concat("\n").concat(Integer.toString(res.getCode())).concat("\n").concat(res.getMessage()));
                    }
                }, appInfoResponse.class);
            }
        });
    }

    private void printText(final String str){
        resultTv.post(new Runnable() {
            @Override
            public void run() {
                resultTv.setText(str);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.DONUT) {
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }
}
