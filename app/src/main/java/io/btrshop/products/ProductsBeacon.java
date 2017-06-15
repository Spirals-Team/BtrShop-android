package io.btrshop.products;

import android.content.Context;
import android.util.Log;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.eddystone.Eddystone;

import java.util.ArrayList;
import java.util.List;

import io.btrshop.products.domain.model.BeaconJson;

/**
 * Created by charlie on 2/12/16.
 */

public class ProductsBeacon {

    private Context pbContext;

    // Constants use to calculate the distance between the phone and the beacons
    private final double COEFF1 = 0.42093;
    private final double COEFF2 = 6.9476;
    private final double COEFF3 = 0.54992;

    // Components
    private BeaconManager beaconManager;
    private Region region;
    private List<Beacon> listBeacons;

    protected final static String TAG = "ProductsFragment";


    public ProductsBeacon(Context context){
        pbContext = context;
        listBeacons = new ArrayList<>();
    }

    public void scanBeacon(){

        beaconManager = new BeaconManager(pbContext);

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    Log.d(TAG, "Nombres d'estimote captés: " + list.size());
                    for(Beacon beac : list){
                        Log.d(TAG, "Estimote uuid : " + beac.getProximityUUID());
                        try {
                            Log.d(TAG, "Estimote distance : " + calculateDistance(beac.getMeasuredPower(), beac.getRssi()));
                        } catch (Exception e) {
                            Log.d(TAG, "No  distance for : " + beac.getProximityUUID());
                        }
                    }
                    listBeacons = list;
                    Log.d(TAG, "ListBeacons : "+listBeacons.size() );
                }
            }
        });

        region = new Region("ranged region", null, null, null);
        Log.i(TAG, "REGION : "  + region.getProximityUUID());

    }

    // more information about the calcul : https://altbeacon.github.io/android-beacon-library/distance-calculations.html
    public double calculateDistance(int txPower, double rssi) throws Exception {
        if (rssi == 0) {
            throw new Exception("We cannot determine accuracy");
        }

        double ratio = rssi*1.0/txPower;
        double distance;
        if (ratio < 1.0) {
            distance =  Math.pow(ratio,10);
        }
        else {
            distance =  (COEFF1)*Math.pow(ratio,COEFF2) + COEFF3;
        }
        return distance;
    }

    public void connect(){
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
    }

    public void stop(){
        beaconManager.stopRanging(region);
    }

    public List<BeaconJson> getListBeacons(){

        Log.d("LIST", ""+this.listBeacons.size());
        List<BeaconJson> returnList = new ArrayList<>();

        if(this.listBeacons.isEmpty() || this.listBeacons == null)
            this.scanBeacon();

        for (Beacon b : this.listBeacons ){

            double distance = 0;
            // If we can't determine an accuracy with the beacon, we don't add it to the list we return.
            try {
                distance = calculateDistance(b.getMeasuredPower(), b.getRssi());
                returnList.add(new BeaconJson(b.getProximityUUID().toString(), distance));
            } catch (Exception e) {
                Log.d("PRODUCTBEACON", "We cannot determine an accuracy with the beacon : "+ b.getProximityUUID());
            }

        }
        return returnList;
    }

}
