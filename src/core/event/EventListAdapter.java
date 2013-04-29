package core.event;

import java.util.List;
import util.KillerboneUtils;
import com.app.killerapp.R;
import core.models.Event;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EventListAdapter extends ArrayAdapter<Event> {
	private final Context context;
	private final List<Event> events;

	public EventListAdapter(Context context, List<Event> events) {
		super(context, R.layout.event_list_item, events);
		this.context = context;
		this.events = events;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater
				.inflate(R.layout.event_list_item, parent, false);

		TextView name = (TextView) rowView.findViewById(R.id.nameText);
		name.setText(events.get(position).getTitle());

		TextView start = (TextView) rowView.findViewById(R.id.startText);
		start.setText(events.get(position).getStartDate()
				.toString(KillerboneUtils.KILLERBONE_DATE_FORMAT));
		return rowView;

	}
}
