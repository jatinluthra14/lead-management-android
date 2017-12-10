package com.community.jboss.leadmanagement;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import com.aykuttasil.callrecord.CallRecord;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CallReceiver extends BroadcastReceiver {
    private static final int ID = 47981;
    private static String currentDateTimeString;

    CallRecord callRecord = new CallRecord.Builder(this)
            .setRecordFileName("Temp")            //Just temp name for initialisation
            .setRecordDirName("LeadManagement_Recordings")
            .setRecordDirPath(Environment.getExternalStorageDirectory().getPath()) // Save in External SDCard
            .setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            .setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
            .setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION)
            .setShowSeed(true)
            .buildService();

    @Override
    public void onReceive(Context context, Intent intent) {
        try{
            Bundle extras = intent.getExtras();
            if (extras != null) {
                String state = extras.getString(TelephonyManager.EXTRA_STATE);
                String number = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                if(state!=null){
                    if(number!=null && state.equals(TelephonyManager.EXTRA_STATE_RINGING) ){
                        showNotification(context, number);
                    }else if(state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                        hideNotification(context);
                    }else if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                        showNotification(context,number);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void hideNotification(Context context) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            callRecord.stopCallReceiver();          //Stop Call Recording
            manager.cancel(ID);
        }
    }


    public void showNotification(Context context, String text){
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.call_ico)
                .setContentTitle("Calling")
                .setTicker("Lead Management")
                .setContentText("Number: "+text);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.cancel(ID);
            manager.notify(ID,notification.build());
            Date curDate = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss a");
            String currentDateTimeString = format.format(curDate);
            callRecord.changeRecordFileName(currentDateTimeString);
            callRecord.startCallRecordService();                                         //Start Call Recording Service
        }
    }
}
