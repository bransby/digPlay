package com.database;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.util.Log;

import com.businessclasses.Field;
import com.businessclasses.Formation;
import com.businessclasses.GamePlan;
import com.businessclasses.Image;
import com.businessclasses.Player;
import com.db4o.Db4o;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.AndroidSupport;
import com.db4o.config.Configuration;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ext.ExtObjectSet;
import com.db4o.query.Query;
import com.db4o.ta.TransparentActivationSupport;

public final class DigPlayDB extends Application{
	private static ObjectContainer playsDB;
	private static ObjectContainer imageDB;
	private static ObjectContainer gamePlanDB;
	private static ObjectContainer formationDB;

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
		//config.common().objectClass(Field.class).objectField("_image").indexed(true);
		config.common().objectClass(Field.class).indexed(true);
		config.common().objectClass(Field.class).cascadeOnUpdate(true);
		config.common().objectClass(Field.class).cascadeOnDelete(true);
		//config.common().objectClass(Field.class).objectField("_image").cascadeOnActivate(true);
		config.common().objectClass(Field.class).objectField("_playName").cascadeOnActivate(true);
		config.common().add(new TransparentActivationSupport());
		
		
		playsDB = Db4oEmbedded.openFile(config, context.getFilesDir().getAbsolutePath() + "/PlaysDB.db4o");
		imageDB = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), context.getFilesDir().getAbsolutePath() + "/imageDB.db4o");
		gamePlanDB = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), context.getFilesDir().getAbsolutePath() + "/GamePlansDB.db4o");
		formationDB = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), context.getFilesDir().getAbsolutePath() + "/formationDB.db4o");
	}

	//gets the instance of this class if it is null, otherwise returns this instance.
	public static synchronized DigPlayDB getInstance(Context context){
		if(instance == null){
			instance = new DigPlayDB(context);
		}
		return instance;
	}

	/////////////////////////////////////////////////////////////////////
	//formation database stuffz
	/////////////////////////////////////////////////////////////////////


	public ArrayList<Formation> getFormations(){
		ArrayList<Formation> temp = new ArrayList<Formation>();
		

		ObjectSet result = formationDB.queryByExample(new Formation());
		while(result.hasNext()){
			temp.add((Formation) result.next());
		}
		return temp;
	}
	
	public void storeFormation(Formation form){
		if(form != null){
			formationDB.store(form);
			formationDB.commit();
		}
		else{}
	}
	
	public ArrayList<String> getFormationNames(){
		ArrayList<String> temp = new ArrayList<String>();
		
		ObjectSet result = formationDB.queryByExample(new Formation());
		while(result.hasNext()){
			temp.add(((Formation)result.next()).getName());
		}
		return temp;
	}

	/////////////////////////////////////////////////////////////////////
	//image database stuffz
	/////////////////////////////////////////////////////////////////////

	public void addImage(Image image){
		if(image != null){
			imageDB.store(image);
			imageDB.commit();
		}
	}

	public byte[] getImage(String playName){
		Image obj = new Image();
		obj.setPlayName(playName);

		ObjectSet result = imageDB.queryByExample(obj);
		if(result.hasNext()){
			return ((Image) result.next()).getImage();
		}
		return null;
	}
	
	public boolean overwriteImage(String playName, Image image){
		if(image != null){
			Image found = null;
			
			Image newImage = new Image();
			newImage.setPlayName(image.getPlayName());
			
			ObjectSet result = imageDB.queryByExample(newImage);
			
			if(result.hasNext()){
				found = (Image) result.next();
				Log.i("old play image", "" + found.getImage().toString());
				imageDB.delete(found);
				imageDB.store(image);
				Log.i("new play image", "" + found.getImage().toString());
				imageDB.commit();
				return true;
			}
			else{
				Log.i("image check isnt finding", "fuck");
			}
		}
		return false;
	}
	

	/////////////////////////////////////////////////////////////////////
	//Plays database stuffz
	/////////////////////////////////////////////////////////////////////


	//completely empties the database
	public void emptyDB(){
		ObjectSet result = playsDB.queryByExample(new Field());
		while(result.hasNext()){
			playsDB.delete(result.next());
		}
		playsDB.commit();
	}
	
	public int getPlaysDBSize(){
		ObjectSet result = playsDB.queryByExample(new Field());
		Log.i("getsize " , "" + result.size());
		return result.size();
	}
	
	/*
	public Bitmap getPlayByInt(int x){		
		ObjectSet result = playsDB.queryByExample(new Field());
		Log.i("getplaybyint result" , "" + result.get(x));
		return ((Field) result.get(x)).getImage();
	}
	*/
	
	public int getIndexByPlayName(String playName){
		Field obj = new Field();
		obj.setPlayName(playName);
		return playsDB.queryByExample(obj).indexOf(this);
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
	
	public boolean overwritePlay(Field field){
		if(field != null){
			Field found = null;
			Field newField = new Field();
			newField.setPlayName(field.getPlayName());
			
			ObjectSet result = playsDB.queryByExample(newField);
			
			if(result.hasNext()){
				found = (Field) result.next();
				playsDB.delete(found);
				playsDB.store(field);
				playsDB.commit();
				return true;
			}
		}
		return false;
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
		
		Long start = System.nanoTime();
		
		ArrayList<String> temp = new ArrayList<String>();
		Field obj = new Field();
		
		ObjectSet result = playsDB.queryByExample(obj);
		
		for(int i = 0 ; i < result.size(); i ++){
			temp.add(((Field)result.get(i)).getPlayName());
		}
		
		Long end = System.nanoTime();
		Log.i("getting all plays names", "" + ((end - start)/1000000));
		
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



	////////////////////////////////////////////////////////////
	//game plan stuffz
	////////////////////////////////////////////////////////////

	//completely empties the database
	public void emptyGamePlanDB(){
		ObjectSet result = gamePlanDB.queryByExample(new Object());
		while(result.hasNext()){
			gamePlanDB.delete(result.next());
		}
		gamePlanDB.commit();
	}

	//store the game plan in the database
	public boolean storeGamePlan(GamePlan gamePlan){
		GamePlan obj = new GamePlan();
		obj.setGamePlanName(gamePlan.getGamePlanName());
		
		ObjectSet result = gamePlanDB.queryByExample(obj);
		
		if(!result.hasNext()){
			gamePlanDB.store(gamePlan);
			gamePlanDB.commit();
			Log.d("added game plan", "game plan added to db");	
			return true;
		}
		else{
			GamePlan gp = (GamePlan) result.next();
			gp.addPlaysToGameplan(gamePlan.getGamePlan());
			deleteGamePlan(gp.getGamePlanName());
			gamePlanDB.store(gp);
			gamePlanDB.commit();
			return true;
		}
	}


	public ArrayList<String> getAllGamePlans(){
		ArrayList<String> temp = new ArrayList<String>();
		GamePlan found = null;
		
		GamePlan obj = new GamePlan();
		ObjectSet<GamePlan> result = gamePlanDB.queryByExample(obj);
		while(result.hasNext()){
			temp.add(result.next().getGamePlanName());
		}
		return temp;
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
				//images.add(this.getPlayByName(found.getGamePlan().get(i)).getImage());
			}
		}
		return images;
	}
	
	public ArrayList<String> getPlaysInGameplan(String gameplanName){
		GamePlan found = null;
		
		GamePlan obj = new GamePlan();
		obj.setGamePlanName(gameplanName);
		
		ObjectSet<GamePlan> result = gamePlanDB.queryByExample(obj);
		
		if(result.hasNext()){
			found = result.next();
			return found.getGamePlan();
		}
		return new ArrayList<String>();
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
			gamePlanDB.commit();
			return true;
		}
		else{
			return false;
		}
	}
	
	public void removePlayFromGameplan(String gameplan, String playName){
		GamePlan found = null;

		GamePlan obj = new GamePlan();
		obj.setGamePlanName(gameplan);

		ObjectSet<GamePlan> result = gamePlanDB.queryByExample(obj);

		if(result.hasNext()){
			found = result.next();
			found.removePlayFromGamePlan(playName);
			gamePlanDB.commit();
		}
	}
	
	public void addPlayToGameplan(String gameplan, String playName){
		GamePlan found = null;

		GamePlan obj = new GamePlan();
		obj.setGamePlanName(gameplan);

		ObjectSet<GamePlan> result = gamePlanDB.queryByExample(obj);

		if(result.hasNext()){
			found = (GamePlan)result.next();
			found.addPlayToGamePlan(playName);
			gamePlanDB.commit();
		}
	}
}
