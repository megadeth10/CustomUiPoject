package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView intent_action_tv;
    TextView scheme_Tv;
    TextView intent_encData_tv;
    String returnScheme;
    Button returnApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.intent_action_tv = findViewById(R.id.intent_action);
        this.scheme_Tv = findViewById(R.id.intent_scheme);
        this.intent_encData_tv = findViewById(R.id.intent_encData);
        this.returnApp = findViewById(R.id.returnApp);
        this.returnApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(returnScheme != null) {
                    Intent intent = new Intent().setAction(Intent.ACTION_VIEW);
                    JSONObject json = new JSONObject();
                    try {
                        json.put("aaaa", "aaaa");
                        json.put("bbbb", "bbbb");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String str = String.format("%s://?data=%s",returnScheme,json.toString());
                    Uri uri = Uri.parse(str);
                    intent.setData(uri);
                    startActivity(intent);
                }
            }
        });
        Button btn = findViewById(R.id.nextButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                // transition code
                // startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                // transition code
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        Button secureBtn = findViewById(R.id.secureButton);
        secureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                // transition code
                // startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                // transition code
                startActivity(intent);
            }
        });
        if(getIntent() != null) {
            onNewIntent(getIntent());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        returnScheme = null;
        if(intent.getAction() != null) {
            intent_action_tv.setText(intent.getAction());
        }

        if(intent.getData() != null) {
//            Bundle bundle = intent.getExtras().getBundle("data");
//            String returnUrlShema = bundle.getString("returnUrlShema");
//            String encData = bundle.getString("encData");
            Uri uri = intent.getData();
            String dataString = uri.getQueryParameter("data");
            try {
                JSONObject json = new JSONObject(dataString);
                returnScheme = json.get("returnUrlShema").toString();
                scheme_Tv.setText(returnScheme);
                intent_encData_tv.setText(json.get("encData").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            intent_encData_tv.setText(encData);
        }
    }
}
