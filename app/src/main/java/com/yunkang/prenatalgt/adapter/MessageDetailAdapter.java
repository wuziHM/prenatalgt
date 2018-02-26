/**
 * -----------------------------------------------------------------------
 * <copyright file="MessageDetailAdapter.java" company="SINOSUN">
 * Copyright (c) Sinosun Technology Co., Ltd. All rights reserved.
 * </copyright>
 * <author>WH1406011</author>
 * <summary>This is the MessageDetailAdapter class.</summary>
 * -----------------------------------------------------------------------
 */

package com.yunkang.prenatalgt.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yunkang.prenatalgt.R;
import com.yunkang.prenatalgt.bean.ChatMessage;
import com.yunkang.prenatalgt.widget.MyLinkTextView;
import com.yunkang.prenatalgt.widget.VMessageLeftFileView;
import com.yunkang.prenatalgt.widget.VMessageLeftLyView;
import com.yunkang.prenatalgt.widget.VMessageLeftStaticImage;
import com.yunkang.prenatalgt.widget.VMessageRightFileView;
import com.yunkang.prenatalgt.widget.VMessageRightLyView;
import com.yunkang.prenatalgt.widget.VMessageRightStaticImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @作者： [欢欢 email：HuanHuanFu@wistronits.com]<br>
 * @版本： [V1.0.0, 2014-6-18]<br>
 * @描述： 消息详情适配器<br>
 */
@SuppressLint("HandlerLeak")
public class MessageDetailAdapter extends BaseAdapter {


    // 数据集
    public List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();
    // 会话图片消息存储变量
    private HashMap<Long, Integer> imgChatMsgInforMap = new HashMap<Long, Integer>();
    // 图片位置索引
    private int imgMsgIndex = 0;
    // 是否编辑 ， false 为不为编辑 true 为编辑
    public boolean isEdit = false;

    private boolean isShouldFire = false;


    private ViewGroup parentView = null;

    private int selfUserId = -1;
    private LayoutInflater mInflater;

    public ViewGroup getParentView() {
        return parentView;
    }

    public void setParentView(ViewGroup parentView) {
        this.parentView = parentView;
    }


    public boolean getIsShouldFire() {
        return isShouldFire;
    }

    public void reSetShouldFire() {
        isShouldFire = false;
    }


    /***
     * @method: setEdited
     * @Description: 接口提供设置编辑状态 （多选、删除）
     * @param isEdited
     * @throws
     */
    public void setEdited(boolean isEdited) {
        isEdit = isEdited;
    }


    public MessageDetailAdapter(List<ChatMessage> chatMessages, Context context) {
        this.chatMessages = chatMessages;
        this.mInflater = LayoutInflater.from(context);

    }

    public void setDataList(List<ChatMessage> datalists) {
        if (chatMessages != null) {
            chatMessages.clear();
        } else {
            chatMessages = new ArrayList<ChatMessage>();
        }
        if (datalists != null) {
            chatMessages.addAll(datalists);
        }
    }

    /***

     /**
     * @method: findView
     * @Description: 控件初始化 查找控件
     * @param convertView
     * @param vHolder
     * @throws
     */
    private void findView(View convertView, ViewHolder vHolder) {
        //左边聊天控件
        vHolder.leftStub = convertView.findViewById(R.id.leftStub);
        vHolder.rightStub = convertView.findViewById(R.id.rightStub);

        vHolder.c_layout = convertView.findViewById(R.id.centerLayout);
        vHolder.sendTime = convertView.findViewById(R.id.sendtime);
        vHolder.rLayout = convertView.findViewById(R.id.rLayout);
        vHolder.selectDel = convertView.findViewById(R.id.iv_select);// select
        vHolder.mWithdraw = convertView.findViewById(R.id.mWithdraw);

        convertView.setTag(vHolder);
        vHolder.needInflate = false;
    }


    private void layoutStub(boolean isSend, final ViewHolder vHolder) {
        if (isSend) {
            vHolder.leftStub.setVisibility(View.GONE);

            if (vHolder.rightStub.getVisibility() != View.VISIBLE) {
                if (vHolder.rightStub instanceof ViewStub) {
                    ViewStub right = (ViewStub) (vHolder.rightStub);
                    vHolder.rightStub = right.inflate();
                } else {
                    vHolder.rightStub.setVisibility(View.VISIBLE);
                    return;
                }
            }

            View rightRoot = vHolder.rightStub;
            vHolder.r_layout = rightRoot.findViewById(R.id.rightLayout);
            vHolder.r_userIcon = rightRoot.findViewById(R.id.right_userIcon);
            vHolder.img_video = rightRoot.findViewById(R.id.rVideoIcon);//视频图标 ZYY
            vHolder.r_layout_appoint = rightRoot.findViewById(R.id.right_ly_apponitcontent);
            vHolder.r_img_appointcall = rightRoot.findViewById(R.id.rAppointCallIcon);// 右边预约通话 ZYY
            vHolder.r_appointcontent_callstate = rightRoot.findViewById(R.id.right_tv_apponitcontent_name);// 右边预约通话 ZYY
            vHolder.r_appointcontent_time = rightRoot.findViewById(R.id.right_tv_apponitcontent_time);// 右边预约通话 ZYY
            vHolder.r_appointcontent_title = rightRoot.findViewById(R.id.right_tv_apponitcontent_title);// 右边预约通话 ZYY
            vHolder.r_content = rightRoot.findViewById(R.id.right_tv_chatcontent);
            vHolder.r_static_img = rightRoot.findViewById(R.id.right_static_img);
            vHolder.recording_right = rightRoot.findViewById(R.id.ly_right);
            vHolder.rsendFailure = rightRoot.findViewById(R.id.rsendFailure);
            vHolder.right_send_failure = rightRoot.findViewById(R.id.right_send_failure);
            vHolder.right_sendName = rightRoot.findViewById(R.id.right_sendName);
            vHolder.right_message_receipt = rightRoot.findViewById(R.id.right_message_receipt);//回执消息
            vHolder.rlContent = rightRoot.findViewById(R.id.rsendFailure);//右边里面的内容
            vHolder.rPicStyle = rightRoot.findViewById(R.id.rPicStyle);
            vHolder.vRightFileView = rightRoot.findViewById(R.id.rRightFileView);//右边文件控件
            vHolder.rightTextForward = rightRoot.findViewById(R.id.rightForward);//右边转发时显示
            vHolder.right_recoding_receipt = rightRoot.findViewById(R.id.right_recoding_receipt);
            vHolder.right_img_receipt = rightRoot.findViewById(R.id.right_img_receipt);
            vHolder.fireLayout = rightRoot.findViewById(R.id.fireLayout);//右边阅后即阅已焚效果
            vHolder.mFireText = rightRoot.findViewById(R.id.mFireText);//右边阅后即阅已焚效果文字
            vHolder.right_text_load = rightRoot.findViewById(R.id.right_text_load);// 右边文本发送状态
            vHolder.Right_joinCall = rightRoot.findViewById(R.id.right_TextJoinCall);//右边显示通话时长消息
        } else {
            vHolder.rightStub.setVisibility(View.GONE);
            if (vHolder.leftStub.getVisibility() != View.VISIBLE) {
                if (vHolder.leftStub instanceof ViewStub) {
                    ViewStub left = (ViewStub) (vHolder.leftStub);
                    vHolder.leftStub = left.inflate();
                } else {
                    vHolder.leftStub.setVisibility(View.VISIBLE);
                    return;
                }
            }

            View leftRoot = vHolder.leftStub;
            vHolder.rlLeft = leftRoot.findViewById(R.id.rlLeftContent);
            //vHolder.l_layout = (LinearLayout) leftRoot.findViewById(R.id.leftLayout);
            vHolder.l_userIcon = leftRoot.findViewById(R.id.left_userIcon);
            vHolder.img_vaccount = leftRoot.findViewById(R.id.img_vaccount);
            vHolder.l_content = leftRoot.findViewById(R.id.left_tv_chatcontent);
            vHolder.l_static_img = leftRoot.findViewById(R.id.left_static_img);
            vHolder.textview_down_time = leftRoot.findViewById(R.id.textview_down_time);
            // del
            vHolder.recording_left = leftRoot.findViewById(R.id.vMessageLeftLyView_left);
            vHolder.left_sendName = leftRoot.findViewById(R.id.left_sendName);
            vHolder.left_message_receipt = leftRoot.findViewById(R.id.left_message_receipt);
            vHolder.llContent = leftRoot.findViewById(R.id.llContent);//左边里面的内容
            vHolder.lPicStyle = leftRoot.findViewById(R.id.lPicStyle);
            vHolder.vLeftFileView = leftRoot.findViewById(R.id.rLeftFileView); //左边文件控件
            vHolder.leftTextForward = leftRoot.findViewById(R.id.leftForward);//左边转发时显示
            //左边预约通话   ZYY-2016.11.3
            vHolder.l_layout_appoint = leftRoot.findViewById(R.id.left_ly_apponitcontent);
            vHolder.l_img_appointcall = leftRoot.findViewById(R.id.lAppointCallIcon);
            vHolder.l_img_joincall = leftRoot.findViewById(R.id.JoinCallIcon);
            vHolder.Left_joinCall = leftRoot.findViewById(R.id.TextJoinCall);
            vHolder.l_appointcontent_callstate = leftRoot.findViewById(R.id.left_tv_apponitcontent_name);
            vHolder.l_appointcontent_time = leftRoot.findViewById(R.id.left_tv_apponitcontent_time);
            vHolder.l_appointcontent_title = leftRoot.findViewById(R.id.left_tv_apponitcontent_title);
        }
    }

    @Override
    public int getCount() {
        return chatMessages.size();
    }

    @Override
    public ChatMessage getItem(int i) {
        return chatMessages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder = null;
        Logger.i("position:" + position);
        if (convertView == null) {
            vHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_msg_text, parent, false);
            findView(convertView, vHolder);

            // 缓存veiw
            setParentView(parent);
        } else if (((ViewHolder) convertView.getTag()).needInflate) {

            vHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_msg_text, parent, false);
            findView(convertView, vHolder);


            setParentView(parent);
        } else {


            vHolder = (ViewHolder) convertView.getTag();
        }
        layoutStub(getItem(position).getType() == 1, vHolder);
        bindData(getItem(position), vHolder);
        //处理消息
        return convertView;
    }

    private void bindData(ChatMessage item, ViewHolder vHolder) {
        vHolder.sendTime.setVisibility(View.VISIBLE);
        vHolder.sendTime.setText("11-20 11:46");
        Logger.d(item.getContent());
        if (item.getType() == 0) {  //0 收到的消息  1发出去的消息
            vHolder.l_userIcon.setImageResource(item.getUserBean().getHead());
            vHolder.l_content.setText(item.getContent());
        } else {
            vHolder.r_content.setText(item.getContent());

        }
    }


    public static class ViewHolder {
        public boolean needInflate = false;

        public View leftStub;
        public View rightStub;

        public LinearLayout c_layout;

        public RelativeLayout rlLeft;
        public TextView sendTime; // r_tvSendTime,
        public MyLinkTextView r_content, l_content;
        public ImageView r_userIcon, l_userIcon, img_vaccount, img_video, r_img_appointcall, l_img_appointcall, l_img_joincall;

        public LinearLayout r_layout_appoint;//右边预约通话内容
        public TextView r_appointcontent_callstate, r_appointcontent_time, r_appointcontent_title;//右边预约通话内容
        public LinearLayout l_layout_appoint;//左边预约通话内容
        public TextView l_appointcontent_callstate, l_appointcontent_time, l_appointcontent_title;//左边边预约通话内容

        // 选择删除
        public RelativeLayout rLayout;
        public ImageView selectDel;

        private ProgressBar right_text_load;// 正在发送
        // 发送失败
        public RelativeLayout rsendFailure;
        public ImageView right_send_failure;
        public TextView right_sendName, left_sendName, Left_joinCall, Right_joinCall;
        //是否回执成功

        public TextView left_message_receipt; //阅后即焚
        //右边里面的内容,
        public RelativeLayout rlContent;
        //左边的内容
        public RelativeLayout llContent;
        //右边图片锁,左边图片锁
        public ImageView rPicStyle, lPicStyle;
        public TextView mWithdraw;//撤回消息

        public LinearLayout r_layout;// 右边的控件

        public VMessageLeftStaticImage l_static_img; //图片左边
        public VMessageRightStaticImage r_static_img; //图片右边

        public TextView textview_down_time;

        public VMessageLeftLyView recording_left; //录音左边
        public VMessageRightLyView recording_right;//录音右边

        public VMessageLeftFileView vLeftFileView;//左边文件控件

        public VMessageRightFileView vRightFileView;//右边文件控件

        public TextView leftTextForward;//左边转发时显示
        public TextView rightTextForward;//右边转发时显示

        public TextView right_message_receipt; //文本回执
        public TextView right_recoding_receipt;//右边录音回执
        public TextView right_img_receipt;//右边图片回执

        public LinearLayout fireLayout; //右边阅后即阅已焚效果
        public TextView mFireText;//右边阅后即阅已焚效果文字
    }


}
