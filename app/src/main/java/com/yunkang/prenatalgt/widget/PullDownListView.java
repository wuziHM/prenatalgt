
package com.yunkang.prenatalgt.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.TextView;

import com.yunkang.prenatalgt.R;


/**
 * @作者： [欢欢 email：HuanHuanFu@wistronits.com]<br>
 * @版本： [V1.0.0, 2015-01-07]<br>
 * @描述：聊天分页控件<br>
 */
public class PullDownListView extends FrameLayout implements
        GestureDetector.OnGestureListener, Animation.AnimationListener {

    public static int MAX_LENGHT = 0;
    public static final int STATE_REFRESH = 1;
    public static final int SCROLL_TO_CLOSE = 2;
    public static final int SCROLL_TO_REFRESH = 3;
    public static final double SCALE = 0.9d;
    private static final int CLOSEDELAY = 100;  //300
    private static final int REFRESHDELAY = 100; //300
    private Animation mAnimationDown;
    private Animation mAnimationUp;
    // 向上向下控件
    private ImageView mArrow;
    // 头部View
    private View emptyHeaderView;
    private ProgressBar mProgressBar;
    private TextView more;
    private ProgressBar mProgressBar2;
    private int mState;
    private TextView mTitle;
    public ListView mListView;
    private LinearLayout foot;
    private LinearLayout footer_layout;
    private LinearLayout header;
    private GestureDetector mDetector;
    private FlingRunnable mFlinger;
    private int mPading;
    private int mDestPading;
    private int mLastTop;
    private LinearLayout mFirstChild;
    private FrameLayout mUpdateContent;
    private OnRefreshListioner mRefreshListioner;
    // 是否自动加载更多true为是false为不是
    private boolean isAutoLoadMore = false;
    private boolean hasMore = true;
    private boolean isEnd = true;
    private boolean listviewDoScroll = false;
    private boolean isFirstLoading = false;
    private boolean mLongPressing;// 如果设置为true说明刚好到了执行长按的时间
    private boolean mPendingRemoved = false;//
    // 向下拉
    //private String pulldowntorefresh;
    // 松开加载更多
    //private String releasetorefresh;
    // 正在加载
    //private String loading;
    private MotionEvent downEvent;
    private CheckForLongPress mPendingCheckForLongPress = new CheckForLongPress();
    private CheckForLongPress2 mPendingCheckForLongPress2 = new CheckForLongPress2();
    // 拖动的数据值
    private float lastY;
    private boolean useempty = true;
    // 是否最后一页 如果是则是true 不是为 false
    private boolean isLastPage = false;

    /**
     * 长按检查方法执行1线程
     * 
     * @author Administrator
     */
    private class CheckForLongPress implements Runnable {
        public void run() {
            if (mListView.getOnItemLongClickListener() == null) {
            } else {
                postDelayed(mPendingCheckForLongPress2, 100);
            }
        }
    }

    /**
     * 长按检查方法执行2线程 ----> 延后 100
     * 
     * @author Administrator
     */
    private class CheckForLongPress2 implements Runnable {
        @SuppressLint("Recycle") public void run() {
            mLongPressing = true;
            MotionEvent e = MotionEvent.obtain(
                    downEvent.getDownTime(),
                    downEvent.getEventTime()
                            + ViewConfiguration.getLongPressTimeout(),
                    MotionEvent.ACTION_CANCEL, downEvent.getX(),
                    downEvent.getY(), downEvent.getMetaState());
            PullDownListView.super.dispatchTouchEvent(e);
        }
    }

    class FlingRunnable implements Runnable {
        private void startCommon() {
            removeCallbacks(this);
        }

        public void run() {
            boolean noFinish = mScroller.computeScrollOffset();
            int curY = mScroller.getCurrY();
            int deltaY = curY - mLastFlingY;
            if (noFinish) {
                move(deltaY, true);
                mLastFlingY = curY;
                post(this);
            } else {
                removeCallbacks(this);
                if (mState == SCROLL_TO_CLOSE) {
                    mState = -1;
                }
            }
        }

        public void startUsingDistance(int distance, int duration) {
            if (distance == 0)
                distance--;
            startCommon();
            mLastFlingY = 0;
            mScroller.startScroll(0, 0, 0, distance, duration);
            post(this);
        }

        private int mLastFlingY;
        private Scroller mScroller;

        public FlingRunnable() {
            mScroller = new Scroller(getContext());
        }
    }

    /**
     * 下拉刷新以及加载更多回调监听接口
     * 
     * @author Administrator
     */
    public interface OnRefreshListioner {
        /**
         * @method: onRefresh
         * @Description: 向下加载后返回接口
         * @param isLastPage
         * @throws
         */
        void onRefresh(boolean isLastPage);

        /**
         * @method: onLoadMore
         * @Description: 向下点击加载更多返回接口
         * @throws
         */
        void onLoadMore();
    }

    /**
     * 直接new时调用的构造方法
     * 
     * @param context
     */
    public PullDownListView(Context context) {
        super(context);
        mDetector = new GestureDetector(context, this);
        mFlinger = new FlingRunnable();
        init();
        addRefreshBar();
    }

    /**
     * 在xml中使用时调用的构造方法
     * 
     * @param context
     */
    public PullDownListView(Context context, AttributeSet att) {
        super(context, att);
        useempty = att.getAttributeBooleanValue(null, "useempty", true);
        mDetector = new GestureDetector(this);
        mFlinger = new FlingRunnable();
        init();
        addRefreshBar();
    }

    View view;

    /**
     * 添加刷新头部的控件
     */
    private void addRefreshBar() {
        // 向上滑动的动画
        mAnimationUp = AnimationUtils.loadAnimation(getContext(),
                R.anim.rotate_up);
        mAnimationUp.setAnimationListener(this);

        // 向下滑动的动画
        mAnimationDown = AnimationUtils.loadAnimation(getContext(),
                R.anim.rotate_down);
        mAnimationDown.setAnimationListener(this);

        // 刷新头部的view
        view = LayoutInflater.from(getContext()).inflate(R.layout.refresh_bar, null);
        // 添加view在本控件中
        addView(view);

        /*
         * 以下都是刷新头部的一些控件的设置
         */
        mFirstChild = (LinearLayout) view;
        mUpdateContent = getChildAt(0).findViewById(R.id.iv_content);
        mArrow = new ImageView(getContext());
        FrameLayout.LayoutParams layoutparams = new FrameLayout.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        mArrow.setScaleType(ImageView.ScaleType.FIT_CENTER);
        mArrow.setLayoutParams(layoutparams);
        mArrow.setImageResource(R.mipmap.arrow_down);
        mUpdateContent.addView(mArrow);
        //欢欢增加
        mArrow.setVisibility(View.GONE);

        FrameLayout.LayoutParams layoutparams1 = new FrameLayout.LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        layoutparams1.gravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;
        mProgressBar = new ProgressBar(getContext(), null,
                android.R.attr.progressBarStyleSmallInverse);
        mProgressBar.setIndeterminate(false);

        int i = getResources().getDimensionPixelSize(R.dimen.p10);
        mProgressBar.setPadding(i, i, i, i);
        mProgressBar.setLayoutParams(layoutparams1);
        mProgressBar.setIndeterminateDrawable(this.getResources().getDrawable(R.drawable.message_progress_send_state));
        mUpdateContent.addView(mProgressBar);
        mTitle = findViewById(R.id.tv_title);
    }

    public void setGone() {
        mTitle.setVisibility(View.GONE);
        mUpdateContent.setVisibility(View.GONE);
    }

    @SuppressLint("ResourceAsColor")
    protected void onFinishInflate() {
        super.onFinishInflate();
        mListView = (ListView) getChildAt(1);
        mListView.setCacheColorHint(R.color.c_00000000);
        footer_layout = (LinearLayout) LayoutInflater.from(getContext()).inflate(
                R.layout.empty_main, null);
        foot = (LinearLayout) LayoutInflater.from(getContext())
                .inflate(R.layout.refresh_head, null);
        more = foot.findViewById(R.id.ref);
        mProgressBar2 = foot.findViewById(R.id.refbar);
        mProgressBar2.setVisibility(View.GONE);
        if (useempty) {
            header = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.empty_main,
                    null);
            mListView.addHeaderView(header);
        }
        mListView.addFooterView(footer_layout);

        foot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!isAutoLoadMore)
                    onLoadMore();
            }
        });

        mListView.setOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isEnd && scrollState == SCROLL_STATE_IDLE && hasMore
                        && isAutoLoadMore) {
                    onLoadMore();
                }
            }

            public void onScroll(AbsListView view, int f, int v, int t) {
                if (isAutoLoadMore) {
                    isEnd = f + v >= t - 1;
                }
            }
        });
    }

    /**
     * 设置没有数据时默认图片
     *
     * @param empty
     */
    public void setEmptyHeaderView(View empty) {
        emptyHeaderView = empty;
    }

    /**
     * 添加空的定部view
     */
    public void addEmptyHeaderView() {
        header.removeAllViews();
        if (emptyHeaderView != null)
            header.addView(emptyHeaderView);
    }

    /**
     * 移除顶部空view
     */
    public void removeEmptyHeaderView() {
        if (emptyHeaderView != null)
            header.removeView(emptyHeaderView);
    }

    /**
     * 初始化设置及变量
     */
    private void init() {
        MAX_LENGHT = getResources().getDimensionPixelSize(
                R.dimen.p62);// 62.0dip
        setDrawingCacheEnabled(false);
        setBackgroundDrawable(null);
        setClipChildren(false);
        mDetector.setIsLongpressEnabled(false);
        mPading = -MAX_LENGHT;
        mLastTop = -MAX_LENGHT;
        //pulldowntorefresh = getContext().getText(R.string.drop_dowm).toString();
        //releasetorefresh = getContext().getText(R.string.release_update)
          //      .toString();
        //loading = getContext().getText(R.string.loading).toString();
    }

    /**
     * @method: isRefresh
     * @Description: 设置页数状态
     * @param lastpage
     * @throws
     */
    public void isLastPage(boolean lastpage) {
        isLastPage = lastpage;
    }

    /** deltaY > 0 向上 */
    private boolean move(float deltaY, boolean auto) {
        // move 方法执行 "
        if (deltaY > 0 && mFirstChild.getTop() == -MAX_LENGHT) {
            mPading = -MAX_LENGHT;
            return false;
        }

        if (auto) {
            // move 方法执行
            if (mFirstChild.getTop() - deltaY < mDestPading) {
                deltaY = mFirstChild.getTop() - mDestPading;
            }
            mFirstChild.offsetTopAndBottom((int) -deltaY);
            mListView.offsetTopAndBottom((int) -deltaY);
            mPading = mFirstChild.getTop();
            if (mDestPading == 0 && mFirstChild.getTop() == 0  && mState == SCROLL_TO_REFRESH) {
                // onRefresh 刷新方法执行
                onRefresh(isLastPage);
            } else if (mDestPading == -MAX_LENGHT) {
            }
            invalidate();
            updateView();
            return true;
        } else {
            if (mState != STATE_REFRESH
                    || (mState == STATE_REFRESH && deltaY > 0)) {
                mFirstChild.offsetTopAndBottom((int) -deltaY);
                mListView.offsetTopAndBottom((int) -deltaY);
                mPading = mFirstChild.getTop();
            } else if (mState == STATE_REFRESH && deltaY < 0
                    && mFirstChild.getTop() <= 0) {
                if (mFirstChild.getTop() - deltaY > 0) {
                    deltaY = mFirstChild.getTop();
                }
                mFirstChild.offsetTopAndBottom((int) -deltaY);
                mListView.offsetTopAndBottom((int) -deltaY);
                mPading = mFirstChild.getTop();
            }
        }
        if (deltaY > 0 && mFirstChild.getTop() <= -MAX_LENGHT) {
            mPading = -MAX_LENGHT;
            deltaY = -MAX_LENGHT - mFirstChild.getTop();
            mFirstChild.offsetTopAndBottom((int) deltaY);
            mListView.offsetTopAndBottom((int) deltaY);
            mPading = mFirstChild.getTop();
            updateView();
            invalidate();
            return false;
        }
        updateView();
        invalidate();
        return true;
    }

    /**
     * @method: updateView
     * @Description: 刷新控件
     * @throws
     */
    private void updateView() {
        // 如果是最后一页则把上面的控件隐藏
        if (isLastPage) {
            mArrow.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
            mTitle.setVisibility(View.GONE);
            return;
        }

        //String s = "";
        if (mState != STATE_REFRESH) {
            if (mFirstChild.getTop() < 0) {
//                mArrow.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
                //mTitle.setText(pulldowntorefresh);

//                if (mLastTop >= 0 && mState != SCROLL_TO_CLOSE) {
//                    mArrow.startAnimation(mAnimationUp);// 向上移动动画
//                }
            } else if (mFirstChild.getTop() > 0) {
//                mTitle.setText(releasetorefresh + s);
                mProgressBar.setVisibility(View.VISIBLE);
//                mArrow.setVisibility(View.VISIBLE);

//                if (mLastTop <= 0) {
//                    mArrow.startAnimation(mAnimationDown);// 向下移动动画
//                }
            }
        }
        mLastTop = mFirstChild.getTop();
    }

    // release 方法执行
    private boolean release() {
        if (listviewDoScroll) {
            listviewDoScroll = false;
            return true;
        }
        if (mFirstChild.getTop() > 0) {
            scrollToUpdate(false);
        } else {
            scrollToClose();
        }
        invalidate();
        return false;
    }

    private void scrollToClose() {
        mDestPading = -MAX_LENGHT;
        mFlinger.startUsingDistance(MAX_LENGHT, CLOSEDELAY);
    }

    // scrollToUpdate 方法执行
    public void scrollToUpdate(boolean load) {
        mState = SCROLL_TO_REFRESH;

        mDestPading = 0;
        if (load) {
            mFlinger.startUsingDistance(50, REFRESHDELAY);
            load = false;
        } else
            mFlinger.startUsingDistance(mFirstChild.getTop(), REFRESHDELAY);
    }

    /**
     * @method: onRefresh
     * @Description: 向上拖动停止时调用,设置数据回调
     * @param lastPage 欢欢注释
     * @throws
     */
    private void onRefresh(boolean lastPage) {
        mState = STATE_REFRESH;
//        mTitle.setText(loading);
        mProgressBar.setVisibility(View.VISIBLE);
        mArrow.setVisibility(View.INVISIBLE);
        if (mRefreshListioner != null) {
            mRefreshListioner.onRefresh(lastPage);
        }
    }

    /**
     * @method: onRefreshComplete
     * @Description: 刷新完毕隐藏控件
     * @throws
     */
    public void onRefreshComplete() {
        mState = SCROLL_TO_CLOSE;
        mArrow.setImageResource(R.mipmap.arrow_down);
        mProgressBar2.setVisibility(View.INVISIBLE);
        updateCommon();
        scrollToClose();
    }

    /**
     * @method: setMore
     * @Description: 设置是否显示更多
     * @param hasMore
     * @throws
     */
    public void setMore(boolean hasMore) {
        if (hasMore) {
            mListView.setFooterDividersEnabled(true);
            footer_layout.removeAllViews();
            footer_layout.addView(foot);
        } else {
            mListView.setFooterDividersEnabled(false);
            footer_layout.removeAllViews();
        }
    }

    private void updateCommon() {
        if (mListView.getCount() == (mListView.getHeaderViewsCount() + mListView
                .getFooterViewsCount())) {
            if (useempty)
                addEmptyHeaderView();
        } else {
            removeEmptyHeaderView();
            mListView.setFooterDividersEnabled(false);
            footer_layout.removeAllViews();
        }
    }

    public void setFoot() {
        footer_layout.setVisibility(View.GONE);
    }

    public void onFirstLoad() {
        if (footer_layout.getChildCount() == 0) {
            footer_layout.addView(foot);
        }
        isFirstLoading = true;
        foot.setEnabled(false);
        // onFirstLoad 方法执行
        mState = STATE_REFRESH;
        mProgressBar2.setVisibility(View.VISIBLE);
        more.setText(R.string.loading);
    }

    public void onLoadMore() {
        // onLoadMore 方法执行
        foot.setEnabled(false);
        mState = STATE_REFRESH;
        mProgressBar2.setVisibility(View.VISIBLE);
        more.setText(R.string.loading);
        if (mRefreshListioner != null) {
            mRefreshListioner.onLoadMore();
        }
    }

    public void onLoadMoreComplete(String date) {
        mState = -1;
        mProgressBar2.setVisibility(View.INVISIBLE);
        more.setText(R.string.seen_more);
        updateCommon();
        if (isFirstLoading)
            isFirstLoading = false;
        foot.setEnabled(true);
    }

    public void onLoadMoreComplete() {
        onLoadMoreComplete(null);
    }

    public boolean dispatchTouchEvent(MotionEvent e) {
        if (isFirstLoading) {
            return false;
        }
        int action;
        float y = e.getY();
        action = e.getAction();
        if (mLongPressing && action != MotionEvent.ACTION_DOWN) {
            return false;
        }
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            mLongPressing = true;
        }
        boolean handled = true;
        handled = mDetector.onTouchEvent(e);
        switch (action) {
            case MotionEvent.ACTION_UP:
                boolean f1 = mListView.getTop() <= e.getY()
                        && e.getY() <= mListView.getBottom();
                if (!handled && mFirstChild.getTop() == -MAX_LENGHT && f1
                        || mState == STATE_REFRESH) {
                    super.dispatchTouchEvent(e);
                } else {
                    // 执行释放方法
                    handled = release();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                handled = release();
                super.dispatchTouchEvent(e);
                break;
            case MotionEvent.ACTION_DOWN:
                downEvent = e;
                mLongPressing = false;
                // 长按的时间间隔
                postDelayed(mPendingCheckForLongPress,
                        ViewConfiguration.getLongPressTimeout() + 100);
                mPendingRemoved = false;
                super.dispatchTouchEvent(e);
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaY = lastY - y;
                lastY = y;
                if (!mPendingRemoved) {
                    removeCallbacks(mPendingCheckForLongPress);
                    mPendingRemoved = true;
                }

                if (!handled && mFirstChild.getTop() == -MAX_LENGHT) {
                    try {
                        return super.dispatchTouchEvent(e);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return true;
                    }
                } else if (handled && mListView.getTop() > 0 && deltaY < 0) {// deltaY小于0，向�?
                    e.setAction(MotionEvent.ACTION_CANCEL);
                    super.dispatchTouchEvent(e);
                }
                break;
            default:
                break;
        }
        return true;
    }

    public void onAnimationEnd(Animation animation) {
        int top = mFirstChild.getTop();
        if (top < 0){
            mArrow.setImageResource(R.mipmap.arrow_down);
        }else if (top > 0){
            mArrow.setImageResource(R.mipmap.arrow_up);
        }else {
            if (top < mLastTop) {
                mArrow.setImageResource(R.mipmap.arrow_down);
            } else {
                mArrow.setImageResource(R.mipmap.arrow_up);
            }
        }
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }

    public boolean onDown(MotionEvent e) {
        return false;
    }

    public boolean onFling(MotionEvent motionevent, MotionEvent e, float f,
                           float f1) {
        return false;
    }

    protected void onLayout(boolean flag, int i, int j, int k, int l) {
        int top = mPading;
        int w = getMeasuredWidth();
        mFirstChild.layout(0, top, w, top + MAX_LENGHT);

        int h = getMeasuredHeight() + mPading + MAX_LENGHT;
        mListView.layout(0, top + MAX_LENGHT, w, h);
    }

    public void onLongPress(MotionEvent e) {
    }

    /** deltaY > 0 向上 */
    public boolean onScroll(MotionEvent curdown, MotionEvent cur, float deltaX,
                            float deltaY) {
        deltaY = (float) ((double) deltaY * SCALE);
        boolean handled = false;
        boolean flag = false;
        if (mListView.getCount() == 0) {
            flag = true;
        } else {
            View c = mListView.getChildAt(0);
            if (mListView.getFirstVisiblePosition() == 0 && c != null
                    && c.getTop() == 0) {
                flag = true;
            }
        }
        // deltaY < 0 向下
        if (deltaY < 0F && flag || getChildAt(0).getTop() > -MAX_LENGHT) {
            handled = move(deltaY, false);
        } else
            handled = false;
        return handled;
    }

    public void onShowPress(MotionEvent motionevent) {
    }

    public boolean onSingleTapUp(MotionEvent motionevent) {
        return false;
    }

    public void setRefreshListioner(OnRefreshListioner RefreshListioner) {
        mRefreshListioner = RefreshListioner;
    }

    public boolean isAutoLoadMore() {
        return isAutoLoadMore;
    }

    public void setAutoLoadMore(boolean isAutoLoadMore) {
        this.isAutoLoadMore = isAutoLoadMore;
        if (!isAutoLoadMore) {
            foot.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onLoadMore();
                }
            });
            mListView.setOnScrollListener(null);
        } else {
            mListView.setOnScrollListener(new OnScrollListener() {
                public void onScrollStateChanged(AbsListView view,
                        int scrollState) {
                    if (isEnd && scrollState == SCROLL_STATE_IDLE && hasMore) {
                        onLoadMore();
                    }
                }

                public void onScroll(AbsListView view, int f, int v, int t) {
                    isEnd = f + v >= t - 1;
                }
            });
            foot.setOnClickListener(null);
        }
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public void removeFoot() {
        footer_layout.removeAllViews();
    }

    public void addFoot() {
        footer_layout.removeAllViews();
        footer_layout.addView(foot);
    }

}
