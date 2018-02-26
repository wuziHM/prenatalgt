/**
 * -----------------------------------------------------------------------
 * <copyright file="MessageListAdapter.java" company="SINOSUN">
 * Copyright (c) Sinosun Technology Co., Ltd. All rights reserved.
 * </copyright>
 * <author>WH1406011</author>
 * <summary>This is the MessageListAdapter class.</summary>
 * -----------------------------------------------------------------------
 */

package com.yunkang.prenatalgt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hlib.util.ScreenUtil;
import com.yunkang.prenatalgt.R;
import com.yunkang.prenatalgt.bean.MessageBean;
import com.yunkang.prenatalgt.widget.DeformablePhotoView;

import java.util.List;

/**
 * @作者： [欢欢 email：HuanHuanFu@wistronits.com]<br>
 * @版本： [V1.0.0, 2014-6-18]<br>
 * @描述： 消息列表适配器<br>
 */
public class MessageListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context mContext;
    private List mConversationList;
    private ListView listView;
    // 加密是否开启
    private boolean encryptionSwticher;


    public MessageListAdapter(Context context, List lists) {
        inflater = LayoutInflater.from(context);
        this.mContext = context;
        mConversationList = lists;
    }

    @Override
    public int getCount() {
        if (mConversationList == null) {
            return 0;
        }
        return mConversationList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void findView(View convertView, ViewHolder holder) {
        holder.user_img = convertView.findViewById(R.id.user_img);
        holder.img_vaccount = convertView.findViewById(R.id.img_vaccount);
        holder.tvTitle = convertView.findViewById(R.id.title_txt);
        holder.mContent = convertView.findViewById(R.id.content_txt);
        holder.reUnread = convertView
                .findViewById(R.id.reUnread);
        holder.unMessageNumber = convertView
                .findViewById(R.id.unMessageNumber);
        holder.unMeetingImg = convertView.findViewById(R.id.unMeetingImg);
        holder.showTime = convertView.findViewById(R.id.showTime);
        holder.sendName_txt = convertView
                .findViewById(R.id.sendName_txt);
        holder.messageListItem = convertView
                .findViewById(R.id.messageListItem);
        holder.picStyle = convertView.findViewById(R.id.picStyle);
        holder.messageDnd = convertView
                .findViewById(R.id.messageDnd);
        holder.rGroupUnread = convertView
                .findViewById(R.id.rGroupUnread);
        holder.at_txt = convertView.findViewById(R.id.at_txt);

        holder.dpv_g2g = convertView.findViewById(R.id.rl_circle_g2g);
        holder.rl_p2p = convertView.findViewById(R.id.rl_circle_p2p);
    }

    /**
     * 画列表的项，根据下标iIndex判断选中项与普通项并赋值
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        Conversation item = mConversationList.get(position);
        long begintime = System.currentTimeMillis();
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_message, null);
            convertView.setLayoutParams(new AbsListView.LayoutParams(
                    android.view.ViewGroup.LayoutParams.FILL_PARENT, ScreenUtil.dip2px(mContext, 76)));
            findView(convertView, holder);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        if (item.getCategory() == Conversation_Type.MEETING_TYPE) {
//            holder.tvTitle.setText(item.getConversationName());
//            holder.dpv_g2g.setVisibility(View.VISIBLE);
//            holder.rGroupUnread.setVisibility(View.GONE);
//            holder.messageDnd.setVisibility(View.GONE);
//            holder.img_vaccount.setVisibility(View.GONE);
//            if (item.isUnReadMeeting()) {
//                holder.reUnread.setVisibility(View.VISIBLE);
//                holder.unMeetingImg.setVisibility(View.VISIBLE);
//                holder.unMessageNumber.setVisibility(View.GONE);
//            } else {
//                holder.reUnread.setVisibility(View.GONE);
//                holder.unMeetingImg.setVisibility(View.GONE);
//                holder.unMessageNumber.setVisibility(View.VISIBLE);
//
//            }
//            holder.rl_p2p.setVisibility(View.VISIBLE);
//            holder.user_img.setVisibility(View.VISIBLE);
//            holder.user_img.setImageResource(R.drawable.icon_assistant_normal);
//            holder.mContent.setText(item.getLastContent());
//            String msgTime = item.getLastMsgTime();
//            msgTime = DateUtil.convertMessageListTime(msgTime);
//            holder.showTime.setText(msgTime);
//            holder.dpv_g2g.setVisibility(View.GONE);
//            holder.picStyle.setVisibility(View.GONE);
//            holder.mContent.setCompoundDrawables(null, null, null, null);
//            holder.mContent.setCompoundDrawablePadding(0);
//            holder.messageListItem.setBackgroundResource(R.drawable.msg_bg_list_click);
//
//        } else {

        holder.dpv_g2g.setVisibility(View.GONE);
        holder.unMessageNumber.setVisibility(View.VISIBLE);
        holder.unMeetingImg.setVisibility(View.GONE);

        MessageBean bean = (MessageBean) mConversationList.get(position);
        holder.user_img.setImageResource(bean.getUser().getHead());
        holder.mContent.setText(bean.getContent());
        holder.tvTitle.setText(bean.getUser().getName());
        holder.showTime.setText(bean.getTime());
        holder.picStyle.setVisibility(View.GONE);

    		/*ZYY 处理是否是 视频累型消息*/
//            if (item.getLastContentType() == ChatMessage.MsgContentType.VIDEO ||
//                    item.getLastContentType() == ChatMessage.MsgContentType.JOIN_CALL) {
//                Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_call);
//                /// 这一步必须要做,否则不会显示.
//                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//                holder.mContent.setCompoundDrawables(drawable, null, null, null);
//                holder.mContent.setCompoundDrawablePadding(5);
//            } else {
        holder.mContent.setCompoundDrawables(null, null, null, null);
        holder.mContent.setCompoundDrawablePadding(0);
//            }
//            // 设置样式和值
//            settingStyle(holder, item);
//            // 设置未读条数
//            setUnReadCount(holder, item);
//            // 消息处理
//            msgProccess(holder, item);
//            // 处理群头像显示
//            settingMsgHead(holder, item);
//        }

        return convertView;
    }


//    /* 头像 */
//    private void settingMsgHead(final ViewHolder holder, Conversation item) {
//        if (item == null) {
//            return;
//        }
//        long downLoadUserId = item.getConversationId();
//        try {
//            holder.showTime.setTextColor(mContext.getResources().getColor(R.color.cj_messsage_list_time));
//
//            if (item.isGroup()) { //群聊头像显示
//                //是群组 则查询组合群组头像所需的url
//                String urls = WiDbHelperMgr.getInstance().getGroupDbHelper().getGruopPhotoUrl(UserManager.getInstance().getUserID(), (int) item.getConversationId());
//                com.orhanobut.logger.Logger.d("头像：" + urls);
//                holder.dpv_g2g.setVisibility(View.VISIBLE);
//                holder.rl_p2p.setVisibility(View.GONE);
//                holder.user_img.setVisibility(View.GONE);
//                holder.img_vaccount.setVisibility(View.GONE);
//                holder.dpv_g2g.photoViewProcess(urls);
//
//            } else if (item.isP2P()) {// 如果是单聊
//                holder.dpv_g2g.setVisibility(View.GONE);
//                holder.rl_p2p.setVisibility(View.VISIBLE);
//                holder.user_img.setVisibility(View.VISIBLE);
//                holder.img_vaccount.setVisibility(View.VISIBLE);
//
//                //WH1605064 程靖 修改单人聊天不能显示头像的bug
//                // 单聊头像处理
//                ContactBaseInfor info = WiDbHelperMgr.getInstance().getContactsHelper().getContactsBaseInfoByUaId(item.getCompanyId(), downLoadUserId);
//                if (info != null) {
//                    String headImageUrl = SsHttpManagerConstants.getURL(SsHttpManagerConstants.HEALTHFILESYS_HEADIMAGE_GETIMAGE + "/" + info.getHeadimg());
//                    DisplayImageOptions options = new DisplayImageOptions.Builder()
//                            .showImageOnFail(R.drawable.icon_c)
//                            .showImageForEmptyUri(R.drawable.icon_c)
//                            .showStubImage(R.drawable.icon_c).cacheInMemory().cacheOnDisc()
//                            .displayer(new SimpleBitmapDisplayer()).build();
//                    String newIconPath = ImageLoadUtils.formateImageAdress(headImageUrl, mContext.getString(R.string.image_3));
//                    Logger.d("接口获取到的头像地址:" + info.getHeadimg() + "      headImageUrl:" + headImageUrl
//                            + "     newIconPath:" + newIconPath);
//
//                    ImageLoader.getInstance().displayImage(newIconPath, holder.user_img, options);
//                    //处理认证账户
//                    if (!TextUtils.equals(String.valueOf(info.getUserType()), ConfigConstants.COMMON_USER) && item.getCategory() != Conversation_Type.MEETING_TYPE) {
//                        holder.img_vaccount.setVisibility(View.VISIBLE);
//                    } else {
//                        holder.img_vaccount.setVisibility(View.GONE);
//                    }
//                } else {
//                    holder.user_img.setImageResource(R.drawable.icon_c);
//                }
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /*
//     * 消息处理
//     */
//    private void msgProccess(ViewHolder holder, Conversation item) {
//        if (item == null) {
//            return;
//        }
//        long userId = item.getConversationId();
//        int lastContentType = item.getLastContentType();
//        int encryptType = item.getEncryptMethod();
//        String encryptContent = item.getEncryptContent();
//        String topTime = item.getTopTime() == null ? "" : item.getTopTime();
//        int messageDnd = item.getMessageDnd();
//        int count = item.getUnReadNum();
//        String context = item.getLastContent() == null ? "" : item
//                .getLastContent();
//        int isFire = item.getIsFired(); // 消息是否已焚毁
//        String content = "";
//        boolean isGroup = item.isGroup();
//        String sendName = item.getLastSenderName() == null ? userId + "" : item.getLastSenderName();
//        //有备注名显示备注名，备注名从缓存中取
//        String remarkName = YKContactRemarkCacheManager.getInstance().getContactRemark(item.getLastSender());
//        if (!TextUtils.isEmpty(remarkName)) {
//            sendName = remarkName;
//        }
//        int msgStatus = item.getMsgStatus();//消息状态
//
//        if (lastContentType == ChatMessage.MsgContentType.IMAGE) {
//            content = mContext.getString(R.string.image);
//        } else if (lastContentType == ChatMessage.MsgContentType.AUDIO) {
//            content = mContext.getString(R.string.recording);
//            //如果是文本消息和截屏消息就解析文本结构   ,提醒消息与IOS同步
//        } else if (lastContentType == ChatMessage.MsgContentType.TEXT
//                || lastContentType == ChatMessage.MsgContentType.VIDEO
//                || lastContentType == ChatMessage.MsgContentType.VIDEO_HINT_MESSAGE
//                || lastContentType == ChatMessage.MsgContentType.SCREENSHOT_MSG
//                || lastContentType == ChatMessage.MsgContentType.CONTENT_TYPE_NOTICE
//                || lastContentType == ChatMessage.MsgContentType.ACCEPT_ADD_FRIEND /*同意添加好友的消息*/) {
//            CommonContent bean = WiJsonTools.json2BeanObject(context,
//                    CommonContent.class);
//            if (bean != null) {
//                content = bean.getContent() == null ? "" : bean.getContent();
//            }
//        } else if (item.getLastContentType() == MsgContentType.FILE_MSG) { //文件消息
//            content = mContext.getResources().getString(R.string.file);
//        } else if (item.getLastContentType() == MsgContentType.APPOINT_CALL) { //预约通话消息 ZYY-2016.11.4
//            AppointContent a = WiJsonTools.json2BeanObject(item.getLastContent(), AppointContent.class);
//            if (a.getAppointCallState() == 0) {
//                content = "[" + mContext.getResources().getString(R.string.appointcall_true) + "]";
//            } else {
//                content = "[" + mContext.getResources().getString(R.string.appointcall_false) + "]";
//            }
//        } else if (item.getLastContentType() == MsgContentType.JOIN_CALL) { //加入话消息xdg
//            content = "";
//            if (item != null) {
//                JoinCallInfo joinCallInfo = WiJsonTools.json2BeanObject(item.getLastContent(), JoinCallInfo.class);
//                if (joinCallInfo != null) {
//                    if (joinCallInfo.getMsgType() != null && "1".equals(joinCallInfo.getMsgType())) {
//                        String timeString = joinCallInfo.getMeetingTime() == null ? "" : joinCallInfo.getMeetingTime();
//                        content = " 通话时长 " + timeString;
//                    } else {
//                        String lastSendName = "";
//                        if (!TextUtils.isEmpty(item.getLastSenderName()) && !"null".equals(item.getLastSenderName())) {
//                            lastSendName = item.getLastSenderName();
//                        }
//                        content = lastSendName + "[" + mContext.getResources().getString(R.string.Join_Call) + "]";
//                    }
//                }
//            }
//
//
//        }
//
//
//        //PLog.e("huanhuan", "内容是:" + content);
//        //内容截取
//        content = StringUtil.subMaxString(content, Config.MAX_LENGTH_SHOW_CONTENT);
//
//        // 如果是加密消息
//        if (MsgEncryptType.ENCRYPT == encryptType) {
//            // 开启的时候显示密文,关闭的时候显示明文
//            if (encryptionSwticher) {
//                content = encryptContent;
//            }
//            // 不是群组
//            if (!item.isGroup()) {
//                holder.picStyle.setVisibility(View.VISIBLE);
//                holder.picStyle.setBackgroundResource(R.drawable.encroption);
//            }
//            holder.messageListItem.setBackgroundResource(R.drawable.msg_bg_list_click_enciption);
//            // 如果是阅后即焚的消息
//        } else if (MsgEncryptType.FIRE_MSG == encryptType) {
//            // 不是群组
//            if (!item.isGroup()) {
//                holder.picStyle.setVisibility(View.VISIBLE);
//                // 已焚毁设置
//                if (isFire == Msg_Fire.FIRED) {
//                    holder.picStyle
//                            .setBackgroundResource(R.drawable.isfireing_bg);
//                    //如果是图片
//                    if (lastContentType == ChatMessage.MsgContentType.IMAGE) {
//                        content = mContext.getResources().getString(
//                                R.string.msg_image_fireing);
//                    } else { //文本的
//                        content = mContext.getResources().getString(
//                                R.string.msg_msg_fireing);
//                    }
//                    holder.messageListItem
//                            .setBackgroundResource(R.drawable.msg_bg_list_click_fireing);
//                    // 未焚毁背景设置
//                } else {
//                    holder.picStyle
//                            .setBackgroundResource(R.drawable.fire_click);
//                    content = mContext.getResources().getString(R.string.friremsg);
//                    holder.messageListItem
//                            .setBackgroundResource(R.drawable.msg_bg_list_click_fire);
//                }
//            }
//        } else {// 普通消息
//            holder.picStyle.setVisibility(View.GONE);
//            holder.messageListItem.setBackgroundResource(R.drawable.msg_bg_list_click);
//        }
//
//
//        if (item.getMsgStatus() == MsgStatus.VERSION_ERROR) {
//            content = mContext.getResources().getString(R.string.no_support);
//        } else if (item.getMsgStatus() == MsgStatus.DECODE_ERROR) {
//            //content = mContext.getResources().getString(R.string.decrypt_failed);
//            content = encryptContent;
//        }
//
//        String stringResult = "";
//        String stringTest = mContext.getResources().getString(
//                R.string.message_list_content);
//
//        Integer atTypeMap = item.getAtType();
//        String atValue = item.getAtValue();
//        Integer isShow = item.getAtIsShow(); // 是否显示过
//
//        //是否有AT消息
//        boolean haveAtMsg = false;
//        // 如果是自己
//        if (!TextUtils.isEmpty(atValue)
//                && WiUserState.getInstance().getCurrentUaid() == StringUtil
//                .parseLong(atValue) && isShow == 0) {
//            // @消息
//            if (atTypeMap == ChatMessageATType.CHATMESSAGEATNORMALTYPE) {
//                holder.at_txt.setText("[有人@了您]");
//                haveAtMsg = true;
//                // *@密送消息
//            } else if (atTypeMap == ChatMessageATType.CHATMESSAGEATENCRYPTTYPE) {
//                holder.at_txt.setText("[有人私信了您]");
//                haveAtMsg = true;
//                // !@提醒某人
//            } else if (atTypeMap == ChatMessageATType.CHATMESSAGEATALERTTYPE) {
//                holder.at_txt.setText("[有人提醒了您]");
//                haveAtMsg = true;
//            } else {
//                holder.at_txt.setText("");
//            }
//        } else {
//            holder.at_txt.setText("");
//        }
//
//        // 如果消息免打扰是打开的状态
//        if (messageDnd == ChatMessage.MessageDndType.DNDOPEN) {
//            // 名称需要不是空,并且要是群,还不能是自己
//            if (isGroup
//                    && WiUserState.getInstance().getCurrentUaid() != item.getLastSender()) {
//                if (haveAtMsg) {
//                    //如果是自己则不显示名称
//                    if (WiUserState.getInstance().getCurrentUaid() != item.getLastSender()) {
//                        stringResult = String.format(stringTest, "", sendName + ":", content);
//                    } else {
//                        stringResult = String.format(stringTest, "", "", content);
//                    }
//                } else {
//                    //是否自己
//                    if (WiUserState.getInstance().getCurrentUaid() != item.getLastSender()) {
//                        stringResult = String.format(stringTest, "[" + count + "条]", sendName + ":", content);
//                    } else {
//                        stringResult = String.format(stringTest, "[" + count + "条]", "", content);
//                    }
//                }
//            } else {//是自己
//                if (haveAtMsg) {
//                    stringResult = String.format(stringTest, "", "", content);
//                } else {
//                    stringResult = String.format(stringTest, "[" + count + "条]", "", content);
//                }
//            }
//
//            // 如果未读条数大于0并且是免打扰的模式就显示未读条数
//            if (count > 0) {
//                content = stringResult;
//            } else {
//                if (isGroup && WiUserState.getInstance().getCurrentUaid() != item.getLastSender()) {
//                    content = String.format(stringTest, "", sendName + ":", content);
//                } else {
//                    content = String.format(stringTest, "", "", content);
//                }
//            }
//        } else {
//            //如果是普通消息则赋值名称
//            if (count > 0) {
//                if (isGroup && WiUserState.getInstance().getCurrentUaid() != item.getLastSender()) {
//                    //如果是提示不显示名称
//                    content = String.format(stringTest, "", sendName + ":", content);
//                } else {
//                    content = String.format(stringTest, "", "", content);
//                }
//            } else {
//                if (isGroup && WiUserState.getInstance().getCurrentUaid() != item.getLastSender()) {
//                    //如果是提示不显示名称
//                    content = String.format(stringTest, "", sendName + ":", content);
//                } else {
//                    content = String.format(stringTest, "", "", content);
//                }
//            }
//        }
//
//        //如果消息状态是撤回
//        if (msgStatus == MsgStatus.REVOKED) {
//            //如果是自己撤销的
//            if (item.getLastSender() == WiUserState.getInstance().getCurrentUaid()) {
//                content = mContext.getResources()
//                        .getString(R.string.message_withdrawal);
//            } else {
//                content = item.getLastSenderName()
//                        + mContext.getResources().getString(R.string.withdrawal);
//            }
//        }
//        //群组相关提醒消息
//        if (lastContentType == MsgContentType.NEW_GROUP_MEMBER || lastContentType == MsgContentType.INVITE_GROUP_MEMBER || lastContentType == MsgContentType.LEAVE_GROUP) {
//            TextContent text = WiJsonTools.json2BeanObject(item.getLastContent(),
//                    TextContent.class);
//            content = text.getContent();
//        }
//        holder.mContent.setText(content);
//
//        // 置顶灰色不是置顶则白色
//        if (!(TextUtils.isEmpty(topTime) || topTime.equals("null") || topTime.equals("0"))) {
//            holder.messageListItem
//                    .setBackgroundResource(R.drawable.msg_bg_list_click_top);
//        }
//    }
//
//
//
//
//    private void setUnReadCount(ViewHolder holder, Conversation item) {
//        if (holder == null || item == null) {
//            return;
//        }
//        int messageDnd = item.getMessageDnd();
//        int count = item.getUnReadNum();
//        // 如果该条会话是会议消息
//        if (item.getConversationId() == Group_Constants.MEETING_CONVERSATION_ID) {
//            //PLog.e("huanhuan", "会议未读条数:" + count);
//            if (count > 0) {
//                // 显示小红点
//                holder.rGroupUnread.setVisibility(View.VISIBLE);
//            } else {
//                //  不显示小红点
//                holder.rGroupUnread.setVisibility(View.GONE);
//            }
//            holder.reUnread.setVisibility(View.GONE);
//        } else {
//            // 普通的聊天消息和群聊消息
//            if (count > 0) {
//                // 如果免打扰是开的则用免打扰的方式显示,否则用普通的字条条显示
//                if (messageDnd == ChatMessage.MessageDndType.DNDOPEN) {
//                    holder.rGroupUnread.setVisibility(View.VISIBLE);
//                    holder.reUnread.setVisibility(View.GONE);
//                } else {
//                    holder.reUnread.setVisibility(View.VISIBLE);
//                    holder.rGroupUnread.setVisibility(View.GONE);
//                }
//                String unre = "";
//                if (count > 99) {
//                    unre = "99+";
//                } else {
//                    unre = count + "";
//                }
//                holder.unMessageNumber.setText(unre);
//            } else {
//                holder.reUnread.setVisibility(View.GONE);
//                holder.rGroupUnread.setVisibility(View.GONE);
//            }
//        }
//    }
//
//
//    private void settingStyle(ViewHolder holder, Conversation item) {
//        if (item == null) {
//            return;
//        }
//
//        String name = item.getConversationName() == null ? "" : item.getConversationName();
//        String msgTime = item.getLastMsgTime();
//        msgTime = DateUtil.convertMessageListTime(msgTime);
//        int messageDnd = item.getMessageDnd();
//
//        //非群聊，就從數據庫中取用戶的備註名     陳曉東 WH1605070
//        if (item.isP2P()) {
//            //fkq 该从缓存中取备注名
//            String remarkByUaid = YKContactRemarkCacheManager.getInstance().getContactRemark(item.getConversationId());
//            if (!TextUtils.isEmpty(remarkByUaid)) {
//                name = remarkByUaid;
//            }
//        }
//
//        //名称截取
//        name = StringUtil.subMaxString(name, Config.MAX_TITLE_LENGTH);
//
//        holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.cj_messsage_list_title));
//
//        holder.tvTitle.setText(name);
//        holder.showTime.setText(msgTime);
//
//        // 控制免打扰是开还是关
//        if (messageDnd == ChatMessage.MessageDndType.DNDOPEN) {
//            holder.messageDnd.setVisibility(View.VISIBLE);
//        } else {
//            holder.messageDnd.setVisibility(View.GONE);
//        }
//    }

    public class ViewHolder {
        ImageView user_img;
        ImageView img_vaccount;
        TextView tvTitle;
        TextView mContent;
        RelativeLayout reUnread;
        TextView unMessageNumber;
        ImageView unMeetingImg;
        TextView showTime;
        RelativeLayout messageListItem;
        TextView sendName_txt;  // 发送者
        ImageView picStyle;     // 图片样式,是否加密或阅后即焚
        ImageView messageDnd;   // 消息免打扰
        RelativeLayout rGroupUnread;// 群未读
        TextView at_txt;        //@消息提示
        DeformablePhotoView dpv_g2g;    // 群头像
        RelativeLayout rl_p2p;          //单聊头像
    }


}
