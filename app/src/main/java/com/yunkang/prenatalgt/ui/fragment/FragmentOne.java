package com.yunkang.prenatalgt.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yunkang.prenatalgt.R;
import com.yunkang.prenatalgt.adapter.ComlistAdapter;
import com.yunkang.prenatalgt.adapter.DividerItemDecoration;
import com.yunkang.prenatalgt.bean.Gravida;
import com.yunkang.prenatalgt.ui.activity.ReportActivity;
import com.yunkang.prenatalgt.ui.base.BaseActivity;
import com.yunkang.prenatalgt.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class FragmentOne extends BaseFragment {


    @BindView(R.id.rec_com)
    RecyclerView recCom;

    private ComlistAdapter adapter;
    private List<Gravida> list;


    public FragmentOne() {
    }

    public static FragmentOne newInstance() {
        FragmentOne fragment = new FragmentOne();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list = new ArrayList();
        list.addAll(createData());
        adapter = new ComlistAdapter(R.layout.item_complete_list, list);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), ReportActivity.class);
                intent.putExtra(BaseActivity.EXTRA_DATA, list.get(position));
                startActivity(intent);
            }
        });
        recCom.setLayoutManager(new LinearLayoutManager(getContext()));
        recCom.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        recCom.setAdapter(adapter);

    }

    private List<Gravida> createData() {
        List<Gravida> list = new ArrayList<>();
        Gravida gravida = new Gravida("20114207000", "李海珍", 30, "女");
        list.add(gravida);
        gravida = new Gravida("20114207001", "高圆圆", 40, "女");
        list.add(gravida);
        gravida = new Gravida("20114207002", "张韶涵", 33, "女");
        list.add(gravida);
        gravida = new Gravida("20114207003", "蔡依林", 43, "女");
        list.add(gravida);
        gravida = new Gravida("20114207004", "唐洋", 23, "女");
        list.add(gravida);
        return list;
    }

}
