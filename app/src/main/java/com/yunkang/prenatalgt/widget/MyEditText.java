/**
 *-----------------------------------------------------------------------
 * <copyright file="MyEditText.java" company="SINOSUN">
 *     Copyright (c) Sinosun Technology Co., Ltd. All rights reserved.
 * </copyright>
 * <author>WH1406009</author>
 * <summary>This is the Widget class.</summary>
 * -----------------------------------------------------------------------
 */
package com.yunkang.prenatalgt.widget;

import android.content.Context;
import android.util.AttributeSet;

/** 
 * @ClassName: MyEditText 
 * @Description: TODO
 * @date 2014年11月19日 
 */
public class MyEditText extends android.support.v7.widget.AppCompatEditText {
    
    public static String TEXT_SIZE = "textSize";

    public static String TEXT_COLOR = "textColor";

    public static String BOLD_FLAG = "bold";

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);  
    }

    public MyEditText(Context context) {
        super(context);
    }
    
    
    private void init(Context contex, AttributeSet attrs) {
        modifyTextAbbritute();
    }
    
    
    private void modifyTextAbbritute() {
//        Object o = getTag();
//        String tag = null;
//        if (o != null && o instanceof String) {
//            tag = (String) o;
//        }
//        // 需要加粗
//        if (!TextUtils.isEmpty(tag) && tag.equals(BOLD_FLAG)) {
//            setTextBold(true);
//        }
    }
    
    
    // 动态调整字体是否加粗
//    public void setTextBold(boolean bold){
//        TextPaint tp = getPaint();
//        tp.setFakeBoldText(bold);
//    }
    

}
