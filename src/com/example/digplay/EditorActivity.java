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
		int playerIndex = -1;
		
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
	        
	        this.setOnTouchListener(this);
	    }

		@Override 
	    protected void onDraw(Canvas canvas) {
	        super.onDraw(c);
	        
	        p.setColor(0xFFFF8000);
	        c = canvas;
	        c.drawBitmap(fieldBitmap, 90*density,
	        		70*density, null);
	        
	        int xpos = -1;
	        int ypos = -1;
	        for (int i = 0; i < field.getAllPlayers().size(); i++)
	        {
	        	xpos = field.getAllPlayers().get(i).getLocation().getX();
	        	ypos = field.getAllPlayers().get(i).getLocation().getY()-50;
	        	System.out.println(xpos);
	        	System.out.println(ypos);
	        	c.drawCircle(xpos, ypos, 15*density, p);
	        }
	        
	        System.out.println("hi");
	    }
		
		private void addPlayer() {
			playerLoc = new Location(Math.round(70*density), Math.round((70+50)*density));
			field.addPlayer(playerLoc, Position.QUARTERBACK);
		}
		
		public boolean onTouch(View v, MotionEvent event) {
		    
			final int x = (int) (event.getRawX()*density);
			final int y = (int) (event.getRawY()*density);
			int playerXPos = -1;
			int playerYPos = -1;
			
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
	        	case MotionEvent.ACTION_DOWN:
	            	for (int i = 0; i < field.getAllPlayers().size(); i++)
	            	{
	            		playerXPos = field.getAllPlayers().get(i).getLocation().getX();
	            		playerYPos = field.getAllPlayers().get(i).getLocation().getY();
	            		double distance = Math.sqrt(((playerXPos-x)*(playerXPos-x)) 
	            				+ ((playerYPos-y)*(playerYPos-y)));
	            		if (distance < 15)
		            	{
		            		playerIndex = i;
		            		break;
		            	}
	            	}
	            	invalidate();
	            	break;
	        	case MotionEvent.ACTION_UP:
	        		playerIndex = -1;
	        		invalidate();
	            	break;
	        	case MotionEvent.ACTION_POINTER_DOWN:
	            	break;
	        	case MotionEvent.ACTION_POINTER_UP:
	            	break;
	        	case MotionEvent.ACTION_MOVE:
	        		if (playerIndex != -1)
	        		{
	        			field.getAllPlayers().get(playerIndex).setLocation(new Location(x, y));
	        		}
	        		invalidate();
	            	break;
			}
			return true;
		}
	}

}
