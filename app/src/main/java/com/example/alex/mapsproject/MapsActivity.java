package com.example.alex.mapsproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback, GoogleMap.OnMapClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest myLocateRequest;
    Marker myMarker;
    public String mLatitudeText;
    public String mLongitudeText;
    public LatLng pointTmp;
    public LatLng initialPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Create an instance of GoogleAPIClient.
        // This is necessary to connect to Google services such as maps
        if (mGoogleApiClient == null) {
            // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(AppIndex.API).build();
        }

        Log.v("in OnCreate ", " On Create Called");
    }

    /**
     * Use the lifecycle methods to connect/disconnect to google services.
     * Necessary for using maps
     */
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.alex.mapsproject/http/host/path")
        );
        AppIndex.AppIndexApi.start(mGoogleApiClient, viewAction);

        Log.v("in OnStart ", " On Start Called");

    }

    // calling intent method onPause, or a tost that no location was selected
    protected void onPause(){
        super.onPause();
        if(pointTmp != null) {
            Toast.makeText(this, "Your book location has been added!", Toast.LENGTH_SHORT).show();
            sendLocation(pointTmp);
        }else {
            Toast.makeText(this, "You did not select a location, no book added", Toast.LENGTH_SHORT).show();
        }

    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.alex.mapsproject/http/host/path")
        );
        AppIndex.AppIndexApi.end(mGoogleApiClient, viewAction);

    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            //showMissingPermissionError();
            Log.v("onResumeFragments", "Permission was denied");
            mPermissionDenied = false;
        }
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

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMapClickListener(this);


//        Log.v("DEBUG", "checning point [" + initialPoint.latitude + " / " + initialPoint.longitude + "]");



        // Add a marker in Sydney and move the camera
         LatLng sydney = new LatLng(-34, 151);
         //mMap.addMarker(new MarkerOptions().position(sydney).title("You Are Here"));

        mMap.addMarker(new MarkerOptions().position(sydney).icon(BitmapDescriptorFactory.
                fromBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_here))));
         mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        Log.v("in OnMapReady ", " OnMapReady Called");




    }
//  ic_mappoint.png

    /**
     * This is the callback method for the mapClickListener
     * @param point the place on the map that was clicked.  Contains latitude and longitude
     */
    @Override
    public void onMapClick(LatLng point) {
        Toast.makeText(this, "Map clicked", Toast.LENGTH_SHORT).show();
        Log.v("DEBUG", "Map clicked [" + point.latitude + " / " + point.longitude + "]");

        mMap.addMarker(new MarkerOptions().position(point).icon(BitmapDescriptorFactory.
                fromBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_mappoint))));

        //Do your stuff with LatLng here
        //Then pass LatLng to other activity


    }



    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            /*
            ActivityCompat.requestPermissions(thisActivity,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            */
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
            Log.v("enableMyLocation: ", "Permission granted");
        }

        Log.v("in Enaplemylocation ", " EnablemyLocation Called");


    }

    @Override
    public boolean onMyLocationButtonClick() {
 //       Toast.makeText(this, "Your location has been added!", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        Log.v("MapsActivity:", "Map click detected");
        // Add a marker in Sydney and move the camera
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return false;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        //LatLng sydney = new LatLng(-34, 151);
        LatLng myLoc = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

//        mMap.addMarker(new MarkerOptions().position(myLoc).title("You are here!").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_book_pointer)));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLoc));
        pointTmp=myLoc;
//        sendLocation(pointTmp);
        return false;
    }

    /**
     * These next three methods are part of the interface that must be implemented to
     * connect to google services.  We're basically connecting to google to use their API
     * including maps
     * @param connectionHint
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        enableMyLocation();
        myLocateRequest = new LocationRequest();
        myLocateRequest.setInterval(1000).setFastestInterval(1000)
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
//                        != PackageManager.PERMISSION_GRANTED) {
//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,myLocateRequest,this);
//        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,myLocateRequest,this);
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
//        if (mLastLocation != null) {
//            mLatitudeText = String.valueOf(mLastLocation.getLatitude());
//            mLongitudeText = String.valueOf(mLastLocation.getLongitude());
//        }

        Log.v("in OnConnected ", " OnConnected Called");


    }

    @Override
    public void onConnectionSuspended(int result) {
        Log.v("Connection Suspended: ", " " + result);
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.v("Connection Failed ", result.getErrorMessage());
    }

    /**
     *  These next methods are used to obtain permissions for using maps
     *
     **/
    @Override
    // public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
    //                                       @NonNull int[] grantResults) {
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
            Log.v("onRequestPermRslt:  ", "enabled permissions");
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
            Log.v("onRequestPermRslt:  ", "DISABLED permissions");
        }
    }

    /**
     * Checks if the result contains a {@link PackageManager#PERMISSION_GRANTED} result for a
     * permission from a runtime permissions request.
     *
     * @see ActivityCompat.OnRequestPermissionsResultCallback
     */
    public static boolean isPermissionGranted(String[] grantPermissions, int[] grantResults,
                                              String permission) {
        for (int i = 0; i < grantPermissions.length; i++) {
            if (permission.equals(grantPermissions[i])) {
                return grantResults[i] == PackageManager.PERMISSION_GRANTED;
            }
        }
        return false;
    }


    @Override// call back method used to update map with current locaton as location chnges
    public void onLocationChanged(Location location) {

        // if Location object is not empty, get the value of the current location
        if (mLastLocation != null) {
            mLatitudeText = String.valueOf(mLastLocation.getLatitude());
            mLongitudeText = String.valueOf(mLastLocation.getLongitude());
        }
        Log.v("in locationChanged", " - pleaes be in here ");

        // set Location object to new location
        mLastLocation = location;
            
         // if there already a marker, remove it 
        if (myMarker != null) {
            myMarker.remove();
        }

        // get new lattitude and lingitude
        LatLng latLng1 = new LatLng(location.getLatitude(),location.getLongitude());
        // instantiate markerOptions object 
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("Current Book Location");
        markerOptions.position(latLng1);
        markerOptions.icon(BitmapDescriptorFactory.
                fromBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_here)));


        // set marker icon
        myMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    // Intent - called in onPause and in onMyLocationClicked
                //sends lattitude and longitude data through intent
    public void sendLocation(LatLng pointTmp) {
        //CREATE AN INTENT TO DISPLAY THE SURVEY INFORMATION
        Intent launchResults = new Intent(this, AddBook.class);
        //ACCESS THE SURVEY SUMMARY ACTIVITY DATA FROM THE SUMMARY:
        double lat = pointTmp.latitude;// get latitude data
        double longitude= pointTmp.longitude;// get longitude data
        launchResults.putExtra("LatPoint", lat); //pass lattitude data in intent
        launchResults.putExtra("LongPoint", longitude);// pass longitude data in intent
        //START THE SURVEY RESULTS ACTIVITY
        startActivity(launchResults);
    }
}
