package com.businessclasses;

import java.util.ArrayList;

public class Player {
	private Position _position;
	private ArrayList<Location> routeLocations;
	private Location _spotOnField;
	private Route _route;
	private Path _path;
	
	public Player(Location l, Position p){
		_spotOnField = l;
		_position = p;
		_route = Route.ARROW;
		_path = Path.SOLID;
		routeLocations = new ArrayList<Location>();
	}

	//overloading constructor for db use
	public Player(Location l, Position p, Route r, Path path){
		_position = p;
		_spotOnField = l;
		_route = r;
		_path = path;
		routeLocations = new ArrayList<Location>();
	}
	
	public void addRouteLocation(Location temp)
	{
		routeLocations.add(temp);
	}
	
	public ArrayList<Location> getRouteLocations()
	{
		return routeLocations;
	}
	
	public void clearRouteLocations()
	{
		routeLocations.clear();
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
	public void clearRoute() {
		this.clearRouteLocations();
	}
	public void flipLocation(int width){
		Location l = this._spotOnField;
		int newX = (int) (width - l._x);
		l._x = newX;	
	}
	public void flipRouteLocations(int width)
	{
		for (int i = 0; i < routeLocations.size(); i++)
		{
			Location thisLoc = routeLocations.get(i);
			int newX = (int) (width - thisLoc.getX());
			thisLoc.changeLocation(newX, thisLoc.getY());
		}
	}
	public Path getPath()
	{
		return _path;
	}
	public void changePath(Path p)
	{
		_path = p;
	}
}
