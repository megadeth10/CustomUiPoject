package com.example.scene.cardscroll;

import android.os.Bundle;
import android.view.View;

import com.example.scene.cardscroll.viewmodel.CardScrollViewModel;
import com.example.custom.activity.ToolbarActivity;
import com.example.custom.view.scrollview.cardscrollview.CardScrollView;
import com.example.test.myapplication.databinding.LayoutCardScrollviewBinding;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

public class CardScrollActivity extends ToolbarActivity implements CardScrollView.iChangeScroll, View.OnClickListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.TAG = CardScrollActivity.class.getSimpleName();
    }

    @Override
    public void setToolbar() {
        this.getToolBar().setTitle("Card Horizontal ScrollView");
    }

    @Override
    public void setContent() {
        LayoutCardScrollviewBinding contextBind = LayoutCardScrollviewBinding.inflate(getLayoutInflater());
        setContentsViewBinding(contextBind);

        CardScrollViewModel model = new ViewModelProvider(this).get(CardScrollViewModel.class);
        contextBind.setScrollViewModel(model);
        contextBind.cardScrollview.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Object obj = view.getTag();
        logE(String.format("onClick: %s",obj.toString()));
    }

    @Override
    public void changeScroll(int index, int size, Object item) {
        logE(String.format("changeScroll: %s", item.toString()));
    }
}
