package core.databasehandlers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.osmdroid.util.GeoPoint;

import util.KillerboneUtils;

import core.models.Event;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EventDataSource {

	private static final String LOGTAG = "IP13HVA";

	private static final String[] columns = { DatabaseOpenHelper.COLUMN_TITLE,
			DatabaseOpenHelper.COLUMN_DESCRIPTION,
			DatabaseOpenHelper.COLUMN_CATEGORY,
			DatabaseOpenHelper.COLUMN_START_DATE,
			DatabaseOpenHelper.COLUMN_END_DATE,
			DatabaseOpenHelper.COLUMN_LATITUDE,
			DatabaseOpenHelper.COLUMN_LONGITUDE,
			DatabaseOpenHelper.COLUMN_PRICE, 
			DatabaseOpenHelper.COLUMN_ISFREE };

	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;

	public EventDataSource(Context context) {
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

	public void addEvent(Event event) {
		ContentValues values = new ContentValues();

		values.put(DatabaseOpenHelper.COLUMN_TITLE, event.getTitle());
		values.put(DatabaseOpenHelper.COLUMN_DESCRIPTION,
				event.getDescription());
		values.put(DatabaseOpenHelper.COLUMN_CATEGORY, event.getCategory());
		values.put(DatabaseOpenHelper.COLUMN_START_DATE, event.getStartDate()
				.toString(KillerboneUtils.KILLERBONE_DATE_FORMAT));
		values.put(DatabaseOpenHelper.COLUMN_END_DATE, event.getEndDate()
				.toString(KillerboneUtils.KILLERBONE_DATE_FORMAT));
		values.put(DatabaseOpenHelper.COLUMN_LATITUDE, event.getLocation()
				.getLatitudeE6() / 1E6);
		values.put(DatabaseOpenHelper.COLUMN_LONGITUDE, event.getLocation()
				.getLongitudeE6() / 1E6);
		values.put(DatabaseOpenHelper.COLUMN_PRICE, event.getPrice().toString());
		values.put(DatabaseOpenHelper.COLUMN_ISFREE, event.isFree());

		database.insert(DatabaseOpenHelper.TABLE_EVENTS, null, values);
	}

	public List<Event> getAllEvents() {
		List<Event> events = new ArrayList<Event>();
		Cursor cursor = database.query(DatabaseOpenHelper.TABLE_EVENTS,
				columns, null, null, null, null, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Event event = new Event();
				event.setTitle(cursor.getString(cursor
						.getColumnIndex(DatabaseOpenHelper.COLUMN_TITLE)));
				
				event.setDescription(cursor.getString(cursor
						.getColumnIndex(DatabaseOpenHelper.COLUMN_DESCRIPTION)));
				
				event.setCategory(cursor.getString(cursor
						.getColumnIndex(DatabaseOpenHelper.COLUMN_CATEGORY)));
				
				DateTime dtStart = new DateTime(parseDateTime(cursor.getString(cursor
						.getColumnIndex(DatabaseOpenHelper.COLUMN_START_DATE)).trim()));
				event.setStartDate(dtStart);
				
				DateTime dtEnd = new DateTime(parseDateTime(cursor.getString(cursor
						.getColumnIndex(DatabaseOpenHelper.COLUMN_END_DATE)).trim()));
				event.setEndDate(dtEnd);
				
				Double latitude = cursor.getDouble(cursor
						.getColumnIndex(DatabaseOpenHelper.COLUMN_LATITUDE));
				Double longitude = cursor.getDouble(cursor
						.getColumnIndex(DatabaseOpenHelper.COLUMN_LONGITUDE));
				event.setLocation(new GeoPoint(latitude, longitude));
				
				event.setPrice(new BigDecimal(cursor.getDouble(cursor
						.getColumnIndex(DatabaseOpenHelper.COLUMN_PRICE))));
				
				event.setFree(Boolean.parseBoolean(cursor.getString(cursor
						.getColumnIndex(DatabaseOpenHelper.COLUMN_ISFREE))));				
				events.add(event);
				Log.i(LOGTAG, "Retrieved event from db");
			}
		}
		cursor.close();
		return events;
	}
	
	private static DateTime parseDateTime(String input) {
		String pattern = KillerboneUtils.KILLERBONE_DATE_FORMAT;
		DateTime dateTime = DateTime.parse(input,
				DateTimeFormat.forPattern(pattern));
		return dateTime;
	}
	
	public void clearTable() {
		database.execSQL("DELETE FROM " + DatabaseOpenHelper.TABLE_EVENTS);
	}
}
