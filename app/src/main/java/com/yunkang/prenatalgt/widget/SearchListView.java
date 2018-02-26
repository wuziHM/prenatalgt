
package com.yunkang.prenatalgt.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.yunkang.prenatalgt.R;

import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * @作者： [欢欢 email：HuanHuanFu@wistronits.com]<br>
 * @版本： [V1.0.0, 2015-08-3]<br>
 * @描述：带有搜索的ListView<br> 同时附带下拉刷新功能<br><br>
 */
public class SearchListView extends ListView implements OnScrollListener,
        android.widget.AdapterView.OnItemClickListener,
        android.widget.AdapterView.OnItemLongClickListener,
        android.widget.AdapterView.OnItemSelectedListener {

    private static final String TAG = "listview";

    private final static int SCROLL_DURATION = 400; // scroll back duration

    private final static float OFFSET_RADIO = 2.5f; // support iOS like pull

    /**
     * 状态
     **/
    private int state = TOUCH_STATE_DONE;

    /**
     * 完成
     **/
    private final static int TOUCH_STATE_DONE = 0;
    /**
     * 松开更新
     **/
    private final static int TOUCH_STATE_RELEASE_TO_REFRESH = 1;
    /**
     * 下拉更新
     **/
    private final static int TOUCH_STATE_PULL_TO_REFRESH = 2;
    /**
     * 正在刷新
     **/
    private final static int TOUCH_STATE_REFRESHING = 3;

    /**
     * 标识查看更多状态
     **/
    private boolean isMore = false;

    private Scroller scroller;

    /**
     * 头部刷新的布局
     **/
    private View viewHeader;
    /**
     * 尾部加载更多的布局
     **/
    private View viewFooter;

    private int headerContentHeight; // 记录头部的高度

    /**
     * 头部显示下拉刷新等的控件
     **/
    private TextView tipsTextview;

    /**
     * 箭头图标
     **/
    private ImageView arrowImageView;

    /**
     * 内容显示
     **/
    private RelativeLayout layoutContent;
    private RelativeLayout head_contentLayout;

    /**
     * 显示动画
     **/
    private RotateAnimation animation;

    /**
     * 头部回退显示动画
     **/
    private RotateAnimation reverseAnimation;

    /**
     * 头部高度
     **/
    private int headHeight;

    /**
     * 第一次记录的Y轴坐标
     **/
    private float firstY;

    /**
     * 最后一次记录的Y轴坐标
     **/
    private float lastY;

    /**
     * 总的item个数
     */
    private int totalItemCount;

    /**
     * 是否要使用下拉刷新功能
     **/
    public boolean enablePullRefresh = true;

    /**
     * 是否自动获取更多
     */
    private boolean isAutoFetchMore = false;
    //是否隐藏头部控件
    private boolean isHiddenView = false;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnItemSelectedListener onItemSelectedListener;

    //下拉刷新获取更多
    private OnRefreshListener onRefreshListener;
    //底部分页
    private OnLastItemVisibleListener onLastItemVisibleListener;
    private TextView txtFooter;
    private ProgressBar progressBarFooter;


    public SearchListView(Context context) {
        super(context);
        init();
    }

    public SearchListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setStyle(context, attrs);
        init();
    }

    public SearchListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setStyle(context, attrs);
        init();
    }


    //增加自定义属性
    private void setStyle(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.searchListLayout);
        isHiddenView = a.getBoolean(R.styleable.searchListLayout_isHidden, false);
        a.recycle();
    }

    private void init() {
        // 屏蔽xml文件不能显示视图
        if (!isInEditMode()) {
            scroller = new Scroller(getContext(), new DecelerateInterpolator());

            measureView(getHeaderView());
            headHeight = getHeaderView().getMeasuredHeight();
            getHeaderView().setPadding(0, -1 * headHeight, 0, 0);
            getHeaderView().invalidate();

            // 添加头部视图
            super.addHeaderView(getHeaderView(), null, false);

            setOnScrollListener(this);

            // 初始化动画效果
            animation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            animation.setInterpolator(new LinearInterpolator());
            animation.setDuration(250);
            animation.setFillAfter(true);

            reverseAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);

            reverseAnimation.setInterpolator(new LinearInterpolator());
            reverseAnimation.setDuration(200);
            reverseAnimation.setFillAfter(true);

            super.setOnItemClickListener(this);
            super.setOnItemLongClickListener(this);
            super.setOnItemSelectedListener(this);
        }
    }

    /**
     * 获取头部视图
     *
     * @return
     */
    private View getHeaderView() {
        // 屏蔽xml文件不能显示视图
        if (!isInEditMode()) {
            if (viewHeader == null) {
                viewHeader = LayoutInflater.from(getContext()).inflate(
                        R.layout.model_pull_listview_head, null);

                arrowImageView = viewHeader.findViewById(R.id.head_arrowImageView);
                arrowImageView.setMinimumWidth(70);
                arrowImageView.setMinimumHeight(50);

                tipsTextview = viewHeader.findViewById(R.id.head_tipsTextView);

                layoutContent = viewHeader.findViewById(R.id.layoutContent);
                head_contentLayout = viewHeader
                        .findViewById(R.id.head_contentLayout);
                // 如果为true就不显示
                if (isHiddenView) {
                    head_contentLayout.setVisibility(View.GONE);
                }

                final ViewConfiguration configuration = ViewConfiguration.get(getContext());
                mTouchSlop = configuration.getScaledTouchSlop();
            }
        }
        return viewHeader;
    }

    /**
     * 设置头部显示状态
     *
     * @return
     */
    public void setHeadHidden() {
        if (head_contentLayout != null) {
            head_contentLayout.setVisibility(View.GONE);
        }

    }

    /**
     * 获取尾部视图
     *
     * @return
     */
    private View getFooterView() {
        if (viewFooter == null) {
            viewFooter = LayoutInflater.from(getContext()).inflate(
                    R.layout.model_pull_listview_footer, null);

            txtFooter = viewFooter.findViewById(R.id.pulldown_footer_text);
            progressBarFooter = viewFooter.findViewById(R.id.pulldown_footer_loading);

            viewFooter.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isMore) {
                        isMore = true;

                        txtFooter.setText(getContext().getResources().getString(R.string.loading));
                        progressBarFooter.setVisibility(View.VISIBLE);

                        if (onLastItemVisibleListener != null) {
                            onLastItemVisibleListener.onLastItemVisible();
                        }
                    }
                }
            });
        }

        return viewFooter;
    }

    /**
     * 添加头部的view 与下拉刷新不冲突
     */
    public void addHeaderView(View header) {
        if (null == header) {
            return;
        }

        layoutContent.addView(header);

        measureView(header);
        headerContentHeight = header.getMeasuredHeight();

        headHeight += headerContentHeight;

        getHeaderView().setPadding(0, -1 * headHeight, 0, 0);
    }

    /**
     * 移除头部自定义添加内容
     */
    public void removeHeader() {
        layoutContent.removeAllViews();

        measureView(getHeaderView()); // 防止多次添加导致高度计算失误
        headHeight = getHeaderView().getMeasuredHeight();
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (onItemClickListener == null) {
            return;
        }

        onItemClickListener.onItemClick(parent, view, position > 0 ? position - 1 : 0, id);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (onItemSelectedListener == null) {
            return;
        }

        onItemSelectedListener.onItemSelected(parent, view, position > 0 ? position - 1 : 0, id);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (onItemLongClickListener == null) {
            return false;
        }

        return onItemLongClickListener.onItemLongClick(parent, view, position > 0 ? position - 1
                : 0, id);
    }

    @Override
    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.onItemSelectedListener = listener;
    }

    public Object getItemAtPosition(int position) {
        return super.getItemAtPosition(position + 1);
    }

    public long getItemIdAtPosition(int position) {
        return super.getItemIdAtPosition(position + 1);
    }

    /**
     * 设置是否可用下拉刷新
     *
     * @param enable
     */
    public void pullRefreshEnable(boolean enable) {
        enablePullRefresh = enable;
    }

    private final int TOUCH_STATE_REST = 0;
    private final int TOUCH_STATE_HORIZONTAL_SCROLLING = 1;
    private final int TOUCH_STATE_VERTICAL_SCROLLING = -1;

    private int mTouchState = TOUCH_STATE_REST;

    private int mTouchSlop;
    private float mLastMotionX;
    private float mLastMotionY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        final int action = ev.getAction();
        boolean intercept = false;

        switch (action) {
            case MotionEvent.ACTION_MOVE:
                if (mTouchState == TOUCH_STATE_HORIZONTAL_SCROLLING) {
                    intercept = false;
                } else if (mTouchState == TOUCH_STATE_VERTICAL_SCROLLING) {
                    intercept = true;
                } else {
                    final float x = ev.getX();
                    final int xDiff = (int) Math.abs(x - mLastMotionX);
                    boolean xMoved = xDiff > mTouchSlop;

                    final float y = ev.getRawY();
                    final int yDiff = (int) Math.abs(y - mLastMotionY);
                    boolean yMoved = yDiff > mTouchSlop;

                    if (xMoved) {
                        // Scroll if the user moved far enough along the X axis
                        if (xDiff >= yDiff)
                            mTouchState = TOUCH_STATE_HORIZONTAL_SCROLLING;
                        mLastMotionX = x;
                    }
                    if (yMoved) {
                        if (yDiff > xDiff)
                            mTouchState = TOUCH_STATE_VERTICAL_SCROLLING;
                        mLastMotionY = y;
                    }
                }
                break;

            case MotionEvent.ACTION_DOWN:
                mTouchState = TOUCH_STATE_REST;
                mLastMotionY = ev.getRawY();
                mLastMotionX = ev.getX();
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mTouchState = TOUCH_STATE_REST;
                intercept = false;
                break;
        }
        return intercept;
    }

    /**
     * 触摸事件的处理
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        cancelLongPress();
        switch (action) {
            case MotionEvent.ACTION_DOWN: // 按下的时候
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                firstY = ev.getRawY();
                lastY = firstY;
                // Log.v(TAG, "在down时候记录当前位置: X: " + ev.getX() + " Y: " +
                // ev.getRawY());
                break;
            case MotionEvent.ACTION_MOVE: // 手指正在移动的时候
                if (!enablePullRefresh) {
                    return false;
                }

                // if(firstY == -1){
                // firstY = ev.getRawY();
                // lastY = firstY;
                // }

                final float deltaY = ev.getRawY() - lastY;
                lastY = ev.getRawY();

                if (getFirstVisiblePosition() == 0
                        && (Math.abs(getHeaderView().getPaddingTop()) < headHeight || deltaY > 0)) {
                    final int paddingTop = getHeaderView().getPaddingTop();

                    getHeaderView().setPadding(0, (int) (paddingTop + deltaY / OFFSET_RADIO), 0, 0);

                    if (getHeaderView().getPaddingTop() > 0) {
                        if (state != TOUCH_STATE_RELEASE_TO_REFRESH
                                && state != TOUCH_STATE_REFRESHING) {
                            state = TOUCH_STATE_RELEASE_TO_REFRESH;
                            changeHeaderViewByState();
                        }
                    } else {
                        if (state != TOUCH_STATE_PULL_TO_REFRESH && state != TOUCH_STATE_REFRESHING) {
                            state = TOUCH_STATE_PULL_TO_REFRESH;
                            changeHeaderViewByState();
                        }
                    }
                } else if (getLastVisiblePosition() == totalItemCount - 1 && deltaY < 0) {
                    // last item, already pulled up or want to pull up.
                    Log.i(TAG, "Footer" + String.valueOf(-deltaY / OFFSET_RADIO));
                } else {
                    // 要兼容onInterceptTouchEvent 必须要这里做处理
                    // 必须加入速率的判断
                    // this.smoothScrollBy(-(int)deltaY, 0);
                }
                break;
            case MotionEvent.ACTION_UP: // 手指抬起来的时候
                if (getFirstVisiblePosition() == 0) {
                    // invoke refresh
                    if (enablePullRefresh) {
                        final int paddingTop = getHeaderView().getPaddingTop();

                        switch (state) {
                            case TOUCH_STATE_REFRESHING:
                                scroller.startScroll(0, paddingTop, 0, -paddingTop, SCROLL_DURATION);
                                break;
                            case TOUCH_STATE_PULL_TO_REFRESH:
                                if (paddingTop + headHeight >= headerContentHeight * 0.75) { // 高过搜索框0.75的
                                    // 那么显示搜索框
                                    scroller.startScroll(0, paddingTop, 0, -(headHeight
                                            - headerContentHeight + paddingTop), SCROLL_DURATION);
                                } else if (firstY - lastY < 0) { // 判断手势方向
                                    scroller.startScroll(0, paddingTop, 0, -(headHeight
                                            - headerContentHeight + paddingTop), SCROLL_DURATION);
                                } else {
                                    scroller.startScroll(0, paddingTop, 0,
                                            -(headHeight + paddingTop), SCROLL_DURATION);
                                }
                                break;
                            case TOUCH_STATE_RELEASE_TO_REFRESH:
                                state = TOUCH_STATE_REFRESHING; // 将进度切换到正在刷新
                                changeHeaderViewByState();
                                scroller.startScroll(0, paddingTop, 0, -paddingTop, SCROLL_DURATION);

                                if (onRefreshListener != null) {
                                    onRefreshListener.onRefresh();
                                }
                                break;

                            default:
                                break;
                        }
                    }
                } else if (getLastVisiblePosition() == totalItemCount - 1) {
                    // 数量充满屏幕才触发
                    if (isAutoFetchMore && !isMore && onLastItemVisibleListener != null
                            && isFillScreenItem() && viewFooter != null) {
                        txtFooter.setText(getContext().getResources().getString(R.string.loading));
                        progressBarFooter.setVisibility(View.VISIBLE);

                        isMore = true;
                        onLastItemVisibleListener.onLastItemVisible();
                        return true;
                    }
                } else {
                    // 这里只是为了方便计算
                    // 不在第一个和最后一个的时候 隐藏头部
                    // 不需要处理动画效果
                    getHeaderView().setPadding(0, -1 * headHeight, 0, 0);
                }
                Log.i(TAG, "up:" + getLastVisiblePosition() + "   " + (totalItemCount - 1));
                firstY = -1;
                lastY = -1; // reset
                break;
        }

        return super.onTouchEvent(ev);
    }

    /**
     * 条目是否填满整个屏幕
     */
    private boolean isFillScreenItem() {
        final int firstVisiblePosition = getFirstVisiblePosition();
        final int lastVisiblePostion = getLastVisiblePosition() - getFooterViewsCount();
        final int visibleItemCount = lastVisiblePostion - firstVisiblePosition + 1;
        final int totalItemCount = getCount() - getFooterViewsCount();

        return visibleItemCount < totalItemCount;

    }

    @Override
    public void computeScroll() {
        // 屏蔽xml文件不能显示视图
        if (!isInEditMode()) {
            if (scroller.computeScrollOffset()) {
                getHeaderView().setPadding(0, scroller.getCurrY(), 0, 0);
                postInvalidate();
            }
            super.computeScroll();
        }
    }

    /**
     * 是否开启自动获取更多 自动获取更多，将会隐藏footer，并在到达底部的时候自动刷新
     */
    public void setAutoFetchMore(boolean enable) {
        isAutoFetchMore = enable;

        if (viewFooter == null) {
            return;
        }

        txtFooter.setText(getContext().getResources().getString(R.string.load_more));
        progressBarFooter.setVisibility(View.GONE);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisiableItem, int visibleItemCount,
                         int totalItemCount) {
        this.totalItemCount = totalItemCount;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    /**
     * 计算view的宽高
     *
     * @param child
     */
    private void measureView(View child) {
        // 屏蔽xml文件不能显示视图
        if (!isInEditMode()) {
            ViewGroup.LayoutParams lp = child.getLayoutParams();
            if (lp == null) {
                lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, lp.width);
            int lpHeight = lp.height;
            int childHeightSpec;
            if (lpHeight > 0) {
                childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
            } else {
                childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            }
            child.measure(childWidthSpec, childHeightSpec);
        }
    }

    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss", Locale.getDefault());

    public void onRefreshComplete() {
        state = TOUCH_STATE_DONE;
        changeHeaderViewByState();

        final int paddingTop = getHeaderView().getPaddingTop();
        scroller.startScroll(0, paddingTop, 0, -(headHeight + paddingTop), SCROLL_DURATION);
        invalidate();

        isMore = false;

        if (viewFooter == null) {
            return;
        }

        txtFooter.setText(getContext().getResources().getString(R.string.load_more));
        progressBarFooter.setVisibility(View.GONE);
    }

    // 当状态改变时候，调用该方法，以更新界面
    private void changeHeaderViewByState() {
        switch (state) {
            case TOUCH_STATE_RELEASE_TO_REFRESH:
                Log.v(TAG, "当前状态，松开刷新");

                arrowImageView.setVisibility(View.VISIBLE);

                tipsTextview.setVisibility(View.VISIBLE);

                arrowImageView.clearAnimation();
                arrowImageView.startAnimation(animation);

                tipsTextview.setText(getContext().getResources().getString(
                        R.string.pull_to_refresh_release));
                break;
            case TOUCH_STATE_PULL_TO_REFRESH:
                Log.v(TAG, "当前状态，下拉刷新");

                tipsTextview.setVisibility(View.VISIBLE);
                arrowImageView.clearAnimation();
                arrowImageView.setVisibility(View.VISIBLE);

                arrowImageView.clearAnimation();
                arrowImageView.startAnimation(reverseAnimation);

                tipsTextview.setText(getContext().getResources().getString(
                        R.string.pull_to_refresh_pull_label));
                break;

            case TOUCH_STATE_REFRESHING:
                Log.v(TAG, "当前状态,正在刷新...");

                arrowImageView.clearAnimation();
                Animation operatingAnim = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
                LinearInterpolator lin = new LinearInterpolator();
                operatingAnim.setInterpolator(lin);

                arrowImageView.startAnimation(operatingAnim);

                tipsTextview.setText(getContext().getResources().getString(
                        R.string.pull_to_refresh_refreshing));
                break;
            case TOUCH_STATE_DONE:
                Log.v(TAG, "当前状态，done");

                arrowImageView.clearAnimation();
                arrowImageView.setImageResource(R.mipmap.ic_goicon);
                tipsTextview.setText(getContext().getResources().getString(
                        R.string.pull_to_refresh_pull_label));
                break;
        }
    }

    /**
     * 下拉刷新监听事件
     *
     * @param listener
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        this.onRefreshListener = listener;
    }

    /**
     * 获取更多监听事件
     *
     * @param listener
     */
    public void setOnLastItemVisibleListener(OnLastItemVisibleListener listener) {
        this.onLastItemVisibleListener = listener;
        if (listener == null) {
            removeFooterView(getFooterView());
            viewFooter = null;
            txtFooter = null;
            progressBarFooter = null;
        } else {
            if (viewFooter == null) {
                addFooterView(getFooterView());
            }
        }

        if (viewFooter == null) {
            return;
        }

        txtFooter.setText(getContext().getResources().getString(R.string.load_more));
        progressBarFooter.setVisibility(View.GONE);
    }

    /**
     * 刷新事件接口
     */
    public interface OnRefreshListener {
        /**
         * 刷新事件接口 这里要注意的是获取更多完 要关闭 刷新的进度条onRefreshComplete()
         **/
        void onRefresh();
    }

    /**
     * 获取更多事件接口
     */
    public interface OnLastItemVisibleListener {
        /**
         * 刷新事件接口 这里要注意的是获取更多完 要关闭 更多的进度条 onRefreshComplete()
         **/
        void onLastItemVisible();
    }
}
