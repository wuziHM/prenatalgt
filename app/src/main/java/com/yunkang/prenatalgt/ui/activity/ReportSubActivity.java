package com.yunkang.prenatalgt.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yunkang.prenatalgt.R;
import com.yunkang.prenatalgt.ui.base.BaseActivity;

public class ReportSubActivity extends BaseActivity {

    public static void toReportSubActivity(Context context) {
        Intent intent = new Intent(context, ReportSubActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_sub);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
