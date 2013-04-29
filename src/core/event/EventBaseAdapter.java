package core.event;

import java.util.ArrayList;

import com.app.killerapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EventBaseAdapter extends BaseAdapter {
	private static ArrayList<EventList> EventArrayList;

	private LayoutInflater mInflater;

	public EventBaseAdapter(Context context, ArrayList<EventList> results) {
		EventArrayList = results;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return EventArrayList.size();
	}

	public Object getItem(int position) {
		return EventArrayList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.custom_row_view, null);
			holder = new ViewHolder();
			holder.txtName = (TextView) convertView.findViewById(R.id.name);
			holder.txtCityState = (TextView) convertView
					.findViewById(R.id.cityState);
			holder.txtPhone = (TextView) convertView.findViewById(R.id.phone);

			 convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}


		return convertView;
	}

	static class ViewHolder {
		TextView txtName;
		TextView txtCityState;
		TextView txtPhone;
	}
}