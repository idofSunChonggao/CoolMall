package com.nmsl.coolmall.hot.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.nmsl.coolmall.R;
import com.nmsl.coolmall.core.data.CommonDataProvider;
import com.nmsl.coolmall.core.data.UrlConstants;
import com.nmsl.coolmall.core.image.BannerImageLoader;
import com.nmsl.coolmall.core.image.GlideHelper;
import com.nmsl.coolmall.core.model.NewCommodityBean;
import com.nmsl.coolmall.core.network.okgo.GsonCallback;
import com.nmsl.coolmall.home.model.PageCommodityBean;
import com.nmsl.coolmall.hot.activity.CommodityDetailActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author NoOne 2019.03.04
 */
public class HotMainHeaderView {

    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.hot_commodity_rv)
    RecyclerView mHotCommodityRv;

    private HotCommodityAdapter mCommodityAdapter;

    private View mView;
    private Context mContext;

    private int mCurrentPage = 1;

    public HotMainHeaderView(Context context) {
        mContext = context;
        mView = View.inflate(mContext, R.layout.hot_main_header_layout, null);
        ButterKnife.bind(this, mView);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        mBanner.setImageLoader(new BannerImageLoader());
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR);

        mCommodityAdapter = new HotCommodityAdapter();
        mCommodityAdapter.bindToRecyclerView(mHotCommodityRv);
        mCommodityAdapter.setEmptyView(R.layout.empty_view);
    }

    private void initData() {
        mBanner.setImages(CommonDataProvider.getInstance().getBannerUrls(10));
        mBanner.start();

        loadData();
    }

    private void loadData() {
        OkGo.<PageCommodityBean>get(UrlConstants.getCommodityByType + UrlConstants.TYPE_SPORTS + "/" + mCurrentPage)
                .execute(new GsonCallback<PageCommodityBean>(PageCommodityBean.class) {
                    @Override
                    public void onSuccess(Response<PageCommodityBean> response) {
                        mCurrentPage++;
                        List<NewCommodityBean> result = response.body().result;
                        List<NewCommodityBean> data = new ArrayList<>();
                        if (result != null) {
                            for (NewCommodityBean newCommodityBean : result) {
                                if (newCommodityBean != null) {
                                    data.add(newCommodityBean);
                                }
                            }
                        }
                        if (data.isEmpty() || mCurrentPage == response.body().pageSum) {
                            mCurrentPage = 1;
                            return;
                        }
                        mCommodityAdapter.setNewData(data);
                    }

                    @Override
                    public void onError(Response<PageCommodityBean> response) {
                        super.onError(response);
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initListener() {
        mCommodityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                NewCommodityBean item = mCommodityAdapter.getItem(position);
                if (item != null) {
                    CommodityDetailActivity.startActivity(mContext, item.getCouponInfoId());
                }

            }
        });
    }

    public View getView() {
        return mView;
    }

    @OnClick(R.id.refresh_btn)
    public void onClick() {
        loadData();
    }


    private static class HotCommodityAdapter extends BaseQuickAdapter<NewCommodityBean, BaseViewHolder> {
        public HotCommodityAdapter() {
            super(R.layout.item_simple_commodity);
        }

        @Override
        protected void convert(BaseViewHolder helper, NewCommodityBean item) {
            GlideHelper.loadImage(mContext, item.getImage(), (ImageView) helper.getView(R.id.thumbnail_iv));
            helper.setText(R.id.name_tv, item.getName());
        }
    }

    public void startBanner() {
        mBanner.startAutoPlay();
    }

    public void stopBanner() {
        mBanner.stopAutoPlay();
    }
}
