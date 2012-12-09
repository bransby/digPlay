package com.businessclasses;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import android.graphics.Bitmap;

public class Formation implements Serializable{
	Field theField;
	String formationName;
	byte[] image;
	
	public Formation(String name, Field f, Bitmap _image){
		theField =f; //f.clone();
		theField.clearRouteLocations();
		formationName = name;
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		_image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		this.image = stream.toByteArray();
	}
	public Formation(String name, Field f){
		theField = f;
		formationName = name;
	}
	public Formation(){};
	
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
	public byte[] getImage(){
		return this.image;
	}
}