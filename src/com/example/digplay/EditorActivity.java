package com.example.digplay;

import com.businessclasses.Field;
import com.businessclasses.Location;
import com.businessclasses.Player;
import com.businessclasses.Position;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class EditorActivity extends Activity {
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(new DrawView(this));
	}
	
	public class DrawView extends View implements OnTouchListener {

		Field field;
		Bitmap fieldBitmap;
		private int _xDelta;
		private int _yDelta;
		Canvas c;
		Paint p;
		Location playerLoc;
		float density = getResources().getDisplayMetrics().density;
		
	    public DrawView(Context context) {
	        super(context);
	        /*
	        this.setLayoutParams(new LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,
	        		RelativeLayout.LayoutParams.FILL_PARENT)); 
	        		*/
	        fieldBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fb, null);
	        p = new Paint();
	        field = new Field();
	        addPlayer();
	    }

		@Override 
	    protected void onDraw(Canvas canvas) {
	        //super.onDraw(canvas);
	        
	        p.setColor(0xFFFF8000);
	        canvas.drawBitmap(fieldBitmap, 90*density,
	        		70*density, null);
	        
	        canvas.drawCircle(70*density, 70*density,
	        		15*density, p);
	        this.setOnTouchListener(this);
	    }
		
		private void addPlayer() {
			playerLoc = new Location((int) (70*density), (int) (70*density));
			field.addPlayer(playerLoc, Position.QUARTERBACK);
		}
		
		public boolean onTouch(View v, MotionEvent event) {
		    
			final int x = (int) event.getRawX();
			final int y = (int) event.getRawY();
			
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
	        	case MotionEvent.ACTION_DOWN:
	            	_xDelta = (int) (x*density);
	            	_yDelta = (int) (y*density);
	            	double distance = Math.sqrt(((70-_xDelta)*(70-_xDelta)) + ((120-_yDelta)*(120-_yDelta)));
	            	System.out.println(_xDelta);
	            	System.out.println(_yDelta);
	            	System.out.println(distance);
	            	if (distance < 15)
	            	{
	            		System.out.println("HI");
	            	}
	            	break;
	        	case MotionEvent.ACTION_UP:
	            	break;
	        	case MotionEvent.ACTION_POINTER_DOWN:
	            	break;
	        	case MotionEvent.ACTION_POINTER_UP:
	            	break;
	        	case MotionEvent.ACTION_MOVE:
	            	//RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.getLayoutParams();
	            	//layoutParams.leftMargin = x - _xDelta;
	            	//layoutParams.topMargin = y - _yDelta;
	            	//this.setLayoutParams(layoutParams);
	            	break;
			}
			return true;
		}
	}

}
