package com.example.activity.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.example.utils.Log;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class CustomFloatingActionButtonBehavior
        extends CoordinatorLayout.Behavior<FloatingActionButton> {
    private final String TAG = CustomFloatingActionButtonBehavior.class.getSimpleName();
    public CustomFloatingActionButtonBehavior() {
    }

    public CustomFloatingActionButtonBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 레이아웃에 트리거가 발생하면 호출
     * true를 리턴하면 onDependentViewChanged 호출
     * @param parent
     * @param child
     * @param dependency
     * @return
     */

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent,
                                   @NonNull FloatingActionButton child,
                                   @NonNull View dependency) {
        if(dependency instanceof Snackbar.SnackbarLayout) {
            Log.e(TAG, String.format("layoutDependsOn() %s",
                    dependency.getClass().getSimpleName()));
            return true;
        }
//        return super.layoutDependsOn(parent, child, dependency);
        return false;
    }

    /**
     * dependency의 View의 변화가 있을때 이벤트가 들어옴.
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent,
                                          @NonNull FloatingActionButton child,
                                          @NonNull View dependency) {
        if(dependency instanceof Snackbar.SnackbarLayout){
            float transitionY = Math.min(0,
                    dependency.getTranslationY() - dependency.getHeight());
            child.setTranslationY(transitionY);
            Log.e(TAG, String.format("onDependentViewChanged() t: %f, height : %d",
                    dependency.getTranslationY(), dependency.getHeight()));
        }
//        return super.onDependentViewChanged(parent, child, dependency);
        return false;
    }
}