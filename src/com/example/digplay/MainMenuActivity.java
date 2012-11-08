package com.example.digplay;

import com.database.DigPlayDBHelper;
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
	private Button viewGameplan;
	private Button createGameplan;
	private Button lookAtPlaybook;
	//public static DigPlayDBHelper db = new DigPlayDBHelper();
	//public String test = this.getDir("data", 0) + "/";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        setButtons();  
        
        //DigPlayDBHelper app = (DigPlayDBHelper) DigPlayDBHelper.getApp();
        //app.onCreate();
       
        //db.openDB("PlaysDB");
        //db.openGamePlanDB("GameplanDB");
        
        //if(db.openDB("PlaysDB") == true){Log.d("playsDb", "playsDB open");}
        //if(db.openGamePlanDB("GameplanDB") == true){Log.d("gameplanDB", "gameplanDB open");}
    }

    private void setButtons() {
		drawNewPlay = (Button) findViewById(R.id.maindrawplay);
		viewGameplan = (Button) findViewById(R.id.mainviewgameplan);
		createGameplan = (Button)findViewById(R.id.maincreategameplan);
		lookAtPlaybook = (Button)findViewById(R.id.mainlookatplaybook);
		
		drawNewPlay.setOnClickListener(this);
		viewGameplan.setOnClickListener(this);
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
    
    
/*
    private void db() {
    	try {
    		if(db.openDB("PlaysDB")){
    			Log.d("playsDB", "opened plays database");
    		}
    		else{Log.d("playsDB", "shit is fucked");}	
    		//System.out.println("playsopen");

    		if(db.openGamePlanDB("GamePlanDB")){
    			Log.d("gameplanDB", "opened gameplan database");
    		}
    		else{Log.d("gameplanDB", "shit is fucked");}
    		//System.out.println("gameplansopen");
    	}finally{}
    	//catch (Exception e) {
    	//	Log.e("db", "unable to open database");
    	//}
    }

*/
    public void onClick(View v) {
    	int buttonPressed = v.getId();
    	Intent intent = null;
    	if(buttonPressed == drawNewPlay.getId())intent = new Intent(v.getContext(),EditorActivity.class);
    	else if(buttonPressed == viewGameplan.getId())intent = new Intent(v.getContext(),PlayViewActivity.class);
    	else if(buttonPressed == createGameplan.getId())intent = new Intent(v.getContext(),GameplanManagerActivity.class);
    	else intent = new Intent(v.getContext(),PlayViewActivity.class);
    	startActivity(intent);
    }
}
