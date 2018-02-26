package com.yunkang.prenatalgt.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yunkang.prenatalgt.R;
import com.yunkang.prenatalgt.bean.DataBean;
import com.yunkang.prenatalgt.bean.Gravida;
import com.yunkang.prenatalgt.ui.base.BaseActivity;

/**
 * 已经检测的结果的界面
 */
public class ReportResActivity extends BaseActivity {
    public static void toReportResActivity(Context context, DataBean dataBean) {
        Intent intent = new Intent(context, ReportResActivity.class);
        intent.putExtra(EXTRA_DATA, dataBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_res);


        Gravida gravida = (Gravida) getIntent().getExtras().get(EXTRA_DATA);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(gravida.getName() + "的报告");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
