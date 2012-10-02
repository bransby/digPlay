package com.businessclasses;

public class Location {
	int _x;
	int _y;
	public Location(int x, int y){
		_x = x;
		_y = y;
	}
	public int getX(){
		return _x;
	}
	public int getY(){
		return _y;
	}
	public void changeLocation(int x, int y){
		_x = x;
		_y = y;
	}
}
