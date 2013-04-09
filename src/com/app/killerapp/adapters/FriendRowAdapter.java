package com.app.killerapp.adapters;

import java.util.ArrayList;
import java.util.List;

import com.app.killerapp.R;

import core.models.User;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FriendRowAdapter extends BaseAdapter {
	private List<User> friends = new ArrayList<User>();
	private final Context context;
	
	
	
	public FriendRowAdapter(Context context) {
		this.context = context;
		Log.d("adapter", "adapter");
	}

	@Override
	public int getCount() {
		if(friends != null){
			return friends.size();
		}
		return 0;
	}
	
	public void setList(final List<User> users) {
		this.friends = users;
		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		if(friends != null){
			return friends.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		if(convertView == null){
			convertView = View.inflate(context, R.layout.friend_row_view, null);
		}
		
		final User user = (User)getItem(position);
		
		final TextView name = (TextView) convertView.findViewById(R.id.txtName);
		final TextView status = (TextView) convertView.findViewById(R.id.txtStatus);
		
		name.setText(user.getUsername());
		status.setText(user.getEmail());

		
		
		return convertView;
	}

}
