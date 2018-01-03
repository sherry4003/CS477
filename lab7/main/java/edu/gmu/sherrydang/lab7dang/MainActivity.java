package edu.gmu.sherrydang.lab7dang;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static final String INTENT1 ="edu.gmu.sherrydang.lab7dang.CUSTOM_INTENT1";
    static final String INTENT2 ="edu.gmu.sherrydang.lab7dang.CUSTOM_INTENT2";
//    final MyReceiver2 r2 = new MyReceiver2();
    EditText text;

    MediaPlayer player;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player = MediaPlayer.create(this, R.raw.braincandy);
        player.setLooping(false);

//        IntentFilter intf = new IntentFilter(INTENT2);
//        registerReceiver(r2,intf);
    }

    public void onClick(View src) {
        switch (src.getId()) {
            case R.id.buttonStart1: //static
                startService(new Intent(MainActivity.this, MusicService.class));
                Intent intent = new Intent("MyCustomIntent");
                intent.setAction(INTENT1);
                sendBroadcast(intent);
                break;
            case R.id.buttonStart3: //dynamic
                try{
                    text = (EditText) findViewById(R.id.time);
                    int i = Integer.parseInt(text.getText().toString());
                    Intent intent2 = new Intent(this,MyReceiver2.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 1, intent2, 0);
                    AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (i*1000), pendingIntent);
                    Toast.makeText(this, "Alarm set in " + i + " seconds", Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(this, "Please enter number of second", Toast.LENGTH_LONG).show();
                }
//                // add data to the Intent
//                intent2.setAction(INTENT2);
//                sendBroadcast(intent2);
                break;


        }
    }

}