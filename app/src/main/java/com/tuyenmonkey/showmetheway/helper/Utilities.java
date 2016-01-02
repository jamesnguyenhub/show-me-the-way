package com.tuyenmonkey.showmetheway.helper;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by tuyen on 1/2/2016.
 */
public class Utilities {

    private static final String TAG = Utilities.class.getSimpleName();

    /**
     * Get width/height of system window
     * @param context Current context
     * @return System size in Point
     */
    public static Point getSystemWindowSize(Context context) {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        return size;
    }
}
