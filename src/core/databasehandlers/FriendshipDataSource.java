package core.databasehandlers;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.osmdroid.util.GeoPoint;

import core.models.Event;
import core.models.Friendship;
import core.models.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FriendshipDataSource {
	private static final String LOGTAG = "IP13HVA";

	private static final String[] friendshipColumns = {
			DatabaseOpenHelper.COLUMN_ID, DatabaseOpenHelper.COLUMN_STATUS,
			DatabaseOpenHelper.COLUMN_INITIATOR_ID,
			DatabaseOpenHelper.COLUMN_PARTICIPANT_ID };

	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;

	public FriendshipDataSource(Context context) {
		dbhelper = DatabaseOpenHelper.getInstance(context);
	}

	public void open() {
		database = dbhelper.getWritableDatabase();
	}

	public void close() {
		dbhelper.close();
	}

	public void addFriendship(Friendship friendship) {
		if (DatabaseHasRows()) {
			Cursor cursor = database.rawQuery(
					"SELECT * FROM " + DatabaseOpenHelper.TABLE_FRIENDSHIPS
							+ " WHERE " + DatabaseOpenHelper.COLUMN_ID + " = "
							+ friendship.getId(), null);
			if (cursor != null) {
				database.execSQL("DELETE FROM "
						+ DatabaseOpenHelper.TABLE_FRIENDSHIPS + " WHERE "
						+ DatabaseOpenHelper.COLUMN_ID + " = "
						+ friendship.getId());
				database.execSQL("DELETE FROM "
						+ DatabaseOpenHelper.TABLE_USERS + " WHERE "
						+ DatabaseOpenHelper.COLUMN_ID + " = "
						+ friendship.getParticipant().getId() + " OR "
						+ DatabaseOpenHelper.COLUMN_ID + " = "
						+ friendship.getInitiator().getId());
			}
		}

		ContentValues friendshipValues = new ContentValues();
		ContentValues initiatorValues = getUserContentValues(friendship
				.getInitiator());
		ContentValues participantValues = getUserContentValues(friendship
				.getParticipant());

		// Friendship values
		friendshipValues.put(DatabaseOpenHelper.COLUMN_ID, friendship.getId());
		friendshipValues.put(DatabaseOpenHelper.COLUMN_STATUS,
				friendship.getStatus());
		friendshipValues.put(DatabaseOpenHelper.COLUMN_INITIATOR_ID, friendship
				.getInitiator().getId());
		friendshipValues.put(DatabaseOpenHelper.COLUMN_PARTICIPANT_ID,
				friendship.getParticipant().getId());
		
		Log.d("insert id", String.valueOf(initiatorValues.get(DatabaseOpenHelper.COLUMN_ID)));
		database.insert(DatabaseOpenHelper.TABLE_FRIENDSHIPS, null,
				friendshipValues);
		database.insert(DatabaseOpenHelper.TABLE_USERS, null, initiatorValues);
		database.insert(DatabaseOpenHelper.TABLE_USERS, null, participantValues);

	}

	public List<Friendship> getAllFriendships() {
		List<Friendship> friendships = new ArrayList<Friendship>();
		Cursor cursor = database.query(DatabaseOpenHelper.TABLE_FRIENDSHIPS,
				friendshipColumns, null, null, null, null, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Friendship friendship = new Friendship();

				friendship.setId(cursor.getLong(cursor
						.getColumnIndex(DatabaseOpenHelper.COLUMN_ID)));
				friendship.setStatus(cursor.getString(cursor
						.getColumnIndex(DatabaseOpenHelper.COLUMN_STATUS)));

				String initiatorId = String
						.valueOf(cursor.getLong(cursor
								.getColumnIndex(DatabaseOpenHelper.COLUMN_INITIATOR_ID)));
				Cursor initiatorCursor = database.rawQuery("select * from "
						+ DatabaseOpenHelper.TABLE_USERS + " WHERE "
						+ DatabaseOpenHelper.COLUMN_ID + " = " + initiatorId,
						null);
				
				String participantId = String
						.valueOf(cursor.getLong(cursor
								.getColumnIndex(DatabaseOpenHelper.COLUMN_PARTICIPANT_ID)));
				Cursor participantCursor = database.rawQuery("select * from "
						+ DatabaseOpenHelper.TABLE_USERS + " WHERE "
						+ DatabaseOpenHelper.COLUMN_ID + " = " + participantId,
						null);
				
				Log.d("dbcursor ding","" + participantId);
				Log.d("dbcursor ding2", initiatorId);
				
				friendship.setParticipant(getUserfromCursor(participantCursor));
				friendship.setInitiator(getUserfromCursor(initiatorCursor));

				friendships.add(friendship);
			}
		}
		
		return friendships;
	}

	public void clearFriendshipTable() {
		database.execSQL("DELETE FROM " + DatabaseOpenHelper.TABLE_FRIENDSHIPS);
	}

	public void clearUserTable() {
		database.execSQL("DELETE FROM " + DatabaseOpenHelper.TABLE_USERS);
	}

	public boolean DatabaseHasRows() {
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ DatabaseOpenHelper.TABLE_FRIENDSHIPS + ", "
				+ DatabaseOpenHelper.TABLE_USERS, null);
		if (cursor.getCount() > 0) {
			return true;
		}
		return false;
	}

	public ContentValues getUserContentValues(User user) {
		ContentValues userValues = new ContentValues();

		userValues.put(DatabaseOpenHelper.COLUMN_ID, user.getId());
		userValues.put(DatabaseOpenHelper.COLUMN_USERNAME, user.getUsername());
		userValues.put(DatabaseOpenHelper.COLUMN_EMAIL, user.getEmail());
		userValues.put(DatabaseOpenHelper.COLUMN_LATITUDE, user.getLatitude());
		userValues.put(DatabaseOpenHelper.COLUMN_LONGITUDE,
				user.getLongtitude());
		userValues.put(DatabaseOpenHelper.COLUMN_REFRESH_DATE, user.getRefreshDate());
		
		Log.d("tralala", "" + user.getId());

		return userValues;
	}
	
	public User getUserfromCursor(Cursor cursor){
		User user = new User();
		cursor.moveToFirst();
		user.setId(cursor.getLong(cursor.getColumnIndex(DatabaseOpenHelper.COLUMN_ID)));
		user.setUsername(cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.COLUMN_USERNAME)));
		user.setEmail(cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.COLUMN_EMAIL)));
		user.setLatitude(cursor.getDouble(cursor.getColumnIndex(DatabaseOpenHelper.COLUMN_LATITUDE)));
		user.setLongtitude(cursor.getDouble(cursor.getColumnIndex(DatabaseOpenHelper.COLUMN_LONGITUDE)));
		user.setRefreshDate(cursor.getString(cursor.getColumnIndex(DatabaseOpenHelper.COLUMN_REFRESH_DATE)));
		
		return user;
	}

}
