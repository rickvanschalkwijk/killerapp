package com.app.amsterguide;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.app.killerapp.R;

import core.models.Friendship;
import core.models.User;

public class FriendDetailActivity extends Activity {
	
	final Context context = this;
	private Friendship friendship;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_friendship_details);
    	
    	friendship = (Friendship) getIntent().getSerializableExtra( Friendship.EXTRA );
    	
    	TextView name = (TextView) findViewById(R.id.fullname);
    	
    	SharedPreferences settings = getSharedPreferences("LocalPrefs", 0);
		long userId = Long.valueOf(settings.getString("userID", "0"))
				.longValue();
		
		User otherUser = friendship.getOtherUser(userId);
		name.setText(String.valueOf( otherUser.getId() ) );
    }
}
