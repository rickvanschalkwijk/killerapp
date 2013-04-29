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

import services.ServicesContactActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.app.killerapp.R;

import core.event.EventList;
import core.map.MapActivity;
import core.map.MapSettingsActivity;
import core.place.PlaceList;

public class MainActivity extends Activity {

	public static final String PREFS_NAME = "LocalPrefs";
	public static boolean startUp = true;
	final Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SharedPreferences settings = getSharedPreferences("LocalPrefs", 0);
		if (!settings.getBoolean("loggedIn", false)){
			Button companionButton = (Button) findViewById(R.id.btn_companions_open);
			companionButton.setEnabled(false);
		}
			
			
	}

	public void openMap(View view) {
		Intent intent = new Intent(this, MapActivity.class);
		startActivity(intent);
	}

	public void openFriend(View view) {
		// SharedPreferences settings = getSharedPreferences("LocalPrefs",
		// 0);

		// if(!settings.getBoolean("loggedInGuest", false))
		// {
		Intent intent = new Intent(this, FriendActivity.class);
		startActivity(intent);
		// }
		// else
		// SetMessage("Not LoggedIn", "Please login to use this feature.");
	}

	public void gotoServices(View view) {
		Intent intent = new Intent(this, ServicesContactActivity.class);
		startActivity(intent);
	}

	public void openEventList(View view) {
		Intent intent = new Intent(this, EventList.class);
		startActivity(intent);
	}

	public void openPlaceList(View view) {
		Intent intent = new Intent(this, PlaceList.class);
		startActivity(intent);
	}
	
	public void openSettings(View view){
		Intent intent = new Intent(this, MapSettingsActivity.class);
		startActivity(intent);
	}

	public void signOut(View view) {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();

		editor.remove("userID");
		editor.remove("token");
		editor.putBoolean("stayLoggedIn", false);
		editor.commit();
		Log.e("user sign out", settings.getString("userID", "Empty"));

		Intent intent = new Intent(this, PreLoginActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		
		finish();
        System.exit(0);
	}

	private void SetMessage(String titel, String message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setTitle(titel);
		alertDialogBuilder.setMessage(message).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
}
