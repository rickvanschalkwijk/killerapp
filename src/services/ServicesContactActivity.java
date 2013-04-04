package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.app.killerapp.R;
import com.app.killerapp.R.layout;
import com.app.killerapp.R.menu;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class ServicesContactActivity extends Activity {

	private ArrayAdapter<String> listAdapter;
	ListView mainListView;
	private LayoutInflater mInflater;

	static String[] DETAILS;
	static String[] PHONE;

	// http://www.softwarepassion.com/android-series-custom-listview-items-and-adapters/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setupActionBar();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_services_contact);

		ArrayList<ServicesList> searchResults = GetSearchResults();

		final ListView lv1 = (ListView) findViewById(R.id.ListView01);
		lv1.setAdapter(new MyCustomBaseAdapter(this, searchResults));

		lv1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = lv1.getItemAtPosition(position); 
				ServicesList fullObject = (ServicesList) o;
				
				Intent sIntent = new Intent(Intent.ACTION_DIAL, Uri
						.parse("tel:" + fullObject.getPhone()));
				startActivity(sIntent);
				/*
				 * Object o = lv1.getItemAtPosition(position); ServicesList
				 * fullObject = (ServicesList) o;
				 * Toast.makeText(getApplicationContext(), "You have chosen: " +
				 * " " + fullObject.getName(), Toast.LENGTH_LONG).show();
				 */
			}
		});

	}

	private ArrayList<ServicesList> GetSearchResults() {
		ArrayList<ServicesList> results = new ArrayList<ServicesList>();

		ServicesList sr1 = new ServicesList();
		sr1.setName("Emergency Services");
		sr1.setPhone("112");
		sr1.setOtherNumbers("Police, Fire department, Hospital");
		results.add(sr1);

		sr1 = new ServicesList();
		sr1.setName("Suicide/Crisis lines");
		sr1.setPhone("+31206757575");
		sr1.setOtherNumbers("In case of suicide attempts.");
		results.add(sr1);

		sr1 = new ServicesList();
		sr1.setName("Alcoholics Anonymous");
		sr1.setPhone("+31206256057");
		sr1.setOtherNumbers("");
		results.add(sr1);

		sr1 = new ServicesList();
		sr1.setName("Drugs info line");
		sr1.setPhone("+31205702355");
		sr1.setOtherNumbers("");
		results.add(sr1);

		sr1 = new ServicesList();
		sr1.setName("HIV/AIDS");
		sr1.setPhone("+319002042040");
		sr1.setOtherNumbers("");
		results.add(sr1);

		sr1 = new ServicesList();
		sr1.setName("Child Helpline");
		sr1.setPhone("+318000432");
		sr1.setOtherNumbers("(14:00 - 20:00)");
		results.add(sr1);

		sr1 = new ServicesList();
		sr1.setName("Women’s Helpline");
		sr1.setPhone("+31206116020");
		sr1.setOtherNumbers("(09:00 - 23:00)");
		results.add(sr1);

		sr1 = new ServicesList();
		sr1.setName("Red Cross");
		sr1.setPhone("+31704455888");
		sr1.setOtherNumbers("");
		results.add(sr1);

		sr1 = new ServicesList();
		sr1.setName("Emergency Doctor");
		sr1.setPhone("+31205923434");
		sr1.setOtherNumbers("");
		results.add(sr1);

		sr1 = new ServicesList();
		sr1.setName("Emergency Dentists");
		sr1.setPhone("+319003212230");
		sr1.setOtherNumbers("");
		results.add(sr1);

		sr1 = new ServicesList();
		sr1.setName("Emergency Vet");
		sr1.setPhone("+31205606360");
		sr1.setOtherNumbers("");
		results.add(sr1);

		sr1 = new ServicesList();
		sr1.setName("Pharmacy/Chemist On Duty");
		sr1.setPhone("+31206948709");
		sr1.setOtherNumbers("");
		results.add(sr1);

		sr1 = new ServicesList();
		sr1.setName("Emergency Hospitals");
		sr1.setPhone("112");
		sr1.setOtherNumbers("");
		results.add(sr1);

		sr1 = new ServicesList();
		sr1.setName("ACCESS Information Helpline");
		sr1.setPhone("+3120423321");
		sr1.setOtherNumbers("");
		results.add(sr1);
		return results;

	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.services_contact, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
