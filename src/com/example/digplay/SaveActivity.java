package com.example.digplay;

import java.util.ArrayList;

import com.businessclasses.Constants;
import com.businessclasses.Field;
import com.database.DigPlayDB;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.LinearLayout.LayoutParams;

import com.db4o.*;


public class SaveActivity extends Activity implements OnClickListener {
	private EditText playFormation;
	private EditText playName;
	private Button submit;
	private Spinner playType;
	private String formation;
	private String name;
	private String type;
	
	private PopupWindow popUp;
	private TextView text;
	private Button button;
	private LinearLayout layout;
	private LayoutParams params;
	boolean click = true;
	
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
		
		name = playName.getText().toString();
		Field newField = EditorActivity.getField();
		newField.setPlayName(name);
		newField.setPlayType(playType.getSelectedItem().toString());
		newField.setImage(EditorActivity.getBitmap());

		newField.setPlayFormation(formation);

		//newField.setPlayFormation(playFormation.getText().toString());

		if(DigPlayDB.getInstance(getBaseContext()).playNameExists(name) == false && DigPlayDB.getInstance(getBaseContext()).storePlay(newField) == true){
			new AlertDialog.Builder(this).setMessage("Play added").setPositiveButton("OK", null).show(); 

			Intent intent = new Intent(v.getContext(), MainMenuActivity.class);
			startActivity(intent);
		}
		else{
			new AlertDialog.Builder(this).setMessage("Play name already used in playbook!").setNegativeButton("Cancel", null).setPositiveButton("OK", null).show(); 
			playName.setText("");
		}



		//DigPlayDB.getInstance(getBaseContext()).emptyDB();

		//System.out.println(AnotherTest.getInstance(getBaseContext()).getPlayByName(newField.getPlayName()).getPlayName());
		//System.out.println(DigPlayDB.getInstance(getBaseContext()).getPlayByName("Testing123").getPlayName());
		//System.out.println(DigPlayDB.getInstance(getBaseContext()).getPlayByName("Testing123").getAllPlayers().toString());

	}
}
