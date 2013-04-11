package core.event;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.app.killerapp.R;

import core.databasehandlers.EventDataSource;
import core.models.Event;

public class EventList extends ListActivity {

	public static List<Event> events = new ArrayList<Event>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_list);

		EventDataSource eventDataSource = new EventDataSource(this);
		eventDataSource.open();
		events.clear();
		events = eventDataSource.getAllEvents();
		eventDataSource.close();

		ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(this,
				R.layout.text_view, events);
		setListAdapter(adapter);
		/*
		ListView listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
				onListItemClick(v,pos,id);			
			}
        	
		});
		*/
	}
	
	public void onListItemClick(ListView parent, View v,int position, long id) {
		Dialog dialog = new Dialog(EventList.this);
		dialog.setContentView(R.layout.event_dialog);
		dialog.setTitle("Detailed event info");
		dialog.setCancelable(true);

		TextView name = (TextView) dialog.findViewById(R.id.textView1);
		name.setText(events.get(position).getTitle());

		TextView description = (TextView) dialog.findViewById(R.id.textView2);
		description.setText(events.get(position).getDescription());
	  }
	
	/*
	protected void onListItemClick(View v, int pos, long id) {
		Dialog dialog = new Dialog(EventList.this);
		dialog.setContentView(R.layout.event_dialog);
		dialog.setTitle("Detailed event info");
		dialog.setCancelable(true);

		TextView name = (TextView) dialog.findViewById(R.id.textView1);
		name.setText(events.get(pos).getTitle());

		TextView description = (TextView) dialog.findViewById(R.id.textView2);
		description.setText(events.get(pos).getDescription());
	}
	*/
}
