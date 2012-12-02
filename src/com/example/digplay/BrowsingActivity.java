package com.example.digplay;

import java.util.ArrayList;
import java.util.Timer;

import com.businessclasses.Field;
import com.database.DigPlayDB;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
	private ArrayList<String> playNameList = new ArrayList<String>();
	private int counter;
	ArrayList<String> playFormationList = new ArrayList<String>();
	//ArrayList<String> test1 = new ArrayList<String>();
	ArrayList<Bitmap> test = new ArrayList<Bitmap>();
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
		playNameList = getIntent().getExtras().getStringArrayList("playList");
		//int temp = DigPlayDB.getInstance(getBaseContext()).getPlaysDBSize();	
		//Log.i("list of plays", "" + playNameList);
				
		String thePlay = getIntent().getExtras().getString("playName");
		counter = playNameList.indexOf(thePlay);
		Log.i("counter", "" + counter);
		
		byte[] test = DigPlayDB.getInstance(getBaseContext()).getImage(thePlay);	
		((TextView)findViewById(R.id.browsing_play_name)).setText(thePlay);
		page.addView(setFlipperImage(BitmapFactory.decodeByteArray(test, 0, test.length)));
		
		
	/*	
		ArrayList<Field> tmp = new ArrayList<Field>();
		tmp  = DigPlayDB.getInstance(getBaseContext()).getAllPlays();	
		
	
		for(int i = 0 ;i < tmp.size(); i++){
			//  This will create dynamic image view and add them to ViewFlipper	
			//setFlipperImage(test.get(i), test1.get(i));
			//setFlipperImage(test.get(i));
			Log.i("formation of play", "" + DigPlayDB.getInstance(getBaseContext()).getImage(tmp.get(i).getPlayFormation()));
			Log.i("play name", "" + DigPlayDB.getInstance(getBaseContext()).getImage(tmp.get(i).getPlayName()));
			
			byte[] test = DigPlayDB.getInstance(getBaseContext()).getImage(tmp.get(i).getPlayName());
			
			setFlipperImage(BitmapFactory.decodeByteArray(test, 0, test.length));
			//setFlipperImage(DigPlayDB.getInstance(getBaseContext()).getImage(tmp.get(i).getPlayName()));
			//setFlipperImage(tmp.get(i).getImage());
			//playNameList.add(DigPlayDB.getInstance(getBaseContext()).getPlayByInt(i).getPlayName());
		}
		//page.setDisplayedChild(playNameList.indexOf(getIntent().getExtras().getString("playName")));
*/
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
		//String thePlayer = getIntent().getExtras().getString("playName");
		String thePlayer = "";
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
		//page.showPrevious();
		// TODO update text UI
		//int currentIndex = page.getDisplayedChild();
		
		if(counter - 1 >= 0){
			counter -= 1;
			byte[] test = DigPlayDB.getInstance(getBaseContext()).getImage(playNameList.get(counter));	
			((TextView)findViewById(R.id.browsing_play_name)).setText(playNameList.get(counter));
			page.removeAllViews();
			page.invalidate();
			System.gc();
			page.addView(setFlipperImage(BitmapFactory.decodeByteArray(test, 0, test.length)));
			
			Log.i("counter", "" + counter);
			
			animFlipInForeward = AnimationUtils.loadAnimation(this, R.anim.flipin);
			animFlipOutForeward = AnimationUtils.loadAnimation(this, R.anim.flipout);
			animFlipInBackward = AnimationUtils.loadAnimation(this, R.anim.flipin_reverse);
			animFlipOutBackward = AnimationUtils.loadAnimation(this, R.anim.flipout_reverse);
			
			//page.showPrevious();
		}		
	}

	private void SwipeLeft(){
		page.setInAnimation(animFlipInForeward);
		page.setOutAnimation(animFlipOutForeward);
		//page.showNext();
		// TODO update text UI
		//int currentIndex = page.getDisplayedChild();
		if(counter + 1 < playNameList.size()){
			counter += 1;
			byte[] test = DigPlayDB.getInstance(getBaseContext()).getImage(playNameList.get(counter));	
			((TextView)findViewById(R.id.browsing_play_name)).setText(playNameList.get(counter));
			page.removeAllViews();
			page.invalidate();
			
			System.gc();
			page.addView(setFlipperImage(BitmapFactory.decodeByteArray(test, 0, test.length)));
			
			Log.i("counter", "" + counter);
			
			animFlipInForeward = AnimationUtils.loadAnimation(this, R.anim.flipin);
			animFlipOutForeward = AnimationUtils.loadAnimation(this, R.anim.flipout);
			animFlipInBackward = AnimationUtils.loadAnimation(this, R.anim.flipin_reverse);
			animFlipOutBackward = AnimationUtils.loadAnimation(this, R.anim.flipout_reverse);
			//page.showNext();
		}
		
		
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
	
	private View setFlipperImage(Bitmap image){	
		ImageView _image = new ImageView(getApplicationContext());
		_image.setImageBitmap(image);
		return _image;
		//page.addView(_image);
		//Log.d("db", "" + image);
	}
}

