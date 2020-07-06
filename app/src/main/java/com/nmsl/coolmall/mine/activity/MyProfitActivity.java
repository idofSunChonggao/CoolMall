package com.nmsl.coolmall.mine.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nmsl.coolmall.R;
import com.nmsl.coolmall.core.base.BaseActivity;
import com.nmsl.coolmall.core.data.CommonDataProvider;
import com.nmsl.coolmall.core.widget.MyToolbar;
import com.nmsl.coolmall.mine.adapter.ProfitAdapter;

import butterknife.BindView;

public class MyProfitActivity extends BaseActivity {


    @BindView(R.id.tool_bar)
    MyToolbar mToolBar;
    @BindView(R.id.total_profit_tv)
    TextView mTotalProfitTv;
    @BindView(R.id.profit_rv)
    RecyclerView mProfitRv;

    private ProfitAdapter mProfitAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_profit;
    }

    @Override
    public void initView() {
        /*mToolBar.setPadding(0, getStatusBarHeight(), 0, 0);
        mToolBar.addRightText("提现", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(WithdrawActivity.class);
            }
        });*/

        mProfitAdapter = new ProfitAdapter();
        mProfitAdapter.bindToRecyclerView(mProfitRv);
        mProfitAdapter.setEmptyView(R.layout.empty_view);
        mProfitAdapter.addFooterView(View.inflate(mActivity, R.layout.rv_footer_layout, null));
    }

    @Override
    public void initData() {
        mProfitAdapter.setNewData(CommonDataProvider.getInstance().getBillList(0));
    }

    @Override
    public void initListener() {

    }
}
