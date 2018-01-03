package edu.gmu.sherrydang.lab10_dang;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static java.sql.Types.NULL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double latitude = 30, longitude= -30;
    String address = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        double latAddress= 30, longAddres=-30;

        double lat1 = 30;
        double long1= -30;
        double lat2= 30, long2=-30;
        double lat3 =30, long3=-30;
        double lat4 =30, long4=-30;
        double lat5 =30, long5=-30;
        double lat6 =30, long6=-30;


        List<Address> geocodeAddress = null;
        List<Address> geocodeMatches = null;
        List<Address> gc = null;
        List<Address> gc3 = null;
        List<Address> gc4 = null;
        List<Address> gc5 = null;
        List<Address> gc6 = null;
   //     List<Address> addresses = null;

        try {
            geocodeMatches =  new Geocoder(this).getFromLocationName("George Mason University, Fairfax, VA 22030", 1);
            gc =  new Geocoder(this).getFromLocationName("Tysons Corner Center, Tysons, VA 22102", 1);
            gc3 =  new Geocoder(this).getFromLocationName("GreatWall Market,  Falls Church, VA 22042", 1);
            gc4 =  new Geocoder(this).getFromLocationName("4010 Bryce, Alexandria, VA 22312", 1);
            gc5 =  new Geocoder(this).getFromLocationName("Northen Virginia Community College, Annandale, VA 22003", 1);
            gc6 =  new Geocoder(this).getFromLocationName("7442 Little River Turnpike, Annandale, VA 22003 ", 1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if ( !geocodeMatches.isEmpty())
        {
            lat1 = geocodeMatches.get(0).getLatitude();
            long1 = geocodeMatches.get(0).getLongitude();
            lat2 = gc.get(0).getLatitude();
            long2 = gc.get(0).getLongitude();
            lat3 = gc3.get(0).getLatitude();
            long3 = gc3.get(0).getLongitude();
            lat4 = gc4.get(0).getLatitude();
            long4 = gc4.get(0).getLongitude();
            lat5 = gc5.get(0).getLatitude();
            long5 = gc5.get(0).getLongitude();
            lat6 = gc6.get(0).getLatitude();
            long6 = gc6.get(0).getLongitude();

        }
        //initial location that marked on the map
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //LatLng fairfax = new LatLng(38.8462, -77.3064);
        LatLng GMU = new LatLng(lat1,long1);
        mMap.addMarker(new MarkerOptions().position(GMU).title("Marker at GMU in Fairfax VA"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(GMU,14));

        LatLng tyson = new LatLng(lat2,long2);
        mMap.addMarker(new MarkerOptions().position(tyson).title("Marker at Tyson Corner Mall VA"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tyson,14));

        LatLng bryce = new LatLng(lat3,long3);
        mMap.addMarker(new MarkerOptions().position(bryce).title("Marker at GreatWall Market"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bryce,14));

        LatLng loc1 = new LatLng(lat4,long4);
        mMap.addMarker(new MarkerOptions().position(loc1).title("Marker at SweetHome"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc1,14));

        LatLng nova = new LatLng(lat5,long5);
        mMap.addMarker(new MarkerOptions().position(nova).title("Marker at NOVA Comunity College"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nova,14));

        LatLng pho = new LatLng(lat6,long6);
        mMap.addMarker(new MarkerOptions().position(pho).title("Marker at My favous Pho Noodle Restaurant"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pho,14));

        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("latitude",latitude );
        longitude = intent.getDoubleExtra("longitude", longitude);



        // Add marker to current location
        LatLng myloca = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(myloca).title("I'm here ^_^"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myloca,14));


            //get the address from user input
            Intent intent1 = getIntent();
            address = intent1.getStringExtra("address");

            if (address.length() !=0){
                try {
                    geocodeAddress =  new Geocoder(this).getFromLocationName(address, 1);

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                if (!geocodeAddress.isEmpty())
                {
                    latAddress = geocodeAddress.get(0).getLatitude();
                    longAddres = geocodeAddress.get(0).getLongitude();
                    Toast.makeText(getApplicationContext(), latAddress+"  , " + longAddres, Toast.LENGTH_SHORT).show();

                }
                LatLng addressloc = new LatLng(latAddress,longAddres);
                mMap.addMarker(new MarkerOptions().position(addressloc).title("Marker at " + address));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(addressloc,14));
            }

        }

}
