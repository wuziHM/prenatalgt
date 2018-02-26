package com.yunkang.prenatalgt.ui.fragment;


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
import com.yunkang.prenatalgt.ui.activity.ReportResActivity;
import com.yunkang.prenatalgt.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class FragmentTwo extends BaseFragment {


    @BindView(R.id.rec_com)
    RecyclerView recCom;

    private ComlistAdapter adapter;
    private List<Gravida> list;


    public FragmentTwo() {
    }

    public static FragmentTwo newInstance() {
        FragmentTwo fragment = new FragmentTwo();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);
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
                ReportResActivity.toReportResActivity(getContext(), list.get(position));
            }
        });
        recCom.setLayoutManager(new LinearLayoutManager(getContext()));
        recCom.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        recCom.setAdapter(adapter);

    }

    private List<Gravida> createData() {
        List<Gravida> list = new ArrayList<>();
        Gravida gravida = new Gravida("20114207005", "佟丽娅", 34, "女");
        list.add(gravida);
        gravida = new Gravida("20114207006", "林心如", 42, "女");
        list.add(gravida);
        gravida = new Gravida("20114207007", "柳岩", 38, "女");
        list.add(gravida);
        gravida = new Gravida("20114207008", "刘涛", 40, "女");
        list.add(gravida);
        gravida = new Gravida("20114207009", "林允儿", 28, "女");
        list.add(gravida);
        return list;
    }

}
