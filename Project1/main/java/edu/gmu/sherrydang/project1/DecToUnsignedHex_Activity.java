package edu.gmu.sherrydang.project1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import java.util.Arrays;
import java.util.Random;

public class DecToUnsignedHex_Activity extends Activity implements AdapterView.OnItemSelectedListener {
    public final static String RESCORE2 = "edu.gmu.sherrydang.project1.RESCORE2";
    public final static String RETOTAL2 = "edu.gmu.sherrydang.project1.RETOTAL2";
    TextView txtdec;
    TextView correctResulttxt;
    TextView scoretxt;
    TextView totaltxt;
    Button check, next;
    Spinner s1, s2;
    RadioGroup group;
    int randdec;
    int dec;
    int score2 = 0;
    int total2 = 0;
    ArrayList <String> arrayList;
    ArrayAdapter myAdapter;
    String val1 , val2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dec_to_unsigned_hex_);

        check = (Button) findViewById(R.id.result);
        next = (Button) findViewById(R.id.nextbtn);
        txtdec = (TextView) findViewById(R.id.dec);
        group = (RadioGroup) findViewById(R.id.radioGroup_answersOptions);
        correctResulttxt = (TextView) findViewById(R.id.correctResult);
        scoretxt = (TextView) findViewById(R.id.score_computed);
        totaltxt = (TextView) findViewById(R.id.total);
        Intent intent = getIntent();

        //create spinner view
        s1 = (Spinner) findViewById(R.id.spinner1);
        s2 = (Spinner) findViewById(R.id.spinner2) ;

        myAdapter =  new ArrayAdapter (this, android.R.layout.simple_list_item_1, getResources().getStringArray((R.array.vals)));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(myAdapter);
        s2.setAdapter(myAdapter);
        s1.setOnItemSelectedListener(this);
        s2.setOnItemSelectedListener(this);

        randdec = randomDec();
        String decimal = Integer.toString(randdec);
        txtdec.setText("In unsigned number, what is the 8-bit hex value for decimal value " + decimal +" ?");

    }

    //spinner :value of hex
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
        switch (parent.getId()){
            case R.id.spinner1:
                val1 = String.valueOf(parent.getItemAtPosition(position));
           //     Toast.makeText(getBaseContext(), "val1 = " + val1 , Toast.LENGTH_SHORT).show();
                break;
            case R.id.spinner2:
                val2 = String.valueOf(parent.getItemAtPosition(position));
            //    Toast.makeText(getBaseContext(), "val2 = " + val2 , Toast.LENGTH_SHORT).show();
                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getBaseContext(), "Please choose the answer." , Toast.LENGTH_SHORT).show();

    }
    // check onRadiobutton
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

    //generate the random decimal number
    public int randomDec () {
        //String decimal;
        Random rand = new Random();
        int n = rand.nextInt(355)+1;
        return n;
    }

    // hide the next button and display when check button click
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

        String hex;
        hex = Integer.toHexString(randdec);
     //   Toast.makeText(DecToUnsignedHex_Activity.this, "hex =  "+ hex , Toast.LENGTH_SHORT).show();
            switch (idOfSelected) {
                //when the result is too small
                case R.id.option1:
                    if (randdec < 0) {
                        correctResulttxt.setText("Correct !");
                        score2++;
                        Toast.makeText(DecToUnsignedHex_Activity.this, "too small ", Toast.LENGTH_SHORT).show();

                    } else {
                        correctResulttxt.setText("Answer = 0x" + hex);
                    }
                    break;
                //chose the answer value
                case R.id.option2:
                    //  Toast.makeText(DecToUnsignedHex_Activity.this,"2 click" , Toast.LENGTH_SHORT).show();
                    if (randdec > 255) {
                        correctResulttxt.setText("The value is too large");
                    } else if (randdec < 0) {
                        correctResulttxt.setText("The value is too small");
                    } else if ((randdec <= 255) && (randdec >= 0) && (hex.length() >= 2) && (val2.equalsIgnoreCase(hex.substring(1))) && (val1.equalsIgnoreCase(hex.substring(0, 1)))) {
                        correctResulttxt.setText("Correct !");
                        score2++;

                    } else if ((randdec <= 255) && (randdec >= 0) && (hex.length() >= 1) && (val1.equalsIgnoreCase("0")) && (val2.equalsIgnoreCase(hex.substring(0, 1)))) {
                        correctResulttxt.setText("Correct !");
                        score2++;
                    } else {
                        correctResulttxt.setText("Answer = 0x" + hex);
                    }
                    break;
                // the result is too large, out of range
                case R.id.option3:
                    if (randdec > 255) {
                        correctResulttxt.setText("Correct !");
                        score2++;

                    } else {
                        correctResulttxt.setText("Answer = 0x" + hex);
                    }
                    break;

                default:
                    if ((randdec > 0)&& (randdec <=255)){
                        correctResulttxt.setText("Answer = 0x" + hex);
                    }else if (randdec < 0){
                        correctResulttxt.setText("The value is too small");
                    }else{
                        correctResulttxt.setText("The value is too large");
                    }
            }
            total2 ++;
            String s = Integer.toString(score2);
            scoretxt.setText(s);
            String t = Integer.toString(total2);
            totaltxt.setText(t);


            Intent intent = new Intent();
            //store the score and total val and pass it back to main activity
            intent.putExtra("s2", score2);
            intent.putExtra("t2", total2);
            setResult(Activity.RESULT_OK, intent);
    }

    //onclick next button
    public void nextbuttonCliked (View view){
        if (check.getVisibility() == View.GONE){
            check.setVisibility(View.VISIBLE);
            next.setVisibility(View.GONE);
        }else{
            next.setVisibility(View.VISIBLE);
            check.setVisibility(View.GONE);
        }
        randdec = randomDec();
        String decimal = Integer.toString(randdec);

        txtdec.setText("In unsigned number, what is the 8-bit hex value for decimal value " + decimal +" ?");
    }
}
