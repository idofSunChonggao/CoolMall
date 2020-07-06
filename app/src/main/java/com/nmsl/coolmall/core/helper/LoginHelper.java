package com.nmsl.coolmall.core.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.nmsl.coolmall.BaseApplication;
import com.nmsl.coolmall.core.data.SharedPreConstants;
import com.nmsl.coolmall.core.data.SharedPreDataHelper;
import com.nmsl.coolmall.core.event.LoginEvent;
import com.nmsl.coolmall.core.event.LogoutEvent;
import com.nmsl.coolmall.core.utils.DialogUtils;
import com.nmsl.coolmall.core.utils.SharedPreUtil;
import com.nmsl.coolmall.mine.activity.LoginActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * @description:
 * @author: NoOne
 * @date: 2019-04-26 13:33
 */
public class LoginHelper {

    public static boolean isLogin(Context context) {
        return isLogin(context, false);
    }

    public static boolean isLogin(Context context, boolean showDialog) {
        boolean isLogin = SharedPreUtil.getBooleanValue(BaseApplication.getInstance(), SharedPreConstants.LOGIN_STATE, false);
        if (!isLogin && showDialog) {
            showLoginDialog(context);
        }
        return isLogin;
    }

    public static void login() {
        EventBus.getDefault().post(new LoginEvent());
        SharedPreUtil.putBooleanValue(BaseApplication.getInstance(), SharedPreConstants.LOGIN_STATE, true);
    }

    public static void logout() {
        EventBus.getDefault().post(new LogoutEvent());
        SharedPreUtil.putBooleanValue(BaseApplication.getInstance(), SharedPreConstants.LOGIN_STATE, false);
        SharedPreDataHelper.setUserId("");
    }

    public static void showLoginDialog(final Context context) {
        DialogUtils.showDialog(context, "提示", "您还未登录，请先登录", "登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(context, LoginActivity.class));
            }
        }, "取消", null);
    }
}
