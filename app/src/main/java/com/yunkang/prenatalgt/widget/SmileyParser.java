
package com.yunkang.prenatalgt.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;

import com.hlib.util.ScreenUtil;
import com.hlib.util.StringUtil;
import com.yunkang.prenatalgt.MApplication;
import com.yunkang.prenatalgt.R;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @作者： [欢欢 email：HuanHuanFu@wistronits.com]<br>
 * @版本： [V1.0.1, 2014-6-16]<br>
 * @描述： 表情<br>
 */
public class SmileyParser {
    public static final int EMOTION_DEL_RESOURCE = R.drawable.sl_emotion_del;

    private Context mContext;

    private static final int MAX_FACE_COUNT = 95;
    private String[] mSmileyTexts;
    private int[] mImageIds;
    private HashMap<String, Integer> mSmileyToRes;
    private HashMap<Integer, String> mResToSmiley;

    public static final int DEFAULT_SMILEY_TEXTS = R.array.default_smiley_texts;

    private Pattern mPattern;
    //private HashMap<Integer,Drawable> drawableList = new HashMap<Integer,Drawable>(); 


    public SmileyParser(Context context) {
        mContext = context;
        mSmileyTexts = mContext.getResources().getStringArray(DEFAULT_SMILEY_TEXTS);
        mSmileyToRes = new HashMap<String, Integer>(mSmileyTexts.length);
        mResToSmiley = new HashMap<Integer, String>(mSmileyTexts.length);
        mImageIds = new int[mSmileyTexts.length];
        buildSmileyToRes();
        mPattern = buildPattern();
    }

    private void buildSmileyToRes() {
        // 生成表情的id、含义及对应的Hash表，封装
        for (int i = 0, k = 0; i < MAX_FACE_COUNT; i++) {
            try {
                if (i < 10) {
                    Field field = R.drawable.class.getDeclaredField("f00" + i);
                    if (field == null) {
                        continue;
                    }
                    int resourceId = StringUtil.parseInt(field.get(null)
                            .toString());
                    if (resourceId == 0) {
                        continue;
                    }
                    mImageIds[k++] = resourceId;
                } else if (i < 100) {
                    Field field = R.drawable.class.getDeclaredField("f0" + i);
                    if (field == null) {
                        continue;
                    }
                    int resourceId = StringUtil.parseInt(field.get(null)
                            .toString());
                    mImageIds[k++] = resourceId;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        for (int i = 0; i < mSmileyTexts.length; i++) {
            String value = mSmileyTexts[i];
            mSmileyToRes.put(value, mImageIds[i]);
            mResToSmiley.put(mImageIds[i], value);
        }

    }

    // 构建正则表达式
    private Pattern buildPattern() {
        StringBuilder patternString = new StringBuilder(mSmileyTexts.length * 3);

        patternString.append('(');
        for (String s : mSmileyTexts) {
            patternString.append(Pattern.quote(s));
            patternString.append('|');
        }
        patternString.replace(patternString.length() - 1,
                patternString.length(), ")");
        return Pattern.compile(patternString.toString());
    }

    public int[] getImageIds() {
        return mImageIds;
    }

    /**
     * get the face text
     *
     * @param faceIndex
     * @return
     */
    public String getText(int faceIndex) {
        return mResToSmiley.get(getResource(faceIndex));
    }

    /**
     * get the resource id(the face drawable id) by the faceIndex
     *
     * @param faceIndex
     * @return
     */
    public int getResource(int faceIndex) {
        return mImageIds[faceIndex];
    }

    public CharSequence getImageSpan(int faceIndex) {
        String text = getText(faceIndex);
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        builder.setSpan(new ImageSpan(mContext, getResource(faceIndex)), 0,
                text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    /**
     * text中最后一个表情的长度
     *
     * @param text
     * @return
     */
    public int getLastLength(CharSequence text) {
        // text为空时，返回0
        if (text == null || text.length() <= 0) {
            return 0;
        }
        int len = 1;
        Matcher matcher = mPattern.matcher(text);
        while (matcher.find()) {
            len = matcher.end() - matcher.start();
        }
        return len;
    }

//    private Drawable getDrawable(int resId){
//        Drawable drawable = null;
//       if(drawableList.containsKey(resId)) {
//           drawable= drawableList.get(resId);
//       }else {
//           Drawable newDrawable = mContext.getResources().getDrawable(resId);
//           newDrawable.setBounds(0, 0, SystemUtil.dip2px(App.getContext(), 30),
//                   SystemUtil.dip2px(App.getContext(), 30));// 这里设置图片的大小
//           drawableList.put(resId, newDrawable);
//           drawable=newDrawable;
//       }
//        return drawable;
//    }

    // 根据文本替换成图片
    public synchronized CharSequence replace(CharSequence text) {
        if (text == null)
            return "";
        SpannableStringBuilder builder = new SpannableStringBuilder(text);

        Matcher matcher = mPattern.matcher(text);
        while (matcher.find()) {
            int resId = mSmileyToRes.get(matcher.group());
            // drawableList = new HashMap<Integer, Drawable>();
            //Drawable drawable = getDrawable(resId);

            Drawable drawable = mContext.getResources().getDrawable(resId);
            drawable.setBounds(0, 0, ScreenUtil.dip2px(MApplication.getContext(), 30),
                    ScreenUtil.dip2px(MApplication.getContext(), 30));// 这里设置图片的大小
            ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
            builder.setSpan(imageSpan, matcher.start(),
                    matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
             
            /* builder.setSpan(new ImageSpan(mContext, resId),
             matcher.start(),matcher.end(),
             Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);*/
        }
        return builder;

    }

}
