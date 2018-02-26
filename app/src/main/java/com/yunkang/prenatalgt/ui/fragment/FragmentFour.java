package com.yunkang.prenatalgt.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunkang.prenatalgt.R;
import com.yunkang.prenatalgt.ui.base.BaseFragment;

/**
 *
 */
public class FragmentFour extends BaseFragment {


    public FragmentFour() {
    }

    public static FragmentFour newInstance() {
        FragmentFour fragment = new FragmentFour();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_four, container, false);
    }

}
