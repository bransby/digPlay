package com.database;

import java.io.File;
import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.util.Log;

import com.businessclasses.Field;
import com.businessclasses.GamePlan;
import com.businessclasses.Player;
import com.db4o.Db4o;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.AndroidSupport;
import com.db4o.config.Configuration;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Query;

public final class DigPlayDB extends Application{
	private static ObjectContainer playsDB;
	private static ObjectContainer gamePlanDB;

	private final Context context;
	private static DigPlayDB instance;

	public DigPlayDB(Context context){
		this.context = context;

		//creates the embedded database config
		//final EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		//config.common().add(new AndroidSupport());
		
		//creates and deletes the database files (checks location)
		//new File(context.getFilesDir().getAbsolutePath(), "/PlaysDB.db4o").delete();
		//new File(context.getFilesDir().getAbsolutePath(), "/GamePlansDB.db4o").delete();

		//logs the databases paths
		Log.d("db",	"" + context.getFilesDir().getAbsolutePath());

		//creates and/or loads the databases
		EmbeddedConfiguration config =  Db4oEmbedded.newConfiguration();
		config.common().objectClass(Field.class).objectField("_playName").indexed(true);
		config.common().objectClass(Field.class).objectField("_image").indexed(true);
		
		playsDB = Db4oEmbedded.openFile(config, context.getFilesDir().getAbsolutePath() + "/PlaysDB.db4o");
		gamePlanDB = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), context.getFilesDir().getAbsolutePath() + "/GamePlansDB.db4o");
	
	}

	//gets the instance of this class if it is null, otherwise returns this instance.
	public static synchronized DigPlayDB getInstance(Context context){
		if(instance == null){
			instance = new DigPlayDB(context);
		}
		return instance;
	}

	/////////////////////////////////////////////////////////////////////
	//Plays database stuffz
	/////////////////////////////////////////////////////////////////////


	//completely empties the database
	public void emptyDB(){
		ObjectSet result = playsDB.queryByExample(new Object());
		while(result.hasNext()){
			playsDB.delete(result.next());
		}
		playsDB.commit();
	}
	
	public int getPlaysDBSize(){
		return this.getAllPlays().size();
	}
	public Field getPlayByInt(int x){
		return this.getAllPlays().get(x);
	}

	//stores the play given the field
	public boolean storePlay(Field field){
		if(field != null){
			playsDB.store(field);
			playsDB.commit();
			Log.d("added play", "play added to db");	
			return true;
		}
		else{
			Log.e("field input null", "play not added to db");
			return false;
		}
	}	

	//deletes a play given the play name
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

	//gets the play from the database by the given play name
	
	public Field getPlayByName(String name){
		Field obj = new Field();
		obj.setPlayName(name);

		ObjectSet result = playsDB.queryByExample(obj);

		if(result.hasNext()){
			return (Field) result.next();
		}
		return null;
	}
	
	
	public boolean playNameExists(String name){
		Field obj = new Field();
		obj.setPlayName(name);
		ObjectSet result = playsDB.queryByExample(obj);
		
		if(result.hasNext()){
			return true;
		}
		else{
			return false;
		}
	}
	
	public ArrayList<Field> getAllPlays(){
		ArrayList<Field> temp = new ArrayList<Field>();
		Field obj = new Field();
		
		ObjectSet result = playsDB.queryByExample(obj);
		
		while(result.hasNext()){
			temp.add((Field)result.next());
		}
		return temp;		
	}
	
	public ArrayList<String> getAllPlayNames(){
		ArrayList<String> temp = new ArrayList<String>();
		Field obj = new Field();
		
		ObjectSet result = playsDB.queryByExample(obj);
		
		while(result.hasNext()){
			temp.add(((Field)result.next()).toString());
		}
		return temp;		
	}

	//takes the old name and new name as arguments and changes the play to newName
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

	//takes the new arraylist of players and updates the given play, used if
	public boolean changePlayers(String playName, ArrayList<Player> newPlayers){
		Field found = null;

		Field obj = new Field();
		obj.setPlayName(playName);

		ObjectSet<Field> result = playsDB.queryByExample(obj);

		if(result.hasNext()){
			found = result.next();
			found.removeAllPlayers();

			for(int i = 0; i < newPlayers.size(); ++i){		
				found.addPlayer(newPlayers.get(i).getLocation(), newPlayers.get(i).getPosition(), newPlayers.get(i).getRoute(), newPlayers.get(i).getPath());
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
	//game plan stuffz
	////////////////////////////////////////////////////////////

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

	//takes gameplan name as input and return an arraylist of the images of the plays in the gameplan
	public ArrayList<Bitmap> getImagesOfGameplan(String gp){
		ArrayList<Bitmap> images = new ArrayList<Bitmap>();
		GamePlan found = null;

		GamePlan obj = new GamePlan();
		obj.setGamePlanName(gp);

		ObjectSet<GamePlan> result = gamePlanDB.queryByExample(obj);

		if(result.hasNext()){
			found = result.next();

			for(int i = 0; i < found.getGamePlan().size(); i++){
				images.add(this.getPlayByName(found.getGamePlan().get(i)).getImage());
			}
		}
		return images;
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
