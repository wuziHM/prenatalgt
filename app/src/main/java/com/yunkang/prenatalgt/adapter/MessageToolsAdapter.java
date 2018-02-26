package com.yunkang.prenatalgt.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yunkang.prenatalgt.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageToolsAdapter extends BaseAdapter implements OnClickListener {

	private Map<Integer,String> titles;
	private Map<Integer,Drawable> topics;
	private Context context;
	private List<Integer> index;
	
	public MessageToolsAdapter(Context context, List<Integer> index){
		this.titles = new HashMap<Integer, String>();
		this.topics = new HashMap<Integer, Drawable>();
		this.context = context;
		this.index = index;
		initData();
	}
	
	private void initData() {
		// TODO Auto-generated method stub
		Drawable drawable_pic = context.getResources().getDrawable(R.drawable.sl_chat_pic_btn);
		Drawable drawable_takePhoto= context.getResources().getDrawable(R.drawable.sl_chat_takephoto);
		Drawable drawable_video = context.getResources().getDrawable(R.drawable.sl_chat_video_btn);
		Drawable drawable_freePhone= context.getResources().getDrawable(R.drawable.sl_chat_freephone_btn);
		Drawable drawable_voiceMeeting = context.getResources().getDrawable(R.drawable.sl_chat_voicemeeting_btn);
		Drawable drawable_folder = context.getResources().getDrawable(R.drawable.sl_chat_folder_selector);
		
		topics.put(0, drawable_pic);
		topics.put(1, drawable_takePhoto);
		topics.put(2, drawable_video);
		topics.put(3, drawable_freePhone);
		topics.put(4, drawable_voiceMeeting);
		topics.put(5, drawable_folder);
		
		titles.put(0, "图片");
		titles.put(1, "拍照");
		titles.put(2, "通话");
		titles.put(3, "专线电话");
		titles.put(4, "通话助手");
		titles.put(5, "文件");
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return index == null?0:index.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return titles.get(index.get(position));
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return index.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = View.inflate(context, R.layout.adapter_message_tools, null);
		TextView title = convertView.findViewById(R.id.tv_message_tools_title);
		title.setText(titles.get(index.get(position)));
		Drawable drawable = topics.get(index.get(position));
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		title.setCompoundDrawables(null, drawable, null, null);
		return convertView;
	}
	
	public void setDataChanged(List<Integer> indexList){
		
		index.clear();
		index.addAll(indexList);
		notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
}
