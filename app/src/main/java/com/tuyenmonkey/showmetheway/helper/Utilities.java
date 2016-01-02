package com.tuyenmonkey.showmetheway.helper;

import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

    /**
     * Checks if the device has any active internet connection.
     *
     * @param context
     * @return
     */
    public static boolean isThereInternetConnection(Context context) {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }
}
