package id.geekgarden.esi.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;
import id.geekgarden.esi.data.apis.ApiClient;
import id.geekgarden.esi.data.model.ResponseError;
import java.io.IOException;
import java.lang.annotation.Annotation;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.HttpException;

/**
 * Created by GeekGarden on 28/07/2017.
 */

public class Utils {
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

    public static void showToast (Context context, String Message){
        Toast.makeText(context,Message,Toast.LENGTH_LONG).show();
    }

    public static String getError(Throwable throwable){
        String errorMsg = null;
        if(throwable instanceof HttpException) {
            //we have a HTTP exception (HTTP status code is not 200-300)
            Converter<ResponseBody, ResponseError> errorConverter =
                ApiClient.getRetrofit().responseBodyConverter(ResponseError.class, new Annotation[0]);
            //maybe check if ((HttpException) throwable).code() == 400 ??
            ResponseError error = null;
            try {
                error = errorConverter.convert(((HttpException) throwable).response().errorBody());
                errorMsg = error.getData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (throwable instanceof IOException) {
            // A network or conversion error happened
            Converter<ResponseBody, ResponseError> errorConverter =
                ApiClient.getRetrofit().responseBodyConverter(ResponseError.class, new Annotation[0]);
            //maybe check if ((HttpException) throwable).code() == 400 ??
            ResponseError error = null;
            try {
                error = errorConverter.convert(((HttpException) throwable).response().errorBody());
                errorMsg = error.getData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return errorMsg;
    }
}
