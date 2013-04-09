package com.app.killerapp.loaders;

import java.util.List;

import core.connection.RESTSocialService;
import core.connection.killerbone.AuthenticationService;
import core.connection.killerbone.AuthenticationService.AuthToken;
import core.models.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

public class FriendLoader  extends AsyncTaskLoader<List<User>> {
	
	private List<User> result;
	private long userId;
	private String authToken;
	
	public FriendLoader(Context context, long id, String authToken) {
		super(context);
		this.userId = id;
		this.authToken = authToken;
	}

	@Override
	public List<User> loadInBackground() {
		Log.d("adapter" , "adapter start");
		RESTSocialService socialService = new RESTSocialService();
		List<User> users = socialService.RetrieveFriendships(userId, authToken);
		if (users != null){
			return users;
		}
		return null;
	}
	
	@Override
	protected void onStartLoading() {
		if (this.result != null) {
			deliverResult(this.result);
		}

		if (takeContentChanged() || this.result == null) {
			forceLoad();
		}

	}

	@Override
	public void deliverResult(List<User> result) {
		if (this.result == null) {
			this.result = result;
		}
		if (isStarted()) {
			super.deliverResult(result);
		}
	}

}
