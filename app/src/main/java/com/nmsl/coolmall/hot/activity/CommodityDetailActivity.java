package com.nmsl.coolmall.hot.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.nmsl.coolmall.R;
import com.nmsl.coolmall.core.base.BaseActivity;
import com.nmsl.coolmall.core.data.UrlConstants;
import com.nmsl.coolmall.core.helper.LoginHelper;
import com.nmsl.coolmall.core.image.GlideHelper;
import com.nmsl.coolmall.core.model.NewCommodityBean;
import com.nmsl.coolmall.core.model.SimpleResponse;
import com.nmsl.coolmall.core.network.okgo.GsonCallback;
import com.nmsl.coolmall.core.widget.DeleteLineTextView;
import com.nmsl.coolmall.home.model.PageCommodityBean;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CommodityDetailActivity extends BaseActivity {

    private static final String TAG = "CommodityDetailActivity";

    public static final String BUNDLE_COMMODITY_BEAN = "BUNDLE_COMMODITY_BEAN";

    public static final String BUNDLE_COMMODITY_ID = "BUNDLE_COMMODITY_ID";


    @BindView(R.id.discount_coupon_iv)
    ImageView mDiscountCouponIv;
    @BindView(R.id.discount_coupon_tv)
    TextView mDiscountCouponTv;
    @BindView(R.id.discount_coupon_time_tv)
    TextView mDiscountCouponTimeTv;
    @BindView(R.id.back_btn)
    ImageView mBackBtn;
    @BindView(R.id.share_btn)
    LinearLayout mShareBtn;
    @BindView(R.id.buy_btn)
    TextView mBuyBtn;
    @BindView(R.id.price_after_tv)
    TextView mPriceAfterTv;
    @BindView(R.id.price_before_tv)
    DeleteLineTextView mPriceBeforeTv;
    @BindView(R.id.name_tv)
    TextView mNameTv;
    @BindView(R.id.intro_tv)
    TextView mIntroTv;
    @BindView(R.id.sold_tv)
    TextView mSoldTv;
    @BindView(R.id.cover_iv)
    ImageView mCoverIv;

    private String id;

    private NewCommodityBean mCommodityBean;


    public static void startActivity(Context context, String id) {
        Intent intent = new Intent(context, CommodityDetailActivity.class);
        intent.putExtra(BUNDLE_COMMODITY_ID, id);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_commodity_detail;
    }

    @Override
    public void initView() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mBackBtn.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin + getStatusBarHeight(), layoutParams.rightMargin, layoutParams.bottomMargin);
        mBackBtn.setLayoutParams(layoutParams);


    }

    @Override
    public void initData() {
        id = getIntent().getStringExtra(BUNDLE_COMMODITY_ID);

        OkGo.<PageCommodityBean>get(UrlConstants.getDetail + id)
                .execute(new GsonCallback<PageCommodityBean>(PageCommodityBean.class) {
                    @Override
                    public void onSuccess(Response<PageCommodityBean> response) {
                        List<NewCommodityBean> result = response.body().result;
                        if (result == null || result.size() == 0) {
                            return;
                        }
                        mCommodityBean = result.get(0);
                        GlideHelper.loadImage(mActivity, mCommodityBean.getImage(), mCoverIv);
                        mPriceAfterTv.setText(mCommodityBean.getPrice());
                        mPriceBeforeTv.setText(mCommodityBean.getPrice());
                        mNameTv.setText(mCommodityBean.getName());
                        mIntroTv.setText(mCommodityBean.getCouponIntro());
                        mSoldTv.setText("已售" + mCommodityBean.getCouponRemainNum());
                        mDiscountCouponTv.setText(mCommodityBean.getPriceTicket() + "元优惠券");
                        mDiscountCouponTimeTv.setText("使用期限 " + mCommodityBean.getStartPeriod() + "\n - " + mCommodityBean.getEndPeriod());
                    }

                    @Override
                    public void onError(Response<PageCommodityBean> response) {
                        super.onError(response);
                        showToast(getResources().getString(R.string.network_error));
                    }
                });
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @OnClick(R.id.back_btn)
    public void onClick() {
        finish();
    }


    @OnClick(R.id.share_btn)
    public void onShareBtnClicked() {
        if (mCommodityBean == null) {
            return;
        }
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "分享自酷邦小助手：" + mCommodityBean.getName() +
                "  领券链接🔗：" + "http://39.107.125.199:3030/coupon_home");
        shareIntent.setType("text/plain");
        startActivity(shareIntent);
       /* showToast("分享成功");*/
    }


    @OnClick(R.id.buy_btn)
    public void onBuyBtnClicked() {
        if (mCommodityBean == null) {
            return;
        } else if (!LoginHelper.isLogin(mActivity, true)) {
            return;
        }
        OkGo.<SimpleResponse>post(UrlConstants.doCollect)
                .params("coupon_activity_id", mCommodityBean.getCouponInfoId())
                .execute(new GsonCallback<SimpleResponse>(SimpleResponse.class) {
                    @Override
                    public void onSuccess(Response<SimpleResponse> response) {
                        SimpleResponse simpleResponse = response.body();
                        showToast(simpleResponse.msg);
                    }

                    @Override
                    public void onError(Response<SimpleResponse> response) {
                        super.onError(response);
                        showToast(getResources().getString(R.string.network_error));
                    }
                });
    }

    @OnClick(R.id.get_detail_btn)
    public void onClickMore() {
        if (mCommodityBean == null) {
            return;
        }

        Uri uri = null;
        try {
            uri = Uri.parse(mCommodityBean.getLink());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
