package com.app.killerapp;

import java.util.ArrayList;

import core.models.Friendship;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class FriendshipRequestsAdapter extends BaseAdapter {
	private static ArrayList<Friendship> searchArrayList;

	private LayoutInflater mInflater;

	public FriendshipRequestsAdapter(Context context,
			ArrayList<Friendship> results) {
		searchArrayList = results;
		mInflater = LayoutInflater.from(context);
	}

	public void setList(ArrayList<Friendship> results) {
		searchArrayList = results;
	}

	public int getCount() {
		if (searchArrayList != null) {
			return searchArrayList.size();
		} else
			return 0;
	}

	public Object getItem(int position) {
		return searchArrayList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolderStatus holder;
		if (convertView == null) {
			holder = new ViewHolderStatus();

			// Search if status is already changed
			if (searchArrayList.get(position).getStatus() == "Neutral") {
				convertView = mInflater.inflate(R.layout.company_request_row,
						null);
				Friendship frTemp = searchArrayList.get(position);

				// Approve
				holder.buttonApprove = (Button) convertView
						.findViewById(R.id.buttonAccept);

				holder.buttonApprove.setTag(frTemp);
				holder.buttonApprove.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						clickApproveListener(v);
					}
				});

				// Decline
				holder.buttonDecline.setTag(frTemp);
				holder.buttonDecline.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						clickDeclineListener(v);
					}
				});
			} else {
				convertView = mInflater.inflate(
						R.layout.company_request_status_row, null);
				holder.txtStatus = (TextView) convertView
						.findViewById(R.id.status);
				holder.txtStatus.setText(searchArrayList.get(position)
						.getStatus().toString());
			}

			holder.txtName = (TextView) convertView.findViewById(R.id.username);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolderStatus) convertView.getTag();
		}

		holder.txtName.setText(searchArrayList.get(position).getParticipant()
				.getEmail());

		return convertView;
	}

	private OnClickListener clickApproveListener(View v) {

		Friendship item = (Friendship) v.getTag();
		Log.d("Approving: ", item.getParticipant().getEmail() + " from: "
				+ item.getInitiator().getEmail() + " Status approved");
		return null;
	}

	private OnClickListener clickDeclineListener(View v) {

		Friendship item = (Friendship) v.getTag();
		Log.d("Trying to approve: ", item.getParticipant().getEmail() + "");
		Log.d("Denying: ", item.getParticipant().getEmail() + " from: "
				+ item.getInitiator().getEmail() + " Status approved");
		return null;
	}

	static class ViewHolder {
		TextView txtName;

	}

	static class ViewHolderStatus {
		TextView txtName;
		TextView txtStatus;
		Button buttonApprove;
		Button buttonDecline;
	}

}
