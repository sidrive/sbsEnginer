package id.geekgarden.esi.data.model.FCM;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by komuri on 30/08/2017.
 */

public class BodyFCM {

    @SerializedName("fcm_token")
    @Expose
    private String fcmToken;

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

}