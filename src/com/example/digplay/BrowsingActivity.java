package com.example.digplay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BrowsingActivity extends Activity implements OnClickListener {
	private TextView playName;
	private Button editPlay;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.browsing);
	    setTextView();
	    setButtons();
	}

	private void setButtons() {
		editPlay = (Button)findViewById(R.id.browsing_edit_play);
		editPlay.setOnClickListener(this);
	}

	private void setTextView() {
		playName = (TextView)findViewById(R.id.browsing_play_name);
		String thePlayer = getIntent().getExtras().getString("playName");
		playName.setText(thePlayer);
	}

	public void onClick(View v) {
		Intent intent = new Intent(v.getContext(),EditorActivity.class);
		startActivity(intent);
	}

}
