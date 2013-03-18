/*
 * Copyright 2012 Hogeschool van Amsterdam.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.app.killerapp;

import map.MapActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.d("Blaat", Environment.getExternalStorageDirectory().getAbsolutePath() );
	}
	
	public void openMap(View view){
		Intent intent = new Intent(this, MapActivity.class);
		startActivity(intent);
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

}
