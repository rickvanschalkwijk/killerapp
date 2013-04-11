package com.app.amsterguide;

import com.app.killerapp.R;
import com.app.killerapp.R.layout;
import com.app.killerapp.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PreLoginActivity extends Activity {
	private Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pre_login);
		Button signin = (Button) findViewById(R.id.btn_login);
		Button register = (Button) findViewById(R.id.btnRegister);
		
		signin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent signinIntent = new Intent(context, LoginActivity.class);
				finish();
				startActivity(signinIntent);
			}
		});
		
		register.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent registerIntent = new Intent(context, RegistrationActivity.class);
				finish();
				startActivity(registerIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pre_login, menu);
		return true;
	}

}
