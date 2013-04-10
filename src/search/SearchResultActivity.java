package search;

import java.util.ArrayList;
import java.util.List;

import com.app.killerapp.R;

import core.models.Event;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class SearchResultActivity extends ListActivity{
	List<Event> events = new ArrayList<Event>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		SearchMainActivity searchMainActivity = new SearchMainActivity();
		//events = searchMainActivity.getResult();
		ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(this, android.R.layout.simple_list_item_1, events);
		setListAdapter(adapter);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	
	
	

}
