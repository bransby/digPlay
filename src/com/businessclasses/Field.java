package com.businessclasses;

import java.util.ArrayList;

public class Field {
	private ArrayList<Player> _playersOnField;
	private boolean _run;
	private String _playName;
	private String _playType;

	public String getPlayName(){
		return _playName;
	}
	public void setPlayName(String name){
		_playName = name;
	}

	public Field() {
		_playersOnField = new ArrayList<Player>();
	}
	
	public ArrayList<Player> getAllPlayers()
	{
		return _playersOnField;
	}
	
	public void addPlayer(Location l, Position p){
		Player player = new Player(l,p);
		_playersOnField.add(player);
	}
	public void clearField(){
		_playersOnField.clear();
	}
	public void clearRoutes(){
		for(int i=0;i<_playersOnField.size();i++){
			_playersOnField.get(i).clearRoute();
		}
	}
	public boolean removePlayer(Player p){
		for(int i=0;i<_playersOnField.size();i++){
			if(_playersOnField.get(i).equals(p)){
				_playersOnField.remove(i);
				return true;
			}
		}
		return false;
	}
	public void changePlayerRoute(Player p,Route r){
		p.changeRoute(r);
	}
	public void removeAllPlayers(){
		_playersOnField.clear();
	}
	public boolean isRun() {
		return _run;
	}
	public void setRun(boolean _run) {
		this._run = _run;
	}
	public String getPlayType() {
		return _playType;
	}
	public void setPlayType(String _playType) {
		this._playType = _playType;
	}
}
