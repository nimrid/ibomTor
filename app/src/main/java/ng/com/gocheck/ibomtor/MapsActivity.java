package ng.com.gocheck.ibomtor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.join;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    // New variables for Current Place Picker
    private static final String TAG = "MapsActivity";
    ListView lstPlaces;
    private PlacesClient mPlacesClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;
    private double latitude;
    private double longitude;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng akwaIbom = new LatLng(4.9300, 7.8722);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // Used for selecting the current place.
    private static final int M_MAX_ENTRIES = 4;
    private String[] mLikelyPlaceNames;
    private String[] mLikelyPlaceAddresses;
    private String[] mLikelyPlaceAttributions;
    private LatLng[] mLikelyPlaceLatLngs;

    private LocationManager locationManager;
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //
        // PASTE THE LINES BELOW THIS COMMENT
        //

        // Set up the action toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the views
        lstPlaces = (ListView) findViewById(R.id.listPlaces);

        // Initialize the Places client
        String apiKey = getString(R.string.google_maps_key);
        Places.initialize(getApplicationContext(), apiKey);
        mPlacesClient = Places.createClient(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.geo_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.geolocate:
                    pickCurrentPlace();
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }
    }

    private void getLocationPermission(){
        mLocationPermissionGranted = false;
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
//            mLocationPermissionGranted = true;


            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                new AlertDialog.Builder(this).setTitle("Access Location")
                        .setMessage("Please turn location on")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{
                                        Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                                mLocationPermissionGranted = true;
                            }
                        }).create().show();
            }
        }else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        mLocationPermissionGranted = true;
                    }
                }
            }
        }
    }

    private void getCurrentPlaceLikelihood(){
        List<Place.Field> placeHolders = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        @SuppressWarnings("MissingPermission") final FindCurrentPlaceRequest request =
                FindCurrentPlaceRequest.builder(placeHolders).build();
        @SuppressWarnings("MissingPermission") Task<FindCurrentPlaceResponse> currentPlace = mPlacesClient.findCurrentPlace(request);
        currentPlace.addOnCompleteListener(this, new OnCompleteListener<FindCurrentPlaceResponse>() {
            @SuppressLint("NewApi")
            @Override
            public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
                if (task.isSuccessful()){
                    FindCurrentPlaceResponse response = task.getResult();
                    int count;
                    if (response.getPlaceLikelihoods().size() < M_MAX_ENTRIES){
                        count = response.getPlaceLikelihoods().size();
                    }else
                        count = M_MAX_ENTRIES;

                    int i = 0;
                    mLikelyPlaceNames = new String[count];
                    mLikelyPlaceAddresses = new String[count];
                    mLikelyPlaceAttributions = new String[count];
                    mLikelyPlaceLatLngs = new LatLng[count];

                    for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()){
                        Place place = placeLikelihood.getPlace();
                        mLikelyPlaceNames[i] = place.getName();
                        mLikelyPlaceAddresses[i] = place.getAddress();
                        mLikelyPlaceAttributions[i] = (place.getAttributions() == null) ?
                                null : join(" ", place.getAttributions());
                        mLikelyPlaceLatLngs[i] = place.getLatLng();

                        Log.i(TAG, String.format("Place " + place.getName()
                                + " has likelihood: " + placeLikelihood.getLikelihood()
                                + " at " + place));

                        i++;
                        if (i > (count - 1))
                            break;
                    }
                    // Populate the ListView
                    placelist();
                }else {
                    Exception exception = task.getException();
                    if (exception instanceof ApiException){
                        ApiException apiException =(ApiException) exception;
                        Log.e(TAG, "Place not found: " + apiException.getStatusCode());
                    }
                }
            }
        });
    }

    private void getDeviceLocation(){
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available or user does not actively use location
         * tracker or maps on device.
         */
        try {
            if (mLocationPermissionGranted) {
                locationManager =(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String provider = String.valueOf(locationManager.getBestProvider(criteria, true));
                mLastKnownLocation = locationManager.getLastKnownLocation(provider);
/**
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
//                         Set the map's camera position to the current location of the device.
                        mLastKnownLocation = task.getResult();
                        if (mLastKnownLocation != null) {
                            latitude = mLastKnownLocation.getLatitude();
                            longitude = mLastKnownLocation.getLongitude();
                        }

                        Log.d(TAG, "Latitude: " + latitude);
                        Log.d(TAG, "Longitude: " + longitude);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(latitude, longitude), DEFAULT_ZOOM));
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.");
                        Log.e(TAG, "Exception: %s", task.getException());
                        mMap.moveCamera(CameraUpdateFactory
                                .newLatLngZoom(akwaIbom, DEFAULT_ZOOM));
                    }

                    getCurrentPlaceLikelihood();
                });
 **/
                    if (mLastKnownLocation != null){
                        latitude = mLastKnownLocation.getLatitude();
                        longitude = mLastKnownLocation.getLongitude();
                        Log.d(TAG, "Latitude: " + latitude);
                        Log.d(TAG, "Longitude: " + longitude);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(latitude, longitude), DEFAULT_ZOOM));
                    }else {
                        locationManager.requestLocationUpdates(provider, 1000, 0,this);
                    }

                    getCurrentPlaceLikelihood();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void pickCurrentPlace(){
        if (mMap == null) {
            return;
        }

        if (mLocationPermissionGranted) {
            getDeviceLocation();
        } else {
            // The user has not granted permission.
            Log.i(TAG, "The user did not grant location permission.");

            // Add a default marker, because the user hasn't selected a place.
            mMap.addMarker(new MarkerOptions()
                    .title(getString(R.string.default_info_title))
                    .position(akwaIbom)
                    .snippet(getString(R.string.default_info_snippet)));

            // Prompt the user for permission.
            getLocationPermission();
        }
    }

//  an adapter to handle the list of most likely places a user is currently visiting
    private AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        position will give us the index of which place he's in
        LatLng markerLatLng = mLikelyPlaceLatLngs[position];
        String markerSnippet = mLikelyPlaceAddresses[position];
        if (mLikelyPlaceAttributions[position] != null) {
            markerSnippet = markerSnippet + "\n" + mLikelyPlaceAttributions[position];
        }

        mMap.addMarker(new MarkerOptions()
                .title(mLikelyPlaceNames[position])
                .position(markerLatLng)
                .snippet(markerSnippet));

        // Position the map's camera at the location of the marker.
        mMap.moveCamera(CameraUpdateFactory.newLatLng(markerLatLng));
        }
    };
//  populate the listView with names of places
    private void placelist(){
        ArrayAdapter<String> placesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mLikelyPlaceNames);
        lstPlaces.setAdapter(placesAdapter);
        lstPlaces.setOnItemClickListener(clickListener);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
        LatLng akwaIbom = new LatLng(4.9300, 7.8722);
        mMap.addMarker(new MarkerOptions().position(akwaIbom).title("Marker in Akwa-Ibom"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(akwaIbom));

        mMap.getUiSettings().setZoomControlsEnabled(true);
//      prompt user for permission
        getLocationPermission();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        locationManager.removeUpdates(this);
//        latitude = mLastKnownLocation.getLatitude();
//        longitude = mLastKnownLocation.getLongitude();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(latitude, longitude), DEFAULT_ZOOM));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
