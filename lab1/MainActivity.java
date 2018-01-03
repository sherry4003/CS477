package com.example.sherrydang.baseconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.icu.text.UnicodeSetSpanner;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements  View.OnClickListener{

    EditText inputvalue;
    TextView txtbinary;
    TextView txtoctal;
    TextView txtDecimal;
    TextView txthex;
    Button clearbnt;
    Button convertbnt;
    RadioGroup rg;
    String radio_checked;
    String binaryOption = "radio_bi";
    String octalOption = "radio_oct";
    String decimalOption = "radio_dec";
    String hexOption = "radio_hex";
    String userinput;
    int value;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //this is user input number to be converted
        inputvalue = (EditText) findViewById(R.id.inputval);

        rg =(RadioGroup) findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                // Check which radio button was clicked
                switch(checkedId) {
                    case R.id.radio_bi:
                        radio_checked = binaryOption;
                        inputvalue.setInputType(InputType.TYPE_CLASS_NUMBER);

                        // Toast.makeText(MainActivity.this, "binary checked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio_oct:
                        radio_checked = octalOption;
                        inputvalue.setInputType(InputType.TYPE_CLASS_NUMBER);
                        //Toast.makeText(MainActivity.this, "oct checked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio_dec:
                        radio_checked = decimalOption;
                        inputvalue.setInputType(InputType.TYPE_CLASS_NUMBER);
                        // Toast.makeText(MainActivity.this, "dec checked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio_hex:
                        radio_checked = hexOption;
                        inputvalue.setInputType(InputType.TYPE_CLASS_TEXT);
                        //Toast.makeText(MainActivity.this, "hex checked", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        //text  view
        txtbinary = (TextView) findViewById(R.id.bitext);
        txtoctal= (TextView) findViewById(R.id.octtext);
        txtDecimal = (TextView) findViewById(R.id.dectext);
        txthex = (TextView) findViewById(R.id.hextext);

        // clear button
        clearbnt= (Button)findViewById(R.id.clearbutton);
        clearbnt.setOnClickListener (MainActivity.this);

        convertbnt = (Button) findViewById(R.id.convertbutton);
        convertbnt.setOnClickListener(MainActivity.this);
        //convert button listener
        convertbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userinput = inputvalue.getText().toString();
                try {
                    switch (radio_checked) {
                        case "radio_bi":        //case for binary
                            //function
                            try {
                                value = Integer.parseInt(userinput, 2);
                                txtbinary.setText("Binary" + "\t\t\t\t\t\t\t" + Integer.toBinaryString(value));
                                txtoctal.setText("Octal" + "\t\t\t\t\t\t\t\t" + Integer.toOctalString(value));
                                txtDecimal.setText("Decimal" + "\t\t\t\t\t" + value);
                                txthex.setText("Hex" + "\t\t\t\t\t\t\t\t\t" + Integer.toHexString(value));
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "Illigal number for base 2 ", Toast.LENGTH_SHORT).show();
                            }
                            break;

                        case "radio_oct":      //case for octal
                            try {
                                value = Integer.parseInt(userinput, 8);
                                txtbinary.setText("Binary" + "\t\t\t\t\t\t\t" + Integer.toBinaryString(value));
                                txtoctal.setText("Octal" + "\t\t\t\t\t\t\t\t" + Integer.toOctalString(value));
                                txtDecimal.setText("Decimal" + "\t\t\t\t\t" + value);
                                txthex.setText("Hex" + "\t\t\t\t\t\t\t\t\t" + Integer.toHexString(value));
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "Illigal number for base 8 ", Toast.LENGTH_SHORT).show();
                            }
                            break;

                        case "radio_dec":     //case for decimal
                            try {
                                value = Integer.parseInt(userinput, 10);
                                txtbinary.setText("Binary" + "\t\t\t\t\t\t\t" + Integer.toBinaryString(value));
                                txtoctal.setText("Octal" + "\t\t\t\t\t\t\t\t" + Integer.toOctalString(value));
                                txtDecimal.setText("Decimal" + "\t\t\t\t\t" + value);
                                txthex.setText("Hex" + "\t\t\t\t\t\t\t\t\t" + Integer.toHexString(value));
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "Illigal number for base 10 ", Toast.LENGTH_SHORT).show();
                            }
                            break;

                        case "radio_hex":  // case for hexadecimal
                            try {
                                value = Integer.parseInt(userinput, 16);
                                txtbinary.setText("Binary" + "\t\t\t\t\t\t\t" + Integer.toBinaryString(value));
                                txtoctal.setText("Octal" + "\t\t\t\t\t\t\t\t" + Integer.toOctalString(value));
                                txtDecimal.setText("Decimal" + "\t\t\t\t\t" + value);
                                txthex.setText("Hex" + "\t\t\t\t\t\t\t\t\t" + Integer.toHexString(value));
                                //   Toast.makeText(MainActivity.this,"radio hex was clicked : "+ value,Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "Illigal number for base 16 ", Toast.LENGTH_SHORT).show();
                            }
                            break;
                    }
                } catch (Exception e){
                    Toast.makeText(MainActivity.this, "choose the convert option", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //clear button listener
    public void onClick (View v){

        inputvalue.setText("");

    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_bi:
                if (checked)
                    break;
            case R.id.radio_oct:
                if (checked)
                    break;
            case R.id.radio_dec:
                if (checked)
                    break;
            case R.id.radio_hex:
                if (checked)
                    break;
        }
    }
}
