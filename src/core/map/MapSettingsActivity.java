package core.map;

import com.app.killerapp.R;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.content.SharedPreferences;

public class MapSettingsActivity extends Activity {
	
	protected ToggleButton toggleCompanions;
	protected ToggleButton toggleLocations;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_settings);
		toggleCompanions = (ToggleButton) findViewById(R.id.companions);
		toggleLocations = (ToggleButton) findViewById(R.id.locations);
		setToggleButtons();
		
		
		toggleCompanions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					saveBooleanInSP("companions", isChecked);
				}else{
					saveBooleanInSP("companions", isChecked);
				}
			}
		});
		toggleLocations.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					saveBooleanInSP("locations", isChecked);
				}else{
					saveBooleanInSP("locations", isChecked);
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
	
	public void setToggleButtons(){
		toggleLocations.setChecked(getBooleanFromSP("locations"));
		toggleCompanions.setChecked(getBooleanFromSP("companions"));
	}
	
	/**
	 * Get the map settings from SP file
	 * @param String key
	 * @return boolean value
	 */
	public boolean getBooleanFromSP(String key){
		 SharedPreferences preferences = getSharedPreferences("MapPref", 0);
		 return preferences.getBoolean(key, true);
	}
	
	/**
	 * Save boolean to SP file
	 * @param String key
	 * @param boolean value
	 * @return void
	 */
	public void saveBooleanInSP(String key, boolean value){
		 SharedPreferences preferences = getSharedPreferences("MapPref", 0);
		 SharedPreferences.Editor editor = preferences.edit();
	     editor.putBoolean(key, value); // value to store
	     editor.commit();
	     
	     return;
	}

}
