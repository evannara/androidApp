package com.example.myapplication;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
/**
 * references
 *
 *http://developer.android.com/reference/android/location/Geocoder.html
 *https://developers.google.com/maps/documentation/android/map#using_xml_attributes
 */

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
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
                mMap.setMyLocationEnabled(true);
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap()
    {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    /**
     * This function is to hide softkeyboard when press GO! so that we can see the whole map
     * I learned and got this function from stackoverflow website
     * http://stackoverflow.com/questions/18977187/how-to-hide-soft-keyboard-when-activity-starts
     * @param v
     */
    private void hideSoftKeyboard(View v)
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void runLocation(View v) throws IOException
    {
        double lat, lon;
        hideSoftKeyboard(v);


        EditText inputLocation = (EditText) findViewById(R.id.mylocation);
        String location = inputLocation.getText().toString();

        Geocoder gc=new Geocoder(this);
        List<Address> list=gc.getFromLocationName(location,1);
        String locality = list.get(0).getLocality();
        LatLng latlon = new LatLng(list.get(0).getLatitude(),list.get(0).getLongitude());
        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlon,12));

        MarkerOptions flagLocation = new MarkerOptions().title(locality);
        flagLocation.position(new LatLng(list.get(0).getLatitude(),list.get(0).getLongitude()));
        mMap.addMarker(flagLocation);


    }

    public void satellite(View screen)
    {
       mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }


    public void hybrid(View screen)
    {
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
    public void terrain(View screen)
    {
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }
    public void normal(View screen)
    {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
}




