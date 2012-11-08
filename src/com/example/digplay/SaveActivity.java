package com.example.digplay;

import java.util.ArrayList;

import com.businessclasses.Constants;
import com.businessclasses.Field;
import com.database.DigPlayDBHelper;
import com.database.anotherTest;
import com.database.testingClass;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.db4o.*;


public class SaveActivity extends Activity implements OnClickListener {
	private EditText playFormation;
	private EditText playName;
	private Button submit;
	private Spinner playType;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.save);
	    setControls();
	}

	private void setControls() {
		playFormation = (EditText) findViewById(R.id.save_formation);
		playName = (EditText) findViewById(R.id.save_name);
		submit = (Button) findViewById(R.id.save_submit);
		playType = (Spinner) findViewById(R.id.save_play_type);
		
		submit.setOnClickListener(this);
		populateSpinner();
	}

	private void populateSpinner() {
		ArrayList<String> playTypes = Constants.getPlayTypes();
		playTypes.remove(0);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,playTypes);
		playType.setAdapter(adapter);
	}

	public void onClick(View v) {
		String formation = playFormation.getText().toString();
		String name = playName.getText().toString();
		String type = playType.getSelectedItem().toString();
		//TODO:send to database
		
		Field newField = new Field();
		newField.setPlayName(name);
		newField.setPlayType(type);
		
		System.out.println(newField.getPlayName());
		System.out.println(newField.getPlayType());
		/*
		//DigPlayDBHelper app = (DigPlayDBHelper)getApplicationContext();
		DigPlayDBHelper test = new DigPlayDBHelper();
		
		//System.out.println("in save " + getApplication().toString());
		DigPlayDBHelper app = (DigPlayDBHelper) test.getInstance().getApplicationContext();
       
		ObjectContainer PlaysDB = app.playsDB();
        app.getInstance().storePlay(newField);
        
        ObjectContainer GamePlanDB = app.gamePlanDB();
		
		//MainMenuActivity.db.storePlay(newField);
		
		//DigPlayDBHelper testing = new DigPlayDBHelper();
		//testing.openDB("testing");
		//testing.storePlay(newField);
		 */
		//testingClass.storePlay(newField);
		
		anotherTest.getInstance().storePlay(newField);
	}

}
