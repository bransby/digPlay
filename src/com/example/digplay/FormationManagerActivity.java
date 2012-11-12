package com.example.digplay;

import java.util.ArrayList;

import com.businessclasses.Constants;

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
import android.widget.Toast;

public class FormationManagerActivity extends Activity implements OnItemClickListener, OnClickListener {
	private ListView formationsList;
	private ArrayList<String> formations;
	private Button addFormation;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.formation_manager);
	    formations = Constants.getFormations();
	    setListView();
	    setButton();
	    Toast toast = Toast.makeText(this, "Choose formation to place on field", Toast.LENGTH_LONG);
	    toast.show();
	}
	private void setButton() {
		addFormation = (Button)findViewById(R.id.fm_add_formation);
		addFormation.setOnClickListener(this);
	}
	private void setListView() {
		formationsList = (ListView)findViewById(R.id.formations_list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,formations);
		formationsList.setAdapter(adapter);
		formationsList.setOnItemClickListener(this);
	}
	public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
		String selectedFormation = (String)adapter.getSelectedItem();
		Intent intent = new Intent(v.getContext(),EditorActivity.class);
		intent.putExtra("Formation", selectedFormation);
		startActivity(intent);
	}
	public void onClick(View v) {
		Intent intent  = new Intent(v.getContext(),EditorActivity.class);
		startActivity(intent);
	}
}
