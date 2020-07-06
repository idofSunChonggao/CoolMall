package com.nmsl.coolmall.search.activity;

import android.content.Intent;

import android.text.TextUtils;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.KeyboardUtils;
import com.nmsl.coolmall.R;
import com.nmsl.coolmall.core.base.BaseActivity;

import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.bCallBack;

import butterknife.BindView;

/**
 * @author NoOne 2019.03.04
 */
public class SearchActivity extends BaseActivity {

    @BindView(R.id.search_view)
    SearchView mSearchView;


    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        setStatusBarTextColor();
        setStatusBarTextBlack(false);
       /* LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mSearchView.getLayoutParams();
        int statusBarHeight = getStatusBarHeight();
        layoutParams.height = layoutParams.height + statusBarHeight;
        mSearchView.setLayoutParams(layoutParams);
        mSearchView.setPadding(0, statusBarHeight, 0, 0);*/
        /*ImageView closeBtn = mSearchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        closeBtn.setColorFilter(getResources().getColor(R.color.color_42d3fd));*/
    }
    @Override
    public void initData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initListener() {
        mSearchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String str) {
                onSearch(str);
            }
        });

        mSearchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });

    }


    private void onSearch(final String s) {
        if (TextUtils.isEmpty(s)) {
            showToast("请输入搜索关键词");
            return;
        }
        KeyboardUtils.hideSoftInput(mSearchView);
        Intent intent = new Intent(mActivity, SearchResultActivity.class);
        intent.putExtra(SearchResultActivity.BUNDLE_KEY_WORD, s);
        startActivity(intent);
    }



}
