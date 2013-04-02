package core.map;

import java.io.File;
import java.util.ArrayList;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.tileprovider.IRegisterReceiver;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.app.killerapp.R;

import core.map.osmdroid.BoundedMapView;
import core.map.osmdroid.MBTileProvider;

@SuppressLint({ "NewApi", "ValidFragment" })

public class MapActivity extends Activity implements IRegisterReceiver {
	
	private LocationManager locationManager;
	private MapController mapController;
	private String locationProvider = LocationManager.GPS_PROVIDER;
	private GeoUpdateHandler locationListener;
	private ArrayList mSelectedItems;
	final Context context = this;
	private Location currentLocation;
	private DefaultResourceProxyImpl resProxy;
	private BoundedMapView mapView;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Create the mapView with an MBTileProvider
        resProxy = new DefaultResourceProxyImpl(this.getApplicationContext());
 
        //String packageDir = "/com.app.killerapp";
        //TODO: change to other path
        String path = "/mnt/sdcard/osmdroid/";
        File file = new File(path, "amsterdam.mbtiles");
 
        MBTileProvider provider = new MBTileProvider(this, file);
                
        mapView = new BoundedMapView(this, resProxy, provider);
        double north = 52.388841;
        double east  =  4.964136;
        double south = 52.322969;
        double west  =  4.835695;
        BoundingBoxE6 bBox = new BoundingBoxE6(north, east, south, west);
 
        mapView.setScrollableAreaLimit(bBox);
        
        mapView.setBuiltInZoomControls(true);
 
        // Zoom in and go to Amsterdam
        mapController = mapView.getController();
        mapController.setZoom(12);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        GeoPoint centralStation = new GeoPoint( 52.379211, 4.899426 );
                
        //this location is central station
        mapController.animateTo( centralStation );
        
        // Set the MapView as the root View for this Activity; done!
        setContentView(mapView);
        
        addMarker( centralStation );
	}
	
	private void addMarker( GeoPoint location ){
		OverlayItem myLocationOverlayItem = new OverlayItem("Here", "Current Position", location );
        Drawable myCurrentLocationMarker = this.getResources().getDrawable(R.drawable.marker);
        myLocationOverlayItem.setMarker(myCurrentLocationMarker);

        final ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
        items.add(myLocationOverlayItem);

        ItemizedIconOverlay currentLocationOverlay = new ItemizedIconOverlay<OverlayItem>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        return true;
                    }
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return true;
                    }
                }, resProxy);
        this.mapView.getOverlays().add( currentLocationOverlay );
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		locationListener = new GeoUpdateHandler();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0, locationListener);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		final boolean gpsEnabled = locationManager.isProviderEnabled( locationProvider );
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
	
	  public boolean onOptionsItemSelected(MenuItem item){
			switch(item.getItemId()){
				case R.id.action_filter:
					filterDialog();
					return true;
				case R.id.action_myposition:
					try {
						mapController.animateTo(new GeoPoint(currentLocation.getLatitude(), currentLocation.getLongitude()));
						mapController.setZoom(17);
					} catch( Exception e){
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
	        			alertDialogBuilder.setTitle("Location error");
	        			alertDialogBuilder
	        				.setMessage("Location not found")
	        				.setCancelable(false)
	        				.setPositiveButton("OK",new DialogInterface.OnClickListener() 
	        				{
	        					public void onClick(DialogInterface dialog,int id) 
	        					{
	        						dialog.cancel();
	        					}
	        				});
	        				
	        				AlertDialog alertDialog = alertDialogBuilder.create();
	        				alertDialog.show();
					}
						
					
					return true;
				case R.id.action_help:
					Intent helpIntent = new Intent(this, util.HelpActivity.class);
					startActivity(helpIntent);
					return true;
				default:
		            return super.onOptionsItemSelected(item);
			}
		}
		
		public Dialog filterDialog(){
			mSelectedItems = new ArrayList();
			
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			
			builder.setTitle(R.string.categories_title)
			.setMultiChoiceItems(R.array.categories, null, 
					new DialogInterface.OnMultiChoiceClickListener() {			
						@Override
						public void onClick(DialogInterface dialog, int which, boolean isChecked) {
							if(isChecked){
								mSelectedItems.add(which);
							}else if(mSelectedItems.contains(which)){
								mSelectedItems.remove(Integer.valueOf(which));
							}
						}
					})
			.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//save check items somewhere
					//or return to underlaning activity
				}
			})
			.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int id) {
	                //user cancels
	            	//return to underlaing activity
	            }
	        });
			builder.create();
			
			return builder.show();
		}
	
	public class GeoUpdateHandler implements LocationListener  {

        @Override
        public void onLocationChanged(Location location) {
        	currentLocation = location;
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