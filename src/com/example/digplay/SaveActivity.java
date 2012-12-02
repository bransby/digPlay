package com.example.digplay;

import java.util.ArrayList;

import com.businessclasses.Constants;
import com.businessclasses.Field;
import com.businessclasses.Image;
import com.database.DigPlayDB;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
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

	public void onClick(final View v) {
		
		name = playName.getText().toString();
		formation = playFormation.getText().toString();
		Field newField = EditorActivity.getField();
		newField.setPlayName(name);
		newField.setPlayType(playType.getSelectedItem().toString());
		newField.setPlayFormation(formation);
			
		Image newImage = new Image();
		newImage.setPlayName(name);
		newImage.setImage(EditorActivity.getBitmap());
		
		if(DigPlayDB.getInstance(getBaseContext()).playNameExists(name) == false && DigPlayDB.getInstance(getBaseContext()).storePlay(newField) == true){
			DigPlayDB.getInstance(getBaseContext()).addImage(newImage);
			newField = null;
			newImage = null;
			
			new AlertDialog.Builder(this).setMessage("Play added").setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					System.gc();
					Intent intent = new Intent(v.getContext(), MainMenuActivity.class);
					startActivity(intent);	
				}
			}).show(); 
		
			//newField = null;
			//newImage = null;
			//System.gc();
			
			//Intent intent = new Intent(v.getContext(), MainMenuActivity.class);
			//startActivity(intent);
		}
		else{
			new AlertDialog.Builder(this).setMessage("Play name already used in playbook!").setNegativeButton("Cancel", null).setPositiveButton("OK", null).show(); 
			playName.setText("");
		}

		//DigPlayDB.getInstance(getBaseContext()).emptyDB();
	}
}
