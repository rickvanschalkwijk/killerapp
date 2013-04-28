package core.place;

import java.util.ArrayList;
import java.util.List;

import com.app.killerapp.R;

import core.databasehandlers.PlaceDataSource;
import core.map.MapActivity;
import core.models.Place;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class PlaceList extends Activity {

	public static List<Place> places = new ArrayList<Place>();
	ArrayAdapter<Place> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_list);

		PlaceDataSource placeDataSource = new PlaceDataSource(this);
		placeDataSource.open();
		places.clear();
		places = placeDataSource.getAllPlaces();
		placeDataSource.close();

		final ListView listView = (ListView) findViewById(R.id.listPlaces);
		EditText inputSearch = (EditText) findViewById(R.id.inputSearch);

		adapter = new ArrayAdapter<Place>(this, R.layout.event_list_item,
				places);
		listView.setAdapter(adapter);

		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// When user changed the Text
				PlaceList.this.adapter.getFilter().filter(cs);
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

				final Dialog dialog = new Dialog(PlaceList.this,
						R.style.AppTheme);
				dialog.setContentView(R.layout.place_dialog);
				dialog.setTitle("Detailed place info");
				dialog.setCancelable(true);

				TextView name = (TextView) dialog.findViewById(R.id.textView1);
				name.setText(places.get(position).getName());

				TextView description = (TextView) dialog
						.findViewById(R.id.textView2);
				description.setText(Html.fromHtml(places.get(position)
						.getDescription()));

				Button dialogButton = (Button) dialog
						.findViewById(R.id.btnShowPlace);
				dialogButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(PlaceList.this,
								MapActivity.class);
						intent.putExtra("place", places.get(position));
						startActivity(intent);

					}
				});

				dialog.show();
			}

		});

	}

}
