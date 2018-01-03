package edu.gmu.sherrydang.project1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener{
    public final static int ACTIVITY_RESULT = 1;
    String item, bit;
    Spinner optionSpinner, bitsSpinner;
    ArrayAdapter optionAdapter, bitsAdapter;
    TextView finalscoretxt, totalquesttxt ;
    int scoreval =0 ,totalval =0;
    int scorestr3 =0;
    int totalstr3=0;
    int scorestr1 = 0;
    int totalstr1=0;
    int scorestr2 =0, totalstr2 =0;
    int scores, totals;
    int s1 =0, s2=0, s3=0, t1=0, t2=0, t3=0;
    int s, t;
    int stemp, ttemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        optionSpinner = (Spinner) findViewById(R.id.spinner_options);
        bitsSpinner = (Spinner) findViewById(R.id.bits_spinner);
        finalscoretxt = (TextView) findViewById(R.id.score_computed);
        totalquesttxt = (TextView) findViewById(R.id.total);
        optionAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.option_types));

        bitsAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.bits));

        optionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        optionSpinner.setAdapter(optionAdapter);
        optionSpinner.setOnItemSelectedListener(this);

        bitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bitsSpinner.setAdapter(bitsAdapter);
        bitsSpinner.setOnItemSelectedListener(this);
        loadPreferences ();
  //      Toast.makeText(getBaseContext(),  "after load 1: "+s +"/"+t , Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        parent.getItemAtPosition(position);

        switch (parent.getId()){
            case R.id.spinner_options:
                item = String.valueOf(parent.getItemAtPosition(position));
      //          Toast.makeText(getBaseContext(), item + " selected", Toast.LENGTH_SHORT).show();
                break;

            case  R.id.bits_spinner:
                bit = String.valueOf(parent.getItemAtPosition(position));
     //           Toast.makeText(getBaseContext(), bit + " ", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "choose the type of question", Toast.LENGTH_SHORT).show();
    }

    public void Gobuttoncliked (View view){

        if (item.equals("Hex To Decimal") && bit.equals("8 bits")) {
            Intent intent = new Intent(MainActivity.this, HexToDec.class);
            startActivityForResult(intent, ACTIVITY_RESULT);
        }
        if ((item.equals("Decimal To Unsigned Hex") && bit.equals("8 bits"))) {
            Intent intent = new Intent(MainActivity.this, DecToUnsignedHex_Activity.class);
            startActivityForResult(intent, ACTIVITY_RESULT);
        }
        if ((item.equals("Decimal To Signed Hex") && bit.equals("8 bits"))) {
            Intent intent = new Intent(MainActivity.this, DecToSignedHex.class);
            startActivityForResult(intent, ACTIVITY_RESULT);
        }
        else{
            Toast.makeText(getBaseContext(),  "selected", Toast.LENGTH_SHORT).show();
        }
    }

    public void savePreferences (String key, int value){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key,value);
        editor.commit();
    }

    public void loadPreferences(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
         s = pref.getInt("SCORE",s);
         t = pref.getInt("TOTAL",t);
        finalscoretxt.setText(Integer.toString(s));
        totalquesttxt.setText(Integer.toString(t));

    }

    public void reset (){
        scorestr1 = 0;
        totalstr1=0;
        scorestr2 =0;
        totalstr2 =0;
        scorestr3 =0;
        totalstr3=0;

    }
    public void onActivityResult (int requestCode, int resultCode, Intent data ) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){

            scorestr1 = data.getExtras().getInt("s1", scorestr1);
            totalstr1 = data.getExtras().getInt("t1", totalstr1);
            scorestr2 = data.getExtras().getInt("s2", scorestr2);
            totalstr2 = data.getExtras().getInt("t2", totalstr2);
            scorestr3 = data.getExtras().getInt("s3", scorestr3);
            totalstr3 = data.getExtras().getInt("t3", totalstr3);

//            Toast.makeText(getBaseContext(),  ""+ scores +"+"+scorestr1+ "+" + scorestr2 + "+" + scorestr3 , Toast.LENGTH_SHORT).show();
//            Toast.makeText(getBaseContext(),  "total= "+ totals +"+"+totalstr1+ "+" + totalstr2 + "+" + totalstr3 , Toast.LENGTH_SHORT).show();

            scores = s + scores +  scorestr1 + scorestr2 + scorestr3;
            totals =  t + totals + totalstr1 + totalstr2+ totalstr3;


            finalscoretxt.setText(Integer.toString(scores));
            totalquesttxt.setText(Integer.toString(totals));

            reset();
            savePreferences ("SCORE", scores);
            savePreferences ("TOTAL", totals);

        }
    }

}

