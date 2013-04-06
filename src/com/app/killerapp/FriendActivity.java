package com.app.killerapp;

import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.widget.EditText;

public class FriendActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend);
		
	}
}

class AddCompanionDialog implements OnDismissListener, OnCancelListener {
    final private EditText editText;
    final private AlertDialog alertDialog;

    private Boolean canceled;

    AddCompanionDialog(Context context) {
        editText = new EditText(context);
        alertDialog = buildAlertDialog(context);
        alertDialog.setOnDismissListener(this);
        alertDialog.setOnCancelListener(this);
        show();
    }

    private AlertDialog buildAlertDialog(Context context) {
        return new AlertDialog.Builder(context)
        .setTitle("Title")
        .setMessage("Enter email address")
        .setView(editText)
        .setNeutralButton("Submit", null)
        .setNegativeButton("Cancel", null)
            .create();
    }

    public void show() {
        canceled = false;
        alertDialog.show();
    }

    @Override public void onDismiss(DialogInterface dialog) {
        if(!canceled) {
            final String name = editText.getText().toString();
            if(!rfc2822.matcher(name).matches()) {
                editText.setError("Email address is not valid");
                show();
            } else {
                // Send request to server
            }
        }
    }

    @Override public void onCancel(DialogInterface dialog) {
        canceled = true;
    }
    
    private static final Pattern rfc2822 = Pattern
			.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
}
