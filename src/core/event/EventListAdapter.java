package core.event;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.List;
import util.KillerboneUtils;
import com.app.killerapp.R;
import core.models.Event;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class EventListAdapter extends BaseAdapter implements Filterable {
	private final Context context;
	List<Event> eventList;
	List<Event> originalEventList;

	public EventListAdapter(Context context, List<Event> eventList) {
		this.context = context;
		this.eventList = eventList;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater
				.inflate(R.layout.event_list_item, parent, false);

		TextView name = (TextView) rowView.findViewById(R.id.nameText);
		name.setText(eventList.get(position).getTitle());

		TextView start = (TextView) rowView.findViewById(R.id.startText);
		start.setText(eventList.get(position).getStartDate()
				.toString(KillerboneUtils.KILLERBONE_DATE_FORMAT));
		return rowView;

	}

	public Filter getFilter() {
		Filter filter = new Filter() {

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				eventList = (List<Event>) results.values; // has the filtered
															// values
				notifyDataSetChanged(); // notifies the data with new filtered
										// values
			}

			@Override
			protected FilterResults performFiltering(CharSequence charSequence) {
				FilterResults results = new FilterResults();
				List<Event> filteredArrayList = new ArrayList<Event>();
				Log.d("FIlter", "aangeroepen");

				if (originalEventList == null) {
					originalEventList = new ArrayList<Event>(eventList);
				}

				// If there's nothing to filter on, return the original data for
				// the list
				if (charSequence == null || charSequence.length() == 0) {
					results.values = originalEventList;
					results.count = originalEventList.size();
					Log.d("Filter", "Maak originele array aan als Reference");

				} else {
					charSequence = charSequence.toString().toLowerCase();

					for (Event event : originalEventList) {
						// In this loop, you'll filter through originalData and
						// compare each item to charSequence.
						// If you find a match, add it to your new ArrayList
						String data = event.getTitle().toLowerCase();
						if (data.toLowerCase().contains(charSequence)) {
							filteredArrayList.add(event);
						}
					}
					results.values = filteredArrayList;
					results.count = filteredArrayList.size();
				}
				return results;
			}

		};
		return filter;
	}

	@Override
	public int getCount() {
		return eventList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
