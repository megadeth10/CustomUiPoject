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
import com.example.test.material.viewmodel.BottomNavigationViewModel;
import com.example.test.material.viewmodelfactory.BottomNavigationViewModelFactory;
import com.example.test.myapplication.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

/**
 * TODO: 화면 rotate시에 Fragment 2번 생성 되는 문제 수정해야함.
 * ref: https://stackoverflow.com/questions/29177961/android-fragment-created-twice-orientation-change
 */
public class BottomNavigationActivity extends ToolbarActivity {
    private BottomNavigationViewModel viewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.TAG = BottomNavigationActivity.class.getSimpleName();
    }

    @Override
    public void setToolbar() {
        this.getToolBar().setTitle("Bottom Navigation");
    }

    @Override
    public void setContent() {
        setContentsLayout(R.layout.layout_bottom_navigation);
        this.viewModel = new ViewModelProvider(this, new BottomNavigationViewModelFactory(R.id.menu_add)).get(BottomNavigationViewModel.class);
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
        setFragmentView(this.viewModel.getCurrentMenuId().getValue());
    }

    private boolean setFragmentView(int menuId){
        Fragment fragment;

        switch (menuId){
            case R.id.menu_add:
                fragment = FirstFragment.newInstance(new Object());
                break;
            case R.id.menu_remove:
                fragment = SecondFragment.newInstance(null, null);
                break;
            case R.id.menu_plus:
                fragment = ThreeFragment.newInstance(null, null);
                break;
            case R.id.menu_minus:
                fragment = FourFragment.newInstance(null, null);
                break;
            default:
                fragment = null;
                break;
        }
        this.viewModel.setCurrentMenuId(menuId);

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
        this.getDialogBuilder().setTitle("타이틀").setMessage("본문").setOnCancelListener(new DialogInterface.OnCancelListener() {
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
