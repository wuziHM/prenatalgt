package com.hlib.util;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 * Author：HM $ on 18/1/23 14:17
 * E-mail：359222347@qq.com
 * <p>
 * use to...
 */
public class AppUtil {


    public static DisplayMetrics getMetrics(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics;
    }

}
