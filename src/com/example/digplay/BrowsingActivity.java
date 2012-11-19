package com.example.digplay;

import java.util.ArrayList;

import com.database.DigPlayDB;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;

public class BrowsingActivity extends Activity implements OnClickListener {
	private TextView playName;
	private Button editPlay;
	
	ArrayList<String> playNameList = new ArrayList<String>();
	ArrayList<String> playFormationList = new ArrayList<String>();
	ViewFlipper page;

	Animation animFlipInForeward;
	Animation animFlipOutForeward;
	Animation animFlipInBackward;
	Animation animFlipOutBackward;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browsing);
		setTextView();
		setImageView();
		setButtons();
		
	}

	private void setImageView() {
		page = (ViewFlipper) findViewById(R.id.viewFlipper1);
		
		int temp = DigPlayDB.getInstance(getBaseContext()).getPlaysDBSize();
		for(int i=0;i<temp; i++)
		{
			setFlipperImage(DigPlayDB.getInstance(getBaseContext()).getPlayByInt(i).getImage());
			playNameList.add(DigPlayDB.getInstance(getBaseContext()).getPlayByInt(i).getPlayName());
		}
		page.setDisplayedChild(playNameList.indexOf(getIntent().getExtras().getString("playName")));
		animFlipInForeward = AnimationUtils.loadAnimation(this, R.anim.flipin);
		animFlipOutForeward = AnimationUtils.loadAnimation(this, R.anim.flipout);
		animFlipInBackward = AnimationUtils.loadAnimation(this, R.anim.flipin_reverse);
		animFlipOutBackward = AnimationUtils.loadAnimation(this, R.anim.flipout_reverse);
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
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	public void onClick(View v) {
		Intent intent = new Intent(v.getContext(),EditorActivity.class);
		startActivity(intent);
	}

	private void SwipeRight(){
		page.setInAnimation(animFlipInBackward);
		page.setOutAnimation(animFlipOutBackward);
		page.showPrevious();
		// TODO update text UI
		int currentIndex = page.getDisplayedChild();
		((TextView)findViewById(R.id.browsing_play_name)).setText(DigPlayDB.getInstance(getBaseContext()).getPlayByInt(currentIndex).getPlayName());
	}

	private void SwipeLeft(){
		page.setInAnimation(animFlipInForeward);
		page.setOutAnimation(animFlipOutForeward);
		page.showNext();
		// TODO update text UI
		int currentIndex = page.getDisplayedChild();
		((TextView)findViewById(R.id.browsing_play_name)).setText(DigPlayDB.getInstance(getBaseContext()).getPlayByInt(currentIndex).getPlayName());
	}
	SimpleOnGestureListener simpleOnGestureListener 
	= new SimpleOnGestureListener(){

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {

			float sensitvity = 50;
			if((e1.getX() - e2.getX()) > sensitvity){
				SwipeLeft();
			}else if((e2.getX() - e1.getX()) > sensitvity){
				SwipeRight();
			}

			return true;
		}

	};
	GestureDetector gestureDetector= new GestureDetector(simpleOnGestureListener);
	
	private void setFlipperImage(Bitmap image){
		Log.d("db", "" + image);
		ImageView _image = new ImageView(getApplicationContext());
		//ImageView testing = (ImageView)findViewById(R.id.imageView);
		_image.setImageDrawable(new BitmapDrawable(image));
		page.addView(_image);
	}
}

