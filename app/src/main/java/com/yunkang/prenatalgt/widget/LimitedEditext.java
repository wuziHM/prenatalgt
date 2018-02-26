/**
 *-----------------------------------------------------------------------
 * <copyright file="LimitedEditext.java" company="SINOSUN">
 *     Copyright (c) Sinosun Technology Co., Ltd. All rights reserved.
 * </copyright>
 * <author>WH1406009</author>
 * <summary>This is the Widget class.</summary>
 * -----------------------------------------------------------------------
 */

package com.yunkang.prenatalgt.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

/**
 * @ClassName: LimitedEditext
 * @Description: 文本限制输入控件
 * @date 2014年9月17日
 */
public class LimitedEditext extends MyEditText {

    /**
     * @param mContext
     * @param attrs
     * @param defStyle
     */

    private int mMaxLength = 0;

    // private Context mContext ;

    private EditextCallback mCallback;

    // 备注最大长度
    public static final int REMARK_EDITEXT_MAX_LENGTH = 500;

    // 用途最大长度
    public static final int PURPOSE_EDITEXT_MAX_LENGTH = 50;

    // 收款人最大长度
    public static final int PAY_NAEM_EDITEXT_MAX_LENGTH = 30;

    // 开户行
    public static final int OPEN_BANK_EDITEXT_MAX_LENGTH = 50;

    // 项目名称
    public static final int PROJECT_NAME_EDITTEXT_MAX_LENGTH = 100;

    // 凭证号码
    public static final int BILLNUMBER_EDITTEXT_MAX_LENGTH = 8;

    // 公告标题长度
    public static final int RTITLE_EDITEXT_MAX_LENGTH = 50;
    
    // 红包祝福语长度
    public static final int RED_MAX_LENGTH = 20;

    // 公告内容长度
    public static final int RCONTEND_EDITEXT_MAX_LENGTH = 4096;

    // 聊天的消息
    public static final int MESSAGE_EDITEXT_MAX_LENGTH = 4096;
    
    
    public static final int MAX_FEEDBACK_CONTENT_LEN = 512;//意见反馈 标题长度：60(个汉字长度) 意见反馈 内容长度：512(个汉字长度
    public static final int MAX_FEEDBACK_TITLE_LEN = 60;

    private boolean mIsMultiFlag = false;

    public LimitedEditext(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public LimitedEditext(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LimitedEditext(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        // mContext = context ;
        setListener();
    }

    private void setListener() {
        addTextChangedListener(wathcer);
    }

    public void setListener(TextWatcher _wathcer) {
        removeTextChangedListener(wathcer);
        addTextChangedListener(_wathcer);
    }

    public void setEditextMaxLength(int length) {
        mMaxLength = length;
    }

    public void setEditextCallback(EditextCallback l) {
        mCallback = l;
    }

    public void setMultiLineFlag(boolean flag) {
        mIsMultiFlag = flag;
    }

    private TextWatcher wathcer = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {

        	if (mTextChangedCallback != null) {
        		mTextChangedCallback.afterTextChanged(s);
        	}

            int textLength = LimitedEditext.this.getText().toString().length();
            if (textLength > mMaxLength) {
                if ((textLength - mMaxLength) == 1) {// 用户手动输入 所以是一个字符一个字符
                    int selectionStart = LimitedEditext.this.getSelectionStart();
                    int selectionEnd = LimitedEditext.this.getSelectionEnd();
                    s.delete(selectionStart - 1, selectionEnd);
                    LimitedEditext.this.setText(s);
                    LimitedEditext.this.setSelection(s.length());
                } else { // 一次性删除多余的数据 可能是复制粘贴进来的 超过最大长度很多位 需要一次删除多余字符
                    s.delete(mMaxLength, s.length());
                    LimitedEditext.this.setText(s);
                    LimitedEditext.this.setSelection(s.length());
                }
                if (mCallback != null) {
                    mCallback.onReachMaxLength();
                }
            }

            if (mIsMultiFlag) {
                int count = LimitedEditext.this.getLineCount();
                if (count < 1) {
                    LimitedEditext.this.setLines(1);
                } else {
                    LimitedEditext.this.setLines(count);
                }
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        // s 是完整字符串， start起始位置， before>0 表示删除before个数的字符， count>0 表示添加count个数的字符
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        	//logd("onTextChanged -- legth : " +s.length() + ", s : " + s.toString() +" , start :" +start+", before : " + before+", count : "+ count);
        	if (mTextChangedCallback != null) {
        		mTextChangedCallback.onTextChanged(s, start, before, count);
        	}
        }
    };

    public interface EditextCallback {
        void onReachMaxLength();
    }

    // 输入内容变化监听
    private TextChangedCallback mTextChangedCallback = null;
    protected void setTextChangedCallback(TextChangedCallback callback) {
    	mTextChangedCallback = callback;
    }
    public interface TextChangedCallback{
    	void afterTextChanged(Editable s);
        void onTextChanged(CharSequence s, int start, int before, int count);
    }
}
