package com.magdsoft.watany.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;

import com.magdsoft.watany.R;

/**
 * Defaults to cancelable false, indeterminate true.
 */
public class WaitingDialog {
    private static ProgressDialog mInstance;

    private WaitingDialog() {
    }

    public static ProgressDialog getInstance() {
        return mInstance;
    }

    public static void show(Context context) {
        show(context, R.string.loading, null);
    }

    public static void show(Context context, @StringRes int message) {
        show(context, message, null);
    }

    public static void show(Context context, DialogInterface.OnCancelListener onCancelListener) {
        show(context, R.string.loading, onCancelListener);
    }

    public static void show(Context context, @StringRes int message, DialogInterface.OnCancelListener onCancelListener) {
        uiThread(() -> {
            ifShowing(() -> mInstance.dismiss());
            mInstance = new ProgressDialog(context);
            mInstance.setMessage(context.getString(message));
            mInstance.setOnCancelListener(onCancelListener);
            mInstance.setCancelable(null != onCancelListener);
            mInstance.setIndeterminate(true);
            mInstance.show();
        });
    }

    public static void setMessage(CharSequence sequence) {
        uiThread(() -> ifShowing(() -> mInstance.setMessage(sequence)));
    }

    private static void ifShowing(Runnable runnable) {
        if (null != mInstance && mInstance.isShowing()) {
            runnable.run();
        }
    }

    private static void uiThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    public static void dismiss() {
        if (null != mInstance) {
            uiThread(mInstance::dismiss);
        }
    }
}
