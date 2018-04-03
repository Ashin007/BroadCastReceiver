package com.ashitech.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by Ashin on 4/1/2018.
 */

public class NumberReceiver extends BroadcastReceiver {
    String number;
    @Override
    public void onReceive(Context context, Intent intent) {

        //receiving broadcast from telephony

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)){

            number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            dbHelper.saveNumber(number,sqLiteDatabase);
            dbHelper.close();


    }

    // sending broad cast

        Intent intent1 = new Intent(DbContract.UPDATE_UI_FILTTER);
        context.sendBroadcast(intent1);

}
}