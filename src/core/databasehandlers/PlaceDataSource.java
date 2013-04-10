package core.databasehandlers;

import core.models.Place;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PlaceDataSource {
	
	private static final String LOGTAG = "IP13HVA";

	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;
	
	public PlaceDataSource(Context context) {
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
	
	public void addPlace(Place place) {
		ContentValues values = new ContentValues();
		
		values.put(DatabaseOpenHelper.COLUMN_TITLE, place.getName());
		values.put(DatabaseOpenHelper.COLUMN_DESCRIPTION, place.getDescription());
		values.put(DatabaseOpenHelper.COLUMN_LATITUDE, place.getLocation().getLatitudeE6() / 1E6);
		values.put(DatabaseOpenHelper.COLUMN_LONGITUDE, place.getLocation().getLongitudeE6() / 1E6);
		
		database.insert(DatabaseOpenHelper.TABLE_PLACES, null, values);
	}
	
}
