package com.yunkang.prenatalgt.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunkang.prenatalgt.R;
import com.yunkang.prenatalgt.adapter.MessageDetailAdapter;
import com.yunkang.prenatalgt.bean.ChatMessage;
import com.yunkang.prenatalgt.bean.MessageBean;
import com.yunkang.prenatalgt.bean.UserBean;
import com.yunkang.prenatalgt.ui.base.BaseActivity;
import com.yunkang.prenatalgt.widget.ChatEditText;
import com.yunkang.prenatalgt.widget.InterceptTouchRelativeLayout;
import com.yunkang.prenatalgt.widget.KeyBoardListenerRelativeLayout;
import com.yunkang.prenatalgt.widget.PullDownListView;
import com.yunkang.prenatalgt.widget.VFaceLayout;
import com.yunkang.prenatalgt.widget.VMessageToolView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageDetailActivity extends BaseActivity {

    @BindView(R.id.messageBack)
    ImageView messageBack;

    @BindView(R.id.mCancel)
    TextView mCancel;
    @BindView(R.id.detailtag)
    TextView detailtag;
    @BindView(R.id.callPhone)
    ImageView callPhone;
    @BindView(R.id.eidtMessage)
    ImageView eidtMessage;
    @BindView(R.id.topRelative)
    LinearLayout topRelative;
    @BindView(R.id.iv_sending)
    TextView ivSending;
    @BindView(R.id.more)
    ImageView more;
    @BindView(R.id.rmore)
    RelativeLayout rmore;
    @BindView(R.id.modeSwitchtools)
    TextView modeSwitchtools;
    @BindView(R.id.rmodeSwitchtools)
    RelativeLayout rmodeSwitchtools;
    @BindView(R.id.recoding)
    ImageView recoding;
    @BindView(R.id.rly)
    RelativeLayout rly;
    @BindView(R.id.downRecoding)
    Button downRecoding;
    @BindView(R.id.et_sendmessage)
    ChatEditText etSendmessage;
    @BindView(R.id.rbqtools)
    RelativeLayout rbqtools;    //表情
    @BindView(R.id.rl_bottom)
    InterceptTouchRelativeLayout rlBottom;
    @BindView(R.id.face_layout)
    VFaceLayout faceLayout;
    @BindView(R.id.rbq)
    RelativeLayout rbq;
    @BindView(R.id.rtools)
    VMessageToolView rtools;
    @BindView(R.id.deleteLine)
    View deleteLine;
    @BindView(R.id.mTools_delete)
    TextView mToolsDelete;
    @BindView(R.id.mTools_froward)
    TextView mToolsFroward;
    @BindView(R.id.mTools)
    RelativeLayout mTools;
    @BindView(R.id.reMessageguwen)
    KeyBoardListenerRelativeLayout reMessageguwen;

    @BindView(R.id.lvMessageDetail)
    ListView lvMessageDetail;

    @BindView(R.id.sreach_list)
    PullDownListView sreachList;

    @BindView(R.id.new_message)
    TextView newMessage;
    @BindView(R.id.mTips)
    TextView mTips;
    @BindView(R.id.messageTips)
    RelativeLayout messageTips;
    @BindView(R.id.ino_net_icon)
    ImageView inoNetIcon;
    @BindView(R.id.rNoNet)
    RelativeLayout rNoNet;
    @BindView(R.id.readMsgCount)
    TextView readMsgCount;
    @BindView(R.id.readCount)
    RelativeLayout readCount;

    private MessageDetailAdapter detailAdapter;

    private List<ChatMessage> messageList;

    private MessageBean messageBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        messageBean = (MessageBean) getIntent().getExtras().get("data");

        detailtag.setText(messageBean.getUser().getName());
        initView();


        initAdapter();
    }


    private void initView() {

    }


    private void initAdapter() {
        messageList = new ArrayList<>();
        createChatMes();


        detailAdapter = new MessageDetailAdapter(messageList, this);
        lvMessageDetail.setAdapter(detailAdapter);

    }

    private void createChatMes() {

        ChatMessage chatMessage = new ChatMessage(0, messageBean.getUser(), messageBean.getContent());
        messageList.add(chatMessage);


        chatMessage = new ChatMessage(0, messageBean.getUser(), "你懂哲学吗?");
        messageList.add(chatMessage);

        chatMessage = new ChatMessage(1, new UserBean("me", R.mipmap.ic_launcher), "不懂");
        messageList.add(chatMessage);


        chatMessage = new ChatMessage(0, messageBean.getUser(), "那你至少失去了一半的生命");
        messageList.add(chatMessage);

        chatMessage = new ChatMessage(0, messageBean.getUser(), "你懂数学吗");
        messageList.add(chatMessage);

        chatMessage = new ChatMessage(1, new UserBean("me", R.mipmap.ic_launcher), "不懂");
        messageList.add(chatMessage);

        chatMessage = new ChatMessage(0, messageBean.getUser(), "那你失去了80%的生命。");
        messageList.add(chatMessage);


        chatMessage = new ChatMessage(1, new UserBean("me", R.mipmap.ic_launcher), "你会游泳吗");
        messageList.add(chatMessage);


        chatMessage = new ChatMessage(0, messageBean.getUser(), "不会");
        messageList.add(chatMessage);

        chatMessage = new ChatMessage(1, new UserBean("me", R.mipmap.ic_launcher), "那你将失去百分之百的生命？");
        messageList.add(chatMessage);


    }

    @OnClick(R.id.callPhone)
    public void onClick() {
    }
}
