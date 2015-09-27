package com.example.theon.bankapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;

import com.reimaginebanking.api.java.NessieClient;
import com.reimaginebanking.api.java.NessieException;
import com.reimaginebanking.api.java.NessieResultsListener;
import com.reimaginebanking.api.java.models.Address;
import com.reimaginebanking.api.java.models.Branch;
import com.reimaginebanking.api.java.models.Geocode;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class ATMActivity extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private LatLng selectedMarkerLatLng;

    private String str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        this.setTitle("Banks");
        //Intent intent = getIntent();

        Intent in = getIntent();
        str = in.getExtras().getString("query");


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_atm);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    public void populateATMs() throws IOException {
        Gson g = new Gson();
        //File fa = new File("atms.txt");

        InputStream is = getResources().openRawResource(R.raw.atms);
        StringWriter writer = new StringWriter();
        IOUtils.copy(is, writer, "utf-8");
        String theString = writer.toString();
        String str = theString;
        ATM[] atms = g.fromJson(str, ATM[].class);

        for (ATM a : atms) {
        Geocode geo = a.getGeocode();
        float lat = geo.getLat();
        float lng = geo.getLng();

        Address ad = a.getAddress();

        String s = "";
        s += ad.getStreetNumber();
        s += " "+ad.getStreetName()+", ";
        s += ad.getCity() + ", "+ad.getState()+" "+ad.getZip();

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat,lng))
                .title(s)
                .snippet(a.getName()));
    }

}

    public void populateBanks() {
        NessieClient n = NessieClient.getInstance();
        n.setAPIKey("3e4b26fce97b7b89d92f0ce8206dd725");
        n.getBranches(new NessieResultsListener() {
            @Override
            public void onSuccess(Object result, NessieException e) {
                if (e == null) {
                    ArrayList<Branch> arrOfBanks = (ArrayList<Branch>) result;
                    for (Branch phillip : arrOfBanks) {

                        //Add b to map
                    }
                } else {
                    System.out.println(e.getMessage() + "TOM");
                }
            }
        });
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() throws SecurityException{
        try {
            populateATMs();
        } catch (IOException e) {

        }
        mMap.setOnInfoWindowClickListener(this);
        mMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null)
        {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 10));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(10)                   // Sets the zoom
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            Circle circle = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(location.getLatitude(), location.getLongitude()))
                    .radius(500)
                    .strokeColor(Color.LTGRAY)
                    .fillColor(Color.TRANSPARENT));

        }
    }

    /*private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            mMap.setMyLocationEnabled(true);
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            if (mMap != null) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
            }
            Circle circle = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(location.getLatitude(), location.getLongitude()))
                    .radius(500)
                    .strokeColor(Color.LTGRAY)
                    .fillColor(Color.TRANSPARENT));
        }
    };*/

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    GoToPOIActivity();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    dialog.dismiss();
                    break;
            }
        }
    };

    public void GoToPOIActivity () {
        double lat = selectedMarkerLatLng.latitude;
        double lon = selectedMarkerLatLng.longitude;

        Uri gmmIntentUri = Uri.parse("geo:" + lat +"," + lon+"?q="+str);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to use this ATM?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

        selectedMarkerLatLng = marker.getPosition();
    }

    public LatLng getSelectedLatLng () {
        return selectedMarkerLatLng;
    }
}
