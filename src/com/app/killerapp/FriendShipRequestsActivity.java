package com.app.killerapp;

import java.util.ArrayList;
import java.util.List;

import com.app.amsterguide.loaders.FriendLoader;

import core.models.Friendship;
import core.models.User;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Menu;
import android.widget.ListAdapter;
import android.widget.ListView;

public class FriendShipRequestsActivity extends FragmentActivity implements
		LoaderCallbacks<List<Friendship>> {

	private static FriendShipRequestsActivity selfReferance = null;
	FriendshipRequestsAdapter comReqAdap = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_ship_requests);
		selfReferance = this;
		
		SharedPreferences settings = getSharedPreferences("LocalPrefs", 0);
		long userId = Long.valueOf(settings.getString("userID", "0"))
				.longValue();
		String authToken = settings.getString("token", "letmein");
		//ArrayList<Friendship> searchResults = GetSearchResults();
		comReqAdap = new FriendshipRequestsAdapter(this, userId, authToken);
		
		final ListView lv1 = (ListView) findViewById(R.id.ListView01);
		lv1.setAdapter(comReqAdap);
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
		return new FriendLoader(getApplicationContext(), userId, authToken, "PENDING");
	}

	@Override
	public void onLoadFinished(Loader<List<Friendship>> loader, List<Friendship> result) {
		if (result.size() > 0) {
			comReqAdap.setList(result);
		}	
	}

	@Override
	public void onLoaderReset(Loader<List<Friendship>> arg0) {
		// TODO Auto-generated method stub

	}

}
