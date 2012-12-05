package com.businessclasses;

import java.util.ArrayList;

public class GamePlan {
	
	private ArrayList<String> gamesInGamePlan = new ArrayList<String>();
	private String gamePlanName;
	
	public String getGamePlanName(){
		return gamePlanName;
	}
	public void setGamePlanName(String name){
		this.gamePlanName = name;
	}
	
	public void addPlayToGamePlan(String newPlay){
		if(!gamesInGamePlan.contains(newPlay)){
			gamesInGamePlan.add(newPlay);
		}
		else{
			//Play is already in game plan.
		}
	}
	
	public void addPlaysToGameplan(ArrayList<String> plays){
		this.gamesInGamePlan = plays;
	}
	
	public boolean removePlayFromGamePlan(String playName){
		if(gamesInGamePlan.indexOf(playName) != -1){
			gamesInGamePlan.remove(playName);
			return true;
		}
		else{
			return false;
		}
	}
	
	public ArrayList<String> getGamePlan(){
		return this.gamesInGamePlan;
	}
	
	public void clearGamePlan(){
		if(gamesInGamePlan.size() != 0){
			gamesInGamePlan.clear();
		}
	}	
}
