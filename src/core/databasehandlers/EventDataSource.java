package core.databasehandlers;


import core.models.Event;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EventDataSource {
	
	private static final String LOGTAG = "IP13HVA";

	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;
	
	public EventDataSource(Context context) {
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
	
	public void addEvent(Event event) {
		ContentValues values = new ContentValues();
		
		values.put(DatabaseOpenHelper.COLUMN_TITLE, event.getTitle());
		values.put(DatabaseOpenHelper.COLUMN_DESCRIPTION, event.getDescription());
		values.put(DatabaseOpenHelper.COLUMN_CATEGORY, event.getCategory());
		values.put(DatabaseOpenHelper.COLUMN_START_DATE, event.getStartDate().toString("dd-MMM-yy hh:mm:ss aa"));
		values.put(DatabaseOpenHelper.COLUMN_END_DATE, event.getEndDate().toString("dd-MMM-yy hh:mm:ss aa"));
		values.put(DatabaseOpenHelper.COLUMN_LATITUDE, event.getLocation().getLatitudeE6() / 1E6);
		values.put(DatabaseOpenHelper.COLUMN_LONGITUDE, event.getLocation().getLongitudeE6() / 1E6);
		values.put(DatabaseOpenHelper.COLUMN_PRICE, event.getPrice().toString());
		values.put(DatabaseOpenHelper.COLUMN_ISFREE, event.isFree());
		
		
	}
	
	

}
