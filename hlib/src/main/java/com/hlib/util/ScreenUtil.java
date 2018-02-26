package com.hlib.util;

import android.content.Context;

/**
 * Author：HM $ on 18/1/24 14:09
 * E-mail：359222347@qq.com
 * <p>
 * use to...
 */
public class ScreenUtil {

    /**
     * 将dip转换为px
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
