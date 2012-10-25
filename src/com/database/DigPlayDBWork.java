package com.database;

import java.util.ArrayList;

import com.businessclasses.Field;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DigPlayDBWork {

	private SQLiteDatabase database;
	private DigPlayDB dbHelper;
	private String[] allColumns = {DigPlayDB.COLUMN_ID,
			DigPlayDB.COLUMN_PLAYNAME, DigPlayDB.COLUMN_COORDINATES,
			DigPlayDB.COLUMN_GAMEPLANS, DigPlayDB.COLUMN_IMAGE};

	//open the database
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	//close the database
	public void close(){
		dbHelper.close();
	}
	
	//method used to add a play to the database
	public DigPlayDBData addPlay(String name, Field field, ArrayList<String> gamePlans, Byte[] image) throws SQLiteException{
		this.open();
		
		//executes sql query with given inputs
		database.execSQL("INSERT INTO " + DigPlayDB.DB_NAME + "(" + DigPlayDB.COLUMN_PLAYNAME + "," + 
				DigPlayDB.COLUMN_COORDINATES + "," + DigPlayDB.COLUMN_GAMEPLANS + 
				DigPlayDB.COLUMN_IMAGE + ")" + "VALUES (" + name + "," + field + "," + gamePlans + "," + image + ")", null);
		
		//sets the cursor to the location of the field that was just added
		Cursor cursor = database.rawQuery("SELECT " + DigPlayDB.COLUMN_PLAYNAME + " FROM " + DigPlayDB.DB_NAME +
				" WHERE " + DigPlayDB.COLUMN_PLAYNAME + " = " + name, null);
		cursor.moveToFirst();
		DigPlayDBData newData = cursorToPlay(cursor);
		cursor.close();
		this.close();
		return newData;
	}
	
	//finds the id number of the play to be deleted,
	//uses built in delete method to delete from database.
	public void deletePlay(DigPlayDBData data){
		long id = data.getId();
		System.out.println("Play deleted with id: " + id);
		database.delete(DigPlayDB.TABLE_DIGPLAY, DigPlayDB.COLUMN_ID + " = " + id, null);
	}
	
	
	//need to implement
	public DigPlayDBData changePlayName(String oldName, String newName){
		
		return null;
	}
	public DigPlayDBData changeField(String playName, Field newField){
		return null;
	}
	public DigPlayDBData addToGamePlan(String, playName, String gamePlan){
		return null;
	}
	public void removeFromGamePlan(String playName, String gamePlan){
		
		return;
	}	
	
	public byte[] getPlayImage(String playName){
		return null;	
	}
	public Field getField(String playName){
		return null;
	}
	

	
	
	

	//have to find a way to get the database values from cursor postion.
	private DigPlayDBData cursorToPlay(Cursor cursor) {
		DigPlayDBData data = new DigPlayDBData();
		data.setId(cursor.getLong(0));
		data.setPlayName(cursor.getString(1));
		/*
		data.setField();
		data.setGamePlans();
		*/
		return data;
		
	}

}
