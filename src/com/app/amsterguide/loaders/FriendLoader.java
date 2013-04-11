package com.app.amsterguide.loaders;

import java.util.List;

import core.connection.RESTSocialService;
import core.connection.killerbone.AuthenticationService;
import core.connection.killerbone.AuthenticationService.AuthToken;
import core.models.Friendship;
import core.models.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

public class FriendLoader  extends AsyncTaskLoader<List<Friendship>> {
	
	private List<Friendship> result;
	private long userId;
	private String authToken;
	private String friendshipStatus;
	
	public FriendLoader(Context context, long id, String authToken, String friendStatus) {
		super(context);
		this.userId = id;
		this.authToken = authToken;
		this.friendshipStatus = friendStatus;
	}

	@Override
	public List<Friendship> loadInBackground() {
		Log.d("adapter" , "adapter start");
		RESTSocialService socialService = new RESTSocialService();
		List<Friendship> friendships = socialService.RetrieveFriendships(userId, authToken, friendshipStatus);
		if (friendships != null){
			return friendships;
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
	public void deliverResult(List<Friendship> result) {
		if (this.result == null) {
			this.result = result;
		}
		if (isStarted()) {
			super.deliverResult(result);
		}
	}

}
