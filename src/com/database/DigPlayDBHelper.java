package com.database;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.util.Log;

import com.businessclasses.Field;
import com.businessclasses.GamePlan;
import com.businessclasses.Player;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class DigPlayDBHelper {
	String dpPath;
	ObjectContainer playsDB;
	ObjectContainer gamePlanDB;

	//opens the database
	private boolean openDB(String name){
		if(name != null){
			playsDB = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), name);
			return true;
		}
		return false;
	}

	//closes the database
	private void closeDB(){
		playsDB.close();
	}

	//completely empties the database
	public void emptyDB(){
		ObjectSet result = playsDB.queryByExample(new Object());
		while(result.hasNext()){
			playsDB.delete(result.next());
		}
	}

	//Store a new play to the database
	public void storePlay(Field field){
		playsDB.store(field);
		playsDB.commit();
		Log.d("added play", "play added to db");	
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
	
	//open game play database
	private boolean openGamePlanDB(String name){
		if(name != null){
			gamePlanDB = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), name);
			return true;
		}
		return false;
	}

	//closes the database
	private void closeGamePlanDB(){
		gamePlanDB.close();
	}

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
