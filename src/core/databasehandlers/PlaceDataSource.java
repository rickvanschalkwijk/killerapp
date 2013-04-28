package core.databasehandlers;

import java.util.ArrayList;
import java.util.List;

import org.osmdroid.util.GeoPoint;

import core.models.Place;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PlaceDataSource {

	private static final String LOGTAG = "IP13HVA";

	private static final String[] columns = { DatabaseOpenHelper.COLUMN_TITLE,
			DatabaseOpenHelper.COLUMN_DESCRIPTION,
			DatabaseOpenHelper.COLUMN_LATITUDE,
			DatabaseOpenHelper.COLUMN_LONGITUDE,
			DatabaseOpenHelper.COLUMN_IMAGEURL };

	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;

	public PlaceDataSource(Context context) {
		dbhelper = DatabaseOpenHelper.getInstance(context);
	}

	public void open() {
		database = dbhelper.getWritableDatabase();
	}

	public void close() {
		dbhelper.close();
	}

	public void addPlace(Place place) {
		if (DatabaseHasRows()) {
			Cursor cursor = database.rawQuery("SELECT * FROM "
					+ DatabaseOpenHelper.TABLE_LOCATIONS + " WHERE "
					+ DatabaseOpenHelper.COLUMN_ID + " = " + place.getId(),
					null);
			if (cursor != null) {
				database.execSQL("DELETE FROM "
						+ DatabaseOpenHelper.TABLE_LOCATIONS + " WHERE "
						+ DatabaseOpenHelper.COLUMN_ID + " = " + place.getId());
			}
		}

		ContentValues values = new ContentValues();

		values.put(DatabaseOpenHelper.COLUMN_TITLE, place.getName());
		values.put(DatabaseOpenHelper.COLUMN_DESCRIPTION,
				place.getDescription());
		values.put(DatabaseOpenHelper.COLUMN_CATEGORY, place.getCategory());
		values.put(DatabaseOpenHelper.COLUMN_LATITUDE, place.getLocation()
				.getLatitudeE6() / 1E6);
		values.put(DatabaseOpenHelper.COLUMN_LONGITUDE, place.getLocation()
				.getLongitudeE6() / 1E6);
		values.put(DatabaseOpenHelper.COLUMN_IMAGEURL, place.getImageUrl());

		database.insert(DatabaseOpenHelper.TABLE_LOCATIONS, null, values);
	}

	public List<Place> getAllPlaces() {
		List<Place> places = new ArrayList<Place>();
		Cursor cursor = database.query(DatabaseOpenHelper.TABLE_LOCATIONS,
				columns, null, null, null, null, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Place place = new Place();
				place.setName(cursor.getString(cursor
						.getColumnIndex(DatabaseOpenHelper.COLUMN_TITLE)));

				place.setDescription(cursor.getString(cursor
						.getColumnIndex(DatabaseOpenHelper.COLUMN_DESCRIPTION)));
				place.setCategory(cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.COLUMN_CATEGORY)));
				Double latitude = cursor.getDouble(cursor
						.getColumnIndex(DatabaseOpenHelper.COLUMN_LATITUDE));
				Double longitude = cursor.getDouble(cursor
						.getColumnIndex(DatabaseOpenHelper.COLUMN_LONGITUDE));
				place.setLocation(new GeoPoint(latitude, longitude));
				place.setImageUrl(cursor.getString(cursor
						.getColumnIndex(DatabaseOpenHelper.COLUMN_IMAGEURL)));

				places.add(place);
			}
		}
		cursor.close();
		return places;
	}

	public void clearTable() {
		database.execSQL("DELETE FROM " + DatabaseOpenHelper.TABLE_LOCATIONS);
	}

	public boolean DatabaseHasRows() {
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ DatabaseOpenHelper.TABLE_LOCATIONS, null);
		if (cursor.getCount() > 0) {
			return true;
		}
		return false;
	}

}
