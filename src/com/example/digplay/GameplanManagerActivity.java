package com.example.digplay;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GameplanManagerActivity extends Activity implements OnItemClickListener, OnClickListener {
	private ListView gameplanLV;
	private ListView playbookLV;
	private ArrayList<String> playbookPlays;
	private ArrayList<String> gameplanPlays;
	private Button delete;
	private boolean deletePressed;
	private TextView gpHeader;
	private TextView pbHeader;
	private int positionSelected;
	private Context deleteContext;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.gameplan_manager);
	    setTexts();
	    playbookPlays = new ArrayList<String>();
	    gameplanPlays = new ArrayList<String>();
	    playbookPlays.add("play 1");
	    playbookPlays.add("play 2");
	    playbookPlays.add("play 3");
	    setListViews();
	    setButton();
	    deletePressed = false;
	    startNotification();
	}
	private void setButton() {
		delete = (Button) findViewById(R.id.gm_delete);
	    delete.setOnClickListener(this);
	    delete.setBackgroundColor(Color.LTGRAY);
	}
	private void startNotification() {
		Toast message = Toast.makeText(this, "Tap plays in playbook list to add to gameplan", Toast.LENGTH_LONG);
		message.show();
	}
	private void setTexts() {
		gpHeader = (TextView)findViewById(R.id.gp_manager_gp_text);
		pbHeader = (TextView)findViewById(R.id.gp_manager_pb_text);
		gpHeader.setTextColor(Color.WHITE);
		pbHeader.setTextColor(Color.WHITE);
	}
	private void setListViews() {
		gameplanLV = (ListView) findViewById(R.id.gm_gameplan);
		playbookLV = (ListView) findViewById(R.id.gm_playbook);
		ArrayAdapter<String> playbookAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,playbookPlays);  
		ArrayAdapter<String> gameplanAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,gameplanPlays); 
		playbookLV.setAdapter(playbookAdapter);
		gameplanLV.setAdapter(gameplanAdapter);
		playbookLV.setOnItemClickListener(this);
		gameplanLV.setOnItemClickListener(this);
		playbookLV.setBackgroundColor(Color.WHITE);
		gameplanLV.setBackgroundColor(Color.WHITE);
	}
	
	public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
		boolean isGamePlanClicked = false;
		if(adapter.getId() == gameplanLV.getId())isGamePlanClicked = true;
		if(!deletePressed){
			String playSelected = (String) adapter.getItemAtPosition(position);
			if(!gameplanPlays.contains(playSelected)){
				gameplanPlays.add(playSelected);
				ArrayAdapter<String> thisAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,gameplanPlays);
				gameplanLV.setAdapter(thisAdapter);
				Toast.makeText(this, "Play has been added to the gameplan",Toast.LENGTH_SHORT).show();
			}else{
				Builder alert = new AlertDialog.Builder(this);
				alert.setTitle("Caution");
				alert.setMessage("This play has already been added to the game plan");
				alert.setNeutralButton("Close", null);
				alert.show();
			}
		}else if(isGamePlanClicked){
			positionSelected = position;
			deleteContext = this;
			verifyDelete();
		}
	}
	private void verifyDelete() {
		Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Caution");
		alert.setMessage("Are you sure you want to delete this play from the gameplan?");
		alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				gameplanPlays.remove(positionSelected);
				ArrayAdapter<String> thisAdapter = new ArrayAdapter<String>(deleteContext,android.R.layout.simple_list_item_1,gameplanPlays);
				gameplanLV.setAdapter(thisAdapter);
			}
		});
		alert.setNegativeButton("Cancel", null);
		alert.show();
	}
	public void onClick(View v) {
		if(!deletePressed){
			delete.setBackgroundColor(Color.YELLOW);
			delete.setText("Tap play to delete");
			deletePressed = true;
		}else{
			delete.setBackgroundColor(Color.LTGRAY);
			delete.setText("Select to delete Play");
			deletePressed = false;
		}
	}
}
