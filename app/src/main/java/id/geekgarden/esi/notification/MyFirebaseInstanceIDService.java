package id.geekgarden.esi.notification;


import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import id.geekgarden.esi.data.apis.Api;
import id.geekgarden.esi.data.apis.ApiService;
import id.geekgarden.esi.data.model.FCM.BodyFCM;
import id.geekgarden.esi.data.model.FCM.ResponseFCM;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by SENTINEL on 2017/08/29.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
    private Api mApi;
    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "Refreshed token: " + refreshedToken);
        sendToServer(refreshedToken);
    }

    private void sendToServer(String refreshedToken) {
        GlobalPreferences globalPreferences = new GlobalPreferences(getApplicationContext());
        mApi = ApiService.getervice();
        if (refreshedToken!=null) {
            globalPreferences.write(PrefKey.refreshToken, refreshedToken, String.class);
            String accessToken = globalPreferences.read(PrefKey.accessToken, String.class);
            BodyFCM bodyFCM = new BodyFCM();
            bodyFCM.setFcmToken(refreshedToken);
            rx.Observable<ResponseFCM> respon = mApi.updateFcmToken(accessToken,bodyFCM).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
            respon.subscribe(new Observer<ResponseFCM>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Log.e("onError", "MainActivity" + e.getMessage());
                }

                @Override
                public void onNext(ResponseFCM responseFCM) {
                    Log.e("", "onNext: "+ responseFCM.getData().getFcmToken().toString());
                }
            });
        }
        else{

        }

    }
}