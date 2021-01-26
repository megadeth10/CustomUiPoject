package com.example.scene.caollapsingtollbar.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.example.utils.Log;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * SnackBar 사용시 FAB behavior 수정파일
 * 물론 com.google.android.material.floatingactionbutton.FloatingActionButton$Behavior 이것이 있다.
 */
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
     *
     * @param parent
     * @param child
     * @param dependency
     * @return
     */

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent,
                                   @NonNull FloatingActionButton child,
                                   @NonNull View dependency) {
        if (dependency instanceof Snackbar.SnackbarLayout) {
            Log.e(TAG, String.format("layoutDependsOn() %s",
                    dependency.getClass().getSimpleName()));
            return true;
        }
//        return super.layoutDependsOn(parent, child, dependency);
        return false;
    }

    /**
     * dependency의 View의 변화가 있을때 이벤트가 들어옴.
     *
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent,
                                          @NonNull FloatingActionButton child,
                                          @NonNull View dependency) {
        if (dependency instanceof Snackbar.SnackbarLayout
                && child.getTranslationY() == 0) {
            float transitionY = Math.min(0,
                    dependency.getTranslationY() - dependency.getHeight());
            child.setTranslationY(transitionY);
            Log.e(TAG, String.format("onDependentViewChanged() transitionY: %f",
                    transitionY));
        }
//        return super.onDependentViewChanged(parent, child, dependency);
        return false;
    }

    /**
     * layoutDependsOn에 설정한 View가 제거시
     * @param parent
     * @param child
     * @param dependency
     */
    @Override
    public void onDependentViewRemoved(@NonNull CoordinatorLayout parent, @NonNull FloatingActionButton child, @NonNull View dependency) {
        super.onDependentViewRemoved(parent, child, dependency);
        if (dependency instanceof Snackbar.SnackbarLayout){
            Log.e(TAG, String.format("onDependentViewRemoved() %s",
                    dependency.getClass().getSimpleName()));
            child.setTranslationY(0);
        }
    }
}
