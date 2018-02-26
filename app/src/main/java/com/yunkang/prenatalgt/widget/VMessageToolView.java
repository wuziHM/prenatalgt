package com.yunkang.prenatalgt.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yunkang.prenatalgt.MApplication;
import com.yunkang.prenatalgt.R;
import com.yunkang.prenatalgt.adapter.MessageToolsAdapter;
import com.yunkang.prenatalgt.listener.ButtonClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者： [欢欢 email：HuanHuanFu@wistronits.com]<br>
 * @版本： [V1.0.0, 2014-07-15]<br>
 * @描述：聊天底部菜单<br>
 */
public class VMessageToolView extends RelativeLayout implements OnItemClickListener {

    private static final int FILE = 5;
    private static final int VOICE_MEETING = 4;
    private static final int FREE_PHONE = 3;
    private static final int VIDEO_CHAT = 2;
    private static final int TAKE_PHOTO = 1;
    private static final int PICTURE = 0;
    private static final int REFRESHTIME = 2000;
    private long timeRecording = 0;
    private GridView gv_container;
    private Context context;

    private boolean isGroup;

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean isGroup) {
        this.isGroup = isGroup;
        isGroup(isGroup);
    }

    private ButtonClickListener buttonClickListener;
    private MessageToolsAdapter adapter;
    private List<Integer> indexList = new ArrayList<Integer>();

    public VMessageToolView(Context context, AttributeSet attrs) {
        super(context, attrs);
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li;
        li = (LayoutInflater) context.getSystemService(infService);
        li.inflate(R.layout.message_tool, this);

        this.context = context;

        initDate();

    }

    private void isGroup(boolean isGroup_) {

        if (!isGroup_) {
            indexList.add(TAKE_PHOTO);
            indexList.add(PICTURE);
//			indexList.add(VIDEO_CHAT);
//
            //暂时将免费电话与视频会议去掉, 需要时再打开   WH1605070
//			indexList.add(FREE_PHONE);
//			indexList.add(VOICE_MEETING);

            indexList.add(FILE);
        } else {
            indexList.add(TAKE_PHOTO);
            indexList.add(PICTURE);
//			indexList.add(VIDEO_CHAT);
            // TODO: 18/1/24 还没引入 UserManager
//            if (UserManager.getInstance().getFunctionFlag() != 0) {
//                indexList.add(VOICE_MEETING);
//            }
            indexList.add(FILE);
        }
        setData(indexList);
    }

    private void initDate() {
        // TODO Auto-generated method stub
        gv_container = findViewById(R.id.gv_container);
        gv_container.setOnItemClickListener(this);
    }

    private void setData(List<Integer> newIndex) {

        if (adapter == null) {
            adapter = new MessageToolsAdapter(context, newIndex);
        }
        gv_container.setAdapter(adapter);

    }

    //是否需要显示文件(阅后即焚);
    public void setShowState(boolean isShow) {

        Log.i("xiao", isShow + "");
        if (isShow) {
            if (!indexList.contains(FILE)) {
                indexList.add(FILE);
            }
        } else {
            indexList.remove(Integer.valueOf(FILE));
        }
        adapter.notifyDataSetChanged();
    }

    public void hideVideoChat() {
        if (indexList.contains(VIDEO_CHAT)) {
            indexList.remove(Integer.valueOf(VIDEO_CHAT));
        }
        adapter.notifyDataSetChanged();
    }


    public void setOnButtonClickListener(ButtonClickListener listener) {
        this.buttonClickListener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, final View view, int position,
                            long id) {

        switch ((int) id) {
            case PICTURE://图片
                if (buttonClickListener != null) {
                    buttonClickListener.onClick(0);
                }
                break;
            case TAKE_PHOTO://拍照
                if (buttonClickListener != null) {
                    buttonClickListener.onClick(1);
                }
                break;
            case VIDEO_CHAT://视频聊天
                boolean equalsTime = equalsTime();
                if (equalsTime) {
                    Toast.makeText(MApplication.getContext(), "请勿频繁操作", Toast.LENGTH_SHORT).show();
                    break;
                }

                if (buttonClickListener != null) {
                    buttonClickListener.onClick(6);
                }
                break;
            case FREE_PHONE://专线电话
                if (buttonClickListener != null) {
                    buttonClickListener.onClick(5);
                }
                break;
            case VOICE_MEETING://电话会议
                if (buttonClickListener != null) {
                    buttonClickListener.onClick(7);
                }
                break;
            case FILE://文件
                if (buttonClickListener != null) {
                    buttonClickListener.onClick(3);
                }
                break;

            default:
                break;
        }
    }

    private boolean equalsTime() {
        long currentTime = System.currentTimeMillis();
        if (Math.abs(currentTime - timeRecording) > REFRESHTIME) {
            timeRecording = System.currentTimeMillis();
            return false;
        }
        return true;
    }
}
