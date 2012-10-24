package com.example.digplay;

import com.businessclasses.Field;
import com.businessclasses.Location;
import com.businessclasses.Position;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class EditorActivity extends Activity implements OnTouchListener {

	private ImageView fieldImage;
	private Field theField;
	private int _xDelta;
	private int _yDelta;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.editor);
	    
	    fieldImage = (ImageView) findViewById(R.id.imageView1);
	    theField = new Field();
	    Location tempLoc = new Location(180, 200);
	    theField.addPlayer(tempLoc, Position.QUARTERBACK);

	    System.out.println(fieldImage.getWidth());
	    fieldImage.setOnTouchListener(this);
	}

	public boolean onTouch(View v, MotionEvent event) {
		final int x = (int) event.getRawX();
		final int y = (int) event.getRawY();
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_DOWN:
            RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) fieldImage.getLayoutParams();
            _xDelta = x - lParams.leftMargin;
            _yDelta = y - lParams.topMargin;
            break;
        case MotionEvent.ACTION_UP:
            break;
        case MotionEvent.ACTION_POINTER_DOWN:
            break;
        case MotionEvent.ACTION_POINTER_UP:
            break;
        case MotionEvent.ACTION_MOVE:
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) fieldImage.getLayoutParams();
            layoutParams.leftMargin = x - _xDelta;
            layoutParams.topMargin = y - _yDelta;
            fieldImage.setLayoutParams(layoutParams);
            break;
		}
		return true;
	}
}
