package id.geekgarden.esi.helper;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;

import id.geekgarden.esi.R;

/**
 * Created by GeekGarden on 28/07/2017.
 */

public class UiUtils {
    private static ProgressDialog pDialog;

    public static void dismissProgress() {
        if (isShowProgress()) pDialog.dismiss();
    }
    public static boolean isShowProgress() {
        return pDialog != null && pDialog.isShowing();
    }
    public static ProgressDialog showProgress(Context context){
        pDialog= new ProgressDialog(context);
        pDialog.setTitle("Loading....");
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setCancelable(false);
        return pDialog;
    }
}
