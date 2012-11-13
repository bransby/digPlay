package com.example.digplay;

import android.app.Activity;
import android.content.Intent;
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
	private int[] pics = {R.drawable.seq1, R.drawable.seq2, R.drawable.seq3};
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
		for(int i=0;i<pics.length;i++)
		{
	     //  This will create dynamic image view and add them to ViewFlipper
			setFlipperImage(pics[i]);
	    }
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
    // TODO Auto-generated method stub
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
	   }
	  
	   private void SwipeLeft(){
	    page.setInAnimation(animFlipInForeward);
	 page.setOutAnimation(animFlipOutForeward);
	 page.showNext();
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
	    private void setFlipperImage(int res) {
	        Log.i("Set Filpper Called", res+"");
	        ImageView image = new ImageView(getApplicationContext());
	        image.setBackgroundResource(res);
	        page.addView(image);
	    }
}

