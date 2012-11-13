package com.businessclasses;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class Field {
	private ArrayList<Player> _playersOnField;
	private boolean _run;
	private String _playName;
	private String _playType;
	private Bitmap _bitmap;

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

	//overloading method for DB use
	public void addPlayerAndRoute(Location l, Position p, Route r){
		Player player = new Player(l,p,r);
		_playersOnField.add(player);
	}
	public void addPlayers(ArrayList<Player> newPlayers){
		this._playersOnField = newPlayers;
	}
	
	public Bitmap getImage(){
		return this._bitmap;
	}
	public void setImage(Bitmap image){
		this._bitmap = image;
	}
	public void clearField(){
		_playersOnField.clear();
	}
	public void clearRoutes(){
		for(int i=0;i<_playersOnField.size();i++){
			_playersOnField.get(i).clearRouteLocations();
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
	public void setPlayFormation(String _playFormation){
		this._playFormation = _playFordmation;
	}
	public String getPlayFormation(){
		return this._playFormation;
	public Field clone(){
		try {
			return (Field)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	public void flip(){
		for(int i = 0;i< _playersOnField.size();i++){
			Player thePlayer = _playersOnField.get(i);
			thePlayer.flipLocation();
		}
	}
	public Player getPlayer(int num){
		return _playersOnField.get(num);
	}
	public Field clone(){
		try {
			return (Field)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	public void flip(){
		for(int i = 0;i< _playersOnField.size();i++){
			Player thePlayer = _playersOnField.get(i);
			thePlayer.flipLocation();
		}
	}
	public Player getPlayer(int num){
		return _playersOnField.get(num);
	}
}
