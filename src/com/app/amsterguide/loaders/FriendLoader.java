package com.app.amsterguide.loaders;

import java.util.List;

import core.connection.RESTSocialService;
import core.models.Friendship;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

public class FriendLoader  extends AsyncTaskLoader<List<Friendship>> {
	
	private List<Friendship> result;
	private long userId;
	private String authToken;
	private String friendshipStatus;
	private Context context;
	
	public FriendLoader(Context context, long id, String authToken, String friendStatus) {
		super(context);
		this.context = context;
		this.userId = id;
		this.authToken = authToken;
		this.friendshipStatus = friendStatus;
	}

	@Override
	public List<Friendship> loadInBackground() {
		RESTSocialService socialService = new RESTSocialService();
		List<Friendship> friendships = socialService.RetrieveFriendships(userId, authToken, friendshipStatus, context);
		
		if (friendships == null){
			return null;
		}
		
		if (friendshipStatus.contains("PENDING")) {
			List<Friendship> friendships2 = socialService.RetrieveFriendships(userId, authToken, "SENT", context);
			for (int i = 0; i < friendships2.size(); i++) {
				friendships.add(friendships2.get(i));
			}
		}
		
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
