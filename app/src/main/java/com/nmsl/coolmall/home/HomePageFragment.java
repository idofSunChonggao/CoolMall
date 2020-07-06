package com.nmsl.coolmall.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.annotations.SerializedName;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.nmsl.coolmall.R;
import com.nmsl.coolmall.core.base.BasePagerFragment;
import com.nmsl.coolmall.core.data.CommonDataProvider;
import com.nmsl.coolmall.core.data.UrlConstants;
import com.nmsl.coolmall.core.image.BannerImageLoader;
import com.nmsl.coolmall.core.image.GlideHelper;
import com.nmsl.coolmall.core.model.NewCommodityBean;
import com.nmsl.coolmall.core.network.okgo.GsonCallback;
import com.nmsl.coolmall.core.utils.CountDownTimerHelper;
import com.nmsl.coolmall.home.activity.EducationListActivity;
import com.nmsl.coolmall.home.activity.SnapUpActivity;
import com.nmsl.coolmall.home.model.CategoryBean;
import com.nmsl.coolmall.home.model.PageCommodityBean;
import com.nmsl.coolmall.hot.activity.CommodityDetailActivity;
import com.nmsl.coolmall.hot.activity.CommodityListActivity;
import com.nmsl.coolmall.hot.adapter.CommodityAdapter;
import com.nmsl.coolmall.search.activity.SearchActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author NoOne 2019.03.04
 */
public class HomePageFragment extends BasePagerFragment {
    private static final String TAG = "HomePageFragment";

    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.tab_category)
    TabLayout mCategory_tab;
    @BindView(R.id.remain_hour_tv)
    TextView mRemainHourTv;
    @BindView(R.id.remain_minute_tv)
    TextView mRemainMinuteTv;
    @BindView(R.id.remain_second_tv)
    TextView mRemainSecondTv;
    @BindView(R.id.more_snap_up_btn)
    TextView mMoreSnapUpBtn;
    @BindView(R.id.recommend_snap_up_rv)
    RecyclerView mRecommendSnapUpRv;
    @BindView(R.id.essential_snap_up_rv)
    RecyclerView mEssentialSnapUpRv;
    @BindView(R.id.category_rv)
    RecyclerView mCategoryRv;
    @BindView(R.id.search_bar_layout)
    ViewGroup mSearchBarLayout;
    //    @BindView(R.id.RefreshPage)
//    SwipeRefreshLayout mRefreshPage;
    private CategoryAdapter mCategoryAdapter;
    private RecommendSnapUpAdapter mRecommendSnapUpAdapter;
    private EssentialSnapUpAdapter mEssentialSnapUpAdapter;
    private LinearLayoutManager mManager;
    //private CategoryCommodityAdapter mCommodityAdapter;
    private int[] imageRes = {R.mipmap.ic_1,R.mipmap.ic_2, R.mipmap.ic_3,R.mipmap.ic_4, R.mipmap.ic_5,};
    private String titles[] = new String[]{"在线教育","精品农业","运动健身","美容美妆","其他"};
    private boolean isScrollAgain = false;
    private int mPosition;

    public static HomePageFragment newInstance() {
        Bundle args = new Bundle();

        HomePageFragment fragment = new HomePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_homepage;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mSearchBarLayout.setPadding(mSearchBarLayout.getPaddingLeft(),
                mSearchBarLayout.getPaddingTop() + getStatusBarHeight(),
                mSearchBarLayout.getPaddingRight(),
                mSearchBarLayout.getPaddingBottom());
        setStatusBarTextBlack(false);
        setStatusBarTextColor();
        mBanner.setImageLoader(new BannerImageLoader());
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        for (int i = 0; i <5; i++) {
            View view_tab = getLayoutInflater().inflate(R.layout.item_tab_category, null);
            TextView tv= view_tab.findViewById(R.id.title_tab);
            tv.setText(titles[i]);
            ImageView iv = view_tab.findViewById(R.id.icon_tab);
            iv.setBackgroundResource(imageRes[i]);
            mCategory_tab.addTab(mCategory_tab.newTab().setCustomView(view_tab));
        }
       /* mCategory_tab.addTab(mCategory_tab.newTab().setText("在线教育").setIcon(R.mipmap.ic_1));
        mCategory_tab.addTab(mCategory_tab.newTab().setText("精品农业").setIcon(R.mipmap.ic_2));
        mCategory_tab.addTab(mCategory_tab.newTab().setText("运动健身").setIcon(R.mipmap.ic_3));
        mCategory_tab.addTab(mCategory_tab.newTab().setText("美容美妆").setIcon(R.mipmap.ic_4));
        mCategory_tab.addTab(mCategory_tab.newTab().setText("其他").setIcon(R.mipmap.ic_5));*/
        mCategoryAdapter = new CategoryAdapter();
        mCategoryAdapter.bindToRecyclerView(mCategoryRv);
        mCategoryAdapter.setEmptyView(R.layout.empty_view);
        mRecommendSnapUpAdapter = new RecommendSnapUpAdapter();
        mRecommendSnapUpAdapter.bindToRecyclerView(mRecommendSnapUpRv);
        mRecommendSnapUpAdapter.setEmptyView(R.layout.empty_view);
        mEssentialSnapUpAdapter = new EssentialSnapUpAdapter();
        mEssentialSnapUpAdapter.bindToRecyclerView(mEssentialSnapUpRv);
        mEssentialSnapUpAdapter.setEmptyView(R.layout.empty_view);

//        mRefreshPage.setRefreshing(false);
//        mRefreshPage.measure(0, 0);

    }


    @Override
    protected void initData() {
        mBanner.setImages(CommonDataProvider.getInstance().getBannerUrls(10));
        mBanner.start();

        CountDownTimerHelper.addCountDownTimer(TAG, 3670, mRemainHourTv, mRemainMinuteTv, mRemainSecondTv);
        getData();
        //getCommodityTypeData();
        //getCommodityTypeDataEdu();
    }

//    private void refresh(){
//        mRefreshPage.setColorSchemeResources(R.color.colorAccent);
//        // 手动调用,通知系统去测量
//        mRefreshPage.setRefreshing(true);
//        getData();
//    }

    private void getData(){
         OkGo.<PageCommodityBean>get(UrlConstants.getCommodityByType + 2 + "/1")
                .execute(new GsonCallback<PageCommodityBean>(PageCommodityBean.class) {
                    @Override
                    public void onSuccess(Response<PageCommodityBean> response) {
                        if(response.body().result == null){
                            getCommodityTypeDataEdu();
                        }else{
                            CategoryBean bean = new CategoryBean();
                            bean.type = 2;
                            bean.setCategoryName("在线教育");
                            bean.setRecommendCommodities(response.body().result);
                            mCategoryAdapter.addData(bean);
                        }

                    }
                    @Override
                    public void onError(Response<PageCommodityBean> response) {
                        if(response.body() == null){
                            getCommodityTypeDataEdu();
                        }
                        super.onError(response);
                        showToast(getResources().getString(R.string.network_error));
                    }
                });
    }


//    private void getCommodityOther() {
//        OkGo.<InterestCommodityBean>get(UrlConstants.getOtherCommodity)
//                .execute(new GsonCallback<InterestCommodityBean>(InterestCommodityBean.class) {
//                    @Override
//                    public void onSuccess(Response<InterestCommodityBean> response) {
//                        InterestCommodityBean bean = response.body();
//                        if (bean.result != null && !bean.result.isEmpty()) {
//                            String[] titles = null;
//                            if (bean.userInterest != null) {
//                                titles = bean.userInterest.split(",");
//                            }
//                            for (int i = 0; i < bean.result.size(); i++) {
//                                CategoryBean categoryBean = new CategoryBean();
//                                categoryBean.type = 5;
//                                String title = "其他";
//                                if (titles != null && i < titles.length) {
//                                    title = titles[i];
//                                }
//                                categoryBean.setCategoryName(title);
//                                categoryBean.setRecommendCommodities(bean.result.get(i));
//                                mCategoryAdapter.addData(categoryBean);
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onError(Response<InterestCommodityBean> response) {
//                        super.onError(response);
//                        showToast(getResources().getString(R.string.network_error));
//                    }
//                });
//    }

    private void getCommodityTypeDataEdu() {
        OkGo.<PageCommodityBean>get(UrlConstants.getCommodityByType + 2 + "/1")
                .execute(new GsonCallback<PageCommodityBean>(PageCommodityBean.class) {
                    @Override
                    public void onSuccess(Response<PageCommodityBean> response) {
                        if(response.body().result == null){
                            getCommodityTypeDataEdu();
                        }else{
                            CategoryBean bean = new CategoryBean();
                            bean.type = 1;
                            bean.setCategoryName("在线教育");
                            bean.setRecommendCommodities(response.body().result);
                            //mCategoryAdapter.remove(0);
                            //mCategoryAdapter.addData(bean);
                            mCategoryAdapter.setData(0,bean);
                        }

                    }
                    @Override
                    public void onError(Response<PageCommodityBean> response) {
                        if(response.body() == null){
                            getCommodityTypeDataEdu();
                        }
                        super.onError(response);
                        showToast(getResources().getString(R.string.network_error));
                    }
                });
    }
    private void getCommodityTypeDataSport() {
        OkGo.<PageCommodityBean>get(UrlConstants.getCommodityByType + 1 + "/1")
                .execute(new GsonCallback<PageCommodityBean>(PageCommodityBean.class) {
                    @Override
                    public void onSuccess(Response<PageCommodityBean> response) {
                        CategoryBean bean = new CategoryBean();
                        bean.type = 3;
                        bean.setCategoryName("运动健身");213
                        if(response.body().result == null){
                            getCommodityTypeDataSport();
                        }
                        bean.setRecommendCommodities(response.body().result);
                        //mCategoryAdapter.remove(0);
                        //mCategoryAdapter.addData(bean);
                        mCategoryAdapter.setData(0,bean);

                    }

                    @Override
                    public void onError(Response<PageCommodityBean> response) {
                        super.onError(response);
                        showToast(getResources().getString(R.string.network_error));
                    }
                });
    }
    private void getCommodityTypeDataMakeUp() {
        OkGo.<PageCommodityBean>get(UrlConstants.getCommodityByType + 3 + "/1")
                .execute(new GsonCallback<PageCommodityBean>(PageCommodityBean.class) {
                    @Override
                    public void onSuccess(Response<PageCommodityBean> response) {
                        CategoryBean bean = new CategoryBean();
                        bean.type = 4;
                        bean.setCategoryName("美容美妆");
                        if(response.body().result==null) {
                            getCommodityTypeDataMakeUp();
                        }
                        bean.setRecommendCommodities(response.body().result);
                        //mCategoryAdapter.remove(0);
                        //mCategoryAdapter.addData(bean);
                        mCategoryAdapter.setData(0,bean);
                    }

                    @Override
                    public void onError(Response<PageCommodityBean> response) {
                        if(response.body().result==null) {
                            getCommodityTypeDataMakeUp();
                        }
                        super.onError(response);
                        showToast(getResources().getString(R.string.network_error));
                    }
                });
    }
    private void getCommodityTypeDataAgu() {
        OkGo.<PageCommodityBean>get(UrlConstants.getCommodityByType + 4 + "/1")
                .execute(new GsonCallback<PageCommodityBean>(PageCommodityBean.class) {
                    @Override
                    public void onSuccess(Response<PageCommodityBean> response) {
                        if(response.body().result==null) {
                            getCommodityTypeDataAgu();
                        }
                        CategoryBean bean = new CategoryBean();
                        bean.type = 2;
                        bean.setCategoryName("精品农业");
                        bean.setRecommendCommodities(response.body().result);
                        //mCategoryAdapter.remove(0);
                        //mCategoryAdapter.addData(bean);
                        mCategoryAdapter.setData(0,bean);
                    }

                    @Override
                    public void onError(Response<PageCommodityBean> response) {
                        if(response.body()==null) {
                            getCommodityTypeDataAgu();
                        }
                        super.onError(response);
                        showToast(getResources().getString(R.string.network_error));
                    }
                });
    }

    private void getCommodityTypeDataOther() {
        OkGo.<PageCommodityBean>get(UrlConstants.getCommodityByType + 5 + "/1")
                .execute(new GsonCallback<PageCommodityBean>(PageCommodityBean.class) {
                    @Override
                    public void onSuccess(Response<PageCommodityBean> response) {
                        if(response.body().result == null){
                            getCommodityTypeDataEdu();
                        }else{
                            CategoryBean bean = new CategoryBean();
                            bean.type = 5;
                            bean.setCategoryName("其他");
                            bean.setRecommendCommodities(response.body().result);
                            //mCategoryAdapter.remove(0);
                            //mCategoryAdapter.addData(bean);
                            mCategoryAdapter.setData(0,bean);
                        }

                    }
                    @Override
                    public void onError(Response<PageCommodityBean> response) {
                        if(response.body() == null){
                            getCommodityTypeDataOther();
                        }
                        super.onError(response);
                        showToast(getResources().getString(R.string.network_error));
                    }
                });
    }
//        OkGo.<PageCommodityBean>get(UrlConstants.getCommodityByType + UrlConstants.TYPE_OTHER + "/1")
//                .execute(new GsonCallback<PageCommodityBean>(PageCommodityBean.class) {
//                    @Override
//                    public void onSuccess(Response<PageCommodityBean> response) {
//                        CategoryBean bean = new CategoryBean();
//                        bean.type = 5;
//                        bean.setCategoryName("其他");
//                        bean.setRecommendCommodities(response.body().result);
//                        mCategoryAdapter.addData(bean);
//                    }
//
//                    @Override
//                    public void onError(Response<PageCommodityBean> response) {
//                        super.onError(response);
//                        showToast(getResources().getString(R.string.network_error));
//                    }
//                });
//    }





    @OnClick(R.id.search_bar_layout)
    public void onClickSearchView() {
        startActivity(SearchActivity.class);
    }

    private static class InterestCommodityBean {
        @SerializedName("user_interest")
        String userInterest;
        List<List<NewCommodityBean>> result;
    }

    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        RecyclerView.LayoutManager layoutManager = mCategoryRv.getLayoutManager();
        mManager = (LinearLayoutManager) layoutManager;
        int firstItem = mManager.findFirstVisibleItemPosition();
        int lastItem = mManager.findLastVisibleItemPosition();
        Log.d(TAG, "first last " + firstItem + ";" + lastItem);

        if (position < firstItem) {
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                // smoothScrollToPosition 不会有效果，此时调用smoothScrollBy来滑动到指定位置
                mRecyclerView.smoothScrollBy(0, top);
                Log.d(TAG, "if--2 ");
            }
        } else {
            mRecyclerView.smoothScrollToPosition(position);
            mPosition = position;
            isScrollAgain = true;
            Log.d(TAG, "if--3 ");
        }
    }
    @Override
    protected void initListener() {
        mCategory_tab.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabSelected: " + tab.getPosition());
                /*if (mCategoryRv.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                    smoothMoveToPosition(mCategoryRv, tab.getPosition());
                   // mCategoryRv.smoothScrollToPosition(tab.getPosition());
                }*/
                switch(tab.getPosition()){
                    case 0:
                        getCommodityTypeDataEdu();
                        break;
                    case 1:
                        getCommodityTypeDataAgu();
                        break;
                    case 2:
                        getCommodityTypeDataSport();
                        break;
                    case 3:
                        getCommodityTypeDataMakeUp();
                        break;
                    case 4:
                        getCommodityTypeDataOther();

                }

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mCategoryRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isScrollAgain && RecyclerView.SCROLL_STATE_IDLE == newState) {
                    isScrollAgain = false;
                    smoothMoveToPosition(mCategoryRv, mPosition);
                    Log.d(TAG, "scroll again");
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //int position = mManager.findFirstVisibleItemPosition();
                //mCategory_tab.setScrollPosition(position, 0, true);
            }
        });

        mRecommendSnapUpAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CommodityDetailActivity.startActivity(mActivity, ((NewCommodityBean) adapter.getData().get(position)).getCouponInfoId());
            }
        });


        mEssentialSnapUpAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CommodityDetailActivity.startActivity(mActivity, ((NewCommodityBean) adapter.getData().get(position)).getCouponInfoId());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mBanner.startAutoPlay();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBanner.stopAutoPlay();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        CountDownTimerHelper.removeCountDownTimer(TAG);
    }

    @OnClick(R.id.more_snap_up_btn)
    public void onClick() {
        startActivity(SnapUpActivity.class);
    }

    private static class CategoryAdapter extends BaseQuickAdapter<CategoryBean, BaseViewHolder> {

        public CategoryAdapter() {
            super(R.layout.item_homepage_category);
            setOnItemChildClickListener(new OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (view.getId() == R.id.more_btn) {
                        CategoryBean categoryBean = mData.get(position);
                        if (categoryBean.getCategoryName() == "在线教育"){
                            Intent intent = new Intent(mContext, EducationListActivity.class);
                            intent.putExtra(EducationListActivity.BUNDLE_TYPE, categoryBean.type);
                            mContext.startActivity(intent);
                        }
                        else {
                            Intent intent = new Intent(mContext, CommodityListActivity.class);
                            intent.putExtra(CommodityListActivity.BUNDLE_TYPE, categoryBean.type);
                            mContext.startActivity(intent);
                        }
                    }
                }
            });
        }
        private CategoryCommodityAdapter mCommodityAdapter;
        @Override
        public void convert(BaseViewHolder helper, CategoryBean item) {
            RecyclerView mCommodityRv = (helper.getView(R.id.category_commodity_rv));
            if (mCommodityRv.getAdapter() == null) {
                mCommodityAdapter = new CategoryCommodityAdapter();
                mCommodityAdapter.bindToRecyclerView(mCommodityRv);
                mCommodityAdapter.setEmptyView(R.layout.empty_view);
                mCommodityAdapter.setNewData(item.getRecommendCommodities());
                mCommodityAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        CommodityDetailActivity.startActivity(mContext, ((NewCommodityBean) adapter.getData().get(position)).getCouponInfoId());
                    }
                });
            }
            helper.setText(R.id.category_name, item.getCategoryName());
            helper.addOnClickListener(R.id.more_btn);
        }
        /*public View getTabView(int position){
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_tab_category, null);
            TextView tv= (TextView) view.findViewById(R.id.title_tab);
            ImageView iv = (ImageView) view.findViewById(R.id.icon_tab);
            tv.setText(titles[position]);
            iv.setImageResource(imageRes[position]);
            return view;
        }*/
    }


    private static class RecommendSnapUpAdapter extends BaseQuickAdapter<NewCommodityBean, BaseViewHolder> {

        public RecommendSnapUpAdapter() {
            super(R.layout.item_simple_discount_commodity);
        }

        @Override
        protected void convert(BaseViewHolder helper, NewCommodityBean item) {
            GlideHelper.loadImage(mContext, item.getImage(), (ImageView) helper.getView(R.id.thumbnail_iv));
            helper.setText(R.id.price_before_tv, String.valueOf(item.getPrice()));
            helper.setText(R.id.price_after_tv, String.valueOf(item.getPrice()));
        }
    }

    private static class CategoryCommodityAdapter extends BaseQuickAdapter<NewCommodityBean, BaseViewHolder> {
        public CategoryCommodityAdapter() {
            super(R.layout.item_homepage_commodity);
        }

        @Override
        protected void convert(BaseViewHolder helper, NewCommodityBean item) {
            GlideHelper.loadImage(mContext, item.getImage(), (ImageView) helper.getView(R.id.thumbnail_iv));
            helper.setText(R.id.coupon_price_tv, item.getPriceTicket());
            helper.setText(R.id.name_tv, item.getName());
            helper.setText(R.id.price_after_tv, String.valueOf(item.getPrice()));
        }
    }

    private static class EssentialSnapUpAdapter extends BaseQuickAdapter<NewCommodityBean, BaseViewHolder> {
        public EssentialSnapUpAdapter() {
            super(R.layout.item_simple_commodity);
        }

        @Override
        protected void convert(BaseViewHolder helper, NewCommodityBean item) {
            GlideHelper.loadImage(mContext, item.getImage(), (ImageView) helper.getView(R.id.thumbnail_iv));
            helper.setText(R.id.name_tv, item.getName());
        }
    }
}
