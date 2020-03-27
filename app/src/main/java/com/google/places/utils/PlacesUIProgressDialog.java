package com.google.places.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class PlacesUIProgressDialog {

    private static ProgressDialog pd;

    public static void showProgressDialog(Context context, String message) {
        pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage(message);
        pd.setProgressStyle((ProgressDialog.STYLE_SPINNER));
        pd.show();
    }

    public static void dismissDialog() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }
}
