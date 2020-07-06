package com.nmsl.coolmall.mine.activity;

import android.support.design.widget.TextInputEditText;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.nmsl.coolmall.R;
import com.nmsl.coolmall.core.base.BaseActivity;
import com.nmsl.coolmall.core.data.SharedPreDataHelper;
import com.nmsl.coolmall.core.data.UrlConstants;
import com.nmsl.coolmall.core.helper.LoginHelper;
import com.nmsl.coolmall.core.model.SimpleResponse;
import com.nmsl.coolmall.core.network.okgo.GsonCallback;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.login_btn)
    TextView mLoginBtn;
    @BindView(R.id.register_btn)
    TextView mRegisterBtn;
    @BindView(R.id.user_name_et)
    TextInputEditText mUserNameEt;
    @BindView(R.id.password_et)
    TextInputEditText mPasswordEt;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        setStatusBarTextBlack(false);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick(R.id.login_btn)
    public void onClickLogin() {
        OkGo.<SimpleResponse>post(UrlConstants.login)
                .params("username", mUserNameEt.getText().toString())
                .params("password", mPasswordEt.getText().toString())
                .execute(new GsonCallback<SimpleResponse>(SimpleResponse.class) {
                    @Override
                    public void onSuccess(Response<SimpleResponse> response) {
                        SimpleResponse body = response.body();
                        if (body.isSuccess()) {
                            showToast("登录成功");
                            SharedPreDataHelper.setUserId(mUserNameEt.getText().toString());
                            LoginHelper.login();
                            finish();
                        } else if (body.code == 402) {
                            showToast("首次登录，需设置兴趣标签");
                            SharedPreDataHelper.setUserId(mUserNameEt.getText().toString());
                            LoginHelper.login();
                            startActivity(ChooseTipsActivity.class);
                            finish();
                        } else {
                            showToast(body.msg);
                        }
                    }


                    @Override
                    public void onError(Response<SimpleResponse> response) {
                        super.onError(response);
                        showToast(getResources().getString(R.string.network_error));
                    }
                });
    }

    @OnClick(R.id.register_btn)
    public void onClickRegisterBtn() {
        startActivity(RegisterActivity.class);
    }

    @OnClick(R.id.back_btn)
    public void onClickBackBtn() {
        finish();
    }
}
