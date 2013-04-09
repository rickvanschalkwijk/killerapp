package search;

import java.util.ArrayList;
import java.util.List;

import com.app.killerapp.R;

import core.databasehandlers.EventDataSource;
import core.databasehandlers.XMLParser;
import core.models.Event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SearchMainActivity extends Activity {
	
	public List<Event> events = new ArrayList<Event>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_main);
		
		
		
	}
	
	public void getEvents(View view) {
		XMLParser parser = new XMLParser();
		parser.getEventsXML(this);
		EventDataSource eventDataSource = new EventDataSource(this);
		eventDataSource.open();
		events = eventDataSource.getAllEvents();
		Intent intent = new Intent(this, SearchResultActivity.class);
		startActivity(intent);
		eventDataSource.close();
	}
	
	public List<Event> getResult() {
		return events;
		
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
