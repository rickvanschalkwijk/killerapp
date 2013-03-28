package services;

import com.app.killerapp.R;
import com.app.killerapp.R.layout;
import com.app.killerapp.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ServicesLinesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_services_lines);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.services_lines, menu);
		return true;
	}

}
