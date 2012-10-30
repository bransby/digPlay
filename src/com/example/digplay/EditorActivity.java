package com.example.digplay;

import com.businessclasses.Field;
import com.businessclasses.Location;
import com.businessclasses.Player;
import com.businessclasses.Position;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
public class EditorActivity extends Activity {

	private static Field field; // the one field
	static Location playerLoc; // location of player
	static float density; // density coefficient
	static int playerIndex = -1; // index of array for which player has been selected
	
	static int x; // for locating screen X value
	static int y; // for locating screen Y value
	
	static DrawView drawView; // custom view
	
	static Button save;
	static Button clearRoutes;
	static Button clearField;

	static Spinner routeType;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editor);

		drawView = (DrawView) findViewById(R.id.DrawView);
		
		save = (Button) findViewById(R.id.save);
		clearRoutes = (Button) findViewById(R.id.clear_routes);
		clearField = (Button) findViewById(R.id.clear_field);

		routeType = (Spinner) findViewById(R.id.route_type);
		
		routeType.setEnabled(false);
		routeType.setClickable(false);

		ArrayAdapter<CharSequence> routeTypeAdapter = ArrayAdapter.createFromResource(this, R.array.route_type_array, R.layout.spinner_layout);
		routeTypeAdapter.setDropDownViewResource(R.layout.spinner_layout);
 		routeType.setAdapter(routeTypeAdapter);
	}
	
	public void onBtnClicked(View v) {
		switch(v.getId()) {
			case R.id.save:
				break;
			case R.id.clear_routes:
				field.clearRoutes();
				playerIndex = -1; // reset player selection index
				drawView.invalidate(); // redraw the screen
				break;
			case R.id.clear_field:
				field.clearField();
				playerIndex = -1; // reset player selection index
				drawView.invalidate(); // redraw the screen
				break;
			default:
				break;
		}
	}
	
	public static class DrawView extends View implements OnTouchListener {

		Bitmap fieldBitmap;
		Canvas c;
		Paint paint;
		int createdPlayerIndex; // this is the index of the 8 players on the
								// bottom of the stuff
		
		// this field is used to just store the bottom 8 "players"
		// that users can drag onto the field
		Field fieldForCreatePlayer;
		
		// locations of the 8 players
		Location playerLocQB;
		Location playerLocWR;
		Location playerLocRB;
		Location playerLocFB;
		Location playerLocTE;
		Location playerLocC;
		Location playerLocG;
		Location playerLocT;

		public DrawView(Context context, AttributeSet attrs) {
			super(context, attrs);
			build(context, attrs);
		}

		public void build(Context context, AttributeSet attrs)
		{
			// this gets the density coefficient.
			density = getResources().getDisplayMetrics().density;
						
			fieldForCreatePlayer = new Field();
			
			short adjustedHeight = 670+50; // pixel location we want to draw the 8
										   // created players at
			
			// add players at bottom of screen, 75dp width between each of them
			playerLocQB = new Location((int)(75*density), (int)(adjustedHeight*density));
			fieldForCreatePlayer.addPlayer(playerLocQB, Position.QUARTERBACK);
			
			playerLocWR = new Location((int)(150*density), (int)(adjustedHeight*density));
			fieldForCreatePlayer.addPlayer(playerLocWR, Position.WIDE_RECIEVER);
			
			playerLocRB = new Location((int)(225*density), (int)(adjustedHeight*density));
			fieldForCreatePlayer.addPlayer(playerLocRB, Position.RUNNING_BACK);
			
			playerLocFB = new Location((int)(300*density), (int)(adjustedHeight*density));
			fieldForCreatePlayer.addPlayer(playerLocFB, Position.FULLBACK);
			
			playerLocTE = new Location((int)(375*density), (int)(adjustedHeight*density));
			fieldForCreatePlayer.addPlayer(playerLocTE, Position.TIGHT_END);
			
			playerLocC = new Location((int)(450*density), (int)(adjustedHeight*density));
			fieldForCreatePlayer.addPlayer(playerLocC, Position.CENTER);
			
			playerLocG = new Location((int)(525*density), (int)(adjustedHeight*density));
			fieldForCreatePlayer.addPlayer(playerLocG, Position.GUARD);
			
			playerLocT = new Location((int)(600*density), (int)(adjustedHeight*density));
			fieldForCreatePlayer.addPlayer(playerLocT, Position.TACKLE);
			
			createdPlayerIndex = -1; // index of which of the 8 players has been selected
			
			// the main field
			field = new Field();

			// the field picture
			fieldBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.one_two_eight_zero_by_eight_zero_zero, null);
			paint = new Paint();

			this.setOnTouchListener(this);
		}

		@Override 
		protected void onDraw(Canvas canvas) {
			super.onDraw(c);

			paint.setColor(Color.BLACK);
			
			c = canvas;
			c.drawBitmap(fieldBitmap, 50*density,
					60*density, null);

			// orangish color
			paint.setColor(0xFFFF8000);

			// y values are all the same, so just reuse one
			float eightPlayerY = playerLocQB.getY()-(50*density);
			
			// the canvas does is not exactly the same as the real pixels, because
			// the canvas is drawn at 50 pixels down from the top of the screen
			c.drawCircle(playerLocQB.getX(), eightPlayerY, 25*density, paint);
			c.drawCircle(playerLocWR.getX(), eightPlayerY, 25*density, paint);
			c.drawCircle(playerLocRB.getX(), eightPlayerY, 25*density, paint);
			c.drawCircle(playerLocFB.getX(), eightPlayerY, 25*density, paint);
			c.drawCircle(playerLocTE.getX(), eightPlayerY, 25*density, paint);
			c.drawCircle(playerLocC.getX(), eightPlayerY, 25*density, paint);
			c.drawCircle(playerLocG.getX(), eightPlayerY, 25*density, paint);
			c.drawCircle(playerLocT.getX(), eightPlayerY, 25*density, paint);
						
			paint.setColor(Color.BLACK);
			
			paint.setTextAlign(Align.CENTER);
			paint.setTextSize(25*density);
			
			// descent and ascent are used for centering text vertically
			float height = (playerLocQB.getY()-50*density)-((paint.descent() + paint.ascent()) / 2);
			
			c.drawText("QB", playerLocQB.getX(), height, paint);
			c.drawText("WR", playerLocWR.getX(), height, paint);
			c.drawText("RB", playerLocRB.getX(), height, paint);
			c.drawText("FB", playerLocFB.getX(), height, paint);
			c.drawText("TE", playerLocTE.getX(), height, paint);
			c.drawText("C", playerLocC.getX(), height, paint);
			c.drawText("G", playerLocG.getX(), height, paint);
			c.drawText("T", playerLocT.getX(), height, paint);
			
			// orangish color
			paint.setColor(0xFFFF8000);
			
			int xpos = -1;
			int ypos = -1;
			
			for (int i = 0; i < field.getAllPlayers().size(); i++)
			{
				xpos = field.getAllPlayers().get(i).getLocation().getX();
				// -50, because 50 pixels are used at the top of the screen on all android devices
				// for the time and app name
				ypos = field.getAllPlayers().get(i).getLocation().getY()-50;
				// this is the selected player
				if (playerIndex == i)
				{
					paint.setColor(Color.RED);
				}
				// draw the play again, but red this time
				c.drawCircle(xpos, ypos, 25*density, paint);
				// descent and ascent are used for centering text vertically
				height = ypos-((paint.descent() + paint.ascent()) / 2);
				paint.setColor(Color.BLACK);
				switch(field.getAllPlayers().get(i).getPosition()) {
				case QUARTERBACK:
					drawCenteredText("QB", xpos, height);
					break;
				case WIDE_RECIEVER:
					drawCenteredText("WR", xpos, height);
					break;
				case RUNNING_BACK:
					drawCenteredText("RB", xpos, height);
					break;
				case FULLBACK:
					drawCenteredText("FB", xpos, height);
					break;
				case TIGHT_END:
					drawCenteredText("TE", xpos, height);
					break;
				case CENTER:
					drawCenteredText("C", xpos, height);
					break;
				case GUARD:
					drawCenteredText("G", xpos, height);
					break;
				case TACKLE:
					drawCenteredText("T", xpos, height);
					break;
				default:
					break;
				}
				// reset to orangish color
				paint.setColor(0xFFFF8000);
			}
			
			// blue color
			paint.setColor(0xFF000080);
			// 5 pixel line width
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(5*density);
			// draw line of scrimmage
			canvas.drawLine(50*density, 475*density, 1230*density, 475*density, paint);
			// fill = fill enclosed shapes with the color, like a circle with the middle one color
			paint.setStyle(Paint.Style.FILL);
		}

		// for drawing the positions on players
		public void drawCenteredText(String value, int x, float height)
		{
			c.drawText(value, x, height, paint);
		}
		
		public boolean onTouch(View v, MotionEvent event) {

			x = (int) (event.getRawX()*density);
			y = (int) (event.getRawY()*density);
			int playerXPos = -1;
			int playerYPos = -1;

			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				boolean staticPlayerSelected = false;
				// loop for selecting bottom 8 players
				for (int i = 0; i < fieldForCreatePlayer.getAllPlayers().size(); i++)
				{
					playerXPos = fieldForCreatePlayer.getAllPlayers().get(i).getLocation().getX();
					playerYPos = fieldForCreatePlayer.getAllPlayers().get(i).getLocation().getY();
					// calculate distance between user click and this player
					double dist = Math.sqrt(((playerXPos-x)*(playerXPos-x)) 
							+ ((playerYPos-y)*(playerYPos-y)));
					if (dist < 25)
					{
						// this player has been selected
						Player temp = fieldForCreatePlayer.getAllPlayers().get(i);
						createdPlayerIndex = i; // save location of player in array
						field.addPlayer(temp.getLocation(), temp.getPosition()); // add to field
						playerIndex = field.getAllPlayers().size()-1; // this player is the last 
																	  // player to be added to field
						routeType.setEnabled(true);
						routeType.setClickable(true);
						staticPlayerSelected = true; // flag to say that one of the 8 players has been selected
						break;
					}
				}
				if (!staticPlayerSelected)
				{
					boolean hasBeenSet = false;
					// check if one of the players has been selected
					for (int i = 0; i < field.getAllPlayers().size(); i++)
					{
						playerXPos = field.getAllPlayers().get(i).getLocation().getX();
						playerYPos = field.getAllPlayers().get(i).getLocation().getY();
						// calculate distance between user click and this player
						double distance = Math.sqrt(((playerXPos-x)*(playerXPos-x)) 
								+ ((playerYPos-y)*(playerYPos-y)));
						if (distance < 25)
						{
							// this player has been selected
							playerIndex = i;
							// routes can be changed for this player
							routeType.setEnabled(true);
							routeType.setClickable(true);
							hasBeenSet = true;
							break;
						}
					}
					// if not selected, disable the route spinner and reset player index
					if (!hasBeenSet)
					{
						playerIndex = -1;
						routeType.setEnabled(false);
						routeType.setClickable(false);
					}
				}
				invalidate(); // redraw
				break;
			case MotionEvent.ACTION_UP:
				if (playerIndex != -1)
				{
					// is player outside of the field?
					if (x < 75*density || x > 1205*density || y < 135*density || y > 655*density)
					{
						field.getAllPlayers().remove(playerIndex);
						playerIndex = -1; // reset selection
						// disable route possibilities
						routeType.setEnabled(false);
						routeType.setClickable(false);
						invalidate(); // redraw
					}
				}
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				break;
			case MotionEvent.ACTION_POINTER_UP:
				break;
			case MotionEvent.ACTION_MOVE:
				// update the player location when dragging
				if (playerIndex != -1)
				{
					field.getAllPlayers().get(playerIndex).setLocation(new Location(x, y));
				}
				invalidate(); // redraw
				break;
			default:
				break;
			}
			return true;
		}
	}
}
