package com.example.digplay;

import com.businessclasses.Field;
import com.businessclasses.Location;
import com.businessclasses.Player;
import com.businessclasses.Position;
import com.businessclasses.Route;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
public class EditorActivity extends Activity implements OnSeekBarChangeListener, OnClickListener, OnItemSelectedListener  {


	private static Field field; // the one field
	private static int playerIndex = -1; // index of array for which player has been selected
	
	private static int x; // for locating screen X value
	private static int y; // for locating screen Y value
	
	private static DrawView drawView; // custom view
	
	private static Button save;
	private static Button clearRoutes;
	private static Button clearField;

	private static Spinner routeType;
	
	private static SeekBar routeDistance;
	private static int routeYardage;
	private static TextView routeYardageTV;
	private static Button trashCan;
	
	private static float density; // density coefficient
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		density = getResources().getDisplayMetrics().density;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editor);

		drawView = (DrawView) findViewById(R.id.DrawView);
		
		save = (Button) findViewById(R.id.save);
		clearRoutes = (Button) findViewById(R.id.clear_routes);
		clearField = (Button) findViewById(R.id.clear_field);
		trashCan = (Button) findViewById(R.id.editor_trash_can);

		routeType = (Spinner) findViewById(R.id.route_type);
		routeDistance = (SeekBar)findViewById(R.id.seekBar1);
		routeYardageTV = (TextView)findViewById(R.id.editor_route_yardage);
		
		routeType.setOnItemSelectedListener(this);
		routeDistance.setOnSeekBarChangeListener(this);
		trashCan.setOnClickListener(this);
		save.setOnClickListener(this);
		
		disableAll();
		
		trashCan.setBackgroundResource(R.drawable.trashcan);
		save.setBackgroundResource(R.drawable.floppy_disk);
		routeDistance.setMax(20);
		routeYardageTV.setText("0 yds");

		ArrayAdapter<String> rtAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,this.getRoutes());
 		routeType.setAdapter(rtAdapter);
	}
	
	public void onBtnClicked(View v) {
		switch(v.getId()) {
			case R.id.save:
				break;
			case R.id.clear_routes:
				field.clearRoutes();
				field.clearYardage();
				disableAll();
				drawView.invalidate(); // redraw the screen
				break;
			case R.id.clear_field:
				field.clearField();
				disableAll();
				drawView.invalidate(); // redraw the screen
				break;
			default:
				break;
		}
	}
	
	public static void disableAll()
	{
		routeType.setEnabled(false);
		routeType.setClickable(false);
		routeDistance.setEnabled(false);
		routeDistance.setClickable(false);
		playerIndex = -1;
		routeType.setSelection(0);
		routeDistance.setProgress(0);
	}
	
	public static void enableAll()
	{
		routeType.setEnabled(true);
		routeType.setClickable(true);
		routeDistance.setEnabled(true);
		routeDistance.setClickable(true);
		routeType.setSelection(field.getAllPlayers().get(playerIndex).getRoute().ordinal());
		routeDistance.setProgress(field.getAllPlayers().get(playerIndex).getYardage());
	}
	
	public static class DrawView extends View implements OnTouchListener {
		
		private Canvas c;
		private Paint paint;
		private int createdPlayerIndex; // this is the index of the 8 players on the
								// bottom of the stuff
		
		// this field is used to just store the bottom 8 "players"
		// that users can drag onto the field
		private Field fieldForCreatePlayer;
		
		// locations of the 8 players
		private Location playerLocQB;
		private Location playerLocWR;
		private Location playerLocRB;
		private Location playerLocFB;
		private Location playerLocTE;
		private Location playerLocC;
		private Location playerLocG;
		private Location playerLocT;
		
		// static final values
		static final private float FIELD_HEIGHT = 575*density;
		static final private float LEFT_MARGIN = 40*density;
		static final private float RIGHT_MARGIN = 1240*density;
		static final private float TOP_MARGIN = 60*density;
		static final private float BOTTOM_MARGIN = TOP_MARGIN+FIELD_HEIGHT;
		static final private float PIXELS_PER_YARD = 13*density;
		static final private float FIELD_LINE_WIDTHS = 4*density;
		static final private float PLAYER_ICON_RADIUS = 25*density;
		static final private float TOP_ANDROID_BAR = 50*density;
		static final private float TOUCH_SENSITIVITY = 35*density;

		public DrawView(Context context, AttributeSet attrs) {
			super(context, attrs);
			build(context, attrs);
		}

		public void build(Context context, AttributeSet attrs)
		{			
			fieldForCreatePlayer = new Field();
			
			float adjustedHeight = (670*density)+TOP_ANDROID_BAR; // pixel location we want to draw the 8
										   // created players at. 50 pixels is used
										   // at the top for all android devices
			
			// add players at bottom of screen, 75dp width between each of them
			playerLocQB = new Location((int)(75*density), (int)(adjustedHeight));
			fieldForCreatePlayer.addPlayer(playerLocQB, Position.QUARTERBACK);
			
			playerLocWR = new Location((int)(150*density), (int)(adjustedHeight));
			fieldForCreatePlayer.addPlayer(playerLocWR, Position.WIDE_RECIEVER);
			
			playerLocRB = new Location((int)(225*density), (int)(adjustedHeight));
			fieldForCreatePlayer.addPlayer(playerLocRB, Position.RUNNING_BACK);
			
			playerLocFB = new Location((int)(300*density), (int)(adjustedHeight));
			fieldForCreatePlayer.addPlayer(playerLocFB, Position.FULLBACK);
			
			playerLocTE = new Location((int)(375*density), (int)(adjustedHeight));
			fieldForCreatePlayer.addPlayer(playerLocTE, Position.TIGHT_END);
			
			playerLocC = new Location((int)(450*density), (int)(adjustedHeight));
			fieldForCreatePlayer.addPlayer(playerLocC, Position.CENTER);
			
			playerLocG = new Location((int)(525*density), (int)(adjustedHeight));
			fieldForCreatePlayer.addPlayer(playerLocG, Position.GUARD);
			
			playerLocT = new Location((int)(600*density), (int)(adjustedHeight));
			fieldForCreatePlayer.addPlayer(playerLocT, Position.TACKLE);
			
			createdPlayerIndex = -1; // index of which of the 8 players has been selected
			
			// the main field
			field = new Field();
			
			paint = new Paint();

			this.setOnTouchListener(this);
		}

		@Override 
		protected void onDraw(Canvas canvas) {
			super.onDraw(c);

			paint.setColor(0xFF007900);
			
			c = canvas;
			
			drawField();

			// orangish color
			paint.setColor(0xFFFF8000);

			// y values are all the same, so just reuse one
			float eightPlayerY = playerLocQB.getY() - TOP_ANDROID_BAR;
			
			// the c does is not exactly the same as the real pixels, because
			// the c is drawn at 50 pixels down from the top of the screen
			c.drawCircle(playerLocQB.getX(), eightPlayerY, PLAYER_ICON_RADIUS, paint);
			c.drawCircle(playerLocWR.getX(), eightPlayerY, PLAYER_ICON_RADIUS, paint);
			c.drawCircle(playerLocRB.getX(), eightPlayerY, PLAYER_ICON_RADIUS, paint);
			c.drawCircle(playerLocFB.getX(), eightPlayerY, PLAYER_ICON_RADIUS, paint);
			c.drawCircle(playerLocTE.getX(), eightPlayerY, PLAYER_ICON_RADIUS, paint);
			c.drawCircle(playerLocC.getX(), eightPlayerY, PLAYER_ICON_RADIUS, paint);
			c.drawCircle(playerLocG.getX(), eightPlayerY, PLAYER_ICON_RADIUS, paint);
			c.drawCircle(playerLocT.getX(), eightPlayerY, PLAYER_ICON_RADIUS, paint);
						
			paint.setColor(Color.BLACK);
			
			paint.setTextAlign(Align.CENTER);
			paint.setTextSize(PLAYER_ICON_RADIUS);
			
			// descent and ascent are used for centering text vertically
			float height = (playerLocQB.getY()-TOP_ANDROID_BAR)-((paint.descent() + paint.ascent()) / 2);
			
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
				ypos = (int) (field.getAllPlayers().get(i).getLocation().getY()-TOP_ANDROID_BAR);
				// this is the selected player
				if (playerIndex == i)
				{
					paint.setColor(Color.RED);
				}
				// draw the play again, but red this time
				c.drawCircle(xpos, ypos, PLAYER_ICON_RADIUS, paint);
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
		}
		
		public void drawField()
		{
			// draw the field
			c.drawRect(LEFT_MARGIN, TOP_MARGIN, RIGHT_MARGIN, BOTTOM_MARGIN, paint);
			
			paint.setColor(Color.WHITE);
			// draw a stroke, not a line
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(FIELD_LINE_WIDTHS);
			
			// 2 = number of pixels between out of bounds and hash mark
			// 18 = length of the hash mark
			drawHashLines(2*density, 18*density);
			
			// PIXELS_PER_YARD * 5, because we are drawing 5 yard lines
			drawFiveYardLines();
			
			// blue color
			paint.setColor(0xFF000080);
			
			// draw line of scrimmage
			paint.setStrokeWidth(6*density);
			
			// 375 = 635(bottom margin) - 260(20 yards * 13 pixels per yard)
			c.drawLine(LEFT_MARGIN, 375+(FIELD_LINE_WIDTHS/2)*density, RIGHT_MARGIN, 375+(FIELD_LINE_WIDTHS/2)*density, paint);
			
			// fill = fill enclosed shapes with the color, like a circle with the middle one color
			paint.setStyle(Paint.Style.FILL);
		}
		
		public void drawHashLines(float outOfBoundsSpacing, float hashLength)
		{
			float middleHashOffset = (450*density);
			for (int i = 0; i < 45; i++)
			{
				if (!(i % 5 == 0))
				{
					float temp = BOTTOM_MARGIN - (PIXELS_PER_YARD*i) + (FIELD_LINE_WIDTHS/2);
					float leftMarginPlusOutOfBounds = LEFT_MARGIN + outOfBoundsSpacing;
					float rightMarginPlusHashLength = RIGHT_MARGIN - middleHashOffset;
					float leftMarginPlusHashLength = LEFT_MARGIN + middleHashOffset;
					c.drawLine(leftMarginPlusOutOfBounds, temp, 
							leftMarginPlusOutOfBounds + hashLength, temp, paint);
					c.drawLine(RIGHT_MARGIN - hashLength, temp, RIGHT_MARGIN-outOfBoundsSpacing, temp, paint);
					c.drawLine(leftMarginPlusHashLength - hashLength/2, temp, 
							leftMarginPlusHashLength + hashLength/2, temp, paint);
					c.drawLine(rightMarginPlusHashLength - hashLength/2, temp, 
							rightMarginPlusHashLength + hashLength/2, temp, paint);
				}
			}
		}
		public void drawFiveYardLines()
		{
			float pixelsPerFiveYards = PIXELS_PER_YARD*5;
			float halfFieldLineWidths =  FIELD_LINE_WIDTHS/2;
			for (int i = 0; i < 8; i++)
			{
				float temp = BOTTOM_MARGIN - pixelsPerFiveYards + halfFieldLineWidths - i*pixelsPerFiveYards;
				c.drawLine(LEFT_MARGIN, temp, RIGHT_MARGIN, temp, paint);
			}
		}

		// for drawing the positions on players
		public void drawCenteredText(String value, int xposition, float yposition)
		{
			c.drawText(value, xposition, yposition, paint);
		}
		
		public boolean onTouch(View v, MotionEvent event) {

			x = (int) (event.getRawX()*density);
			y = (int) (event.getRawY()*density);
			int playerXPos = -1;
			int playerYPos = -1;
			
			double playerIndexDistance = Float.MAX_VALUE;

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
					if (dist < TOUCH_SENSITIVITY)
					{
						// this player has been selected
						Player temp = fieldForCreatePlayer.getAllPlayers().get(i);
						createdPlayerIndex = i; // save location of player in array
						field.addPlayerAndRoute(temp.getLocation(), temp.getPosition(), Route.NO_ROUTE); // add to field
						playerIndex = field.getAllPlayers().size()-1; // this player is the last 
						// player to be added to field
						enableAll();
						staticPlayerSelected = true; // flag to say that one of the 8 players has been selected
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
						if (distance < TOUCH_SENSITIVITY)
						{
							if (distance < playerIndexDistance)
							{
								playerIndexDistance = distance;
								// this player has been selected
								playerIndex = i;
								// routes can be changed for this player
								enableAll();
								hasBeenSet = true;
							}
						}
					}
					// if not selected, disable the route spinner and reset player index
					if (!hasBeenSet)
					{
						disableAll();
					}
				}
				invalidate(); // redraw
				break;
			case MotionEvent.ACTION_UP:
				if (playerIndex != -1)
				{
					// is player outside of the field?
					if (x < LEFT_MARGIN + PLAYER_ICON_RADIUS || x > RIGHT_MARGIN - PLAYER_ICON_RADIUS || y < 135*density || y > 660*density)
					{
						field.getAllPlayers().remove(playerIndex);
						// disable route possibilities
						disableAll();
						invalidate(); // redraw
					}
					else
					{
						Player tempPlayer = field.getAllPlayers().get(playerIndex);
						int tempX = tempPlayer.getLocation().getX()-40;
						int tempY = tempPlayer.getLocation().getY()-60;
						// this is the grid
						if(tempX % 25 >= 13)
						{
							tempX = tempX + (25 - tempX % 25);
						}
						else
						{
							tempX = tempX - (tempX % 25);
						}
						if(tempY % 25 >= 13)
						{
							tempY = tempY + (25 - tempY % 25);
						}
						else
						{
							tempY = tempY - (tempY % 25);
						}
						Location tempLocation = new Location(tempX+40, tempY+60);
						tempPlayer.setLocation(tempLocation);
						invalidate(); // redrew
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

	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		routeYardage = routeDistance.getProgress();
		routeYardageTV.setText("" + routeYardage + " yds");
		if (playerIndex != -1)
		{
			field.getAllPlayers().get(playerIndex).setYardage(arg1);
		}
	}
	
	public String[] getRoutes(){
		Route[] routes = Route.values();
		String[] stringRoutes = new String[routes.length];
		for(int i = 0;i < routes.length;i++){
			stringRoutes[i] = routes[i].toString();
		}
		return stringRoutes;
	}
	
	public Route getRoute(int value)
	{
		Route[] routes = Route.values();
		return routes[value];
	}

	public void onClick(View v) {
		Intent intent = null;
		if(v.getId() == save.getId()){
			intent = new Intent(v.getContext(),SaveActivity.class);
			startActivity(intent);
		}
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (playerIndex != -1)
		{
			field.getAllPlayers().get(playerIndex).changeRoute(getRoute(arg2));
		}
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		
	}

	public void onStartTrackingTouch(SeekBar seekBar) {
		
	}

	public void onStopTrackingTouch(SeekBar seekBar) {
		
	}
}
