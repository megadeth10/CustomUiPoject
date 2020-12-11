package com.example.utils;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;

public class util {
    static String TAG = util.class.getSimpleName();
    public static Point ratioSize(final Activity activity, final float width, final float height, Rect windowRect){
        Point scaledPoint = new Point(0,0);
        try {
            if (windowRect == null) {
                windowRect = new Rect();
                activity.getWindowManager().getDefaultDisplay().getRectSize(windowRect);
            }
            float scale = windowRect.right / width;
            float scaleHeight = scale * height;
            scaledPoint.x = windowRect.right;
            scaledPoint.y = (int)scaleHeight;
        }catch (Exception e){
            Log.e(TAG, String.format("ratioSize() %s", e.getMessage()));
        }
        return scaledPoint;
    }
}
