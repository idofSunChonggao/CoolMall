package com.nmsl.coolmall.mine.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.nmsl.coolmall.R;
import com.nmsl.coolmall.core.base.BaseActivity;
import com.nmsl.coolmall.core.helper.LoginHelper;
import com.nmsl.coolmall.core.widget.MyToolbar;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class PactActivity extends BaseActivity {

    @BindView(R.id.pact_p)
    LinearLayout mPactp;
    @BindView(R.id.pact_state)
    LinearLayout mPacts;
    @BindView(R.id.tool_bar)
    MyToolbar pToolBar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pact;
    }

    @Override
    public void initView() {
        pToolBar.setPadding(0, getStatusBarHeight(), 0, 0);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.pact_state, R.id.pact_p})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pact_p:
                startActivity(PactPActivity.class);
                break;
            case R.id.pact_state:
                startActivity(PactStateActivity.class);
                break;
        }
    }
}
