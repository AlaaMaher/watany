package com.magdsoft.watany.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.magdsoft.garo.R;
import com.magdsoft.garo.helpers.BaseActivity;
import com.magdsoft.garo.views.activity.user.TestSetting;
import com.magdsoft.sqlconnction.SqlliteConnection;

import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    BaseActivity m = new BaseActivity();
    private static final String TAG = "MyFirebaseMsgService";
    static String message;

//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//
//        String title = remoteMessage.getNotification().getTitle();
//        message = remoteMessage.getNotification().getBody();
//        Log.d(TAG, "onMessageReceived: Message Received: \n" +
//                "Title: " + title + "\n" +
//                "Message: " + message);
//        sendNotification(title, message);
//    }

    @Override
    public void handleIntent(Intent intent) {
//       m.ret();
        for (String key : intent.getExtras().keySet()) {
            Log.e("Intent " + key, intent.getExtras().get(key).toString());
        }
        sendNotification("Garo", intent.getStringExtra("gcm.notification.body"));

    }

    Intent intent;
    int count;
    SqlliteConnection db;
    String s;

    private void sendNotification(String title, String messageBody) {
        long[] vibrate = {0, 100, 200, 300};
        db = new SqlliteConnection(this);
        Cursor cursor = db.readcat();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            s = cursor.getString(cursor.getColumnIndex("notinumber"));
            Log.d("count", s);
            cursor.moveToNext();
        }
        count = Integer.parseInt(s);
        intent = new Intent(this, TestSetting.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setVibrate(vibrate)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background))
                .setColor(ContextCompat.getColor(this, R.color.colorAccent))
                .setVibrate(vibrate);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Random r = new Random();
        int i1 = (r.nextInt(1000));
        notificationManager.notify(i1 /* ID of notification */, notificationBuilder.build());
        db = new SqlliteConnection(this);
        count++;
        db.updatenoti(Integer.toString(count));
//        Log.e("count", Integer.toString(count));
    }
}
// this Method is only generating push notification
//    private void sendNotification(Map<String, String> messageBody) {
//        String commenterName = "", link = "";
//        //  if you want return your notification new one in device bar than use this randomID
//        Random random = new Random();
//        int randomID = random.nextInt(9999 - 1000) + 1000;
//        int id = 0, type = 0;
//
////        try {
////            JSONObject body = new JSONObject(messageBody.get("body"));
////            id = body.getInt("id");
////            type = body.getInt("type");
////            commenterName = body.optString("title", "");
////            /*JSONObject adobj = ads.getJSONObject(0);
////            adTitle = adobj.getString("name");*/
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
//        Intent intent = new Intent(this,TestSetting.class);
////        intent.putExtra("youtube_id", Integer.toString(id));
////        intent.putExtra("url", link);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); // this sound for deafultsound
////        Uri defaultSoundUri = Uri.parse("android.resource://com.magd.sendnotificationfcm/" + R.raw.ta_da); // this sound for custom sound
//        long[] vibrate = {0, 100, 200, 300};
//
//        Log.e("message body ", messageBody.toString());
//        if (null == commenterName || commenterName.isEmpty()) {
//            commenterName = messageBody.get("title");
//        }
//        NotificationCompat.Builder notif = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setLargeIcon(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_launcher_background))
//                .setContentTitle("garo")
//                .setContentText("gfhgfhghj")
//                .setColor(ContextCompat.getColor(this, R.color.colorAccent))
//                .setAutoCancel(true)
//                .setVibrate(vibrate)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0, notif.build());
//    }

