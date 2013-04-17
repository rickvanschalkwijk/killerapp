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
	
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_NAME = "offlineStorage.db";
	public static final String TABLE_EVENTS = "events";
	public static final String TABLE_USERS = "users";
	public static final String TABLE_PLACES = "places";
	public static final String TABLE_SERVICES = "services";
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
	
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_USERNAME = "username";
	public static final String COLUMN_PASSWORD = "password";
	
	public static final String COLUMN_KEY = "key";
	public static final String COLUMN_VALUE = "value";
	
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
	
	private static final String CREATE_USERS_TABLE = "CREATE TABLE "
			+ TABLE_USERS + " (" 
			+ COLUMN_ID + " INTEGER, "
			+ COLUMN_EMAIL + " TEXT, "
			+ COLUMN_USERNAME + " TEXT, "
			+ COLUMN_PASSWORD + " TEXT "
			+ ")";
	
	private static final String CREATE_PLACES_TABLE = "CREATE TABLE " 
			+ TABLE_PLACES + " ("
			+ COLUMN_TITLE + " TEXT, "
			+ COLUMN_DESCRIPTION + " TEXT, "
			+ COLUMN_LONGITUDE + " REAL, "
			+ COLUMN_LATITUDE + " REAL "
			+ ")";
	
	private static final String CREATE_APPLICATION_TABLE = "CREATE TABLE " 
			+ TABLE_APPLICATION + " (" 
			+ COLUMN_KEY + " TEXT, " 
			+ COLUMN_VALUE + " TEXT "  
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
		Log.i(LOGTAG, "Created events table");
		db.execSQL(CREATE_USERS_TABLE);
		Log.i(LOGTAG, "Created users table");
		db.execSQL(CREATE_PLACES_TABLE);
		Log.i(LOGTAG, "Created places table");
		db.execSQL(CREATE_APPLICATION_TABLE);
		 Log.i(LOGTAG, "Created application table");
	}

	//update database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPLICATION);
		onCreate(db);
	}
}
