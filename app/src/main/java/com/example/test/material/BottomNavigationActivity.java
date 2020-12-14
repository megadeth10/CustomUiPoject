package com.example.test.material;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.custom.activity.BaseActivity;
import com.example.test.myapplication.R;
import com.example.utils.Log;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BottomNavigationActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.TAG = BottomNavigationActivity.class.getSimpleName();
        this.mToolBar.setTitle("Bottom Navigation");

        setContentsLayout(R.layout.layout_bottom_navigation);

        BottomNavigationView bottomView = findViewById(R.id.bottom_navigation);
        bottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.e(TAG, String.format("onNavigationItemSelected %s", item.getTitle()));
                return setBadge(item);
            }
        });
        bottomView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                Log.e(TAG, String.format("onNavigationItemReselected %s", item.getTitle()));
                setBadge(item);
            }
        });
    }

    private boolean setBadge(MenuItem item) {
        int itemId = item.getItemId();
        boolean retValue = false;
        switch (itemId){
            case R.id.menu_add:
            case R.id.menu_remove:
            case R.id.menu_plus:
            case R.id.menu_minus:
                BottomNavigationView bottomView = findViewById(R.id.bottom_navigation);
                BadgeDrawable badgeDrawable = bottomView.getOrCreateBadge(itemId);
                badgeDrawable.setVisible(true);
                badgeDrawable.setNumber(badgeDrawable.getNumber() + 1);
                retValue = true;
                break;
        }
        return retValue;
    }
}
