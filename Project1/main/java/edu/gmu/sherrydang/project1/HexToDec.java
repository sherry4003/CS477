package edu.gmu.sherrydang.project1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static edu.gmu.sherrydang.project1.R.id.signed;
import static edu.gmu.sherrydang.project1.R.id.unsigned;

public class HexToDec extends AppCompatActivity {
    TextView randhextxt ;
    TextView correctUnSignedResulttxt, correctSignedResulttxt;
    TextView totaltxt;
    TextView scoretxt;
    EditText singnedInputval ;
    EditText unsignedInputVal;
    Button check, next;
    int score1 = 0;
    int total1 =0;
    String randhex;
    int signeddecResult ;
    int usigneddecResult;
    int unsignedvalinput;
    int signedvalinput;
    int signed, unsigned;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hex_to_dec);

        singnedInputval = (EditText) findViewById(R.id.signedInput) ;
        unsignedInputVal = (EditText) findViewById(R.id.unSignedInput);
        randhextxt = (TextView) findViewById(R.id.hexrandvals);
        correctUnSignedResulttxt = (TextView) findViewById(R.id.correctUnSignedResult);
        correctSignedResulttxt= (TextView) findViewById(R.id.correctSignedResult);
        totaltxt = (TextView) findViewById(R.id.total);
        scoretxt = (TextView) findViewById(R.id.score_computed) ;
        check = (Button) findViewById(R.id.result);
        next = (Button) findViewById(R.id.nextbtn);

        Intent intent = getIntent();


        randhex = randomHex();
        randhextxt.setText("What are the signed and unsigned value for the 8-bits hex value 0x"+ randhex +"?");

    }

    public int convertHextoUnsignedDec (String hex){
        unsigned = Integer.parseInt(hex, 16);
        return unsigned;
    }


    public  int convertHexToSignedDec (String hex) {
        signed =  Short.valueOf(hex, 16).byteValue();
        return signed;
    }

    public String randomHex (){
        String hex;
        Random rand = new Random();
        int myRandomNumber = rand.nextInt(0x10) + 0x10;
        hex = Integer.toHexString(myRandomNumber);
        return hex;
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
        try{
        // compute dec
        signeddecResult = convertHexToSignedDec (randhex);
        usigneddecResult = convertHextoUnsignedDec (randhex);
        //convert to int from text
        signedvalinput = Integer.parseInt(singnedInputval.getText().toString());

        unsignedvalinput = Integer.parseInt(unsignedInputVal.getText().toString());

        //    Toast.makeText(getBaseContext(), "signed = " +signeddecResult, Toast.LENGTH_SHORT).show();
            //conditions check
            if ((unsignedvalinput == usigneddecResult )&&(signedvalinput == signeddecResult)){
                correctUnSignedResulttxt.setText("Unsigned Correct!");
                correctSignedResulttxt.setText("Signed Correct!");
                score1 = score1 +2;
            }
            else if ((unsignedvalinput == usigneddecResult )&&(signedvalinput != signeddecResult)){
                correctUnSignedResulttxt.setText("Unsigned Correct!");
                correctSignedResulttxt.setText("Signed = " + signeddecResult);
                score1 ++;
            }else if ((signedvalinput == signeddecResult) && (unsignedvalinput != usigneddecResult )){
                correctSignedResulttxt.setText("Signed Correct!");
                correctUnSignedResulttxt.setText("Unsigned = " + usigneddecResult);
                score1 ++;
            }else{
                correctSignedResulttxt.setText("Singed exp = " + signeddecResult);
                correctUnSignedResulttxt.setText("Unsigned exp = " + usigneddecResult);
            }

        }catch (Exception e){
            if ((signedvalinput == signeddecResult) && (unsignedvalinput != usigneddecResult )) {
                correctSignedResulttxt.setText("Signed Correct!");
                correctUnSignedResulttxt.setText("Unsigned = " + usigneddecResult);
                score1++;
            }
            if ((unsignedvalinput == usigneddecResult )&&(signedvalinput != signeddecResult)) {
                correctUnSignedResulttxt.setText("Unsigned Correct!");
                correctSignedResulttxt.setText("Signed = " + signeddecResult);

                score1++;
            } else{
                    correctSignedResulttxt.setText("Singed exp = " + signeddecResult);
                    correctUnSignedResulttxt.setText("Unsigned exp = " + usigneddecResult);
                }

        }

        total1 = total1 + 2;
        String s = Integer.toString(score1);
        scoretxt.setText(s);
        String t = Integer.toString(total1);
        totaltxt.setText(t);

        Intent intent = new Intent();
        intent.putExtra("s1", score1);
        intent.putExtra("t1", total1);
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

        randhex = randomHex();
        randhextxt.setText("What are the signed and unsigned value for the 8-bits hex value 0x"+ randhex + "?");

    }
}