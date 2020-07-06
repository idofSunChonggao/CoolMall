package com.nmsl.coolmall.mine.activity;



import android.view.View;

import com.nmsl.coolmall.R;
import com.nmsl.coolmall.core.base.BaseActivity;
import com.nmsl.coolmall.core.widget.MyToolbar;

import butterknife.BindView;
import butterknife.OnClick;

public class PactStateActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    MyToolbar psToolBar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pact_state;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        psToolBar.setPadding(0, getStatusBarHeight(), 0, 0);
    }


    @Override
    public void initListener() {

    }

}
