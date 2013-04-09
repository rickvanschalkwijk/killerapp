package com.app.killerapp;

import core.connection.killerbone.AuthenticationService;
import core.connection.killerbone.AuthenticationService.AuthToken;
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
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

	final Context context = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
		registerScreen.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						RegistrationActivity.class);
				startActivity(i);
			}
		});
		Button login = (Button) findViewById(R.id.btnLogin);
		login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				EditText user = (EditText) findViewById(R.id.insertedUsername);
				String username = user.getText().toString();
				EditText pass = (EditText) findViewById(R.id.insertedPassword);
				String password = pass.getText().toString();
				SharedPreferences settings = getSharedPreferences("LocalPrefs",
						0);
				// email: onno@valkering.nl
				// password: valkering
				boolean isAuthenticated = authenticateUser(username, password);
				if (isAuthenticated)
				{
					SharedPreferences.Editor editor = settings.edit();
					editor.putBoolean("loggedIn", true);
					editor.commit();
					Intent i = new Intent(getApplicationContext(),
							MainActivity.class);
					startActivity(i);
				}
				else
				{
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							context);
					alertDialogBuilder.setTitle("Error");
					alertDialogBuilder
							.setMessage("Wrong username or password!")
							.setCancelable(false)
							.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											dialog.cancel();
										}
									});

					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
				}
			}
		});
		Button loginGuest = (Button) findViewById(R.id.btnGuest);
		loginGuest.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				SharedPreferences settings = getSharedPreferences("LocalPrefs",
						0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean("loggedInGuest", true);
				editor.commit();
				Intent i = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(i);
				finish();
			}
		});
	}
	/*
	 * If back-button is pressed goto homescreen.
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent backIntent = new Intent(Intent.ACTION_MAIN);
		backIntent.addCategory(Intent.CATEGORY_HOME);
		startActivity(backIntent);
		finish();
	}
	
	private boolean authenticateUser(String email, String password)
	{
		AuthenticationService authService = new AuthenticationService();
		AuthToken authToken = authService.authenticateWithCredentials(email, password);
		
		if (authToken != null)
		{
			String userId = authToken.userId;
			String token = authToken.token;
			
			Log.d("Authentication", "userId: " + userId + " & authToken: " + token);
			SharedPreferences settings = getSharedPreferences("LocalPrefs",
					0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("userID", userId);
			editor.putString("token", token);
			editor.commit();
			return true;
		}
		return false;
	}
}