package com.businessclasses;

import java.io.Serializable;

public class Formation implements Serializable{
	Field theField;
	String formationName;
	public Formation(String name, Field f){
		theField =f; //f.clone();
		theField.clearRouteLocations();
		formationName = name;
	}
	public String getName(){
		return formationName;
	}
	public void changeName(String newName){
		formationName = newName;
	}
	public Field getFormation(){
		return theField;
	}
	public void flipFormation(int width){
		theField.flip(width);
	}
}
