package core.place;

import java.util.ArrayList;
import java.util.List;

import util.KillerboneUtils;

import com.app.killerapp.R;
import core.models.Place;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class PlaceListAdapter extends BaseAdapter implements Filterable {
	private final Context context;
	List<Place> placeList;
	List<Place> originalPlaceList;

	public PlaceListAdapter(Context context, List<Place> placeList) {
		this.context = context;
		this.placeList = placeList;
	}

	@Override
	public int getCount() {
		return placeList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater
				.inflate(R.layout.place_list_item, parent, false);

		TextView name = (TextView) rowView.findViewById(R.id.nameText);
		name.setText(Html.fromHtml(placeList.get(position).getName()));

		return rowView;
	}

	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				placeList = (List<Place>) results.values; // has the filtered
															// values
				notifyDataSetChanged(); // notifies the data with new filtered
										// values
			}

			@Override
			protected FilterResults performFiltering(CharSequence charSequence) {
				FilterResults results = new FilterResults();
				List<Place> filteredArrayList = new ArrayList<Place>();
				Log.d("FIlter", "aangeroepen");

				if (originalPlaceList == null) {
					originalPlaceList = new ArrayList<Place>(placeList);
				}

				// If there's nothing to filter on, return the original data for
				// the list
				if (charSequence == null || charSequence.length() == 0) {
					results.values = originalPlaceList;
					results.count = originalPlaceList.size();
					Log.d("Filter", "Maak originele array aan als Reference");

				} else {
					charSequence = charSequence.toString().toLowerCase();

					for (Place place : originalPlaceList) {
						// In this loop, you'll filter through originalData and
						// compare each item to charSequence.
						// If you find a match, add it to your new ArrayList
						String data = place.getName().toLowerCase();
						if (data.toLowerCase().contains(charSequence)) {
							filteredArrayList.add(place);
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

}
