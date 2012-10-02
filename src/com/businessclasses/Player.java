package com.businessclasses;

public class Player {
	private Position _position;
	private Location _spotOnField;
	private Route _route;
	
	public Player(Location l, Position p){
		_spotOnField = l;
		_position = p;
		_route = null;		
	}
	
	public Position getPosition(){
		return _position;
	}
	public void setPosition(Position p){
		_position = p;
	}
	public Location getLocation(){
		return _spotOnField;
	}
	public void setLocation(Location l){
		_spotOnField = l;
	}
	public Route getRoute(){
		return _route;
	}
	public void changeRoute(Route r){
		_route = r;
	}
	public void clearRoute(){
		_route = null;
	}
}
