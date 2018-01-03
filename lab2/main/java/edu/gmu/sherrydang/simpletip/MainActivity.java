package edu.gmu.sherrydang.simpletip;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import static edu.gmu.sherrydang.simpletip.updatetips.REVAL;

public class MainActivity extends AppCompatActivity {
    int excellent_tip = 20;
    int average_tip = 18;
    int bad_tip = 14;
    int other_tip ;
    public final static int ACTIVITY_RESULT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent saveIntent = getIntent();

    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        float bill;
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.excellent_button:
                if (checked) {
                    EditText b = (EditText)findViewById(R.id.bill);
                    if (b.getText().toString().equals(""))
                        bill = 0;
                    else bill = Float.parseFloat(b.getText().toString());
                    compute_tip(bill, excellent_tip);
                }
                break;
            case R.id.average_button:
                if (checked) {
                    EditText b = (EditText)findViewById(R.id.bill);
                    if (b.getText().toString().equals(""))
                        bill = 0;
                    else bill = Float.parseFloat(b.getText().toString());
                    compute_tip(bill, average_tip);
                }
                break;
            case R.id.lacking_button:
                if (checked) {
                    EditText b = (EditText)findViewById(R.id.bill);
                    if (b.getText().toString().equals(""))
                        bill = 0;
                    else bill = Float.parseFloat(b.getText().toString());
                    compute_tip(bill, bad_tip);
                }
                break;
            case R.id.other_button:
                if (checked) {
                    EditText b = (EditText)findViewById(R.id.bill);
                    if (b.getText().toString().equals("")){
                        bill = 0;
                    }
                    else {
                        bill = Float.parseFloat(b.getText().toString());
                        SharedPreferences sharePref = getSharedPreferences("TipsPercentage", Context.MODE_PRIVATE);
                        other_tip = sharePref.getInt("othertips", other_tip);
                        compute_tip(bill, other_tip);
                    }
                }
                break;
        }
    }

    public static String roundToTwoDigit(float paramFloat) {
        return String.format("%.2f%n", paramFloat);
    }
    void compute_tip(float bill, int percent) {
        float pct= (float)percent/100;
        float tip = bill * pct;
        float total = bill + tip;
        TextView t = (TextView)findViewById(R.id.computed_tip);
        String s = roundToTwoDigit(tip);
        t.setText(s);
        t = (TextView)findViewById(R.id.bill_total);
        s = roundToTwoDigit(total);
        t.setText(s);

    }

   public void updateButtonClicked (View view){
       Intent intent = new Intent(MainActivity.this, updatetips.class);
       startActivityForResult(intent, ACTIVITY_RESULT );
   }

   public void ImplicitCallButtonClicked (View view){
       Intent intent = new Intent();
       intent.setAction("android.intent.action.VIEW");
       intent.setType("text/plain");
       intent.addCategory("android.intent.category.DEFAULT");
       startActivity(intent);

   }
   public void onActivityResult (int requestCode, int resultCode, Intent data ){

           if (resultCode == RESULT_OK){
               //get the input data from intent second activity
               String input = data.getExtras().getString(updatetips.REVAL);
               TextView percenTipstxt = (TextView)findViewById(R.id.percentTips);
               percenTipstxt.setText(input);    //set text view

           //  Toast.makeText(MainActivity.this, "tips saved = " + input, Toast.LENGTH_SHORT).show();
               SharedPreferences sharePref = getSharedPreferences("TipsPercentage", Context.MODE_PRIVATE);
               other_tip = sharePref.getInt("othertips", other_tip);
               float bill;
               int newtips;
               EditText b = (EditText)findViewById(R.id.bill);
               if (b.getText().toString().equals(""))
                   bill = 0;
               else bill = Float.parseFloat(b.getText().toString());
               newtips = Integer.parseInt(input);
                other_tip = newtips;
               compute_tip(bill, other_tip);
           }
   }


}