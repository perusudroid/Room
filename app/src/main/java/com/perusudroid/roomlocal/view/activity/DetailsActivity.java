package com.perusudroid.roomlocal.view.activity;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.transition.Slide;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.perusudroid.roomlocal.R;
import com.perusudroid.roomlocal.common.Constants;
import com.perusudroid.roomlocal.dao.UserDaoModel;
import com.perusudroid.roomlocal.dao.UserModel;
import com.perusudroid.roomlocal.db.InjectDB;
import com.perusudroid.roomlocal.model.ViewModelFactory;
import com.perusudroid.roomlocal.presenter.DetailPresenter;
import com.perusudroid.roomlocal.presenter.ipresenter.IDetailPresenter;
import com.perusudroid.roomlocal.view.iview.IDetailView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailsActivity extends BaseActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        IDetailView {

    private static final String EXTRA_IMAGE = "com.antonioleiva.materializeyourapp.extraImage";
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private GoogleMap mMap;
    private double longitude;
    private double latitude;
    private static String dishId;
    private ImageView image;
    private GoogleApiClient googleApiClient;
    private TextView txtDesc, txtPlace, txtAddress, txtMobile, txtTime;
    private IDetailPresenter iDetailPresenter;
    private UserDaoModel userDaoModel;
    private ViewModelFactory mViewModelFactory;


    public static void navigate(AppCompatActivity activity, View transitionImage, String dId) {
        Intent intent = new Intent(activity, DetailsActivity.class);
        dishId = dId;
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, EXTRA_IMAGE);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTransitions();
        setContentView(R.layout.activity_details);
        bindViews();
        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), EXTRA_IMAGE);
        supportPostponeEnterTransition();
        initMap();
        setToolbar();
        setAssets();

        mViewModelFactory = InjectDB.provideViewModelFactory(this);
        userDaoModel = ViewModelProviders.of(this, mViewModelFactory).get(UserDaoModel.class);
        iDetailPresenter = new DetailPresenter(this, userDaoModel);
        Bundle b = new Bundle();
        b.putString(Constants.BundleKey.DISH_ID, dishId);
        iDetailPresenter.onCreatePresenter(b);

    }

    private void bindViews() {
        image = findViewById(R.id.image);
        txtDesc = findViewById(R.id.txtDesc);
        txtPlace = findViewById(R.id.txtPlace);
        txtAddress = findViewById(R.id.txtAddress);
        txtMobile = findViewById(R.id.txtMobile);
        txtTime = findViewById(R.id.txtTime);
    }

    private void setAssets() {
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        //setPic();
       /* txtDesc.setText(data.getDish_description());
        txtPlace.setText(data.getShop_address());
        txtAddress.setText(data.getShop_address());
       // txtMobile.setText(data.getShop_mobile().toString());
        txtTime.setText(data.getShop_timing());*/
    }

    private void setPic(String url) {
        String crapUrl = "http://192.168.43.248/RoomApi/"+url;
        Log.d(TAG, "setPic: "+ crapUrl);
        Picasso.with(this).load(crapUrl).into(image, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                Palette.from(bitmap).generate(palette -> applyPalette(palette));
            }

            @Override
            public void onError() {

            }
        });
    }

    private void initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Initializing googleapi client
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        try {
            return super.dispatchTouchEvent(motionEvent);
        } catch (NullPointerException e) {
            return false;
        }
    }

    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    private void applyPalette(Palette palette) {
        int primaryDark = getResources().getColor(R.color.colorPrimaryDark);
        int primary = getResources().getColor(R.color.colorPrimary);
        collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
        collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
        //updateBackground((FloatingActionButton) findViewById(R.id.fab), palette);
        supportStartPostponedEnterTransition();
    }

    private void updateBackground(FloatingActionButton fab, Palette palette) {
        int lightVibrantColor = palette.getLightVibrantColor(getResources().getColor(android.R.color.white));
        int vibrantColor = palette.getVibrantColor(getResources().getColor(R.color.colorAccent));

        fab.setRippleColor(lightVibrantColor);
        fab.setBackgroundTintList(ColorStateList.valueOf(vibrantColor));
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    //Function to move the map
    private void moveMap() {
        //String to display current latitude and longitude
        String msg = latitude + ", " + longitude;

        //Creating a LatLng Object to store Coordinates
        LatLng latLng = new LatLng(latitude, longitude);

        //Adding marker to map
        mMap.addMarker(new MarkerOptions()
                .position(latLng) //setting position
                .draggable(true) //Making the marker draggable
                .title("Current Location")); //Adding a title

        //Moving the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        //Animating the camera
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        //Displaying current coordinates in toast
        //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    //Getting current location
    private void getCurrentLocation() {
        mMap.clear();
        //Creating a location object
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            //moving the map to location
         //   moveMap();
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getCurrentLocation();
        //moveMap();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        //Clearing all the markers
        mMap.clear();

        //Adding a new marker to the current pressed position
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true));
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        //Getting the coordinates
        latitude = marker.getPosition().latitude;
        longitude = marker.getPosition().longitude;

        //Moving the map
        //moveMap();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);
    }

    @Override
    public void setDishInfoData(List<UserModel> data) {

        setPic(data.get(0).getDish_pic());
        txtDesc.setText(data.get(0).getDish_description());
        txtPlace.setText(data.get(0).getShop_name());
        txtMobile.setText(data.get(0).getShop_mobile());
        txtTime.setText(data.get(0).getShop_timing());
        txtAddress.setText(data.get(0).getShop_address());
        latitude = Double.parseDouble(data.get(0).getLat());
        longitude = Double.parseDouble(data.get(0).getLat());

        moveMap();
        // txtMobile.setText(data.get(0).getShop_mobile().toString());
        //
    }
}
