package com.yunkang.prenatalgt.constant;

/**
 * Author：HM $ on 18/1/25 11:42
 * E-mail：359222347@qq.com
 * <p>
 * use to...
 */
public class ConfigData {


    //变暗(三个-50，值越大则效果越深)
    public  static final float[] BT_SELECTED_DARK = new float[] { 1, 0, 0, 0, -50, 0, 1,
            0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0 };

    //变亮
    public static final float[] BT_SELECTED_LIGHT = new float[] { 1, 0, 0, 0, 50, 0, 1,
            0, 0, 50, 0, 0, 1, 0, 50, 0, 0, 0, 1, 0 };
    //恢复
    public static final float[] BT_NOT_SELECTED = new float[] { 1, 0, 0, 0, 0, 0,
            1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };

}
