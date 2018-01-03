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
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;

public class DecToSignedHex extends Activity implements AdapterView.OnItemSelectedListener {
public final static String RESCORE3 = "edu.gmu.sherrydang.project1.RESCORE3";
    public final static String RETOTAL3 = "edu.gmu.sherrydang.project1.RETOTAL3";

    Button check, next;
    RadioGroup group;
    TextView scoretxt, totaltxt , txtdec , correctResulttxt; ;
    Spinner s1, s2;
    ArrayList <String> arrayList;
    ArrayAdapter myAdapter;
    int randsineddec;
    int total3 = 0;
    int score3 = 0;
    String val1;
    String val2;
 //   String scorestr, totalstr ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dec_to_signed_hex);

        check = (Button) findViewById(R.id.result);
        next = (Button) findViewById(R.id.nextbtn);
        group = (RadioGroup) findViewById(R.id.radioGroup_answersOptions);
        scoretxt = (TextView) findViewById(R.id.score_computed);
        totaltxt = (TextView) findViewById(R.id.total);
        txtdec = (TextView) findViewById(R.id.signeddec);
        correctResulttxt = (TextView) findViewById(R.id.correctSignedResult);

//        Intent intent3 = getIntent();
//        String s = intent3.getStringExtra("hello");
//        Toast.makeText(getBaseContext(), "testing " + s , Toast.LENGTH_SHORT).show();

        //create spinner view
        s1 = (Spinner) findViewById(R.id.spinner1);
        s2 = (Spinner) findViewById(R.id.spinner2) ;

        myAdapter =  new ArrayAdapter (this, android.R.layout.simple_list_item_1, getResources().getStringArray((R.array.vals)));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(myAdapter);
        s2.setAdapter(myAdapter);
        s1.setOnItemSelectedListener(this);
        s2.setOnItemSelectedListener(this);

        randsineddec = randomDec();
        String decimal = Integer.toString(randsineddec);
        txtdec.setText("For Signed (twos comlement), what is the 8-bit hex value for " + decimal +" ?");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
        switch (parent.getId()){
            case R.id.spinner1:
                 val1 = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(getBaseContext(), "val1 = " + val1 , Toast.LENGTH_SHORT).show();
                break;
            case R.id.spinner2:
                 val2 = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(getBaseContext(), "val2 = " + val2 , Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getBaseContext(), "Please choose the answer." , Toast.LENGTH_SHORT).show();
    }

    public void onRadioButtonClicked (View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.option1:
                if (checked){
                    Toast.makeText(getBaseContext(), "option1 clicked" , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.option2:
                if (checked){
                    Toast.makeText(getBaseContext(), "option 2 clicked" , Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.option3:
                if (checked){
                    Toast.makeText(getBaseContext(), "option 3 clicked" , Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public int randomDec () {
        //String decimal;
        Random rand = new Random();
        int n = rand.nextInt(300)-150;
        return n;
    }

    public void changeVisibility(){
        if (next.getVisibility() == View.GONE){
            next.setVisibility(View.VISIBLE);
            check.setVisibility(View.GONE);
        }else{
            check.setVisibility(View.VISIBLE);
            next.setVisibility(View.GONE);
        }
    }

    public void checkClicked (View view){

        changeVisibility ();            //check button and next button
        int idOfSelected = group.getCheckedRadioButtonId();

//        SharedPreferences sharedPref = getSharedPreferences("score", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putInt("score3", score3);
//        editor.putInt("total3", total3);
//        editor.apply();


        String hex;
        hex = Integer.toHexString(randsineddec);
   //     Toast.makeText(DecToSignedHex.this, "hex =  "+ hex , Toast.LENGTH_SHORT).show();


        switch (idOfSelected) {
            case R.id.option1:
                correctResulttxt.setText("Answer = 0x " + hex.substring(hex.length()-2));
          //      Toast.makeText(DecToSignedHex.this,"-3 =" + hex.substring(hex.length()-2 , hex.length()-1) , Toast.LENGTH_SHORT).show();

                break;

            case R.id.option2:

                if (randsineddec > 255) {
                    correctResulttxt.setText("The value is too large");
                } else if ((randsineddec < 0) && (val1.equalsIgnoreCase(hex.substring(hex.length()-2, hex.length()-1))) && (val2.equalsIgnoreCase(hex.substring(hex.length()-1))) ){
                    correctResulttxt.setText("Correct! ");
                    score3++;

                } else if ((randsineddec <= 255) && (randsineddec >= 0) && (hex.length() >= 2) && (val2.equalsIgnoreCase(hex.substring(1))) && (val1.equalsIgnoreCase(hex.substring(0, 1)))) {
                    correctResulttxt.setText("Correct!");
                    score3++;

                } else if ((randsineddec <= 255) && (randsineddec >= 0) && (hex.length() >= 1) && (val1.equalsIgnoreCase("0")) && (val2.equalsIgnoreCase(hex.substring(0, 1)))) {
                    correctResulttxt.setText("Correct!");
                    score3++;
                } else {
                  //  Toast.makeText(DecToSignedHex.this, "not equal ", Toast.LENGTH_SHORT).show();
                    if(randsineddec < 0){
                        correctResulttxt.setText("Answer = 0x" + hex.substring(hex.length()-2));
                    }
                    else{
                        correctResulttxt.setText("Answer = 0x" + hex);
                    }
                }
                break;

            case R.id.option3:
                if (randsineddec > 255) {
                    correctResulttxt.setText("Correct !");
                    score3++;
                }
                else if (randsineddec < 0){
                        correctResulttxt.setText("Answer = 0x" + hex.substring(hex.length()-2));
                    }else{
                        correctResulttxt.setText("Answer = 0x" + hex);
                    }
                break;
            default:
                if ((randsineddec > 0)&& (randsineddec <=255)){
                    correctResulttxt.setText("Answer = 0x" + hex);
                }else if (randsineddec < 0){
                    correctResulttxt.setText("Answer = 0x" + hex.substring(hex.length()-2));
                }else{
                    correctResulttxt.setText("The value is too large");
                }
        }
        total3 ++;
        String s = Integer.toString(score3);
        scoretxt.setText(s);
        String t = Integer.toString(total3);
        totaltxt.setText(t);

        String scorestr = Integer.toString(score3);
        String totalstr = Integer.toString(total3);

        Intent intent = new Intent();

        intent.putExtra("s3", score3);
        intent.putExtra("t3", total3);

        setResult(Activity.RESULT_OK, intent);

    }


    public void nextbuttonCliked (View view){

        if (check.getVisibility() == View.GONE){
            check.setVisibility(View.VISIBLE);
            next.setVisibility(View.GONE);
        }else{
            next.setVisibility(View.VISIBLE);
            check.setVisibility(View.GONE);
        }
        randsineddec = randomDec();
        String decimal = Integer.toString(randsineddec);
        txtdec.setText("For Signed (twos comlement), what is the 8-bit hex value for " + decimal +" ?");
    }


}