package com.nmsl.coolmall.mine.activity;


import android.view.View;

import com.nmsl.coolmall.R;
import com.nmsl.coolmall.core.base.BaseActivity;
import com.nmsl.coolmall.core.widget.MyToolbar;

import butterknife.BindView;
import butterknife.OnClick;

public class PactPActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    MyToolbar ppToolBar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pact_p;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        ppToolBar.setPadding(0, getStatusBarHeight(), 0, 0);
    }


    @Override
    public void initListener() {

    }
}