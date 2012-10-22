package com.example.digplay;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class BrowsingActivity extends Activity {
	private TextView playName;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.browsing);
	    setTextView();
	}

	private void setTextView() {
		playName = (TextView)findViewById(R.id.browsing_play_name);
		String thePlayer = getIntent().getExtras().getString("playName");
		playName.setText(thePlayer);
	}

}
