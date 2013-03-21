package core.map;

import java.io.File;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.tileprovider.IRegisterReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;

import com.app.killerapp.R;

import core.map.osmdroid.MBTileProvider;

@SuppressLint({ "NewApi", "ValidFragment" })

public class MapActivity extends Activity implements IRegisterReceiver {
	
	private LocationManager locationManager;
	private MapController mapController;
	private String locationProvider = LocationManager.GPS_PROVIDER;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_map);
		
		// Create the mapView with an MBTileProvider
        DefaultResourceProxyImpl resProxy;
        resProxy = new DefaultResourceProxyImpl(this.getApplicationContext());
 
        //String packageDir = "/com.app.killerapp";
        //TODO: change to other path
        String path = "/mnt/sdcard/osmdroid/";
        File file = new File(path, "amsterdam.mbtiles");
 
        MBTileProvider provider = new MBTileProvider(this, file);
        MapView mapView = new MapView(this,
                provider.getTileSource()
                        .getTileSizePixels(),
                resProxy,
                provider);
        /*
         *TODO: implement the boundbox
        BoundedMapView mapView = new BoundedMapView(this, resProxy, provider);
 
        double north = 52.419150;
        double east  =  4.994745;
        double south = 52.333638;
        double west  =  4.968842;
        BoundingBoxE6 bBox = new BoundingBoxE6(north, east, south, west);
 
        mapView.setScrollableAreaLimit(bBox);
        */
        mapView.setBuiltInZoomControls(true);
 
        // Zoom in and go to Amsterdam
        mapController = mapView.getController();
        mapController.setZoom(10);
        //mapController.animateTo(new GeoPoint(52.378003, 4.899709));
        
        // Get a reference to the LocationManager object.
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0, locationListener);
        
        //Location location = locationManager.getLastKnownLocation(locationProvider);
        //Log.d("Device Latitude", "Last known: " + location.getLatitude());
    	//Log.d("Device Longitude", "Last known: " + location.getLongitude());
        
        //mapController.animateTo(new GeoPoint( location.getLatitude(), location.getLongitude() ));
        
        // Set the MapView as the root View for this Activity; done!
        setContentView(mapView);
	}
	@Override
	protected void onStart() {
		super.onStart();
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if(!gpsEnabled){
			new EnableGpsDialogFragment().show(getFragmentManager(), "enableGpsDialog");
		}
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	    locationManager.removeUpdates(locationListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		
		return true;
	}
	
	private final LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            // the location update.
        	mapController.setZoom(17);
        	mapController.animateTo(new GeoPoint(location.getLatitude(), location.getLongitude()));
        	Log.d("Device Latitude", "" + location.getLatitude());
        	Log.d("Device Longitude", "" + location.getLongitude());
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };
	
	
	 // Method to launch Settings
    private void enableLocationSettings() {
        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(settingsIntent);
    }
	
    private class EnableGpsDialogFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.enable_gps)
                    .setMessage(R.string.enable_gps_dialog)
                    .setPositiveButton(R.string.enable_gps, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            enableLocationSettings();
                        }
                    })
                    .create();
        }
    }
}
