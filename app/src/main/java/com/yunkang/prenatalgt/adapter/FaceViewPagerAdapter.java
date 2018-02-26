/**
 *-----------------------------------------------------------------------
 * <copyright file="FaceViewPagerAdapter.java" company="SINOSUN">
 *     Copyright (c) Sinosun Technology Co., Ltd. All rights reserved.
 * </copyright>
 * <author>WH1406011</author>
 * <summary>This is the FaceViewPagerAdapter class.</summary>
 * -----------------------------------------------------------------------
 */
package com.yunkang.prenatalgt.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.yunkang.prenatalgt.widget.VFaceLayout;


/**
 * @作者： [欢欢 email：HuanHuanFu@wistronits.com]<br>
 * @版本： [V1.0.0, 2014-6-18]<br>
 * @描述： 表情适配器<br>
 */
public class FaceViewPagerAdapter extends PagerAdapter {
    // 每一页多少个表情
    VFaceLayout mLayout;

    private static final String TAG = "FaceViewPagerAdapter";
    private GridView[] views = new GridView[VFaceLayout.MAX_PAGE];
 


    public FaceViewPagerAdapter(Context context, VFaceLayout layout) {
        mLayout = layout;
    }

    /**
     * @return
     * 
     */
    @Override
    public int getCount() {
        return views.length;
    }

    /**
     * @param arg0
     * @param arg1
     * @return
     */
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d(TAG, "instantiateItem index:" + position);
        if (views[position] == null) {
            views[position] = mLayout.createGridView(position);
        }
        container.addView(views[position]);
        return views[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d(TAG, "destroyItem index:" + position);
        container.removeView(views[position]);
    }



}
