package core.map;

import java.io.File;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.tileprovider.IRegisterReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.app.killerapp.R;

import core.map.osmdroid.MBTileProvider;

public class MapActivity extends Activity implements IRegisterReceiver{
	
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
 
        mapView.setBuiltInZoomControls(true);
 
        // Zoom in and go to Amsterdam
        MapController controller = mapView.getController();
        controller.setZoom(12);
        controller.animateTo(new GeoPoint(52.378003, 4.899709));
 
        // Set the MapView as the root View for this Activity; done!
        setContentView(mapView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		
		return true;
	}

}
