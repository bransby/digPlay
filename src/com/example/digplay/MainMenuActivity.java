package com.example.digplay;

import com.database.DigPlayDB;
import com.db4o.*;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenuActivity extends Activity implements OnClickListener {
	
	private Button drawNewPlay;
	private Button createGameplan;
	private Button lookAtPlaybook;
	//public static DigPlayDBHelper db = new DigPlayDBHelper();
	//public String test = this.getDir("data", 0) + "/";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        setButtons();
    }

    private void setButtons() {
		drawNewPlay = (Button) findViewById(R.id.maindrawplay);
		createGameplan = (Button)findViewById(R.id.maincreategameplan);
		lookAtPlaybook = (Button)findViewById(R.id.mainlookatplaybook);
		
		drawNewPlay.setOnClickListener(this);
		createGameplan.setOnClickListener(this);
		lookAtPlaybook.setOnClickListener(this);
		/*
		drawNewPlay.setGravity(Gravity.CENTER_HORIZONTAL);
		drawNewPlay.setGravity(Gravity.CENTER_VERTICAL);
		
		viewGameplan.setGravity(Gravity.CENTER_HORIZONTAL);
		viewGameplan.setGravity(Gravity.CENTER_VERTICAL);
		createGameplan.setGravity(Gravity.CENTER_HORIZONTAL);
		createGameplan.setGravity(Gravity.CENTER_VERTICAL);
		lookAtPlaybook.setGravity(Gravity.CENTER_HORIZONTAL);
		lookAtPlaybook.setGravity(Gravity.CENTER_VERTICAL);*/
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

	public void onClick(View v) {
		int buttonPressed = v.getId();
		Intent intent = null;
		if(buttonPressed == drawNewPlay.getId())intent = new Intent(v.getContext(),FormationManagerActivity.class);
		else if(buttonPressed == createGameplan.getId())intent = new Intent(v.getContext(),GameplanManagerActivity.class);
		else intent = new Intent(v.getContext(),PlayViewActivity.class);
		startActivity(intent);
	}
}
