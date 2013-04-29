package core.event;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.app.killerapp.R;

import core.databasehandlers.EventDataSource;
import core.map.MapActivity;
import core.models.Event;

public class EventList extends Activity {

	public static List<Event> events = new ArrayList<Event>();
	EventListAdapter adapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_list);

		EventDataSource eventDataSource = new EventDataSource(this);
		eventDataSource.open();
		events.clear();
		events = eventDataSource.getAllEvents();
		// eventDataSource.close();

		final ListView listView = (ListView) findViewById(R.id.listEvents);
		EditText inputSearch = (EditText) findViewById(R.id.inputSearch);

		adapter = new EventListAdapter(this, events);
		listView.setAdapter(adapter);

		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// When user changed the Text
				EventList.this.adapter.getFilter().filter(cs);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View v,
					final int position, long id) {

				final Dialog dialog = new Dialog(EventList.this,
						R.style.AppTheme);
				dialog.setContentView(R.layout.event_dialog);
				dialog.setTitle("Detailed event info");
				dialog.setCancelable(true);

				TextView name = (TextView) dialog.findViewById(R.id.textView1);
				name.setText(Html.fromHtml(events.get(position).getTitle()));

				TextView description = (TextView) dialog
						.findViewById(R.id.textView2);
				description.setText(Html.fromHtml(events.get(position)
						.getDescription()));

				Button dialogButton = (Button) dialog
						.findViewById(R.id.btnShowEvent);
				dialogButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(EventList.this,
								MapActivity.class);
						intent.putExtra("event", events.get(position));
						startActivity(intent);

					}
				});
				dialog.show();
			}
		});
	}
}
