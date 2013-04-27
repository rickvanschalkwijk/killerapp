package com.app.amsterguide;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.killerapp.R;

import core.connection.RESTSocialService;
import core.models.Friendship;
import core.models.User;

public class FriendDetailActivity extends Activity {
	
	final Context context = this;
	private static FriendDetailActivity selfReferance = null;
	private Friendship friendship;
	private String authToken;
	private long userId;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_friendship_details);
    	
    	friendship = (Friendship) getIntent().getSerializableExtra( Friendship.EXTRA );
    	
    	TextView name = (TextView) findViewById(R.id.fullname);
    	TextView email = (TextView) findViewById(R.id.txtUserEmail);
    	SharedPreferences settings = getSharedPreferences("LocalPrefs", 0);
		userId = Long.valueOf(settings.getString("userID", "0"))
				.longValue();
		authToken = settings.getString("token", "letmein");
		
		User otherUser = friendship.getOtherUser(userId);
		name.setText(String.valueOf( otherUser.getUsername()));
		email.setText(otherUser.getEmail());
    }
    
    public static Context getContext() {
		if (selfReferance != null) {
			return selfReferance.getApplicationContext();
		}
		return null;
	}
    
    public void deleteFriendship(View view){
    	RESTSocialService socialService = new RESTSocialService();
    	socialService.DeleteFriendship(userId, authToken, friendship.getId());
    	Intent intent = new Intent(this, FriendActivity.class);
    	Toast.makeText(context, "Friendship deleted",
				Toast.LENGTH_SHORT).show();
		startActivity(intent);
    }
}
