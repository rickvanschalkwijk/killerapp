package com.app.amsterguide.adapters;

import java.util.ArrayList;
import java.util.List;

import com.app.killerapp.R;

import core.models.Friendship;
import core.models.User;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FriendRowAdapter extends BaseAdapter {
	private List<Friendship> friends = new ArrayList<Friendship>();
	private final Context context;
	private final long userId;

	public FriendRowAdapter(Context context, long userId) {
		this.context = context;
		this.userId = userId;
		Log.d("adapter", "adapter");
	}

	@Override
	public int getCount() {
		if (friends != null) {
			return friends.size();
		}
		return 0;
	}

	public void setList(final List<Friendship> friendships) {
		this.friends = friendships;
		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		if (friends != null) {
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
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.friend_row_view, null);
		}

		Friendship friendship = (Friendship) getItem(position);
		User user = friendship.getOtherUser(userId);

		TextView name = (TextView) convertView.findViewById(R.id.txtName);
		TextView status = (TextView) convertView.findViewById(R.id.txtStatus);

		name.setText(user.getUsername());
		status.setText(friendship.getStatus());

		return convertView;
	}

}
