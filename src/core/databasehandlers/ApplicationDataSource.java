package core.databasehandlers;

import core.models.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ApplicationDataSource {

	private static final String LOGTAG = "IP13HVA";

	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;

	public ApplicationDataSource(Context context) {
		dbhelper = DatabaseOpenHelper.getInstance(context);
	}

	public void open() {
		Log.i(LOGTAG, "Database opened");
		database = dbhelper.getWritableDatabase();
	}

	public void close() {
		Log.i(LOGTAG, "Database closed");
		dbhelper.close();
	}

	public void create(Application application) {
		ContentValues values = new ContentValues();

		values.put(DatabaseOpenHelper.COLUMN_KEY, application.getKey());
		values.put(DatabaseOpenHelper.COLUMN_VALUE, application.getValue());

		database.insert(DatabaseOpenHelper.TABLE_APPLICATION, null, values);
	}
}
