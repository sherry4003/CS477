package edu.gmu.sherrydang.lab7dang;

/**
 * Created by SherryDang on 10/27/2017.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Hello, This is BINH DANG :) ", Toast.LENGTH_LONG).show();
        Vibrator v;
        v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(4000);

    }
}
