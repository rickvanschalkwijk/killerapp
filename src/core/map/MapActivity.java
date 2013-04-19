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
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.amsterguide.EventActivity;
import com.app.amsterguide.loaders.FriendLoader;
import com.app.killerapp.R;

import core.connection.RESTSocialService;
import core.databasehandlers.EventDataSource;
import core.databasehandlers.PlaceDataSource;
import core.map.osmdroid.BoundedMapView;
import core.map.osmdroid.MBTileProvider;
import core.models.Event;
import core.models.Friendship;
import core.models.Place;
import core.models.User;
import core.place.PlaceUtil;

@SuppressLint({ "NewApi", "ValidFragment" })
public class MapActivity extends FragmentActivity implements IRegisterReceiver,
		LoaderCallbacks<List<Friendship>> {

	private LocationManager locationManager;
	private MapController mapController;
	private String locationProvider = LocationManager.GPS_PROVIDER;
	private GeoUpdateHandler locationListener;
	public ArrayList<Integer> mSelectedItems = new ArrayList<Integer>();;
	final Context context = this;
	private Location currentLocation;
	private DefaultResourceProxyImpl resProxy;
	private BoundedMapView mapView;
	private int defaultZoomLevel = 13;
	private static MapActivity selfReferance = null;
	public ArrayList<String> selectedCategoryIds;
	public String[] categories = { "music", "art", "nightlife" };
	private List<Friendship> friendships;
	private long userId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupActionBar();

		// Create the mapView with an MBTileProvider
		resProxy = new DefaultResourceProxyImpl(this.getApplicationContext());

		// String packageDir = "/com.app.killerapp";
		// TODO: change path to
		// Environment.getExternalStorageDirectory().getPath()
		String path = "/mnt/sdcard/osmdroid/";
		File file = new File(path, "amsterdam.mbtiles");

		MBTileProvider provider = new MBTileProvider(this, file);

		mapView = new BoundedMapView(this, resProxy, provider);
		double north = 52.388841;
		double east = 4.964136;
		double south = 52.322969;
		double west = 4.835695;
		BoundingBoxE6 bBox = new BoundingBoxE6(north, east, south, west);

		mapView.setScrollableAreaLimit(bBox);

		mapView.setBuiltInZoomControls(true);
		mapView.setMultiTouchControls(true);

		// Zoom in and go to Amsterdam
		mapController = mapView.getController();
		mapController.setZoom(defaultZoomLevel);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		GeoPoint centralStation = new GeoPoint(52.379211, 4.899426);

		// this location is central station
		if (getIntent().getSerializableExtra("event") != null) {
			Event event = (Event) getIntent().getSerializableExtra("event");
			mapController.animateTo(event.getLocation());
			this.createEventOverlay(event);
		} else if (getIntent().getSerializableExtra("place") != null) {
			Place place = (Place) getIntent().getSerializableExtra("place");
			mapController.animateTo(place.getLocation());
		} else {
			mapController.animateTo(centralStation);
		}
		// Set the MapView as the root View for this Activity; done!
		setContentView(mapView);
		addMarkers();

		getSupportLoaderManager().initLoader(0, null, this);
	}

	private void addLocations() {
		PlaceDataSource placeDataSource = new PlaceDataSource(context);
		placeDataSource.open();
		List<Place> places = placeDataSource.getAllPlaces();
		placeDataSource.close();

		for (Place place : places) {
			addPlaceMarker(place);
		}
	}

	private void addFilterEvents(ArrayList<String> filter) {
		EventDataSource eventDataSource = new EventDataSource(context);
		eventDataSource.open();
		List<Event> events = eventDataSource.getAllEvents();
		eventDataSource.close();

		for (Event event : events) {
			if (filter.contains(event.getCategory())) {
				addEventMarker(event);
			}
		}
	}

	private void addEvents() {
		EventDataSource eventDataSource = new EventDataSource(context);
		eventDataSource.open();
		List<Event> events = eventDataSource.getAllEvents();
		eventDataSource.close();

		for (Event event : events) {
			addEventMarker(event);
		}
	}

	private void addFriends() {
		for (Friendship friendship : friendships) {
			addFriendMarker(friendship);
		}

		mapView.invalidate();
	}

	private void sendLocationToFriends() {
		if (currentLocation != null) {
			RESTSocialService restSocialService = new RESTSocialService();
			SharedPreferences settings = getSharedPreferences("LocalPrefs", 0);
			long userId = Long.valueOf(settings.getString("userID", "0"))
					.longValue();
			String authToken = settings.getString("token", "letmein");
			Double longitude = currentLocation.getLongitude();
			Double latitude = currentLocation.getLatitude();
			for (Friendship friendship : friendships) {
				restSocialService.setFriendshipCoordinates(userId, friendship,
						authToken, latitude, longitude, context);
			}
			Toast.makeText(context, "Location was send to friends",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(context, "Location was not found",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void addFriendMarker(final Friendship friendship) {
		final User friend = friendship.getOtherUser(userId);
		GeoPoint friendLocation = new GeoPoint(friend.getLatitude(),
				friend.getLongtitude());
		OverlayItem friendOverLayItem = new OverlayItem("Friendship",
				"Some friend", friendLocation);
		Drawable friendMarker = this.getResources().getDrawable(
				R.drawable.friend_marker);
		friendOverLayItem.setMarker(friendMarker);

		final ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
		items.add(friendOverLayItem);

		ItemizedIconOverlay<OverlayItem> currentLocationOverlay = new ItemizedIconOverlay<OverlayItem>(
				items,
				new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
					private User otherFriend = friend;

					public boolean onItemSingleTapUp(final int index,
							final OverlayItem item) {
						Toast.makeText(context, friend.getUsername(),
								Toast.LENGTH_SHORT).show();
						return true;
					}

					public boolean onItemLongPress(final int index,
							final OverlayItem item) {
						return true;
					}
				}, resProxy);
		this.mapView.getOverlays().add(currentLocationOverlay);
	}

	private void addEventMarker(final Event newEvent) {
		OverlayItem eventOverLayItem = new OverlayItem("Event", "Some event",
				newEvent.getLocation());
		Drawable eventMarker = this.getResources().getDrawable(
				R.drawable.marker);
		eventOverLayItem.setMarker(eventMarker);

		final ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
		items.add(eventOverLayItem);

		ItemizedIconOverlay<OverlayItem> currentLocationOverlay = new ItemizedIconOverlay<OverlayItem>(
				items,
				new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
					private Event event = newEvent;

					public boolean onItemSingleTapUp(final int index,
							final OverlayItem item) {
						createEventOverlay(event);
						return true;
					}

					public boolean onItemLongPress(final int index,
							final OverlayItem item) {
						return true;
					}
				}, resProxy);
		this.mapView.getOverlays().add(currentLocationOverlay);
	}

	private void addPlaceMarker(final Place newPlace) {
		OverlayItem placeOverLayItem = new OverlayItem("Place", "Some place",
				newPlace.getLocation());
		Drawable placeMarker = this.getResources().getDrawable(
				R.drawable.place_marker);
		placeOverLayItem.setMarker(placeMarker);

		final ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
		items.add(placeOverLayItem);

		ItemizedIconOverlay<OverlayItem> currentPlaceOverLayItem = new ItemizedIconOverlay<OverlayItem>(
				items,
				new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
					private Place place = newPlace;

					public boolean onItemSingleTapUp(final int index,
							final OverlayItem item) {
						createPlaceOverlay(place);
						return true;
					}

					public boolean onItemLongPress(final int index,
							final OverlayItem item) {
						return true;
					}
				}, resProxy);
		this.mapView.getOverlays().add(currentPlaceOverLayItem);
	}

	private void createPlaceOverlay(final Place place) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setTitle(place.getName());
		alertDialogBuilder
				.setMessage(place.getDescription())
				.setCancelable(true)
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								// user cancels
								// return to underlaing activity
							}
						})
				.setPositiveButton(R.string.event_more_information,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Toast.makeText(context,
										R.string.event_more_information,
										Toast.LENGTH_SHORT).show();
							}
						});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	private void createEventOverlay(final Event event) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setTitle(event.getTitle());
		alertDialogBuilder
				.setMessage(Html.fromHtml(event.getDescription()))
				.setCancelable(true)
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								// user cancels
								// return to underlaing activity
							}
						})
				.setPositiveButton(R.string.event_more_information,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent intent = new Intent(
										getApplicationContext(),
										EventActivity.class);
								intent.putExtra(Event.EXTRA, event);
								startActivity(intent);
								Toast.makeText(context,
										R.string.event_more_information,
										Toast.LENGTH_SHORT).show();
							}
						});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	public void addMarkers() {
		if (getBooleanFromSP("events")) {
			addEvents();
		}
		if (getBooleanFromSP("locations")) {
			addLocations();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		locationListener = new GeoUpdateHandler();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				100, 0, locationListener);
	}

	@Override
	protected void onStart() {
		super.onStart();
		addMarkers();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		final boolean gpsEnabled = locationManager
				.isProviderEnabled(locationProvider);
		if (!gpsEnabled) {
			new EnableGpsDialogFragment().show(getFragmentManager(),
					"enableGpsDialog");
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

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_filter:
			filterDialog();
			return true;
		case R.id.action_myposition:
			try {
				mapController.setZoom(17);
				mapController.animateTo(new GeoPoint(currentLocation
						.getLatitude(), currentLocation.getLongitude()));
			} catch (Exception e) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);
				alertDialogBuilder.setTitle("Location error");
				alertDialogBuilder
						.setMessage("Location not found")
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
			return true;
		case R.id.action_sendmyposition:
			sendLocationToFriends();
			return true;
		case R.id.action_map_settings:
			Intent mapSettingsIntent = new Intent(this,
					core.map.MapSettingsActivity.class);
			startActivity(mapSettingsIntent);
			return true;
		case R.id.action_help:
			Intent helpIntent = new Intent(this, util.HelpActivity.class);
			startActivity(helpIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public boolean[] getSelectedFilter(ArrayList<Integer> selectedItems) {
		int size = categories.length;
		boolean[] isSelected = new boolean[size];
		int i = 0;
		for (Integer checkItem : selectedItems) {
			// String selected = categories[checkItem];
			if (checkItem == null) {
				isSelected[i] = false;
			} else {
				isSelected[i] = true;
			}
			i++;
		}
		return isSelected;
	}

	public Dialog filterDialog() {
		boolean[] booleans = getSelectedFilter(mSelectedItems);
		for (Boolean bool : booleans) {
			Log.d("bool", bool + "");
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(R.string.categories_title)
				.setMultiChoiceItems(categories, booleans,
						new DialogInterface.OnMultiChoiceClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which, boolean isChecked) {
								if (isChecked) {
									mSelectedItems.add(which);
								} else if (mSelectedItems.contains(which)) {
									mSelectedItems.remove(Integer
											.valueOf(which));
								}
							}
						})
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (mSelectedItems.isEmpty()) {
									Toast.makeText(context,
											"You did'nt select anything!",
											Toast.LENGTH_SHORT).show();
								} else {
									mapView.getOverlays().clear();
									mapView.invalidate();

									ArrayList<String> selectedCategoryIds = new ArrayList<String>();

									for (Integer selectedItem : mSelectedItems) {
										String selectedCategory = categories[selectedItem];
										if (selectedCategory != null) {
											selectedCategoryIds
													.add(selectedCategory);
										}
									}
									addFilterEvents(selectedCategoryIds);
									addLocations();
								}
							}
						})
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								// user cancels
								// return to underlaing activity
							}
						});
		builder.create();

		return builder.show();
	}

	public class GeoUpdateHandler implements LocationListener {

		private OverlayItem myLocationOverlayItem;
		private Drawable myCurrentLocationMarker = context.getResources()
				.getDrawable(R.drawable.bluedot);
		private ItemizedIconOverlay<OverlayItem> currentLocationOverlay;
		private int arrayLocation = 0;

		@Override
		public void onLocationChanged(Location location) {
			if (myLocationOverlayItem == null) {
				currentLocation = location;

				myLocationOverlayItem = new OverlayItem("Here",
						"Current Position", new GeoPoint(currentLocation));

				myLocationOverlayItem.setMarker(myCurrentLocationMarker);

				final ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
				items.add(myLocationOverlayItem);

				currentLocationOverlay = new ItemizedIconOverlay<OverlayItem>(
						items, null, resProxy);
				mapView.getOverlays().add(currentLocationOverlay);
				mapView.invalidate();
				arrayLocation = mapView.getOverlays().size() - 1;
			} else {
				mapView.getOverlays().remove(arrayLocation);
				mapView.invalidate();

				currentLocation = location;
				myLocationOverlayItem = new OverlayItem("Here",
						"Current Position", new GeoPoint(currentLocation));
				myLocationOverlayItem.setMarker(myCurrentLocationMarker);

				final ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
				items.add(myLocationOverlayItem);

				ItemizedIconOverlay<OverlayItem> currentLocationOverlay = new ItemizedIconOverlay<OverlayItem>(
						items, null, resProxy);
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
		Intent settingsIntent = new Intent(
				Settings.ACTION_LOCATION_SOURCE_SETTINGS);
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

	public static Context getContext() {
		if (selfReferance != null) {
			return selfReferance.getApplicationContext();
		}
		return null;
	}

	/**
	 * Get the map settings from SP file
	 * 
	 * @param String
	 *            key
	 * @return boolean value
	 */
	public boolean getBooleanFromSP(String key) {
		SharedPreferences preferences = getSharedPreferences("MapPref", 0);
		return preferences.getBoolean(key, false);
	}

	private class EnableGpsDialogFragment extends DialogFragment {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return new AlertDialog.Builder(getActivity())
					.setTitle(R.string.enable_gps)
					.setMessage(R.string.enable_gps_dialog)
					.setPositiveButton(R.string.enable_gps,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									enableLocationSettings();
								}
							}).create();
		}
	}

	@Override
	public Loader<List<Friendship>> onCreateLoader(int id, Bundle args) {
		SharedPreferences settings = getSharedPreferences("LocalPrefs", 0);
		userId = Long.valueOf(settings.getString("userID", "0")).longValue();
		String authToken = settings.getString("token", "letmein");
		return new FriendLoader(getApplicationContext(), userId, authToken,
				"APPROVED");
	}

	@Override
	public void onLoadFinished(Loader<List<Friendship>> loader,
			List<Friendship> result) {
		friendships = result;
		addFriends();
	}

	@Override
	public void onLoaderReset(Loader<List<Friendship>> arg0) {
		// TODO Auto-generated method stub

	}

}