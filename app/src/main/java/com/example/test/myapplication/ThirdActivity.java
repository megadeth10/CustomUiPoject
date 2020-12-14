package com.example.test.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.test.javatest.ClassTest;
import com.example.secure.secure;
import com.example.utils.Log;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ThirdActivity extends AppCompatActivity {
    TextView resultTv;
    String key = "aaa";
    Context ctx = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(ThirdActivity.class.getSimpleName(), "onClick");
                finish();
            }
        });

        this.resultTv = findViewById(R.id.resultTV);
        Button encryptBtn = findViewById(R.id.encryptBtn);
        encryptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAssetData();
            }
        });

        Button decryptBtn = findViewById(R.id.decryptBtn);
        decryptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int result = SampleKt.sum(5,7);
//                AAA aaa = new AAA(1);
//                BBB bbb = new BBB("AAA");
//                ArrayList<Integer> list = EnumtestKt.getColor();
//                String colorStr = "A ";
//                for(int i=0; i<list.size(); i++){
//                    int value = list.get(i);
//                    String valueStr = Integer.toString(value).concat(" ");
//                    colorStr = colorStr.concat(valueStr);
//                }
//                String msg = Integer.toString(result).concat(Integer.toString(aaa.getMValue())).concat(bbb.getMValue()).concat("\n").concat(colorStr);
//                resultTv.setText(msg);
                ClassTest cls1 = new ClassTest(1);
                cls1.displayValue();
                ClassTest cls2 = new ClassTest("AAA");
                cls2.displayValue();
            }
        });

        Button secureEncryptBtn = findViewById(R.id.secureEncryptBtn);
        secureEncryptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new secure().set(ctx, key, "abc");
            }
        });

        Button secureDecryptBtn = findViewById(R.id.secureDecryptBtn);
        secureDecryptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = new secure().get(ctx, key, "error");
                resultTv.setText(str);
            }
        });
    }

    private void getAssetData () {
        AssetManager am = getResources().getAssets();
        InputStream is = null;

        StringBuffer textbuf = new StringBuffer();
        try {
            is = am.open("data.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            while (true) {
                String line = reader.readLine();
                if (line == null)
                    break;
                textbuf.append(line);
            }
            reader.close();
            Log.e(ThirdActivity.class.getSimpleName(), textbuf.toString());
            Gson gson = new Gson();
            secureData data = gson.fromJson(textbuf.toString(),secureData.class);
            resultTv.setText("ACCESS_TOKEN".concat("\n\n").concat(data.getACCESS_TOKEN())
                    .concat("\n\n").concat("BASE_URL").concat("\n\n").concat(data.getBASE_URL())
                    .concat("\n\n").concat("SECURE").concat("\n\n").concat(Boolean.toString(data.isSECURE())));
//            resultTv.setText(textbuf.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(ThirdActivity.class.getSimpleName(), e.getMessage());
        }
        if(am != null) {
//            am.close();
        }
        if(is != null){
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(ThirdActivity.class.getSimpleName(), e.getMessage());
            }
        }
    }


}
