package com.example.test.material;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toolbar;

import com.example.custom.activity.DialogActivity;
import com.example.custom.activity.ToolbarActivity;
import com.example.test.fragment.FirstFragment;
import com.example.test.fragment.FourFragment;
import com.example.test.fragment.SecondFragment;
import com.example.test.fragment.ThreeFragment;
import com.example.test.myapplication.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BottomNavigationActivity extends ToolbarActivity {

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
                logE(String.format("onNavigationItemSelected %s", item.getTitle()));
                setFragmentView(item.getItemId());
                return setBadge(item);
            }
        });
        bottomView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                logE(String.format("onNavigationItemReselected %s", item.getTitle()));
                showDialog();
            }
        });
        setFragmentView(R.id.menu_add);
    }

    private boolean setFragmentView(int menuId){
        Fragment fragment;

        switch (menuId){
            case R.id.menu_add:
                fragment = new FirstFragment(new Object());
                break;
            case R.id.menu_remove:
                fragment = new SecondFragment();
                break;
            case R.id.menu_plus:
                fragment = new ThreeFragment();
                break;
            case R.id.menu_minus:
                fragment = new FourFragment();
                break;
            default:
                fragment = null;
                break;
        }

        if(fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.bottom_content_view, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void showDialog() {
        this.mMaterialDialog.setTitle("타이틀").setMessage("본문").setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                logE("onCancel()");
            }
        }).setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logE("setPositiveButton()");
            }
        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logE("setNegativeButton()");
            }
        }).setNeutralButton("중간", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logE("setNeutralButton()");
            }
        }).show();
    }

    private boolean setBadge(MenuItem item) {
        int itemId = item.getItemId();
        boolean retValue = false;
        switch (itemId) {
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

    @Override
    public void onBackPressed() {
        BottomNavigationView bottomView = findViewById(R.id.bottom_navigation);
        if (bottomView.getSelectedItemId() == R.id.menu_add) {
            super.onBackPressed();
            return;
        }

        bottomView.setSelectedItemId(R.id.menu_add);
    }
}
