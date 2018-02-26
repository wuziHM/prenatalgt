package com.yunkang.prenatalgt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yunkang.prenatalgt.R;
import com.yunkang.prenatalgt.bean.MessageBean;
import com.yunkang.prenatalgt.widget.CircleImageView;

import java.util.List;

/**
 * Author：HM $ on 18/1/25 16:13
 * E-mail：359222347@qq.com
 * <p>
 * use to...
 */
public class MMessageListAdapter extends BaseAdapter {

    private List<MessageBean> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public MMessageListAdapter(List<MessageBean> list, Context context) {
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public MessageBean getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.item_message_me, null);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.userImg.setImageResource(getItem(i).getUser().getHead());
        viewHolder.contentTxt.setText(getItem(i).getContent());
        viewHolder.titleTxt.setText(getItem(i).getUser().getName());
        viewHolder.showTime.setText(getItem(i).getTime());

        return view;
    }

    static class ViewHolder {
        CircleImageView userImg;


        TextView showTime;


        TextView titleTxt;

        TextView contentTxt;


        ViewHolder(View view) {
            userImg = view.findViewById(R.id.user_img);
            showTime = view.findViewById(R.id.showTime);
            titleTxt = view.findViewById(R.id.title_txt);
            contentTxt = view.findViewById(R.id.content_txt);
//            ButterKnife.bind(this, view);
        }
    }
}
