
package com.yunkang.prenatalgt.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hlib.util.ScreenUtil;
import com.yunkang.prenatalgt.R;
import com.yunkang.prenatalgt.listener.MessageRecordingListener;


/**
 * @作者： [欢欢 email：HuanHuanFu@wistronits.com]<br>
 * @版本： [V1.0.0, 2014-07-15]<br>
 * @描述：聊天右边录音<br>
 */
public class VMessageRightLyView extends RelativeLayout implements OnClickListener {

    private ImageView right_play;
    private TextView recoding_time;
    private RelativeLayout ly_right;
    private RelativeLayout ly_content;
    //private RelativeLayout myRightly;
    public ImageView send_failure;
    public ProgressBar progressBar;

    public VMessageRightLyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li;
        li = (LayoutInflater) context.getSystemService(infService);
        li.inflate(R.layout.message_ly_right, this);

        findViewsById();
        setListener();
    }

    private void findViewsById() {
        right_play = findViewById(R.id.right_play);
        if (right_play != null) {
            if(animationDrawable == null) {
                animationDrawable = (AnimationDrawable) right_play.getBackground();
                animationDrawable.stop();
            }
        }
        
        ly_right = findViewById(R.id.ly_right);
        ly_content = findViewById(R.id.ly_content);
        recoding_time = findViewById(R.id.recoding_time);
        //myRightly = (RelativeLayout) findViewById(R.id.myRightly);
        send_failure = findViewById(R.id.send_failure);
        progressBar = findViewById(R.id.progress_bar);
    }

    /**
     * @method: setSendState
     * @Description: 如果发送失败则显示重新发送并把时长隐藏
     * @param sendState
     * @throws
     */
    public void setSendState(boolean sendState) {
        if (send_failure == null || recoding_time == null) {
            return;
        }
        if (sendState) {
            send_failure.setVisibility(View.VISIBLE);
            //recoding_time.setVisibility(View.GONE);
        } else {
            send_failure.setVisibility(View.GONE);
            //recoding_time.setVisibility(View.VISIBLE);
        }
    }

    public void setRightRecodingWidth(int width) {
        //120
        int settingWidth = 140 + (width * 3);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ScreenUtil.dip2px(
                getContext(), settingWidth > 220 ? 220 : settingWidth),
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        ly_content.setLayoutParams(params);

    }

    /**
     * @method: getViewWidth
     * @Description: 获取控件的宽
     * @param view
     * @return
     * @throws
     */
    public int getViewWidth(RelativeLayout view) {
        if (view == null) {
            return 0;
        }
        view.measure(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        return view.getMeasuredWidth();
    }

    public void playAnmin() {
        if (right_play != null) {
            if(!animationDrawable.isRunning()) {
                animationDrawable.stop();
                
                animationDrawable.start();
            }
            
            
//            final AnimationDrawable animationDrawable = (AnimationDrawable) right_play
//                    .getBackground();
//            new Handler().post(new Runnable() {
//                @Override
//				public void run() {
//                    animationDrawable.start();
//                }
//            });
        }
    }

    private AnimationDrawable animationDrawable = null;
    
    public void stopAnmin() {
        if (right_play != null) {
//          final AnimationDrawable animationDrawable = (AnimationDrawable) right_play
//          .getBackground();
            
//            if(animationDrawable == null) {
//                animationDrawable = (AnimationDrawable) right_play.getBackground();
//            }
            
            if(animationDrawable.isRunning()) {
                animationDrawable.stop();
                animationDrawable.selectDrawable(0);
            }
            
            
//            new Handler().post(new Runnable() {
//                @Override
//				public void run() {
//                    animationDrawable.stop();
//                    animationDrawable.selectDrawable(0);
//                }
//            });
        }
    }

    public void setLongListener(OnLongClickListener li) {
        ly_right.setOnLongClickListener(li);
    }

    /*
     * public void setName(String name) { if (mlyName != null) {
     * mlyName.setText(name); } }
     */
    public void setRecodingTime(String recodingTime) {
        if (recoding_time != null) {
            recoding_time.setText(recodingTime);
        }
    }

    /*
     * public void setSize(String size) { if (mlyName != null) {
     * mlySize.setText(size); } }
     */

    private void setListener() {
        right_play.setOnClickListener(this);
        ly_right.setOnClickListener(this);
    }

    public void setProcessBarTag(String tag){
    	if(!TextUtils.isEmpty(tag)){
    	  progressBar.setTag(tag);
    	}
    }
    
    public String getProcessBarTag(){
	   String tag=	(String)progressBar.getTag();
    	return tag;    	
    }
        
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ly_right:
                if (messageRecordingListener != null)
                    messageRecordingListener.onClick();
                break;
        }
    }

    // 设置播放状态
    public void setPlayState(int bgId) {
        right_play.setBackgroundResource(bgId);
    }

    private MessageRecordingListener messageRecordingListener;

    public void setOnClickListener(MessageRecordingListener listener) {
        this.messageRecordingListener = listener;
    }
    
    /**
     * @method: showProgress 
     * @Description: 显示进度条状态
     * @throws
    */
	public void showProgress(final boolean showView) {
		post(new Runnable() {
			@Override
			public void run() {
				// 需要在主线程进行
				progressBar.setVisibility(showView ? View.VISIBLE : View.GONE);
			}
		});
	}
	/**
     * @method: isProgressVisible 
     * @Description: 进度条显示状态    
     * @throws
    */
	public boolean isProgressVisible() {
        return progressBar.getVisibility() == View.VISIBLE;
	}
}
