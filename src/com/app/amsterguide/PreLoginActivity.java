package com.app.amsterguide;

import com.app.killerapp.R;
import com.app.killerapp.R.layout;
import com.app.killerapp.R.menu;

import core.connection.killerbone.AuthenticationService;
import core.connection.killerbone.AuthenticationService.AuthToken;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class PreLoginActivity extends Activity {
	private Context context = this;
	public static final String PREFS_NAME = "LocalPrefs";
	public static boolean startUp = true;
	private static PreLoginActivity selfReferance = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		if(selfReferance == null){
			selfReferance = this;
		}

		if (settings.getBoolean("stayLoggedIn", false)) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
		}

		setContentView(R.layout.activity_pre_login);

		LinearLayout loginLayout = (LinearLayout) findViewById(R.id.loginLayout);
		loginLayout.setVisibility(LinearLayout.GONE);

		// Show the Up button in the action bar.
		setupActionBar();
		hideActionBar();

		Button showLogin = (Button) findViewById(R.id.btn_show_login);
		Button logginAsGuest = (Button) findViewById(R.id.btn_login_as_guest);
		Button register = (Button) findViewById(R.id.btn_register);

		showLogin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				LinearLayout loginLayout = (LinearLayout) findViewById(R.id.loginLayout);
				loginLayout.setVisibility(LinearLayout.VISIBLE);
			}
		});

		logginAsGuest.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferences settings = getSharedPreferences("LocalPrefs",
						0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean("loggedInGuest", true);
				editor.putBoolean("loggedIn", false);
				editor.commit();
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(intent);
				finish();
			}
		});

		register.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent registerIntent = new Intent(context,
						RegistrationActivity.class);
				startActivity(registerIntent);
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

				boolean isAuthenticated = authenticateUser(username, password);
				if (isAuthenticated) {
					SharedPreferences.Editor editor = settings.edit();
					editor.putBoolean("loggedIn", true);
					editor.putBoolean("stayLoggedIn", true);
					Spinner categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
					
					editor.putString("category", String.valueOf(categorySpinner.getSelectedItem()) );
					editor.commit();

					Intent i = new Intent(context, MainActivity.class);
					startActivity(i);
					finish();
				} else {
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
	}

	private boolean authenticateUser(String email, String password) {
		AuthenticationService authService = new AuthenticationService();
		AuthToken authToken = authService.authenticateWithCredentials(email,
				password);

		if (authToken != null) {
			String userId = authToken.userId;
			String token = authToken.token;

			SharedPreferences settings = getSharedPreferences("LocalPrefs", 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("userID", userId);
			editor.putString("token", token);
			editor.commit();

			return true;
		}
		return false;
	}

	public static Context getContext() {
		if (selfReferance != null) {
			return selfReferance.getApplicationContext();
		}
		return null;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void hideActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.hide();
		}
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pre_login, menu);
		return true;
	}

}
