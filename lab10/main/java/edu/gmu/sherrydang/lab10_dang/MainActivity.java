package edu.gmu.sherrydang.lab10_dang;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnShowCurLocation;
    EditText edittxt;
    String address;
    double latitude, longitude;
    private Location mLocation;

    // GPSTracker class
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShowCurLocation = (Button) findViewById(R.id.btnShowCurLocation);
        edittxt = (EditText) findViewById(R.id.edittxt);

        // show  current location button click event
        btnShowCurLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // create class object
               // gps = new GPSTracker(MainActivity.this);
                gps = new GPSTracker(getApplicationContext());
                List<Address> addresses = null;

                // check if GPS enabled
                if (gps.canGetLocation()) {

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                    try {
                        addresses =  new Geocoder(MainActivity.this).getFromLocation(latitude, longitude, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            address = addresses.get(0).getAddressLine(0);
            Toast.makeText(getApplicationContext(), " "+ address, Toast.LENGTH_SHORT).show();
                    if (latitude != 0.0 && longitude != 0.0) {
                        // create class object
                        Intent intent1 = new Intent (MainActivity.this, MapsActivity.class);
                        intent1.putExtra("latitude", latitude);
                        intent1.putExtra("longitude", longitude);
                        intent1.putExtra("address", address);
                        startActivity(intent1);

                    } else {
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                        gps.showSettingsAlert();
                    }
                }
                Toast.makeText(getApplicationContext(), latitude+"  , " + longitude, Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void findAddressButtonClicked (View view){
        address = edittxt.getText().toString();

        if (address.length() !=0){
            // create class object
            Intent intent = new Intent (MainActivity.this, MapsActivity.class);
            intent.putExtra("address", address);
            Toast.makeText(getApplicationContext(), " " + address, Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }else {
            Toast.makeText(getApplicationContext(), "Please enter an address name", Toast.LENGTH_SHORT).show();
        }


    }
}
