package com.yunkang.prenatalgt.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunkang.prenatalgt.R;
import com.yunkang.prenatalgt.widget.MyLinkTextView;

import java.util.Timer;

/**
 * Author：HM $ on 18/1/25 10:58
 * E-mail：359222347@qq.com
 * <p>
 * use to...
 */
public class VDialog {


    private static VDialog instance;
    private MyPopupWindow pw;
    private Timer mTimer;


    public static VDialog getDialogInstance() {
        if (null == instance) {
            instance = new VDialog();
        }
        return instance;
    }



    /*
         * 打电话弹出框 (联系人详情中使用)
         */
    public void showCallPhotoPopu(final Activity context, View parent,
                                  final String phoneNumber) {
        if (isHasShow()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View vPopupWindow = inflater.inflate(R.layout.call_photo_dialog,
                null, false);

        // 文字修改
        TextView mCallPhotoTip = vPopupWindow.findViewById(R.id.mCallPhotoTip);
        mCallPhotoTip.setText(phoneNumber);

        RelativeLayout callPhone = vPopupWindow.findViewById(R.id.two);// 呼叫
        RelativeLayout sendMsg = vPopupWindow.findViewById(R.id.three); // 发短信
        RelativeLayout four = vPopupWindow.findViewById(R.id.four);// 复制
        RelativeLayout cancel = vPopupWindow.findViewById(R.id.layout_cancel);// 取消

        pw = new MyPopupWindow(context, vPopupWindow, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, true);
        pw.setOutsideTouchable(true);
        pw.setAnimationStyle(R.style.AnimationPreview);
        pw.showAtLocation(parent, Gravity.BOTTOM, 0, 0);

        callPhone.setOnClickListener(new View.OnClickListener() { // 呼叫
            @Override
            public void onClick(View v) {
                MyLinkTextView.getPhone(context, "tel:" + phoneNumber);
                closePw();
            }
        });

        sendMsg.setOnClickListener(new View.OnClickListener() { // 发短信
            @Override
            public void onClick(View v) {
                MyLinkTextView.getSms(context, phoneNumber);
                closePw();
            }
        });

        four.setOnClickListener(new View.OnClickListener() {// 复制
            @Override
            public void onClick(View v) {
                MyLinkTextView.copy(phoneNumber, context);
                closePw();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {// 取消
            @Override
            public void onClick(View v) {
                closePw();
            }
        });

        RelativeLayout rback = vPopupWindow
                .findViewById(R.id.layout_call_phone_dialog);
        rBackEvent(rback, null);
    }

    /**
     * @method: closePw @Description: 关闭和释放资源 @throws
     */
    public void closePw() {
        if (pw != null) {
            Activity attach = pw.getAttachActivity();
            if (attach != null && !attach.isFinishing()) {
                pw.closePopupWindow();
                pw = null;
            }
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }


    public boolean isHasShow() {
        if (pw != null) {
            // return pw.isShowing();
            closePw();
        }
        return false;
    }


    public interface OnDialogDismissListener {
        void onDismiss();
    }


    // 公用事件
    private void rBackEvent1(final RelativeLayout rback,
                             final OnDialogDismissListener listener, final View view) {
        rback.setFocusable(true);
        rback.setFocusableInTouchMode(true);
        rback.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (pw != null) {
                        closePw();
                        if (view != null) {
                            view.setBackgroundDrawable(null);
                        }
                        if (listener != null) {
                            listener.onDismiss();
                        }
                    }
                }
                return true;
            }
        });

        rback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pw != null) {
                    closePw();
                    if (view != null) {
                        view.setBackgroundDrawable(null);
                    }
                    if (listener != null) {
                        listener.onDismiss();
                    }
                }

            }
        });
    }
    private void rBackEvent(final RelativeLayout rback,
                            final OnDialogDismissListener listener) {
        rBackEvent1(rback, listener, null);
    }
}



