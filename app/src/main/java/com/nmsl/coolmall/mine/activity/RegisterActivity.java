package com.nmsl.coolmall.mine.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.nmsl.coolmall.R;
import com.nmsl.coolmall.core.base.BaseActivity;
import com.nmsl.coolmall.core.data.UrlConstants;
import com.nmsl.coolmall.core.model.SimpleResponse;
import com.nmsl.coolmall.core.network.okgo.GsonCallback;
import com.nmsl.coolmall.core.utils.RandomStrUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    private boolean isChecked;

    @BindView(R.id.register_btn)
    TextView mRegisterBtn;
    @BindView(R.id.phone_et)
    TextInputEditText mPhoneEt;
    @BindView(R.id.user_name_et)
    TextInputEditText mUserNameEt;
    @BindView(R.id.password_et)
    TextInputEditText mPasswordEt;
    @BindView(R.id.password_et_check)
    TextInputEditText mPasswordCk;
    @BindView(R.id.AgreeBotton)
    CheckBox mAgree;
    @BindView(R.id.Agreetext)
    TextView mAgreeText;
    @BindView(R.id.mgetcode)
    Button mGetcode;
    @BindView(R.id.code_et)
    TextInputEditText mSendcode;

    private void initText() {
        SpannableString spannableString = new SpannableString("我已阅读并同意《用户协议》和《免责声明》");


        ClickableSpan clickPact = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                startActivity(PactPActivity.class);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(ContextCompat.getColor(RegisterActivity.this, R.color.colorPrimary));//设置颜色
                ds.setUnderlineText(false);//去掉下划线
            }
        };
        spannableString.setSpan(clickPact, 7, 13, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        ClickableSpan clickState = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                startActivity(PactStateActivity.class);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(ContextCompat.getColor(RegisterActivity.this, R.color.colorPrimary));//设置颜色
                ds.setUnderlineText(false);//去掉下划线
            }

        };
        spannableString.setSpan(clickState, 14, 20, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
        spannableString.setSpan(colorSpan, 7, 13, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
        spannableString.setSpan(colorSpan2, 14, 20, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        mAgreeText.setText(spannableString);
        mAgreeText.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        setStatusBarTextBlack(false);
        initText();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick(R.id.register_btn)
    public void onClickRegister() {
        if (TextUtils.isEmpty(mPhoneEt.getText().toString())) {
            showToast("请输入手机号");
            return;
        }
        else if(TextUtils.isEmpty(mUserNameEt.getText().toString()))
        {
            showToast("请输入用户名");
            return;
        }
        else if(TextUtils.isEmpty(mPasswordEt.getText().toString()))
        {
            showToast("请输入密码");
            return;
        }
        else if (!mPasswordEt.getText().toString().equals(mPasswordCk.getText().toString())) {
            showToast("两次密码输入不一致，请确认");
            return;

        }

        if (mAgree.isChecked()) {
            OkGo.<SimpleResponse>post(UrlConstants.register)
                    .params("phonenum", mPhoneEt.getText().toString())
                    .params("username", mUserNameEt.getText().toString())
                    .params("password", mPasswordEt.getText().toString())
                    .params("rec_code", RandomStrUtils.getRandomString(8))
                    .params("con_code", mSendcode.getText().toString())
                    .execute(new GsonCallback<SimpleResponse>(SimpleResponse.class) {
                        @Override
                        public void onSuccess(Response<SimpleResponse> response) {
                            if (response.body().isSuccess()) {
                                showToast("注册成功");
                                finish();
                            } else {
                                showToast(response.body().msg);
                            }
                        }

                        @Override
                        public void onError(Response<SimpleResponse> response) {
                            super.onError(response);
                            showToast(getResources().getString(R.string.network_error));
                        }
                    });
        }
        else{
            showToast("请阅读并同意《用户协议》及《隐私政策》");
        }
    }

    @OnClick(R.id.back_btn)
    public void onClickBackBtn() {
        finish();
    }
}
