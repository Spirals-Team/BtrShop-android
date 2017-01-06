package io.btrshop.products;

import android.content.Context;
import android.util.Log;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.eddystone.Eddystone;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import io.btrshop.products.domain.model.BeaconData;
import io.btrshop.products.domain.model.BeaconObject;

/**
 * Created by charlie on 2/12/16.
 */

public class ProductsBeacon {

    private Context pbContext;

    private final double COEFF1 = 0.42093;
    private final double COEFF2 = 6.9476;
    private final double COEFF3 = 0.54992;

    // Components
    private BeaconManager beaconManager;
    private Region region;
    private List<BeaconObject> listBeacons;

    protected final static String TAG = "ProductsFragment";


    public ProductsBeacon(Context context){
        pbContext = context;
        listBeacons = new ArrayList<>();
    }

    public void scanBeacon(){

        beaconManager = new BeaconManager(pbContext);
        beaconManager.setEddystoneListener(new BeaconManager.EddystoneListener() {
            @Override
            public void onEddystonesFound(List<Eddystone> list) {

            }
        });
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    listBeacons = new ArrayList<BeaconObject>();
                    Log.d(TAG, "Nombres d'estimote captés: " + list.size());
                    for(Beacon beac : list){
                        Log.d(TAG, "Estimote uuid : " + beac.getProximityUUID());
                        Log.d(TAG, "Estimote distance : " + calculateDistance(beac.getMeasuredPower(), beac.getRssi()));
                        listBeacons.add(new BeaconObject(
                                        calculateDistance(beac.getMeasuredPower(), beac.getRssi()),
                                        new BeaconData(beac.getProximityUUID().toString())
                                        ));
                    }
                }
            }
        });

        region = new Region("ranged region", null, null, null);
        Log.i(TAG, "REGION : "  + region.getProximityUUID());

    }


    public double calculateDistance(int txPower, double rssi) {
        if (rssi == 0) {
            return -1.0; // if we cannot determine accuracy, return -1.
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

    public List<BeaconObject> getListBeacons(Map<String, BeaconObject> mapBeacons){
        Log.d("MAP", ""+mapBeacons.size());
        Log.d("LIST", ""+this.listBeacons.size());

        List<BeaconObject> returnList = new ArrayList<>();

        if(this.listBeacons.isEmpty() || this.listBeacons == null)
            this.scanBeacon();

        int nbBeaconCorrect = 0;
        for (BeaconObject b : this.listBeacons ){
            double distance = b.getDistance();
            Log.d("BEACON", b.getData().getUuid());
            BeaconObject beacon = mapBeacons.get(b.getData().getUuid().toUpperCase());
            if(beacon != null) {
                Log.d("DISTANCE", distance+ "m");
                nbBeaconCorrect++;
                beacon.setDistance(distance);
                returnList.add(beacon);
            }
        }
        Log.d("NBBEACONCORRECT", "il y a : " + nbBeaconCorrect + " beacons captés corrects");
        if(nbBeaconCorrect < 2)
            return null;
        return returnList;
    }

}
