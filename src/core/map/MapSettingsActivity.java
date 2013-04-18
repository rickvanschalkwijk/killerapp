package core.map;

import com.app.killerapp.R;
import com.app.killerapp.R.layout;
import com.app.killerapp.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MapSettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_settings);
		
		final ToggleButton toggleEvents = (ToggleButton) findViewById(R.id.events);
		final ToggleButton toggleCompanions = (ToggleButton) findViewById(R.id.companions);
		final ToggleButton toggleLocations = (ToggleButton) findViewById(R.id.locations);
		final ToggleButton toggleGuides = (ToggleButton) findViewById(R.id.guides);
		
		toggleEvents.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					Log.d("button", "checkt");
				}else{
					Log.d("button", "not checkt");
				}
				
			}
		});
		toggleCompanions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					Log.d("button", "checkt");
				}else{
					Log.d("button", "not checkt");
				}
				
			}
		});
		toggleLocations.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					Log.d("button", "checkt");
				}else{
					Log.d("button", "not checkt");
				}
				
			}
		});
		toggleGuides.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					Log.d("button", "checkt");
				}else{
					Log.d("button", "not checkt");
				}
				
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map_settings, menu);
		return true;
	}

}
