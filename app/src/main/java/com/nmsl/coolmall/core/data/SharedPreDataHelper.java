package com.nmsl.coolmall.core.data;

import com.blankj.utilcode.util.GsonUtils;
import com.nmsl.coolmall.BaseApplication;
import com.nmsl.coolmall.core.utils.SharedPreUtil;
import com.nmsl.coolmall.mine.model.UserInfoBean;

/**
 * @description:
 * @author: NoOne
 * @date: 2019-04-26 10:33
 */
public class SharedPreDataHelper {

    private static final String TAG = "SharedPreDataHelper";


    public static String getUserId() {
        return SharedPreUtil.getStringValue(BaseApplication.getInstance(), SharedPreConstants.USER_ID, "");
    }

    public static void setUserId(String userId) {
        SharedPreUtil.putStringValue(BaseApplication.getInstance(), SharedPreConstants.USER_ID, userId);
    }


    public static String getUserName() {
        return SharedPreUtil.getStringValue(BaseApplication.getInstance(), SharedPreConstants.USER_NAME, "");
    }

    public static void setUserName(String userName) {
        SharedPreUtil.putStringValue(BaseApplication.getInstance(), SharedPreConstants.USER_NAME, userName);
    }


    public static String getAvatarUrl() {
        return SharedPreUtil.getStringValue(BaseApplication.getInstance(), SharedPreConstants.USER_AVATAR_URL, "");
    }

    public static void setAvatarUrl(String avatarUrl) {
        SharedPreUtil.putStringValue(BaseApplication.getInstance(), SharedPreConstants.USER_AVATAR_URL, avatarUrl);
    }

    public static void setUserInfoBean(UserInfoBean bean) {
        SharedPreUtil.putStringValue(BaseApplication.getInstance(), SharedPreConstants.USER_BEAN, GsonUtils.toJson(bean));
    }

    public static UserInfoBean getUserInfoBean() {
        String jsonStr = SharedPreUtil.getStringValue(BaseApplication.getInstance(), SharedPreConstants.USER_BEAN, "");
        return GsonUtils.fromJson(jsonStr, UserInfoBean.class);
    }
}
