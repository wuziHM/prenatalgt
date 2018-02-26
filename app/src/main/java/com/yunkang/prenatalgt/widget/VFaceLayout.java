package com.yunkang.prenatalgt.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;

import com.yunkang.prenatalgt.R;
import com.yunkang.prenatalgt.adapter.FaceViewPagerAdapter;

/**
 * @作者： [付欢欢 email：HuanHuanFu@wistronits.com]<br>
 * @版本： [V1.0.0, 2014-4-23]<br>
 * @描述：表情控件<br>
 */
public class VFaceLayout extends RelativeLayout {
    public static final int MAX_PAGE_COUNT = 24;
    public static final int MAX_PAGE = 4;
    private ViewPager mViewPager;
    private ImageView mCursor;
    private Context mContext;
    private static String TAG = "FaceLayout";
    
    public interface OnDeleteListener {
        void onDelete();
    }

    public interface OnFaceClickListener {
        void onFaceClick(int face);
    }

    public void setOnFaceClickListener(OnFaceClickListener listener) {
        mOnFaceClickListener = listener;
    }
    
    public void setDeleteListener(OnDeleteListener listener) {
        mOnDeleteListener = listener;
    }

    /**
     * @param smileyParser
     */
    public void setSmileyParser(SmileyParser smileyParser) {
        mSmileyParser = smileyParser;
    }

    private OnDeleteListener mOnDeleteListener;
    private OnFaceClickListener mOnFaceClickListener;
    
    private int[] cursorBg = {R.mipmap.cursor_1, R.mipmap.cursor_2, R.mipmap.cursor_3, R.mipmap.cursor_4};
    
    private SmileyParser mSmileyParser;
    public VFaceLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater li;
        li = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(R.layout.face_layout, this);
        findView();
        setListener();
    }

    private void findView() {
        mViewPager = findViewById(R.id.view_pager);
        mCursor = findViewById(R.id.cursor_switch_page);
        mCursor.setImageResource(cursorBg[0]);
    }

    private void setListener() {
        FaceViewPagerAdapter adapter = new FaceViewPagerAdapter(mContext, this);
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
                Log.d(TAG, "onPageScrollStateChanged index:" + arg0);
            }

            @Override
            public void onPageScrolled(int position, float arg1, int arg2) {
                Log.d(TAG, "onPageScrolled position:" + position + " arg2:"
                        + arg2);
            }

            @Override
            public void onPageSelected(int arg0) {
                Log.d(TAG, "onPageSelected index:" + arg0);
                if (arg0 < MAX_PAGE && arg0 >= 0) {
                    mCursor.setImageResource(cursorBg[arg0]);
                }
            }
        });
    }

    public GridView createGridView(int page) {

        int[] imageIds = new int[MAX_PAGE_COUNT];
        final int[] mImageIds = mSmileyParser.getImageIds();

        // 取24个表情的id，封装
        for (int i = 0; i < (imageIds.length - 1); i++) {
            int faceIndex = getFaceIndex(page, i);
            if (faceIndex < mImageIds.length) {
                imageIds[i] = mImageIds[faceIndex];
            } else {
                imageIds[i] = 0;
            }
//            CJLog.d(TAG, "faceIndex:" + faceIndex + " imageIds[i]:" + imageIds[i]);
        }

        imageIds[MAX_PAGE_COUNT - 1] = SmileyParser.EMOTION_DEL_RESOURCE;
        GridView view = new GridView(mContext);

        view.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        view.setTag(page);
        ListAdapter adapter = new FaceItemAdapter(mContext, imageIds,
                R.layout.team_layout_single_expression_cell, view);
        view.setAdapter(adapter);
        view.setNumColumns(6);
        view.setHorizontalSpacing(1);
        view.setVerticalSpacing(1);
        view.setGravity(Gravity.CENTER);
        view.setOnItemClickListener(mItemClickListener);
        Log.d(TAG, "getCount:" + adapter.getCount());

        return view;

    }

    private OnItemClickListener mItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            int page = (Integer) parent.getTag();
            int faceIndex = getFaceIndex(page, position);
            Log.d(TAG, "onItemClick page:" + page + " position:" + position
                    + " faceIndex:" + faceIndex);
            if (position < MAX_PAGE_COUNT - 1) {
                if (mOnFaceClickListener != null) {
                    mOnFaceClickListener.onFaceClick(faceIndex);
                }
            } else if (position == MAX_PAGE_COUNT - 1) {
                if (mOnDeleteListener != null) {
                    mOnDeleteListener.onDelete();
                }
            }

        }

    };

    private int getFaceIndex(int page, int position) {
        int faceIndex = page * (MAX_PAGE_COUNT - 1) + position;
        return faceIndex;
    }

    static class FaceItemAdapter extends BaseAdapter {
        private int mTo = R.id.image;

        private int[] mData;

        private int mResource;
        private LayoutInflater mInflater;
        int height;
        int width;
        GridView view;

        /**
         * 
         */
        public FaceItemAdapter(Context context, int[] data, int resource, GridView view) {
            mResource = resource;
            mData = data;
            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.view = view;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            height = view.getHeight() / 4 - 1;
            width = view.getWidth() / 6;
            View v;
            if (convertView == null) {
                v = mInflater.inflate(mResource, parent, false);
            } else {
                v = convertView;
            }
            
            v.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, height));

            bindView(position, v);

            return v;
        }

        /**
         * @param position
         * @param view
         */
        private void bindView(int position, View view) {

            final ImageView v = view.findViewById(mTo);

            setViewImage(v, mData[position]);

        }

        private void setViewImage(ImageView v, int value) {
            v.setImageResource(value);
        }

        /**
         * @see android.widget.Adapter#getCount()
         */
        public int getCount() {
            return mData.length;
        }

        /**
         * @see android.widget.Adapter#getItem(int)
         */
        public Object getItem(int position) {
            return mData[position];
        }

        /**
         * @see android.widget.Adapter#getItemId(int)
         */
        public long getItemId(int position) {
            return position;
        }
    }
}
