package fujitora.kaigun_taisho.staysafe;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class UserMap extends FragmentActivity implements OnMapReadyCallback ,GoogleMap.OnMarkerClickListener,GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    AutoCompleteTextView userMapAutoComplete;
    PlaceAutocompleteAdapter adaptor;

    private static final LatLngBounds bangaloreBounds= new LatLngBounds(
            new LatLng(12.972530, 77.398899),
            new LatLng(13.017916, 77.913598));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        Button userMapAutocompleteCancelButton=(Button) findViewById(R.id.userMapAutocompleteCancelButton);

        mapFragment.getMapAsync(this);
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        userMapAutoComplete=(AutoCompleteTextView) findViewById(R.id.userMapAutoComplete);
        adaptor=new PlaceAutocompleteAdapter(this,mGoogleApiClient,bangaloreBounds,null);
        userMapAutoComplete.setAdapter(adaptor);

        userMapAutocompleteCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userMapAutoComplete.setText("");
            }
        });
        userMapAutoComplete.setOnItemClickListener(mAutocompleteClickListener);


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
        mMap.setInfoWindowAdapter(new customInfoAdaptor());
        mMap.setPadding(2,2,2,2);
        final UiSettings uiSettings=mMap.getUiSettings();
        mMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.


            return;
        }

        mMap.setMyLocationEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(false);
        uiSettings.setRotateGesturesEnabled(false);
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setZoomGesturesEnabled(true);



        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(12.8949065,77.5839843);

       Marker marker= mMap.addMarker(new MarkerOptions().position(sydney).title("Set title"));
    //    marker.showInfoWindow();
      //  mMap.animateCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,13));
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent i=new Intent(getApplicationContext(),DisplayPgDetails.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(getApplicationContext(),"Marker clicked",Toast.LENGTH_SHORT).show();


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(marker.getPosition())      // Sets the center of the map to Mountain View
                .zoom(15).build();
        // CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(marker.getPosition());
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        if(!marker.isInfoWindowShown())
        marker.showInfoWindow();
        else
        marker.hideInfoWindow();
        return true;
    }



      void moveMapToMarker(LatLng lat)
   {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(lat, 10);
        mMap.animateCamera(cameraUpdate);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
            ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                    .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);


            final AutocompletePrediction item = adaptor.getItem(position);
            final String placeId = item.getPlaceId();
            Log.v("Auto",placeId);
            final CharSequence primaryText = item.getPrimaryText(null);

          //  Log.i(TAG, "Autocomplete item selected: " + primaryText);

            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
             details about the place.
              */
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

            String temp=userMapAutoComplete.getText().toString();
            temp=temp.substring(0,temp.indexOf(','));
            userMapAutoComplete.setText(temp);

            Log.v("Auto",temp);
            Toast.makeText(getApplicationContext(), "Clicked: " + primaryText,
                    Toast.LENGTH_SHORT).show();
           // Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);
        }
    };
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
               // Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);
            Log.v("Auto results","Entering onResult");
        Log.v("Auto results",""+place);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15);
            mMap.moveCamera(cameraUpdate);

            places.release();
        }
    };


    class customInfoAdaptor implements GoogleMap.InfoWindowAdapter {


        private View v;

        public customInfoAdaptor() {
            v = getLayoutInflater().inflate(R.layout.user_map_custom_info_window, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            View v = getLayoutInflater().inflate(R.layout.user_map_custom_info_window, null);
            ImageView userMapCustomWindowImg=(ImageView) v.findViewById(R.id.userMapCustomWindowImg);
            TextView userMapCustomWindowText1=(TextView) v.findViewById(R.id.userMapCustomWindowText1);
            userMapCustomWindowText1.setText("Shaata "+marker.getPosition());
            return v;
        }
    }
}
