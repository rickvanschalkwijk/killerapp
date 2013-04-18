package com.app.amsterguide;


import com.app.killerapp.R;

import core.models.Event;
import core.models.Friendship;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class FriendDetailActivity extends Activity {
	
	final Context context = this;
	private Friendship friendship;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	Intent i = getIntent();
    	friendship = (Friendship) getIntent().getSerializableExtra( friendship.EXTRA );
    	
    	TextView name = (TextView) findViewById(R.id.fullname);
    	
    	SharedPreferences settings = getSharedPreferences("LocalPrefs", 0);
		long userId = Long.valueOf(settings.getString("userID", "0"))
				.longValue();
		Log.d("UserID Detail", String.valueOf(userId));
		name.setText(String.valueOf(friendship.getOtherUser(userId).getId()));
    	
    	
		setContentView(R.layout.activity_friendship_details);
    }
}
