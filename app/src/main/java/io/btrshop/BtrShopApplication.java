package io.btrshop;

import android.app.Application;
import android.content.Context;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.EstimoteSDK;

import io.btrshop.data.source.api.ApiComponent;
import io.btrshop.data.source.api.ApiModule;
import io.btrshop.data.source.api.DaggerApiComponent;

/**
 * Created by denis on 18/11/16.
 */

public class BtrShopApplication extends Application {

    private static Context mContext;
    private ApiComponent mApiComponent;
    private BeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mApiComponent = DaggerApiComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiModule(new ApiModule(mContext.getResources().getString(R.string.base_url_test)))
                .build();

        // TODO: put your App ID and App Token here
        // You can get them by adding your app on https://cloud.estimote.com/#/apps
        EstimoteSDK.initialize(getApplicationContext(), "hamann-denis-gmail-com-s-y-nqc", "2c217585b54fa97b46a39f1b9f3d603f");
    }

    public static Context getContext(){
        return mContext;
    }

    public ApiComponent getApiComponent() {
        return mApiComponent;
    }
}
