package com.example.digplay;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.businessclasses.Constants;
import com.businessclasses.Field;
import com.businessclasses.GamePlan;
import com.businessclasses.PlayAdapter;
import com.businessclasses.Sort;
import com.database.DigPlayDB;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.digplay.EmailPlaybook;

public class PlayViewActivity extends Activity implements OnItemClickListener, OnClickListener, OnItemSelectedListener {
	private ListView playList;
	private Spinner playSort;
	private Spinner gamePlans;
	private Button refineSearch;
	private PlayAdapter _adapter; 
	private TextView title;
	private TextView playTypeTitle;
	private TextView gamePlanTitle;
	public static ArrayList<Field> plays = new ArrayList<Field>();
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.playview);
	    setSpinners();
	    setButtons();
	    setText();
	    setListView();
	}
	
	private void email() throws IOException {
		String emailText = "This email includes the following Play Types: " + (String)playSort.getSelectedItem() + 
				"\nFrom the gameplan: " + (String)gamePlans.getSelectedItem();
		String subject = (String)playSort.getSelectedItem() + " from ";
				
		// TODO: save image to file system, and add the file paht to atachment
		ArrayList<String> attachments = DigPlayDB.getInstance(getBaseContext()).getAllPlayNames();
		ArrayList<String> attachmentPath = new ArrayList<String>();
		for (int att = 0; att < attachments.size(); att++) 
		{

			attachmentPath.add(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/playbook/" + ((TextView)findViewById(att)).getText().toString() + ".jpeg");
			File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/playbook/" + ((TextView)findViewById(att)).getText().toString() + ".jpeg");
			
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(DigPlayDB.getInstance(getBaseContext()).getImage(((TextView)findViewById(R.id.browsing_play_name)).getText().toString()));
			fos.close();

		}

		EmailPlaybook.EmailWithSingleAttachment(this, "krebsba4@gmail.com", subject, emailText, attachmentPath);
	}
	
	
	private void setText() {
		title = (TextView)findViewById(R.id.pv_title);
		playTypeTitle = (TextView)findViewById(R.id.pv_play_type_title);
		gamePlanTitle = (TextView)findViewById(R.id.pv_gameplan_title);
		title.setTextColor(Color.WHITE);
		playTypeTitle.setTextColor(Color.WHITE);
		gamePlanTitle.setTextColor(Color.WHITE);
		
	}
	private void setSpinners() {
		playSort = (Spinner)findViewById(R.id.playview_sort_by);
		gamePlans = (Spinner)findViewById(R.id.playview_gameplan);
		ArrayList<String> playTypes = Constants.getPlayTypes();
		ArrayList<String> listOfGamePlans = new ArrayList<String>();
		listOfGamePlans.add("All Gameplans");
		listOfGamePlans.addAll(DigPlayDB.getInstance(getBaseContext()).getAllGamePlans());
		
		ArrayAdapter<String> playTypeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,playTypes);
		ArrayAdapter<String> gamePlanAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listOfGamePlans);
		playSort.setAdapter(playTypeAdapter);
		gamePlans.setAdapter(gamePlanAdapter);

		playSort.setOnItemSelectedListener(new OnItemSelectedListener() 
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long i) 
            {
            	updateList();
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });
		gamePlans.setOnItemSelectedListener(new OnItemSelectedListener() 
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long i) 
            {
            	updateList();
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });
	}
	private void setListView() {
		playList = (ListView)findViewById(R.id.playviewlist);
		PlayAdapter adapter = new PlayAdapter (this,R.layout.listview_item_row,DigPlayDB.getInstance(getBaseContext()).getAllPlays());
		_adapter = adapter;
		playList.setAdapter(_adapter);
		playList.setOnItemClickListener(this);
	}
	public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
		ArrayList<String> playNameList = new ArrayList<String>();
		for(int i = 0; i < this.plays.size(); i ++){
			playNameList.add(this.plays.get(i).getPlayName());
		}
		
		Field play = (Field) adapter.getItemAtPosition(position);
		Intent intent = new Intent(v.getContext(),BrowsingActivity.class);
		intent.putStringArrayListExtra("playList", playNameList);
		intent.putExtra("playName", play.getPlayName());
		startActivity(intent);
	}
	
	private void setButtons(){
		refineSearch = (Button)findViewById(R.id.playview_email_current_selection);
		refineSearch.setOnClickListener(this);
	}
	public void onClick(View v) {
		try {
			email();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void updateList()
	{
		// get plays
		Sort s = new Sort();
		PlayAdapter adapter = new PlayAdapter(this,R.layout.listview_item_row,DigPlayDB.getInstance(getBaseContext()).getAllPlays());
		
		// get selections from spinners
		String playType = (String)playSort.getSelectedItem();
		String playbook = (String)gamePlans.getSelectedItem();
		
		// filter by selection
		ArrayList<String> listOfPlaysInGameplan = DigPlayDB.getInstance(getBaseContext()).getPlaysInGameplan(playbook);
		adapter = s.sortPlaysByRunPass(adapter, playType);
		adapter = s.sortPlaysByPlaybook(adapter, playbook, listOfPlaysInGameplan);
		playList.setAdapter(adapter);
	}
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {}
	public void onNothingSelected(AdapterView<?> arg0) {}
}
