package com.app.amsterguide;

import java.util.List;

import com.app.amsterguide.FriendActivity.AddCompanionDialog;
import com.app.amsterguide.adapters.FriendRowAdapter;
import com.app.amsterguide.loaders.FriendLoader;
import com.app.killerapp.R;
import com.app.killerapp.R.layout;
import com.app.killerapp.R.menu;

import core.models.Friendship;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

public class FriendPendingActivity extends FragmentActivity implements
LoaderCallbacks<List<Friendship>> {

	private FriendRowAdapter adapter;
	private static FriendPendingActivity selfReferance = null;
	private List<Friendship> friendships;

	private FragmentActivity GetThis() {
		return this;
	}
	
	public static Context getContext() {
		if (selfReferance != null) {
			return selfReferance.getApplicationContext();
		}
		return null;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_pending);
		// Show the Up button in the action bar.
		setupActionBar();
		
		selfReferance = this;

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
				Intent intent = new Intent(FriendPendingActivity.this,
						FriendDetailActivity.class);
				intent.putExtra(Friendship.EXTRA, friendships.get(position));
				startActivity(intent);
			}
		});

		getSupportLoaderManager().initLoader(0, null, this);
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
		getMenuInflater().inflate(R.menu.friend_pending, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
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
	public Loader<List<Friendship>> onCreateLoader(int arg0, Bundle arg1) {
		SharedPreferences settings = getSharedPreferences("LocalPrefs", 0);
		long userId = Long.valueOf(settings.getString("userID", "0"))
				.longValue();
		String authToken = settings.getString("token", "letmein");
		return new FriendLoader(getApplicationContext(), userId, authToken,
				"PENDING");
	}

	@Override
	public void onLoadFinished(Loader<List<Friendship>> arg0,
		List<Friendship> result) {
			friendships = result;
			adapter.setList(result);
			
			if (result == null || result.isEmpty()) {			
				makeToast("No pending requests found");
			}
		
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
	public void onLoaderReset(Loader<List<Friendship>> arg0) {
		// TODO Auto-generated method stub
		
	}

}
