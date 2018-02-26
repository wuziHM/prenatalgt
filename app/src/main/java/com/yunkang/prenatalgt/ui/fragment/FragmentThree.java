package com.yunkang.prenatalgt.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunkang.prenatalgt.R;
import com.yunkang.prenatalgt.adapter.MessageListAdapter;
import com.yunkang.prenatalgt.bean.MessageBean;
import com.yunkang.prenatalgt.bean.UserBean;
import com.yunkang.prenatalgt.ui.activity.MessageDetailActivity;
import com.yunkang.prenatalgt.ui.base.BaseFragment;
import com.yunkang.prenatalgt.widget.SearchListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class FragmentThree extends BaseFragment {


    @BindView(R.id.ino_net_icon)
    ImageView inoNetIcon;
    @BindView(R.id.rNoNet)
    RelativeLayout rNoNet;
    @BindView(R.id.etSearchBarSearch)
    View etSearchBarSearch;
    @BindView(R.id.searchPic)
    ImageView searchPic;
    @BindView(R.id.tv_meetting_search)
    TextView tvMeettingSearch;
    @BindView(R.id.rSearchPic)
    RelativeLayout rSearchPic;
    @BindView(R.id.search_rl)
    RelativeLayout searchRl;
    @BindView(R.id.mListViewNews)
    SearchListView mListViewNews;
    @BindView(R.id.noMessage)
    TextView noMessage;
    @BindView(R.id.noContent)
    LinearLayout noContent;
    @BindView(R.id.homeTop)
    RelativeLayout homeTop;


    private MessageListAdapter adapter;
    private List<MessageBean> mesList;

    public FragmentThree() {
    }

    public static FragmentThree newInstance() {
        FragmentThree fragment = new FragmentThree();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mesList = new ArrayList();
        mesList.add(new MessageBean("医生您好，我的情况是这样的....", new UserBean("李秀丽", R.mipmap.iv_lol_icon1), "2018-01-01"));
        mesList.add(new MessageBean("感谢您的用心治疗....", new UserBean("李宏毅", R.mipmap.iv_lol_icon2), "2017-11-19"));
        mesList.add(new MessageBean("医生您好，耳聋基因异常....", new UserBean("张晓彤", R.mipmap.iv_lol_icon3), "2017-11-09"));
        mesList.add(new MessageBean("医生您好，甲功三项的检查报告不明白....", new UserBean("刘啸天", R.mipmap.iv_lol_icon4), "2017-10-06"));
        mesList.add(new MessageBean("萨摩耶乘地铁出走....", new UserBean("张周志", R.mipmap.iv_lol_icon5), "2017-09-09"));
        mesList.add(new MessageBean("10游客滞留泰国机场....", new UserBean("肯迪尼", R.mipmap.iv_lol_icon6), "2018-08-02"));
        adapter = new MessageListAdapter(getContext(), mesList);
        mListViewNews.setAdapter(adapter);
        mListViewNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getContext(), MessageDetailActivity.class);
                intent.putExtra("data", mesList.get(i));
                startActivity(intent);
            }
        });
    }
}
