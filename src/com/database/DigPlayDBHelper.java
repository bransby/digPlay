package com.database;

import java.io.File;
import java.util.ArrayList;

import android.app.Application;
import android.app.ListActivity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.util.Log;

import com.businessclasses.Field;
import com.businessclasses.GamePlan;
import com.businessclasses.Player;
import com.db4o.*;
import com.db4o.config.AndroidSupport;
import com.db4o.config.EmbeddedConfiguration;
import com.example.digplay.MainMenuActivity;


public class DigPlayDBHelper extends Application {
	private static DigPlayDBHelper singleton;
	
	private volatile ObjectContainer playsDB;
	private volatile ObjectContainer gamePlanDB;
	String testing12 = "in db " + this.toString();
	
	
	public DigPlayDBHelper getInstance(){
		return singleton;
	}
	
	public void onCreate(){
		super.onCreate();
		singleton = this;
		
		final EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.common().add(new AndroidSupport());
		this.playsDB = Db4oEmbedded.openFile(config, this.getFilesDir().getPath() + "PlaysDB.db4o");
		
		//new File(this.getFilesDir().getPath() + "GamePlanDB.db4o").delete();
		
		this.gamePlanDB = Db4oEmbedded.openFile(config, this.getFilesDir().getPath() + "GamePlanDB.db4o");
			
		if(this.playsDB == null){
			Log.e("test", "playsDB is null");
		}
		
		System.out.println("in db " + getApplicationInfo().toString());
	}
	
	
	public void onTerminate(){
		super.onTerminate();
		
		playsDB.rollback();
		gamePlanDB.rollback();
		playsDB.close();
		gamePlanDB.close();
	}
	
	public ObjectContainer playsDB(){
		return playsDB;
	}
	public ObjectContainer gamePlanDB(){
		return gamePlanDB;
	}
	
	//public String test = this.getDir("data", 0) + "/";
	
	//String test = this.getFilesDir().getPath();

	/*
	//opens the database
	public boolean openDB(String name){
		if(name != null){
			if(this.getFilesDir().getPath() == null){
				Log.d("db", "path is null scrub");
			}
			//String path = "/data/data/" + name + "/database/" + name + ".db4o";
			
			//new File(System.getProperty("user.home") + "/testing1.db4o").delete();
			new File(this.getFilesDir().getPath() + name + ".db4o").delete();
			
			final EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
			config.common().add(new AndroidSupport());
			
			playsDB = Db4oEmbedded.openFile(config, this.getFilesDir().getPath() + name + ".db4o");
			
			//playsDB = Db4oEmbedded.openFile(config, System.getProperty("user.home") + "/testing1.db4o");
			//playsDB = Db4oEmbedded.openFile(config, "/data/data/" + name + "/database/" + name + ".db4o");
			return true;
		}
		return false;
	}
	
	
	
	//closes the database
	public void closeDB(){
		playsDB.close();
	}
*/
	//completely empties the database
	public void emptyDB(){
		ObjectSet result = playsDB.queryByExample(new Object());
		while(result.hasNext()){
			playsDB.delete(result.next());
		}
	}

	//Store a new play to the database
	public void storePlay(Field field){
		if(field != null){
			if(playsDB == null){
				Log.d("db", "database is null");
			}
			playsDB.store(field);
			playsDB.commit();
			Log.d("added play", "play added to db");	
		}
		else{
			Log.e("field input null", "play not added to db");
		}
	}

	//Delete a play from the database
	public boolean deletePlay(String playName){
		Field found = null;

		Field obj = new Field();
		obj.setPlayName(playName);

		ObjectSet<Field> result = playsDB.queryByExample(obj);

		if(result.hasNext()){
			found = result.next();
			playsDB.delete(found);
			return true;
		}
		else{
			return false;
		}
	}

	//Get the stored play according to the plays name
	public Field getPlayByName(String name){
		Field obj = new Field();
		obj.setPlayName(name);

		ObjectSet result = playsDB.queryByExample(obj);

		if(result.hasNext()){
			return (Field) result.next();
		}
		return null;
	}

	//takes plays old name and changes it to a new name
	public boolean changePlayName(String oldName, String newName){
		Field found = null;
		Field obj = new Field();
		obj.setPlayName(oldName);

		ObjectSet<Field> result = playsDB.queryByExample(obj);
		if(result.hasNext()){
			found = result.next();
			found.setPlayName(newName);
			playsDB.store(found);
			playsDB.commit();
			return true;
		}
		return false;
	}

	//changes the field to a new field (updating players, etc.)
	public boolean changePlayers(String playName, ArrayList<Player> newPlayers){
		Field found = null;

		Field obj = new Field();
		obj.setPlayName(playName);

		ObjectSet<Field> result = playsDB.queryByExample(obj);

		if(result.hasNext()){
			found = result.next();
			found.removeAllPlayers();

			for(int i = 0; i < newPlayers.size(); ++i){		
				found.addPlayerAndRoute(newPlayers.get(i).getLocation(), newPlayers.get(i).getPosition(), newPlayers.get(i).getRoute());
				playsDB.store(found);
				playsDB.commit();
			}
			return true;
		}
		else{
			return false;
		}
	}

	//inputs new image if the field changes
	public boolean changeImage(String playName, Bitmap newImage){
		Field found = null;

		Field obj = new Field();
		obj.setPlayName(playName);

		ObjectSet<Field> result = playsDB.queryByExample(obj);

		if(result.hasNext()){
			found = result.next();
			found.setImage(newImage);
			playsDB.store(found);
			playsDB.commit();

			return true;
		}
		else{
			return false;
		}
	}

	////////////////////////////////////////////////////////////
	//game plan
	///////////////////////////////////////////////
	
	/*
	//open game play database
	public boolean openGamePlanDB(String name){
		if(name != null){
			//gamePlanDB = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), name);
			
			new File(this.getFilesDir().getPath() + name + ".db4o").delete();
			
			final EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
			config.common().add(new AndroidSupport());
			
			playsDB = Db4oEmbedded.openFile(config, this.getFilesDir().getPath() + name + ".db4o");
			return true;
		}
		return false;
	}

	//closes the database
	public void closeGamePlanDB(){
		gamePlanDB.close();
	}
*/
	//completely empties the database
	public void emptyGamePlanDB(){
		ObjectSet result = gamePlanDB.queryByExample(new Object());
		while(result.hasNext()){
			gamePlanDB.delete(result.next());
		}
	}

	//store the game plan in the database
	public void storeGamePlan(GamePlan gamePlan){
		gamePlanDB.store(gamePlan);
		gamePlanDB.commit();
		Log.d("added game plan", "game plan added to db");	
	}
	
	//delete game play from database
	public boolean deleteGamePlan(String gamePlanName){
		GamePlan found = null;

		GamePlan obj = new GamePlan();
		obj.setGamePlanName(gamePlanName);

		ObjectSet<GamePlan> result = gamePlanDB.queryByExample(obj);

		if(result.hasNext()){
			found = result.next();
			gamePlanDB.delete(found);
			return true;
		}
		else{
			return false;
		}
	}
}
