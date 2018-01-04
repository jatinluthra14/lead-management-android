package com.community.jboss.leadmanagement;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.community.jboss.leadmanagement.data.daos.ContactNumberDao;
import com.community.jboss.leadmanagement.data.entities.ContactNumber;
import com.community.jboss.leadmanagement.main.Client;
import com.community.jboss.leadmanagement.main.MainActivity;
import com.community.jboss.leadmanagement.main.contacts.editcontact.EditContactActivity;
import com.community.jboss.leadmanagement.main.model.Result;
import com.community.jboss.leadmanagement.utils.DbUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CallReceiver extends BroadcastReceiver {
    private static final int ID = 47981;

    public static final String BASE_URL = "http://192.168.0.132:4000/";
    public static String callnumber = null;

    private ContactNumber mContactNumber;
    private Context mContext;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    final Client client = retrofit.create(Client.class);
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!TextUtils.equals(intent.getAction(), "android.intent.action.PHONE_STATE")) {
            return;
        }

        final Bundle extras = intent.getExtras();
        if (extras == null) return;

        final String state = extras.getString(TelephonyManager.EXTRA_STATE);
        if (state == null) return;

        final String number = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
        if (number == null) return;

        mContext = context;
        final ContactNumberDao dao = DbUtil.contactNumberDao(mContext);
        mContactNumber = dao.getContactNumber(number);

        // Don't show the notification is the contact is not in the database
        if (mContactNumber == null) {
            return;
        }

        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            showNotification();
        } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            hideNotification();
        } else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            showNotification();
        }
    }

    private void hideNotification() {
        final NotificationManager manager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.cancel(ID);
        }
        if(callnumber != null) {
            Call<Result> call = client.endCall("Call from " + callnumber + " ended.");

            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                        /*
                        1 = success
                        0 = fail
                         */
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                }
            });
            callnumber = null;
        }
    }

    private void showNotification() {

        callnumber = mContactNumber.getNumber();
        if(callnumber !=null) {
            Call<Result> call = client.newCall("Receiving Call from " + callnumber);

            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                        /*
                        1 = success
                        0 = fail
                         */
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                }
            });
        }
        final Intent notificationIntent = new Intent(mContext, EditContactActivity.class);

        notificationIntent.putExtra(
                EditContactActivity.INTENT_EXTRA_CONTACT_ID, mContactNumber.getContactId());

        final PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        final NotificationCompat.Builder notification = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.drawable.ic_call_black_24dp)
                .setContentTitle("Call in Progress")
                .setTicker("Lead Management")
                .setContentIntent(contentIntent)
                .setContentText("Number: " + mContactNumber.getNumber());

        final NotificationManager manager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.cancel(ID);
            manager.notify(ID, notification.build());
        }
    }
}
