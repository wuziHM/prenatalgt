package com.yunkang.prenatalgt.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.yunkang.prenatalgt.R;
import com.yunkang.prenatalgt.bean.Gravida;
import com.yunkang.prenatalgt.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_erlong)
    TextView tvErlong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);

        initView();


    }

    private void initView() {

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

        tvAge.setText("" + gravida.getAge());
        tvName.setText(gravida.getName());
        tvSex.setText(gravida.getSex());
    }

    @OnClick({R.id.tv_age, R.id.tv_erlong})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_age:
                break;
            case R.id.tv_erlong:
                ReportSubActivity.toReportSubActivity(this);
                break;
        }
    }
}
