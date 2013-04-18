package core.map;

import com.app.killerapp.R;


import android.R.bool;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.content.SharedPreferences;

public class MapSettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_settings);
		
		// Restore preferences
	    //SharedPreferences settings = getSharedPreferences("MapPref", 0);
	  //  boolean eventsValue = settings.getBoolean("events", false);
		//Log.d("eventvalue", eventsValue + "");
		
		final ToggleButton toggleEvents = (ToggleButton) findViewById(R.id.events);
		final ToggleButton toggleCompanions = (ToggleButton) findViewById(R.id.companions);
		final ToggleButton toggleLocations = (ToggleButton) findViewById(R.id.locations);
		final ToggleButton toggleGuides = (ToggleButton) findViewById(R.id.guides);
		
		toggleEvents.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					setPref("events", isChecked);
				}else{
					setPref("events", isChecked);
				}
			}
		});
		toggleCompanions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					setPref("companions", isChecked);
				}else{
					setPref("companions", isChecked);
				}
			}
		});
		toggleLocations.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					setPref("locations", isChecked);
				}else{
					setPref("locations", isChecked);
				}
			}
		});
		toggleGuides.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					setPref("guides", isChecked);
				}else{
					setPref("guides", isChecked);
				}
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map_settings, menu);
		return true;
	}
	
	public void setPref(String key, boolean value){
		 SharedPreferences preferences = getSharedPreferences("MapPref", 0);
		 SharedPreferences.Editor editor = preferences.edit();
	     editor.putBoolean(key, value); // value to store
	     editor.commit();
	     
	     return;
	}

}
