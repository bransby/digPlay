package com.businessclasses;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Field {
	private ArrayList<Player> _playersOnField;
	private boolean _run;
	private String _playName;
	private String _playType;
	private byte[] _image;

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
	       return BitmapFactory.decodeByteArray(this._image, 0, _image.length);
	}
	public void setImage(Bitmap image){
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		this._image = stream.toByteArray();
	}
	public void clearField(){
		_playersOnField.clear();
	}
	public void clearRouteLocations(){
		for(int i=0;i<_playersOnField.size();i++){
			_playersOnField.get(i).clearRouteLocations();
		}
	}
	public void clearRoutes(Route route)
	{
		for(int i=0;i<_playersOnField.size();i++){
			_playersOnField.get(i).changeRoute(route);
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
	/*public void setPlayFormation(String _playFormation){
		this._playFormation = _playFordmation;
	}
	public String getPlayFormation(){
		return this._playFormation;
	}*/
	public Field clone(){
		try {
			return (Field)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	public void flip(int width){
		for(int i = 0;i< _playersOnField.size();i++){
			Player thePlayer = _playersOnField.get(i);
			thePlayer.flipLocation(width);
		}
	}
	public Player getPlayer(int num){
		return _playersOnField.get(num);
	}

}
