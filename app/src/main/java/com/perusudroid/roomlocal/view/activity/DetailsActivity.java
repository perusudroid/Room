package com.perusudroid.roomlocal.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.transition.Slide;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

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
import com.perusudroid.roomlocal.databinding.ActivityDetailsBinding;
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


    private CollapsingToolbarLayout collapsingToolbarLayout;
    private GoogleMap mMap;
    private double longitude;
    private double latitude;
    private static String dishId;
    private GoogleApiClient googleApiClient;
    private IDetailPresenter iDetailPresenter;
    private UserDaoModel userDaoModel;
    private ViewModelFactory mViewModelFactory;

    private ActivityDetailsBinding activityDetailsBinding;

    public static void navigate(AppCompatActivity activity, View transitionImage, String dId) {
        Intent intent = new Intent(activity, DetailsActivity.class);
        dishId = dId;
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, Constants.Common.EXTRA_IMAGE);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTransitions();
        activityDetailsBinding = DataBindingUtil.setContentView(DetailsActivity.this, R.layout.activity_details);
        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), Constants.Common.EXTRA_IMAGE);
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

    private void setAssets() {
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
    }

    private void setPic(String url) {
        String crapUrl = "http://192.168.43.248/RoomApi/" + url;
        Log.d(TAG, "setPic: " + crapUrl);
        Picasso.with(this).load(crapUrl).into(activityDetailsBinding.image, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) activityDetailsBinding.image.getDrawable()).getBitmap();
                Palette.from(bitmap).generate(palette -> applyPalette(palette));
            }

            @Override
            public void onError() {

            }
        });
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
        supportStartPostponedEnterTransition();
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

    private void moveMap() {

        String msg = latitude + ", " + longitude;
        Log.d(TAG, "moveMap: " + msg);
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true)
                .title("Shop Location"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
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
     /*   mMap.clear();
       mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true));
*/    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        latitude = marker.getPosition().latitude;
        longitude = marker.getPosition().longitude;

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
        activityDetailsBinding.txtDesc.setText(data.get(0).getDish_description());
        activityDetailsBinding.txtPlace.setText(data.get(0).getShop_name());
        activityDetailsBinding.txtMobile.setText(data.get(0).getShop_mobile());
        activityDetailsBinding.txtTime.setText(data.get(0).getShop_timing());
        activityDetailsBinding.txtAddress.setText(data.get(0).getShop_address());
        latitude = Double.parseDouble(data.get(0).getLat());
        longitude = Double.parseDouble(data.get(0).getLng());
        moveMap();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
