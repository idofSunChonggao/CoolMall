package com.nmsl.coolmall.home.activity;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nmsl.coolmall.R;
import com.nmsl.coolmall.core.base.BaseActivity;
import com.nmsl.coolmall.core.model.NewCommodityBean;
import com.nmsl.coolmall.core.utils.CountDownTimerHelper;
import com.nmsl.coolmall.core.utils.DialogUtils;
import com.nmsl.coolmall.core.widget.MyToolbar;
import com.nmsl.coolmall.home.adapter.SnapUpAdapter;
import com.nmsl.coolmall.hot.activity.CommodityDetailActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SnapUpActivity extends BaseActivity {

    private static final String TAG = "SnapUpActivity";


    @BindView(R.id.tool_bar)
    MyToolbar mToolBar;
    @BindView(R.id.remain_hour_tv)
    TextView mRemainHourTv;
    @BindView(R.id.remain_minute_tv)
    TextView mRemainMinuteTv;
    @BindView(R.id.remain_second_tv)
    TextView mRemainSecondTv;
    @BindView(R.id.snap_up_rv)
    RecyclerView mSnapUpRv;

    private SnapUpAdapter mAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_snap_up;
    }

    @Override
    public void initView() {
        setStatusBarTextBlack(false);
        mToolBar.setBackgroundColor(Color.TRANSPARENT);
        mToolBar.addRightIcon(R.drawable.icon_share_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showToast("share success");
            }
        });

        mAdapter = new SnapUpAdapter();
        mAdapter.bindToRecyclerView(mSnapUpRv);
        mAdapter.addFooterView(View.inflate(mActivity, R.layout.rv_footer_layout, null));
        mAdapter.setEmptyView(R.layout.empty_view);
    }

    @Override
    public void initData() {
        CountDownTimerHelper.addCountDownTimer(TAG, 3800, mRemainHourTv, mRemainMinuteTv, mRemainSecondTv);
    }

    @Override
    public void initListener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                NewCommodityBean item = mAdapter.getItem(position);
                if (item != null) {
                    CommodityDetailActivity.startActivity(mActivity, item.getCouponInfoId());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CountDownTimerHelper.removeCountDownTimer(TAG);
    }

    @OnClick(R.id.remind_btn)
    public void onClickRemindBtn() {
        DialogUtils.showDialog(mActivity, "设置成功");
    }
}
