package com.yunkang.prenatalgt.widget;

import android.content.Context;
import android.text.Editable;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import com.yunkang.prenatalgt.MApplication;
import com.yunkang.prenatalgt.R;


/**
 * 聊天界面的输入框，主要是处理输入框里面的特殊输入事件（如 @，*@， !@）
 *
 * @author miaojun
 */
public class ChatEditText extends LimitedEditext implements LimitedEditext.TextChangedCallback {


    // 目前使用的几个特殊动作
    public static final String FLAG_AT = "@";
    public static final String FLAG_X_AT = "*@";
    public static final String FLAG_W_AT = "!@";

    /*
     * 根据action类型，获取action的字符串
     */
    public static String getActionString(int action) {
        switch (action) {
            case EditTextActionCallback.INPUT_ACTION_AT: {
                return FLAG_AT;
            }
            case EditTextActionCallback.INPUT_ACTION_X_AT: {
                return FLAG_X_AT;

            }
            case EditTextActionCallback.INPUT_ACTION_W_AT: {
                return FLAG_W_AT;

            }
        }
        return "";
    }

    public ChatEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ChatEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChatEditText(Context context) {
        super(context);
    }

    public void setTextChangedCallback() {
        setTextChangedCallback(this);
    }

    // 输入特殊事件回调
    private EditTextActionCallback mInputActionCallback;

    public void setEditTextActionCallback(EditTextActionCallback callback) {
        mInputActionCallback = callback;
    }

    // 当前的action事件
    int mLastAction = EditTextActionCallback.INPUT_ACTION_NONE;
    // 事件的开始位置
    int mLastActionStart = -1;

    @Override
    public void afterTextChanged(Editable s) {
        // 如果刚才的输入有“@”等特殊动作,通知UI处理（选择联系人去）
        if (mLastAction > EditTextActionCallback.INPUT_ACTION_NONE) {
            // 让回调函数去处理“action”
            if (mInputActionCallback != null) {
                mInputActionCallback.onInputAction(mLastAction, mLastActionStart);
            }
            mLastAction = EditTextActionCallback.INPUT_ACTION_NONE;
            mLastActionStart = -1;
            return;
        }

        // 否则检查一下，actionbeans， 是否有变动
        ActionBean[] actions = getActions();
        for (ActionBean action : actions) {
            int st = s.getSpanStart(action);
            int en = s.getSpanEnd(action);
            // 	logd("afterTextChanged - st:"+st+",en:"+en+", action start:"+action.start+", end:"+(action.start+action.count));
            if (action.action == EditTextActionCallback.INPUT_ACTION_NONE) {
                s.removeSpan(action);
                if (st >= 0 && en >= st && s.length() >= en)
                    s.delete(st, en);
            }
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //	logd("onTextChanged --####--- s : " +s.toString() +" start:" +start +", before:"+before+", count:"+count);
        ActionBean[] actions = getActions();
        // 更新所有的action的信息
        if (actions != null) {
            for (ActionBean action : actions) {
                action.updatePos(start, before, count);
            }
        }

        // count > 0 是输入字符
        if (count > 0) {
            // 输入字符是否有特殊动作定义？
            mLastAction = whichAction(s, start, count);
            if (mLastAction > EditTextActionCallback.INPUT_ACTION_NONE) {
                mLastActionStart = start;
            }
        }
    }

    /**
     * 判断输入的动作类型 @, !@, *@
     */
    private int whichAction(CharSequence s, int start, int count) {
        int action = EditTextActionCallback.INPUT_ACTION_NONE;
        // count > 0,是添加字符串;
        if (count > 0) {
            // 新输入字符串的 “最后一个字符”
            char addedLastChar = s.charAt(start + count - 1);
            if ('@' == addedLastChar) {
                action = EditTextActionCallback.INPUT_ACTION_AT; // @
                int pos2 = start + count - 2;
                if (s.length() >= pos2 && pos2 > -1) {
                    // "倒数第二个字符"
                    char addedLastChar2 = s.charAt(pos2);
                    // "!@"
                    if ('!' == addedLastChar2 || '！' == addedLastChar2) {
                        action = EditTextActionCallback.INPUT_ACTION_W_AT; // !@
                    }
                    // "*@"
                    else if ('*' == addedLastChar2) {
                        action = EditTextActionCallback.INPUT_ACTION_X_AT; // *@
                    } else if (addedLastChar2 >= 'a' && addedLastChar2 <= 'z'
                            || addedLastChar2 >= 'A' && addedLastChar2 <= 'Z'
                            || addedLastChar2 >= '0' && addedLastChar2 <= '9'
                            || addedLastChar2 == '_') {
                        //邮箱模式
                        action = EditTextActionCallback.INPUT_ACTION_NONE;
                    }
                }
            }
        }
        // logd("whichAction -- action : " + action);
        return action;
    }

    /*
     * EditText中的特殊action集合
     */
    public ActionBean[] getActions() {
        Editable et = getEditableText();
        ActionBean[] actions = this.getEditableText().getSpans(0, et.length(), ActionBean.class);
        return actions;
    }

    /*
     * editText中的特殊动作bean
     */
    public static class ActionBean extends ForegroundColorSpan {
        public ActionBean() {
            //super(Color.GREEN);
            super(MApplication.getContext().getResources().getColor(R.color.c_fdaa83));
        }

        public int start; // 开始位置
        public int count; // actionBean字符串内容长度
        public String content; // 内容
        public int action; // 动作类型(@, *@, !@)

        public long uaid; // @对象的uaid
        public String name; // @对象的用户名

        /**
         * @param _start  : 开始位置
         * @param _before : > 0 表示删除 before个数字符
         * @param _count  : > 0 表示添加count个个字符
         * @method: updatePos
         */
        int updatePos(int _start, int _before, int _count) {
//			logd("[updatePos] s:"+_start+",b:"+_before+",c:"+_count);
//			logd("[updatePos] action 0  s:"+start+",c:"+count+", action:"+action);
            // 在bean前面，新增字符
            if (_count > 0 && _start < start) {
                start += _count;
            }
            // 在bean前面，删除字符
            else if (_before > 0 && _start < start) {
                start -= _before;
            }
            // 删除字符时，删除了action
            else if (_before > 0
                    && (_start + _before) >= start
                    && (_start) < (start + count - 1)) {
                action = EditTextActionCallback.INPUT_ACTION_NONE;
            }
            // logd("[updatePos] action 1  s:"+start+",c:"+count+", action:"+action);
            return action;
        }
    }

    /**
     * @ClassName: EditTextActionCallback
     * @Description: 输入框的特殊事件回调
     * @date 2015年8月28日
     */
    public interface EditTextActionCallback {
        /**
         * 无特殊动作
         */
        int INPUT_ACTION_NONE = -1;
        /**
         * "@"
         */
        int INPUT_ACTION_AT = 1;
        /**
         * "*@"
         */
        int INPUT_ACTION_X_AT = 2;
        /**
         * "!@"
         */
        int INPUT_ACTION_W_AT = 3;

        /**
         * @param action : INPUT_ACTION_AT, INPUT_ACTION_X_AT, INPUT_ACTION_W_AT
         * @method: onInputAction
         * @Description: 输入字符特殊动作，如： @，*@， !@
         */
        void onInputAction(int action, int start);
    }
}
