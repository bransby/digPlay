package com.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DigPlayDB extends SQLiteOpenHelper {
	
	//final vars to use with other classes
	public final static String TABLE_DIGPLAY = "PlayBook";
	public static final String COLUMN_ID = "_id";
	public final static String COLUMN_PLAYNAME = "PlayName";
	public final static String COLUMN_COORDINATES = "Coordinates";
	public final static String COLUMN_GAMEPLANS = "GamePlans";
	public final static String COLUMN_IMAGE = "Image";
	
	//database name and version
	public final static int DB_VERSION = 1;
	public final static String DB_NAME = "digPlayDB";
	
	//SQL query as a string to pass into the constructor
	private static final String DATABASE_CREATE = "CREATE TABLE" + TABLE_DIGPLAY +
		"(" +  COLUMN_ID + " integer primary key autoincrement, " + 
		COLUMN_PLAYNAME + "TEXT NOT NULL," + COLUMN_COORDINATES + "TEXT NOT NULL," +
		COLUMN_GAMEPLANS + "TEXT NOT NULL," + COLUMN_IMAGE + "BLOB";
	
	//constructor
	public DigPlayDB(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	//overrides the onCreate method.
	//creates a new database.
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}
	
	//overrides onUpgrade method
	//when upgrading to a new version, deletes the old and makes a new one.
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DigPlayDB.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		    db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
		    onCreate(db);
	}
}