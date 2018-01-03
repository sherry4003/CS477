package edu.gmu.sherrydang.simplemusicalchair;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    // remove the consts
    private static final int UPDATEPLAYER =2;
    private static final int LOSERINFO = 3;
    private static final int FREEZE_BUTTON = 4;
    private static final int UNFREEZE_BUTTON = 5;
    private static final int NUM_PLAYERS = 6;
    public int num_seats = NUM_PLAYERS-1;
    private ImageView [] mImages, lImages;
    private Button playbutton;
    private TextView info;
    public Player p[];
    public Seat[] seats;
    int whichPlayer;
    int whichState;
    int newloser;
    ArrayList<Integer>loserList = new ArrayList<Integer>();
//    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
//    SharedPreferences.Editor editor = pref.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImages = new ImageView[NUM_PLAYERS];
        mImages[0] = (ImageView)findViewById(R.id.img1);
        mImages[1] = (ImageView) findViewById(R.id.img2);
        mImages[2] = (ImageView) findViewById(R.id.img3);
        mImages[3] = (ImageView) findViewById(R.id.img4);
        mImages[4] = (ImageView) findViewById(R.id.img5);
        mImages[5] = (ImageView) findViewById(R.id.img6);

        lImages = new ImageView[NUM_PLAYERS];
        lImages[0] = (ImageView)findViewById(R.id.loser1);
        lImages[1] = (ImageView) findViewById(R.id.loser2);
        lImages[2] = (ImageView) findViewById(R.id.loser3);
        lImages[3] = (ImageView) findViewById(R.id.loser4);
        lImages[4] = (ImageView) findViewById(R.id.loser5);
        lImages[5] = (ImageView) findViewById(R.id.loser6);



        seats = new Seat[NUM_PLAYERS];
        playbutton = (Button)findViewById(R.id.playbutton);
        info = (TextView)findViewById(R.id.loserInfo);
        p = new Player[NUM_PLAYERS];
        for (int i = 0;i<NUM_PLAYERS;i++) {
            p[i] = new Player(i);
            Bitmap b = getImage(i);
            mImages [i].setImageBitmap(b);
        }
    }

    class Seat {
        // This class implements the seats.  Initially a seat is empty
// (isTaken = false) - once some thread calls sit, it claims that
// seat (isTaken = true) and any other player is returned a false value.
// Notice that the 'sit' function is synchronized - only one player can be
// trying to sit at a time.
        boolean isTaken;
        int who;
        Seat() {
            isTaken=false;
            who = -1;
        }
        int whoseSeat() {
            return who;
        }
        synchronized boolean sit(int i) {
            if (isTaken) {
                return false;
            } else {
                who = i;
                isTaken = true;
                return true;
            }
        }
        int get_player() {
            return who;
        }
        void reset_seat() {
            isTaken = false;
        }
    }

    public void doRound(View v) {
        Round r = new Round(num_seats);
        num_seats--;
        Thread t = new Thread(r);
        t.start();

        if (num_seats <= 0) {
            playbutton.setEnabled(false);
            playbutton.setText("Done");
            Toast.makeText(MainActivity.this, "Congratulation You are the Winner!", Toast.LENGTH_SHORT).show();
        } else {
            playbutton.setText(" Next Round: "+num_seats + " Remaining Seats");
        }

    }
    // remove the handler
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ImageView img1,img2;

            switch (msg.what) {
                case UPDATEPLAYER:
                    whichPlayer = msg.arg1;
                    whichState = msg.arg2;
                    Bitmap b = getImage(whichState);
                    mImages [whichPlayer].setImageBitmap(b);
                    break;
                case LOSERINFO:
                    whichPlayer = msg.arg1;
                    whichState = msg.arg2;
             //       newloser = whichState+1;
                    mImages [whichPlayer].setVisibility(View.GONE);
                    Bitmap bloser = getImage(whichState);
                    switch (whichPlayer){
                        case 5:
                            whichPlayer = 0;
                            break;
                        case 4:
                            whichPlayer = 1;
                            break;
                        case 3:
                            whichPlayer = 2;
                            break;
                        case 2:
                            whichPlayer = 3;
                            break;
                        case 1:
                            whichPlayer = 4;
                            break;
                    }
                    lImages [whichPlayer].setImageBitmap(bloser);
                    info.setText("Losers: " + loserList);
                    break;
            }
        }
    };

    class Round implements Runnable{
        int num_seats;

        Round(int seats) {
            num_seats = seats;

        }
        public void run() {

            for (int i = 0;i<NUM_PLAYERS;i++)
                seats[i] = new Seat();  // create seats for the round

            for (int i = 0;i<NUM_PLAYERS;i++) {
                if (!p[i].isLoser()) {
                    Thread pt = new Thread(p[i]);
                    pt.start();
                }
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    private Bitmap getImage(int num) {
        switch (num) {
            case 0: return BitmapFactory.decodeResource(getResources(),
                    R.drawable.one);
            case 1: return BitmapFactory.decodeResource(getResources(),
                    R.drawable.two);
            case 2: return BitmapFactory.decodeResource(getResources(),
                    R.drawable.three);
            case 3: return BitmapFactory.decodeResource(getResources(),
                    R.drawable.four);
            case 4: return BitmapFactory.decodeResource(getResources(),
                    R.drawable.five);
            case 5: return BitmapFactory.decodeResource(getResources(),
                    R.drawable.six);
        }
        return
                BitmapFactory.decodeResource(getResources(),R.drawable.robot);
    }

    class Player implements Runnable{
        private int which_player;   // so they know which player they represent
        private boolean loser;      // keep track of whether or not they have lost

        //   - this is not used in the 1-round version
        private Random r = new Random();
        Player(int which) {
            which_player = which;
            loser = false;
        }

        // next two functions are not used in 1-round version but I found them
        // useful for the full game
        public boolean isLoser() {
            return loser;
        }
        public void lost() {
            loser = true;
        }

        // run() gets called when the thread is started
        public void run() {
            Message msg;
            int x=0;
            // start with random wait
            try {
                int i = r.nextInt(10);
                Thread.sleep(10+i);
            }catch(InterruptedException e){}
            // then try to get a seat

            for (int j = 0;j<(num_seats+2);j++) {
                if (seats[j].sit(which_player)) {
                    // update seat
                    System.out.println("player: "+ which_player + "get seat " + j);
                    msg = handler.obtainMessage(UPDATEPLAYER,j, which_player);
                    handler.sendMessage(msg);

                    if (j == num_seats+1) {
                        // update loser info
                        System.out.println("loser: "+ which_player + "get seat " + j);
                        msg = handler.obtainMessage(LOSERINFO ,j, which_player);
                        handler.sendMessage(msg);
                        newloser = which_player +1;
                        loserList.add(newloser);
                        loser = true;
                    }
                    break;
                }
            }
            System.out.println("");


        }
    }

}