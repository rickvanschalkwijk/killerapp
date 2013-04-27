package com.app.killerapp;

import java.util.ArrayList;
import java.util.List;

import com.app.amsterguide.FriendActivity;
import com.app.amsterguide.loaders.FriendLoader;

import core.models.Friendship;
import core.models.User;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.ListAdapter;
import android.widget.ListView;

public class FriendShipRequestsActivity extends FragmentActivity implements
		LoaderCallbacks<List<Friendship>> {

	private static FriendShipRequestsActivity selfReferance = null;
	FriendshipRequestsAdapter comReqAdap = null;
	private long userID;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_ship_requests);
		selfReferance = this;
		
		SharedPreferences settings = getSharedPreferences("LocalPrefs", 0);
		userID = Long.valueOf(settings.getString("userID", "0"))
				.longValue();
		String authToken = settings.getString("token", "letmein");
		//ArrayList<Friendship> searchResults = GetSearchResults();
		comReqAdap = new FriendshipRequestsAdapter(this, userID, authToken);
		
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
		if (result != null && result.size() > 0) {
			
			List<Friendship> tempL = new ArrayList<Friendship>();
			for (int i = 0; i < result.size(); i++) {
				if (result.get(i).getInitiator().getId() != userID) {
					tempL.add(result.get(i));
				}
			}		
			comReqAdap.setList(tempL);
		}	
	}

	@Override
	public void onLoaderReset(Loader<List<Friendship>> arg0) {
		// TODO Auto-generated method stub

	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	    	Intent intent = new Intent(this, FriendActivity.class);
			startActivity(intent);
	        return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}
}
