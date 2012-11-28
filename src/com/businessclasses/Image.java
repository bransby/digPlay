package com.businessclasses;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


public class Image {
	
	private byte[] _image;
	private String _playName;
	
	
	public String getPlayName(){
		return _playName;
	}
	public void setPlayName(String name){
		_playName = name;
	}

	public byte[] getImage(){
		//Bitmap temp =  BitmapFactory.decodeByteArray(this._image, 0, _image.length);
		//return temp;
		return this._image;
	}
	public void setImage(Bitmap image){
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		this._image = stream.toByteArray();
	}
}
