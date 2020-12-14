package com.example.activity;

import android.os.Bundle;
import android.view.View;

import com.example.custom.activity.BaseActivity;
import com.example.custom.view.scrollview.cardscrollview.CardItem;
import com.example.custom.view.scrollview.cardscrollview.CardScrollView;
import com.example.test.myapplication.R;
import com.example.utils.Log;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class CardScrollActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.TAG = CardScrollActivity.class.getSimpleName();

        setTextToolBar("Card Horizontal ScrollView");
        setContentsLayout(R.layout.layout_card_scrollview);
        CardScrollView cardScrollView = findViewById(R.id.card_scrollview);

        ArrayList<CardItem> list = new ArrayList<>();
        list.add(new CardItem(R.drawable.pager_image_1, new Integer(1)));
        list.add(new CardItem(R.drawable.pager_image_2, new Integer(2)));
        list.add(new CardItem(R.drawable.pager_image_3, new Integer(3)));

        cardScrollView.setImage(list, new CardScrollView.iChangeScroll() {
                    @Override
                    public void changeScroll(int index, int size, Object item) {
                        Log.e(TAG, String.format("changeScroll: %s", item.toString()));
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Object obj = view.getTag();
                        Log.e(TAG, String.format("onClick: %s",obj.toString()));
                    }
                });
    }
}
