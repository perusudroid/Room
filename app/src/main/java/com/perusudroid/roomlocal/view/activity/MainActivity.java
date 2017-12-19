package com.perusudroid.roomlocal.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.perusudroid.roomlocal.R;
import com.perusudroid.roomlocal.adapter.DishesAdapter;
import com.perusudroid.roomlocal.adapter.listener.DishCallback;
import com.perusudroid.roomlocal.dao.UserDaoModel;
import com.perusudroid.roomlocal.databinding.ActivityLaunchBinding;
import com.perusudroid.roomlocal.db.InjectDB;
import com.perusudroid.roomlocal.model.ViewModelFactory;
import com.perusudroid.roomlocal.model.dto.response.Data;
import com.perusudroid.roomlocal.presenter.MainPresenter;
import com.perusudroid.roomlocal.presenter.ipresenter.IMainPresenter;
import com.perusudroid.roomlocal.view.iview.IMainView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements IMainView, DishCallback {

    private ActivityLaunchBinding activityLaunchBinding;
    private IMainPresenter iMainPresenter;
    private ViewModelFactory mViewModelFactory;
    private UserDaoModel userDaoModel;
    private DishesAdapter dishesAdapter;
    private boolean isFavoriteClicked = false;
    private AlphaAnimation alphaAnimation, alphaAnimationShowIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLaunchBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_launch);
        setToolbar();
        setAssets();

        mViewModelFactory = InjectDB.provideViewModelFactory(this);
        userDaoModel = ViewModelProviders.of(this, mViewModelFactory).get(UserDaoModel.class);
        iMainPresenter = new MainPresenter(this, userDaoModel);
        iMainPresenter.onCreatePresenter(getIntent().getExtras());
    }


    private void setAssets() {
        activityLaunchBinding.inflate(getLayoutInflater());
        activityLaunchBinding.rv.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dishesAdapter = new DishesAdapter(this);
        activityLaunchBinding.rv.recyclerView.setAdapter(dishesAdapter);
        activityLaunchBinding.rv.swipeRefreshLay.setOnRefreshListener(
                () -> iMainPresenter.doFetchApiData()
        );
        alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(700);
        alphaAnimationShowIcon = new AlphaAnimation(0.2f, 1.0f);
        alphaAnimationShowIcon.setDuration(500);
    }


    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        activityLaunchBinding.rv.recyclerView.scheduleLayoutAnimation();
    }


    @Override
    public void onClick(Data data) {

    }

    @Override
    public void onClick(String dish_id, View transitionImage) {

        new Handler().postDelayed(
                () -> DetailsActivity.navigate(MainActivity.this, transitionImage, dish_id)
                , 50);

    }

    @Override
    public void onLikeClicked(View iView) {
        if (iView instanceof ImageView) {
            ImageView ivFav = (ImageView) iView;
            if (!isFavoriteClicked) {
                ivFav.setImageResource(R.drawable.ic_favorite_black_24dp);
                ivFav.startAnimation(alphaAnimationShowIcon);
                ivFav.startAnimation(alphaAnimationShowIcon);
                isFavoriteClicked = true;
            } else {
                ivFav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                ivFav.startAnimation(alphaAnimationShowIcon);
                ivFav.startAnimation(alphaAnimationShowIcon);
                isFavoriteClicked = false;
            }
        }

    }

    @Override
    public void sampleClick() {

        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void refreshRecyclerData(List<Data> newData) {

        if(activityLaunchBinding.rv.swipeRefreshLay.isRefreshing()){
            activityLaunchBinding.rv.swipeRefreshLay.setRefreshing(false);
        }

        if(dishesAdapter != null){
            dishesAdapter.setDishList(newData);
        }
    }
/*

    private void loadSampleData() {

        List<Data> data = new ArrayList<>();

        data.add(new Data(1,"Dec 15,2017","latlng","Pepper Chicken","Fudbowl","Rs Puram, Coimbatore","pics/pizza.png" ));
        data.add(new Data(2,"Dec 27,2017","latlng","Ginger Chicken","House of Pizzas","Saibaba colony, Coimbatore" ,"pics/pizza.png"));

        dishesAdapter = new DishesAdapter(data, this);
        activityLaunchBinding.rv.recyclerView.setAdapter(dishesAdapter);
    }
*/


}
