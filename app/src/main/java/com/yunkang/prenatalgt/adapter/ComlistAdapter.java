package com.yunkang.prenatalgt.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunkang.prenatalgt.R;
import com.yunkang.prenatalgt.bean.Gravida;

import java.util.List;

/**
 * Author：HM $ on 18/2/1 10:36
 * E-mail：359222347@qq.com
 * <p>
 * use to...
 */
public class ComlistAdapter extends BaseQuickAdapter<Gravida, BaseViewHolder> {

    public ComlistAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Gravida item) {

        helper.setText(R.id.tv_num, item.getNum())
                .setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_sex, item.getSex());

    }

}
