package ch.avendia.passabene.gps;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by Markus on 24.01.2015.
 */
public class GpsProvider {

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public GpsProvider(Context context) {
        locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * @return the last know best location
     */
    public Location getLastBestLocation() {
        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        long GPSLocationTime = 0;
        if (null != locationGPS) {
            GPSLocationTime = locationGPS.getTime();
        }

        long NetLocationTime = 0;

        if (null != locationNet) {
            NetLocationTime = locationNet.getTime();
        }

        if (0 < GPSLocationTime - NetLocationTime) {
            return locationGPS;
        } else {
            return locationNet;
        }
    }

}
