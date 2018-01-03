package edu.gmu.sherrydang.lab4;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    TextView westtxt, easttxt, countertxt;
    public final static int MAX = 20;
    Turnstile west;
    Turnstile east;
    int value = 0;
    Counter counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        westtxt = (TextView) findViewById(R.id.westCounter);
        easttxt = (TextView) findViewById(R.id.eastCounter);
        countertxt = (TextView) findViewById(R.id.counter);

    }

    public void goOnButtonClicked (View view){
        counter = new SynchronizedCounter();
        west= new Turnstile(counter);
        east= new Turnstile(counter);
        west.start();
        east.start();
        westtxt.setText(value);
        easttxt.setText(value);

    }


    class Turnstile extends Thread {
     //   NumberCanvas display;
        Counter people;

        //Turnstile(NumberCanvas n,Counter c){
        Turnstile(Counter c){

                //display = n;
            people = c;
        }

        public void run() {
            try{
                value = 0;
                for (int i=1;i<=20;i++){
                    Thread.sleep(500); //0.5 second

               //     display.setvalue(i);
                    value = i;

                    people.increment();
                }
            } catch (InterruptedException e) {}
        }
    }

    class Counter {
        int val = 0;
//        NumberCanvas display;

//        Counter(NumberCanvas n) {
//            display=n;
//            display.setvalue(value);
//        }

        void increment() {
            int temp = value;   //read[v]
            Simulate.HWinterrupt();
            val=temp+1;       //write[v+1]
            value  =  val;
//            display.setvalue(value);
        }
    }

    class SynchronizedCounter extends Counter {

//        SynchronizedCounter(NumberCanvas n) {
//            super(n);
//        }

        synchronized void increment() {
            super.increment();
        }
    }

}

class Simulate {
    public static void HWinterrupt() {
        if (Math.random()<0.5)
            try{Thread.sleep(200);} catch(InterruptedException e){};
        //used instead of Thread.yield() to ensure portability
    }
}
