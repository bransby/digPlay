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
import android.graphics.Color;
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
	private Field newField;
	private Image newImage;
	
	private PopupWindow popUp;
	private TextView text;
	private Button button;
	private LinearLayout layout;
	private LayoutParams params;
	boolean click = true;
	
	private TextView enterName;
	private TextView enterFormation;
	private TextView enterType;
	
	private Spinner selectFormation;
	
	private ArrayList<String> formations;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.save);
	    setControls();
	    setText();
	}

	private void setText() {
		enterName = (TextView)findViewById(R.id.save_enter_name);
		enterFormation = (TextView)findViewById(R.id.save_enter_formation);
		enterType = (TextView)findViewById(R.id.save_enter_type);
		
		enterName.setTextColor(Color.WHITE);
		enterFormation.setTextColor(Color.WHITE);
		enterType.setTextColor(Color.WHITE);
	}

	private void setControls() {
		playFormation = (EditText) findViewById(R.id.save_formation);
		playName = (EditText) findViewById(R.id.save_name);
		submit = (Button) findViewById(R.id.save_submit);
		playType = (Spinner) findViewById(R.id.save_play_type);
		selectFormation = (Spinner)findViewById(R.id.save_select_formation);
		
		submit.setOnClickListener(this);
		populateSpinner();
		populateFormationSpinner();
	}

	private void populateFormationSpinner() {
		//TODO: formations = Database formations - Ryan - delete next line when done
		formations = Constants.getFormations();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,formations);
		selectFormation.setAdapter(adapter);
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
					
		if(DigPlayDB.getInstance(getBaseContext()).playNameExists(name) == false){
			newImage = new Image();
			newImage.setPlayName(name);
			newImage.setImage(EditorActivity.getBitmap());
			
			
			newField = EditorActivity.getField();
			newField.setPlayName(name);
			newField.setPlayType(playType.getSelectedItem().toString());
			newField.setPlayFormation(formation);
			
			DigPlayDB.getInstance(getBaseContext()).storePlay(newField);
			DigPlayDB.getInstance(getBaseContext()).addImage(newImage);
					
			new AlertDialog.Builder(this).setMessage("Play added").setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					newField = null;
					newImage = null;
			
					System.gc();
					Intent intent = new Intent(v.getContext(), MainMenuActivity.class);
					startActivity(intent);	
				}
			}).show(); 
		}
		else{
			new AlertDialog.Builder(this).setTitle("Play name already used in playbook!").setMessage("Would you like to overwrite the play?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					newImage = new Image();
					newImage.setPlayName(name);
					newImage.setImage(EditorActivity.getBitmap());
					
					newField = EditorActivity.getField();
					newField.setPlayName(name);
					newField.setPlayType(playType.getSelectedItem().toString());
					newField.setPlayFormation(formation);
					
					DigPlayDB.getInstance(getBaseContext()).overwritePlay(newField);
					DigPlayDB.getInstance(getBaseContext()).overwriteImage(name, newImage);
					
					newField = null;
					newImage = null; 
					
					System.gc();
					
					Intent intent = new Intent(v.getContext(), MainMenuActivity.class);
					startActivity(intent);	
				}
			}).setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					playName.setText("");
				}
			}).show();	
		}

		//DigPlayDB.getInstance(getBaseContext()).emptyDB();
	}
}
