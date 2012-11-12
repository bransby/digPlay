package com.example.digplay;

import java.util.ArrayList;

import com.businessclasses.Constants;
import com.businessclasses.Field;
import com.businessclasses.PlayAdapter;
import com.businessclasses.Sort;
import com.database.DigPlayDB;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class PlayViewActivity extends Activity implements OnItemClickListener, OnClickListener {
	private ListView playList;
	private Spinner playSort;
	private Spinner gamePlans;
	private Button refineSearch;
	private PlayAdapter _adapter; 
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.playview);
	    setListView();
	    setSpinners();
	    setButtons();
	}
	private void setSpinners() {
		playSort = (Spinner)findViewById(R.id.playview_sort_by);
		gamePlans = (Spinner)findViewById(R.id.playview_gameplan);
	
		ArrayList<String> playTypes = Constants.getPlayTypes();
		ArrayList<String> listOfGamePlans = Constants.getGamePlans(); 
		
		ArrayAdapter<String> playTypeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,playTypes);
		ArrayAdapter<String> gamePlanAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listOfGamePlans);
		
		playSort.setAdapter(playTypeAdapter);
		gamePlans.setAdapter(gamePlanAdapter);
	}
	private void setListView() {
		playList = (ListView)findViewById(R.id.playviewlist);
		ArrayList<Field> plays = new ArrayList<Field>();
		plays = getDummyPlays();
		PlayAdapter adapter = new PlayAdapter (this,R.layout.listview_item_row,plays);
		_adapter = adapter;
		playList.setAdapter(_adapter);
		playList.setOnItemClickListener(this);
	}
	private ArrayList<Field> getDummyPlays() {
		ArrayList<Field> plays = new ArrayList<Field>();
		for(int i = 0;i < 15;i++){
			Field newPlay = new Field();
			newPlay.setPlayName("Play Number " + i);
			if(i % 2 == 0){
				newPlay.setPlayType("RUN");
			}else newPlay.setPlayType("PASS");
			plays.add(newPlay);
		}
		return plays;
	}
	public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
		Field play = (Field) adapter.getItemAtPosition(position);
		Intent intent = new Intent(v.getContext(),BrowsingActivity.class);
		intent.putExtra("playName", play.getPlayName());
		startActivity(intent);
	}
	private void setButtons(){
		refineSearch = (Button)findViewById(R.id.playview_refine_search);
		refineSearch.setOnClickListener(this);
	}
	public void onClick(View v) {
		Sort s = new Sort();
		PlayAdapter adapter = new PlayAdapter(this,R.layout.listview_item_row,DigPlayDB.getInstance(getBaseContext()).getAllPlays());
		//PlayAdapter adapter = new PlayAdapter(this,R.layout.listview_item_row,getDummyPlays());
		String playType = (String)playSort.getSelectedItem();
		adapter = s.sortPlaysByRunPass(adapter, playType);
		playList.setAdapter(adapter);
	}
	

}