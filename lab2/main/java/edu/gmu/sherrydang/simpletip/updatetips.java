package edu.gmu.sherrydang.simpletip;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class updatetips extends AppCompatActivity {
    private EditText tips;

    int tipsVal;
    public final static String REVAL = "edu.gmu.sherrydang.simpletip.REVAL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatetips);
        Intent intent = getIntent();
    }

    public void saveButton (View view){
//        //sharePrefernce
        SharedPreferences sharePref = getSharedPreferences("TipsPercentage", Context.MODE_PRIVATE);
        SharedPreferences.Editor tipsEditor = sharePref.edit();

        //get the input tip back to main activity when button save is presses
        tips = (EditText)findViewById(R.id.tipsinput);
        if (tips.getText().toString().equals("")) {
            Toast.makeText(updatetips.this,"Please enter the value or press Back to return to main page!", Toast.LENGTH_SHORT).show();
            tipsEditor.putInt("othertips", tipsVal);
        }
        else {
            tipsVal = Integer.parseInt(tips.getText().toString());
            tipsEditor.putInt("othertips", tipsVal);
            tipsEditor.apply();

           // Toast.makeText(updatetips.this, "other tips sharePref= " + other, Toast.LENGTH_SHORT).show();

            String result = Integer.toString(tipsVal);
            Toast.makeText(updatetips.this, "Saved", Toast.LENGTH_SHORT).show();
            Intent saveIntent = new Intent();
            saveIntent.putExtra(REVAL, result);
            // set result of tips and finish this activity
            setResult(Activity.RESULT_OK, saveIntent);
            finish();
        }

    }


    public void BackButton (View view){
        Intent backIntent = new Intent();
        Toast.makeText(updatetips.this, "Nothing has changed", Toast.LENGTH_SHORT).show();
        setResult(Activity.RESULT_CANCELED, backIntent);
        finish();

    }

}
