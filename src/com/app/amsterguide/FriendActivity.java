package com.app.amsterguide;

import java.util.List;
import java.util.regex.Pattern;

import com.app.amsterguide.adapters.FriendRowAdapter;
import com.app.amsterguide.loaders.FriendLoader;

import core.connection.CheckConnection;
import core.connection.RESTSocialService;
import core.models.Friendship;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import com.app.killerapp.R;

public class FriendActivity extends FragmentActivity implements
		LoaderCallbacks<List<Friendship>> {

	private FriendRowAdapter adapter;
	private static FriendActivity selfReferance = null;
	private List<Friendship> friendships;

	private FragmentActivity GetThis() {
		return this;
	}

	AddCompanionDialog dialig;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activityfriend);
		selfReferance = this;

		dialig = new AddCompanionDialog(this);

		SharedPreferences settings = getSharedPreferences("LocalPrefs", 0);
		long userId = Long.valueOf(settings.getString("userID", "0"))
				.longValue();

		adapter = new FriendRowAdapter(this, userId);

		ListView listView = (ListView) findViewById(R.id.friendlist);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Intent intent = new Intent(FriendActivity.this,
						FriendDetailActivity.class);
				intent.putExtra(Friendship.EXTRA, friendships.get(position));
				startActivity(intent);
			}
		});

		getSupportLoaderManager().initLoader(0, null, this);

	}

	public static Context getContext() {
		if (selfReferance != null) {
			return selfReferance.getApplicationContext();
		}
		return null;
	}

	@Override
	public Loader<List<Friendship>> onCreateLoader(int id, Bundle args) {
		SharedPreferences settings = getSharedPreferences("LocalPrefs", 0);
		long userId = Long.valueOf(settings.getString("userID", "0"))
				.longValue();
		String authToken = settings.getString("token", "letmein");
		return new FriendLoader(getApplicationContext(), userId, authToken,
				"APPROVED");
	}

	@Override
	public void onLoadFinished(Loader<List<Friendship>> loader,
			List<Friendship> result) {
		friendships = result;
		adapter.setList(result);
	}

	@Override
	public void onLoaderReset(Loader<List<Friendship>> arg0) {
		// TODO Auto-generated method stub

	}

	// a simple method to create an intent
	public static Intent createIntent(Context context) {
		Intent i = new Intent(context, MainActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return i;
	}

	public void makeToast(String message) {
		// with jam obviously
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.friend_ship_requests, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (!CheckConnection.isOnline(getContext())) {
			makeToast("No internet connection");
			return false;
		}

		switch (item.getItemId()) {
		case R.id.addfriends:
			dialig.show();
			break;
		case R.id.friendrequests:
			Intent intent = new Intent(this, FriendShipRequestsActivity.class);
			startActivity(intent);
			break;
		default:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	class AddCompanionDialog implements OnDismissListener, OnCancelListener {
		final private EditText editText;
		final private AlertDialog alertDialog;

		private Boolean canceled;

		AddCompanionDialog(Context context) {
			editText = new EditText(context);
			alertDialog = buildAlertDialog(context);
			alertDialog.setOnDismissListener(this);
			alertDialog.setOnCancelListener(this);

		}

		private AlertDialog buildAlertDialog(Context context) {
			return new AlertDialog.Builder(context)
					.setTitle("Add travel accompany")
					.setMessage("Enter email address").setView(editText)
					.setNeutralButton("Submit", null)
					.setNegativeButton("Cancel", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}
					}).create();
		}

		public void show() {
			canceled = false;
			alertDialog.show();
		}

		@Override
		public void onDismiss(DialogInterface dialog) {
			if (!canceled) {
				final String name = editText.getText().toString();
				if (!rfc2822.matcher(name).matches()) {
					editText.setError("Email address is not valid");
					show();
				} else {
					canceled = true;
					SharedPreferences settings = getSharedPreferences(
							"LocalPrefs", 0);
					long userId = Long.valueOf(
							settings.getString("userID", "0")).longValue();
					String authToken = settings.getString("token", "letmein");
					RESTSocialService socialService = new RESTSocialService();
					canceled = socialService.AddFriendship(userId, authToken,
							name);

					if (!canceled) {
						editText.setError("User does not exist or is already invited");
						show();
					}
					// Send request to server
				}
			}
		}

		@Override
		public void onCancel(DialogInterface dialog) {
			canceled = true;
		}

		private final Pattern rfc2822 = Pattern
				.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
	}
}