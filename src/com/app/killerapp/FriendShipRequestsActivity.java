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

		//ArrayList<Friendship> searchResults = GetSearchResults();
		//comReqAdap = new FriendshipRequestsAdapter(
	//			this, searchResults);
		final ListView lv1 = (ListView) findViewById(R.id.ListView01);
		lv1.setAdapter(comReqAdap);
		ListAdapter lAdapter = lv1.getAdapter();
		getSupportLoaderManager().initLoader(0, null, this);
		
		selfReferance = this;
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
		return new FriendLoader(getApplicationContext(), userId, authToken, "NEUTRAL");
	}

	@Override
	public void onLoadFinished(Loader<List<Friendship>> loader, List<Friendship> result) {
			
		User currentUser = new User(1, "Test@User.nl", "DummyUser");
		
		ArrayList<Friendship> frList = new ArrayList<Friendship>();
		for (int i = 0; i < result.size(); i++) {
			Friendship fr = new Friendship();
			fr.setParticipant(result.get(i).getParticipant());
			fr.setStatus("Neutral");
			fr.setInitiator(result.get(i).getInitiator());
		}
		if (frList.size() > 0) {
			comReqAdap.setList(frList);
		}	
	}

	@Override
	public void onLoaderReset(Loader<List<Friendship>> arg0) {
		// TODO Auto-generated method stub

	}
/*
	public ArrayList<Friendship> GetSearchResults() {
		ArrayList<Friendship> results = new ArrayList<Friendship>();

		User current = new User(1, "Current@user.nl", "CurrentUser");

		Friendship req1 = new Friendship(1, current, new User(1,
				"New1@user.nl", "CurrentUser"), "Neutral");

		Friendship req2 = new Friendship(1, current, new User(2,
				"New2@user.nl", "CurrentUser"), "Neutral");

		Friendship req4 = new Friendship(1, current, new User(3,
				"New3@user.nl", "CurrentUser"), "Neutral");

		Friendship req3 = new Friendship(1, current, new User(4,
				"New4@user.nl", "CurrentUser"), "Neutral");

		results.add(req3);
		results.add(req4);
		results.add(req2);
		results.add(req1);

		return results;
	}
*/
}
