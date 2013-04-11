package core.map;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.tileprovider.IRegisterReceiver;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
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
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.amsterguide.EventActivity;
import com.app.killerapp.R;

import core.databasehandlers.EventDataSource;
import core.map.osmdroid.BoundedMapView;
import core.map.osmdroid.MBTileProvider;
import core.models.Category;
import core.models.Event;
import core.models.Place;
import core.place.PlaceUtil;

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
	private int defaultZoomLevel = 13;
	private static MapActivity selfReferance = null;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupActionBar();
		 Bundle b = getIntent().getExtras();
	     if(b != null){
	    	 String[] resultArr = b.getStringArray("selectedItems");
	    	 Log.d("Filter result", resultArr + "");
	     }
	     // Create the mapView with an MBTileProvider
        resProxy = new DefaultResourceProxyImpl(this.getApplicationContext());
 
        //String packageDir = "/com.app.killerapp";
        //TODO: change path to Environment.getExternalStorageDirectory().getPath()
        String path = "/mnt/sdcard/osmdroid/";
        File file = new File(path, "amsterdam.mbtiles");
 
        MBTileProvider provider = new MBTileProvider(this, file);
        //4.8653,52.3325,4.9604,52.3839
        
        mapView = new BoundedMapView(this, resProxy, provider);
        double north = 52.388841;
        double east  =  4.964136;
        double south = 52.322969;
        double west  =  4.835695;
        BoundingBoxE6 bBox = new BoundingBoxE6(north, east, south, west);
 
        mapView.setScrollableAreaLimit(bBox);
        
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
 
        // Zoom in and go to Amsterdam
        mapController = mapView.getController();
        mapController.setZoom( defaultZoomLevel );
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        GeoPoint centralStation = new GeoPoint( 52.379211, 4.899426 );
                
        //this location is central station
        mapController.animateTo( centralStation );
        
        // Set the MapView as the root View for this Activity; done!
        setContentView(mapView);
        
        addEvents();
        addLocations();
	}
	
	private void addLocations(){
		ArrayList<Place> places = PlaceUtil.getDummyData();
		
		for( Place place : places ){
			addPlaceMarker( place );
		}
	}
	
	private void addEvents(){
		EventDataSource eventDataSource = new EventDataSource(context);
		eventDataSource.open();
		List<Event> events = eventDataSource.getAllEvents();
		eventDataSource.close();
		
		for( Event event : events ){
			addEventMarker( event );
		}
	}
	
	private void addEventMarker( final Event newEvent ){
		OverlayItem eventOverLayItem = new OverlayItem("Event", "Some event", newEvent.getLocation() );
        Drawable eventMarker = this.getResources().getDrawable(R.drawable.marker);
        eventOverLayItem.setMarker(eventMarker);

        final ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
        items.add(eventOverLayItem);

        ItemizedIconOverlay<OverlayItem> currentLocationOverlay = new ItemizedIconOverlay<OverlayItem>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
        			private Event event = newEvent;
        			
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                    	createEventOverlay(event);
                        return true;
                    }
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return true;
                    }
                }, resProxy);
        this.mapView.getOverlays().add( currentLocationOverlay );
	}
	
	private void addPlaceMarker( final Place newPlace ){
		OverlayItem placeOverLayItem = new OverlayItem("Place", "Some place", newPlace.getLocation() );
        Drawable placeMarker = this.getResources().getDrawable(R.drawable.place_marker);
        placeOverLayItem.setMarker(placeMarker);

        final ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
        items.add(placeOverLayItem);

        ItemizedIconOverlay<OverlayItem> currentPlaceOverLayItem = new ItemizedIconOverlay<OverlayItem>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
        			private Place place = newPlace;
        			
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                    	createPlaceOverlay(place);
                        return true;
                    }
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return true;
                    }
                }, resProxy);
        this.mapView.getOverlays().add( currentPlaceOverLayItem );
	}
	
	private void createPlaceOverlay( final Place place ){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle( place.getName() );
		alertDialogBuilder
			.setMessage( place.getDescription() )
			.setCancelable(true)
			.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int id) {
	                //user cancels
	            	//return to underlaing activity
	            }
	        })
			.setPositiveButton( R.string.event_more_information ,new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog,int id) 
				{
					Toast.makeText(context, R.string.event_more_information, Toast.LENGTH_SHORT).show();
				}
			});
			
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
	}
	
	private void createEventOverlay( final Event event){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle( event.getTitle() );
		alertDialogBuilder
			.setMessage( Html.fromHtml( event.getDescription() ) )
			.setCancelable(true)
			.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int id) {
	                //user cancels
	            	//return to underlaing activity
	            }
	        })
			.setPositiveButton( R.string.event_more_information ,new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog,int id) 
				{
					Intent intent = new Intent(getApplicationContext(), EventActivity.class );
					intent.putExtra(Event.EXTRA, event);
					startActivity(intent);
					Toast.makeText(context, R.string.event_more_information, Toast.LENGTH_SHORT).show();
				}
			});
			
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
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
			final ArrayList<Category> categories = new ArrayList<Category>();
			
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
					if(mSelectedItems.isEmpty()){
						Toast.makeText(context, "You did'nt select anything!", Toast.LENGTH_SHORT).show();
					}else{
						Intent intent = new Intent(context, MapActivity.class);
						Bundle b = new Bundle();
						b.putStringArrayList("selectedItems", mSelectedItems);
						intent.putExtras(b);
						finish();
						startActivity(intent);
					}
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
		
		private OverlayItem myLocationOverlayItem;
		private Drawable myCurrentLocationMarker = context.getResources().getDrawable(R.drawable.bluedot);
		private ItemizedIconOverlay<OverlayItem> currentLocationOverlay;
		private int arrayLocation = 0;

        @Override
        public void onLocationChanged(Location location) {
        	if( myLocationOverlayItem == null ){
        		currentLocation = location;
            	
            	myLocationOverlayItem = new OverlayItem("Here", "Current Position", new GeoPoint(currentLocation));
                
                myLocationOverlayItem.setMarker(myCurrentLocationMarker);

                final ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
                items.add(myLocationOverlayItem);

                currentLocationOverlay = new ItemizedIconOverlay<OverlayItem>(items, null, resProxy );
                mapView.getOverlays().add(currentLocationOverlay);
                mapView.invalidate();
                arrayLocation = mapView.getOverlays().size() - 1;
        	} else {
        		mapView.getOverlays().remove( arrayLocation );
        		mapView.invalidate();
        		
        		currentLocation = location;
        		myLocationOverlayItem = new OverlayItem("Here", "Current Position", new GeoPoint(currentLocation));
                myLocationOverlayItem.setMarker(myCurrentLocationMarker);

                final ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
                items.add(myLocationOverlayItem);

                ItemizedIconOverlay<OverlayItem> currentLocationOverlay = new ItemizedIconOverlay<OverlayItem>(items, null, resProxy );
                mapView.getOverlays().add(currentLocationOverlay);
                
                arrayLocation = mapView.getOverlays().size() - 1;
        	}
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
    
    /**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	public static Context getContext(){
		if (selfReferance != null){
			return selfReferance.getApplicationContext();
		}
		return null;
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