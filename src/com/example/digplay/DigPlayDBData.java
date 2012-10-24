package com.example.digplay;

import java.util.ArrayList;
import com.businessclasses.Field;

public class DigPlayDBData {
	
	//private vars for the database
	private long id;
	private String playName;
	private Field field;
	private ArrayList<String> gamePlans;
	
	//getters
	public long getId(){
		return id;
	}
	public String getPlayName(){
		return playName;
	}	
	public Field getField(){
		return this.field;
	}
	public ArrayList<String> getGamePlans(){
		return this.gamePlans;
	}
	
	
	//setters
	public void setId(long _id){
		this.id = _id;
	}
	public void setPlayName(String _playName){
		this.playName = _playName;
	}	
	public void setField(Field _field){
		this.field = _field;
	}
	public void setGamePlans(ArrayList<String> _gamePlans){
		this.gamePlans = _gamePlans;
	}	
	public String toString(){
		return playName;
	}
}
