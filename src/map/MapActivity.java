package map;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import com.app.killerapp.R;
import com.app.killerapp.R.id;
import com.app.killerapp.R.layout;
import com.app.killerapp.R.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class MapActivity extends Activity {
	
	private MapView         mMapView;
    private MapController   mMapController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		MapView mapView = new MapView(this, 256); //constructor
        mapView.setClickable(true);
        mapView.setBuiltInZoomControls(true);
        setContentView(mapView); //displaying the MapView
        mapView.getController().setZoom(15); //set initial zoom-level, depends on your need
        mapView.getController().setCenter(new GeoPoint(52.378003, 4.899709)); //This point is in Amsterdam Centraal Station, Netherlands. You should select a point in your map or get it from user's location.
        mapView.setUseDataConnection(false); //keeps the mapView from loading online tiles using network connection.
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		
		return true;
	}

}
