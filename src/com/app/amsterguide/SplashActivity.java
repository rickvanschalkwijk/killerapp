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

package com.app.amsterguide;

import com.app.killerapp.R;

import core.databasehandlers.DatabaseLoaderThread;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MotionEvent;

public class SplashActivity extends Activity {
	
	protected int _splashTime = 1500;
	private Thread splashThread;
	private static SplashActivity selfReferance = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); 
		DatabaseLoaderThread databaseLoaderThread = new DatabaseLoaderThread(this);
		Thread thread = new Thread(databaseLoaderThread);
		thread.start();
		
		if(selfReferance == null){
			selfReferance = this;
		}
		
		final SplashActivity sPlashActivity  = this;
		
		splashThread = new Thread(){
			@Override
			public void run(){
				try {
					synchronized (this) {
						if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
							buildAlertNoGPS();
						}
						wait(_splashTime);
					}
				} catch (InterruptedException e) {
					
				}finally{
					Intent intent = new Intent(sPlashActivity, PreLoginActivity.class);
					startActivity(intent);
					finish();
				}
			}
		};
		splashThread.start();
	}
	
	private void buildAlertNoGPS() {
		 final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
		           .setCancelable(false)
		           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		               public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
		                   startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		               }
		           })
		           .setNegativeButton("No", new DialogInterface.OnClickListener() {
		               public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
		                    dialog.cancel();
		               }
		           });
		    final AlertDialog alert = builder.create();
		    alert.show();
	}
	@Override
	public boolean onTouchEvent(MotionEvent event){
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			synchronized (splashThread) {
				//splashThread.notifyAll();
			}
		}
		return true;
	}
	
	public static Context getContext() {
		if (selfReferance != null) {			
			return selfReferance.getApplicationContext();
		}
		return null;
	}
}
