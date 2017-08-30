package id.geekgarden.esi.notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import id.geekgarden.esi.MainActivity;
import id.geekgarden.esi.R;
import id.geekgarden.esi.login.LoginActivity;
import id.geekgarden.esi.preference.GlobalPreferences;
import id.geekgarden.esi.preference.PrefKey;


/**
 * Created by GeekGarden on 09/08/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private GlobalPreferences GlPref;

  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
      super.onMessageReceived(remoteMessage);
      GlPref = new GlobalPreferences(getApplicationContext());
      if (remoteMessage.getData().size()>0){
        Log.e("onMessageReceived", "data" + remoteMessage.getData().toString());
      }


          if (GlPref.read(PrefKey.accessToken,String.class)!= null){
              if (remoteMessage.getNotification()!=null){
              Log.e("onMessageReceived", "body" + remoteMessage.getNotification().getBody());

              String body = remoteMessage.getNotification().getBody();

              handleNotification(body);
          }
      }
    }

  private void handleNotification(String body) {
    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    PendingIntent pendingIntent = PendingIntent.getActivity(this, (int)System.currentTimeMillis() /* Request code */, intent,
            PendingIntent.FLAG_UPDATE_CURRENT);

    Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Title Notification")
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent);

    NotificationManager notificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

  }
}

