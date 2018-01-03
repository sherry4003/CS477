package edu.gmu.sherrydang.lab7dang;

/**
 * Created by SherryDang on 10/27/2017.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Vibrator;
import android.widget.Toast;

public class MyReceiver2 extends BroadcastReceiver {
//    public MyReceiver2() {
//    }

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        Toast.makeText(arg0, "Time OUT ! " , Toast.LENGTH_LONG).show();
        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 400);

    }

}