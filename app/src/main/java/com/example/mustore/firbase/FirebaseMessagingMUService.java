package com.example.mustore.firbase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.example.mustore.MainActivity;
import com.example.mustore.R;
import com.example.mustore.UserChatActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;
import java.util.concurrent.ExecutionException;


public class FirebaseMessagingMUService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        System.out.println("----------------------------------------------");


        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (true) {


            } else {

                handleNow();
            }

        }
        System.out.println("------------------"+ remoteMessage.getMessageType());

        if (remoteMessage.getNotification() != null) {
            System.out.println();
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            String notificationBody = remoteMessage.getNotification().getBody();
            if (remoteMessage.getNotification().getBody() != null) {
                try {
                    if(remoteMessage.getData().get("type")!=null){
                        System.out.println();
                        String type=remoteMessage.getData().get("type");
                        System.out.println(type);
                        switch (Objects.requireNonNull(type)){
                            case "ADD_TO_CART":{
                                sendNotification(notificationBody,remoteMessage.getNotification().getImageUrl(),MainActivity.class,"CART");

                                break;
                            }
                            case "ORDER":{
                                sendNotification(notificationBody,remoteMessage.getNotification().getImageUrl(),MainActivity.class,"ORDER");

                                break;
                            }
                            case "CHAT":{
                                sendNotification(notificationBody,remoteMessage.getNotification().getImageUrl(), UserChatActivity.class,"CHAT");
                                break;
                            }
                        }
                    }
                   else{
                       sendNotification(notificationBody,remoteMessage.getNotification().getImageUrl(),MainActivity.class,"HOME");}
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }


    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        sendRegistrationToServer(token);
    }

    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

    private void sendNotification(String messageBody,Uri uri,Class<?> aClass,String type) throws ExecutionException, InterruptedException {
        Intent intent = new Intent(this, aClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("REDIRECT",type);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_IMMUTABLE);
        FutureTarget<Bitmap> futureTarget=null;
        if(uri!=null) {
            futureTarget = Glide.with(this)
                    .asBitmap()
                    .load(uri)
                    .submit();
        }
        String channelId = "HEADS_UP_NOTIFICATION";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.icon_mu_144)
                        .setContentTitle("")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setLights(Color.BLUE, 500, 500)
                        .setVibrate(new long[]{500, 500, 500})
                        .setSound(defaultSoundUri)
                        .setLargeIcon(futureTarget!=null? futureTarget.get():null)
                        .setContentIntent(pendingIntent);
        if(!Objects.equals(type, "CHAT"))
            notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(futureTarget!=null? futureTarget.get():null));

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);
        Glide.with(this).clear(futureTarget);
        notificationManager.notify(1 /* ID of notification */, notificationBuilder.build());
    }

}
