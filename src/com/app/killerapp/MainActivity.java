package com.app.killerapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	@Override
	protected void onStart(){
		super.onStart();
		
	}
	
	@Override 
	protected void onPause(){
		super.onPause();
		
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
	} 
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
