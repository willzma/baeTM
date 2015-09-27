package com.example.theon.bankapp;

import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PointsOfInterestActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private boolean grocToggle = false;
    private boolean gasToggle = false;
    private boolean restToggle = false;

    private double lat = 0;
    private double lon = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle("Points of Interest");

        Intent in = getIntent();
        grocToggle = in.getBooleanExtra("grocToggle",false);
        gasToggle = in.getBooleanExtra("gasToggle",false);
        restToggle = in.getBooleanExtra("restToggle",false);

        lat = in.getDoubleExtra("preLat", 0);
        lon = in.getDoubleExtra("preLon",0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_of_interest);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
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
    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(lat, lon), 10));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(lat, lon))      // Sets the center of the map to location user
                    .zoom(10)                   // Sets the zoom
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            Circle circle = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(lat, lon))
                    .radius(500)
                    .strokeColor(Color.LTGRAY)
                    .fillColor(Color.TRANSPARENT));

        String s = "?q=";
        if (grocToggle) {
            s += "";
        }

        if (gasToggle) {

        }

        if (restToggle) {

        }

        Uri gmmIntentUri = Uri.parse("geo:" + lat +"," + lon+"?q=restaurants");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}
