package com.happyface.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.happyface.R;
import com.happyface.helpers.GPSTracker;
import com.happyface.helpers.StaticMembers;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    GPSTracker gpsTracker;
    @BindView(R.id.confirm)
    CardView confirm;
    double selectedLat = -1, selectedLong = -1;
    private boolean mLocationPermissionGranted;
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        confirm.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(StaticMembers.LAT, selectedLat);
            intent.putExtra(StaticMembers.LONG, selectedLong);
            if (selectedLat != -1 && selectedLong != -1) {
                setResult(RESULT_OK, intent);
            }
            finish();
        });
    }

    private void getLocationPermission() {
        mLocationPermissionGranted = false;
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        getLocationPermission();
        mMap.setOnMapClickListener(this::addMarker);
    }

    void addMarker(LatLng latLng) {
        mMap.clear();
        LatLng sydney = new LatLng(latLng.latitude, latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        selectedLong = latLng.longitude;
        selectedLat = latLng.latitude;
        goToLocation(latLng);
        confirm.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    getCurrentLocation();
                } else {
                    gpsTracker.showSettingsAlert();
                }
            }
        }
    }

    public void getCurrentLocation() {
        try {
            gpsTracker = new GPSTracker(this);
            if (gpsTracker.canGetLocation()) {
                mMap.setMyLocationEnabled(true);
                selectedLat = getIntent().getDoubleExtra(StaticMembers.LAT, 0);
                selectedLong = getIntent().getDoubleExtra(StaticMembers.LONG, 0);
                if (selectedLat > 0 && selectedLong > 0) {
                    addMarker(new LatLng(selectedLat, selectedLong));
                    goToLocation(new LatLng(selectedLat, selectedLong));
                } else
                    goToLocation(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()));
            } else {
                gpsTracker.showSettingsAlert();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    void goToLocation(LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)      // Sets the center of the map to Mountain View
                .zoom(DEFAULT_ZOOM)                   // Sets the zoom
                //.bearing(360)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
