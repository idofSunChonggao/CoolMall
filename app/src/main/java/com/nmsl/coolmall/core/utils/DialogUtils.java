package com.nmsl.coolmall.core.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;


/**
 * Created by NoOne on 2017/8/4 0004.
 */

public class DialogUtils {


    public static void showDialog(final Context context, String message) {
        showDialog(context, "提示", message);
    }

    public static void showDialog(final Context context, String titleStr, String message) {
        showDialog(context, titleStr, message, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }


    public static void showDialog(final Context context, String titleStr, @Nullable String message, String posStr, DialogInterface.OnClickListener posListener) {
        showDialog(context, titleStr, message, posStr, posListener, null, null);
    }

    public static void showDialog(final Context context, String titleStr, @Nullable String message, @Nullable String posStr, @Nullable DialogInterface.OnClickListener posListener, @Nullable String negStr, @Nullable DialogInterface.OnClickListener negListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titleStr);
        if (!TextUtils.isEmpty(message))
            builder.setMessage(message);
        if (!TextUtils.isEmpty(posStr))
            builder.setPositiveButton(posStr, posListener);
        if (!TextUtils.isEmpty(negStr))
            builder.setNegativeButton(negStr, negListener);

        AlertDialog dialog = builder.show();
    }

    public static void showDialog(final Context context, String titleStr, @Nullable String message, @Nullable String posStr, @Nullable DialogInterface.OnClickListener posListener, @Nullable String negStr, @Nullable DialogInterface.OnClickListener negListener, View customView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titleStr);
        if (!TextUtils.isEmpty(message))
            builder.setMessage(message);
        if (!TextUtils.isEmpty(posStr))
            builder.setPositiveButton(posStr, posListener);
        if (!TextUtils.isEmpty(negStr))
            builder.setNegativeButton(negStr, negListener);
        builder.setView(customView);

        AlertDialog dialog = builder.show();
    }
}
