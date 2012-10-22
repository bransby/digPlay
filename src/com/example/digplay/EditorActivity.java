package com.example.digplay;

import com.businessclasses.Field;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class EditorActivity extends Activity {

	private ImageView image;
	private Field theField;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.editor);
	    
	    image = (ImageView) findViewById(R.id.imageView1);
	    theField = new Field();
	    
	}
	

}
