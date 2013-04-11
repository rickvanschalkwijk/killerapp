package core.event;

import java.util.ArrayList;
import java.util.List;

import com.app.killerapp.R;

import core.databasehandlers.EventDataSource;
import core.models.Event;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

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
		
		ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(this,
				android.R.layout.simple_list_item_1, events);
		setListAdapter(adapter);
		
		eventDataSource.close();
	}
	

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
}