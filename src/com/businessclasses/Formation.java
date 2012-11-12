package com.businessclasses;

public class Formation {
	Field theField;
	String formationName;
	public Formation(String name, Field f){
		theField = f.clone();
		theField.clearRoutes();
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
	public void flipFormation(){
		theField.flip();
	}
}
