package com.businessclasses;

import java.util.ArrayList;

public class Field {
	private ArrayList<Player> _playersOnField;
	
	public void addPlayer(Location l, Position p){
		Player player = new Player(l,p);
		_playersOnField.add(player);
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
}