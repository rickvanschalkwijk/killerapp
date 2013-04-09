package core.databasehandlers;

import core.models.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDataSource {

	private static final String LOGTAG = "IP13HVA";

	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;

	public UserDataSource(Context context) {
		dbhelper = new DatabaseOpenHelper(context);
	}

	public void open() {
		Log.i(LOGTAG, "Database opened");
		database = dbhelper.getWritableDatabase();
	}

	public void close() {
		Log.i(LOGTAG, "Database closed");
		dbhelper.close();
	}

	public void addUser(User user) {
		ContentValues values = new ContentValues();

		values.put(DatabaseOpenHelper.COLUMN_ID, user.getId());
		values.put(DatabaseOpenHelper.COLUMN_EMAIL, user.getEmail());
		values.put(DatabaseOpenHelper.COLUMN_USERNAME, user.getUsername());
		values.put(DatabaseOpenHelper.COLUMN_PASSWORD, user.getPassword());

		database.insert(DatabaseOpenHelper.TABLE_USERS, null, values);
	}
}
