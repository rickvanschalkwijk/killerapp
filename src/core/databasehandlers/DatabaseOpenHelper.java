/*
 * Copyright 2012 Hogeschool van Amsterdam.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Notes:
 * It's best practice to make a new DatabaseHandler for each table
 * 
 */

package core.databasehandlers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOpenHelper extends SQLiteOpenHelper{
	
	private static DatabaseOpenHelper mInstance = null;

	private static final String LOGTAG = "IP13HVA";
	
	private static final int DATABASE_VERSION = 5;
	
	private static final String DATABASE_NAME = "offlineStorage.db";
	public static final String TABLE_EVENTS = "events";
	public static final String TABLE_LOCATIONS = "locations";
	public static final String TABLE_USERS = "users";
	public static final String TABLE_FRIENDSHIPS = "friendships";
	public static final String TABLE_APPLICATION = "application";


	
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_TITLE =  "title";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_CATEGORY = "category";
	public static final String COLUMN_START_DATE = "start";
	public static final String COLUMN_END_DATE = "end";
	public static final String COLUMN_LONGITUDE = "longitude";
	public static final String COLUMN_LATITUDE = "latitude";
	public static final String COLUMN_PRICE =  "price";
	public static final String COLUMN_ISFREE = "free";
	public static final String COLUMN_IMAGEURL = "imageurl";
	public static final String COLUMN_INITIATOR_ID = "initiator_id";
	public static final String COLUMN_PARTICIPANT_ID = "participant_id";
	public static final String COLUMN_STATUS = "status";
	public static final String COLUMN_USERNAME = "username";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_REFRESH_DATE = "refresh_date";
	
	private static final String CREATE_EVENTS_TABLE = "CREATE TABLE "
			+ TABLE_EVENTS + " ("
			+ COLUMN_ID + " INTEGER, "
			+ COLUMN_TITLE + " TEXT, " 
			+ COLUMN_DESCRIPTION + " TEXT, "
			+ COLUMN_CATEGORY + " TEXT, "
			+ COLUMN_START_DATE + " TEXT, "
			+ COLUMN_END_DATE + " TEXT, "
			+ COLUMN_LONGITUDE + " REAL, "
			+ COLUMN_LATITUDE + " REAL, "
			+ COLUMN_PRICE + " TEXT, "
			+ COLUMN_ISFREE + " NUMERIC "
			+ ")";
	
	private static final String CREATE_LOCATIONS_TABLE = "CREATE TABLE " 
			+ TABLE_LOCATIONS + " ("
			+ COLUMN_ID + " INTEGER, "
			+ COLUMN_TITLE + " TEXT, "
			+ COLUMN_DESCRIPTION + " TEXT, "
			+ COLUMN_CATEGORY + " TEXT, "
			+ COLUMN_LONGITUDE + " REAL, "
			+ COLUMN_LATITUDE + " REAL, "
			+ COLUMN_IMAGEURL + " TEXT "
			+ ")";
	
	private static final String CREATE_FRIENDSHIPS_TABLE = "CREATE TABLE " 
			+ TABLE_FRIENDSHIPS + " ("
			+ COLUMN_ID + " INTEGER, "
			+ COLUMN_STATUS + " TEXT, "
			+ COLUMN_INITIATOR_ID + " INTEGER, "
			+ COLUMN_PARTICIPANT_ID + " INTEGER "
			+ ")";
	
	private static final String CREATE_USER_TABLE = "CREATE TABLE "
			+ TABLE_USERS + " ("
			+ COLUMN_ID + " INTEGER, "
			+ COLUMN_USERNAME + " TEXT, " 
			+ COLUMN_EMAIL + " TEXT, "
			+ COLUMN_LONGITUDE + " REAL, "
			+ COLUMN_LATITUDE + " REAL, "
			+ COLUMN_REFRESH_DATE + " TEXT "
			+ ")";
	
	
	public static DatabaseOpenHelper getInstance(Context ctx) {
	      
	    // Use the application context, which will ensure that you 
	    // don't accidentally leak an Activity's context.
	    if (mInstance == null) {
	      mInstance = new DatabaseOpenHelper(ctx.getApplicationContext());
	    }
	    return mInstance;
	  }
	
	private DatabaseOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}
	
	//Gets called when no database is found on the device
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_EVENTS_TABLE);
		db.execSQL(CREATE_LOCATIONS_TABLE);
		db.execSQL(CREATE_FRIENDSHIPS_TABLE);
		db.execSQL(CREATE_USER_TABLE);
	}

	//update database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDSHIPS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPLICATION);
		onCreate(db);
	}
}
