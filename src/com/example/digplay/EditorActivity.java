package com.example.digplay;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.businessclasses.Field;
import com.businessclasses.Location;
import com.businessclasses.Player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.FloatMath;
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
import android.widget.Toast;
public class EditorActivity extends Activity implements OnClickListener, OnItemSelectedListener  {

	private static boolean blockRoute;
	private static boolean dashLine;
	private static boolean movePlayer;
	private static int selectionColor;
	private static boolean drawingRoute;
	
	private static final int TAN_COLOR = 0xFFFF8000;

	private static Field field; // the one field
	private static int playerIndex = -1; // index of array for which player has been selected
	private static int previousPlayerIndex = -1;
	private static boolean selectedNewPlayer;
	
	private static int x; // for locating screen X value
	private static int y; // for locating screen Y value
	
	private static int lastPlayerX;
	private static int lastPlayerY;
	
	private static DrawView drawView; // custom view
	
	private static Button save;
	private static Button clearRoutes;
	private static Button clearField;
	
	private Button arrowButton;
	private Button dashButton;
	private Button clearPlayerRoute;
	private Button testButton;
	
	private static Button trashCan;
	
	private static float DENSITY; // DENSITY coefficient
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		DENSITY = getResources().getDisplayMetrics().density;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editor);
		
		movePlayer = false;
		selectedNewPlayer = false;
		selectionColor = TAN_COLOR;

		drawView = (DrawView) findViewById(R.id.DrawView);
		
		save = (Button) findViewById(R.id.save);
		clearRoutes = (Button) findViewById(R.id.clear_routes);
		clearField = (Button) findViewById(R.id.clear_field);
		trashCan = (Button) findViewById(R.id.editor_trash_can);
		arrowButton  = (Button)findViewById(R.id.edi_arrow_button);
		dashButton = (Button)findViewById(R.id.edi_dash_button);
		clearPlayerRoute = (Button)findViewById(R.id.edi_clear_player_route);
		testButton = (Button)findViewById(R.id.edi_test_button);

		trashCan.setOnClickListener(this);
		save.setOnClickListener(this);
		arrowButton.setOnClickListener(this);
		dashButton.setOnClickListener(this);
		clearPlayerRoute.setOnClickListener(this);
		testButton.setOnClickListener(this);
		
		disableAll();
		
		trashCan.setBackgroundResource(R.drawable.trashcan);
		save.setBackgroundResource(R.drawable.floppy_disk);
		
	}
	
	public void onBtnClicked(View v) {
		switch(v.getId()) {
			case R.id.save:
				//sets the bitmap variable in the field object to the current view for database
				field.setImage(drawView.getBitmap());
				break;
			case R.id.clear_routes:
				field.clearRoutes();
				disableAll();
				previousPlayerIndex = -1;
				drawView.invalidate(); // redraw the screen
				break;
			case R.id.clear_field:
				field.clearField();
				disableAll();
				previousPlayerIndex = -1;
				drawView.invalidate(); // redraw the screen
				break;
			default:
				break;
		}
	}
	
	public static void disableAll()
	{
		playerIndex = -1;
	}
	
	public static void enableAll(int player_index)
	{
		playerIndex = player_index;
	}
	
	public static class DrawView extends View implements OnTouchListener {
		
		private Canvas c;
		private Paint paint;
		
		// this field is used to just store the bottom 8 "players"
		// that users can drag onto the field
		private Field fieldForCreatePlayer;
		
		// static final values
		static final private float FIELD_HEIGHT = 575*DENSITY;
		static final private float LEFT_MARGIN = 40*DENSITY;
		static final private float RIGHT_MARGIN = 1240*DENSITY;
		static final private float TOP_MARGIN = 60*DENSITY;
		static final private float BOTTOM_MARGIN = TOP_MARGIN+FIELD_HEIGHT;
		static final private float PIXELS_PER_YARD = 13*DENSITY;
		static final private float FIELD_LINE_WIDTHS = 4*DENSITY;
		static final private float PLAYER_ICON_RADIUS = 25*DENSITY;
		static final private float TOP_ANDROID_BAR = 50*DENSITY;
		static final private float TOUCH_SENSITIVITY = 35*DENSITY;
		
		static private Bitmap bitmap = Bitmap.createBitmap((int)(RIGHT_MARGIN-LEFT_MARGIN), (int)(FIELD_HEIGHT), Bitmap.Config.ARGB_8888);
		static private Canvas bitmapCanvas;

		public DrawView(Context context, AttributeSet attrs) throws IOException {
			super(context, attrs);
			build(context, attrs);
		}

		public Bitmap getBitmap() {
			
			return bitmap;
		}

		public void build(Context context, AttributeSet attrs) throws IOException
		{		
			bitmapCanvas = new Canvas(bitmap);
			
			fieldForCreatePlayer = new Field();
			
			// the main field
			field = new Field();
			
			paint = new Paint();
			
			c = new Canvas();

			this.setOnTouchListener(this);
		}

		@Override 
		protected void onDraw(Canvas canvas) {
			super.onDraw(c);
			
			paint.setColor(Color.BLACK);
			
			c = canvas;
			
			// 2 = out of bounds spacing, the number of pixels between out of bounds and the hash mark
			// 18 = length of the hash marks in pixels 
			DrawingUtils.drawField(LEFT_MARGIN, RIGHT_MARGIN, TOP_MARGIN, BOTTOM_MARGIN, DENSITY, FIELD_LINE_WIDTHS, PIXELS_PER_YARD, 
					2*DENSITY, 18*DENSITY, bitmapCanvas, paint);
			
			DrawingUtils.drawRoutes(field, FIELD_LINE_WIDTHS, TOP_ANDROID_BAR, bitmapCanvas, paint, LEFT_MARGIN, TOP_MARGIN);
	
			DrawingUtils.drawPlayers(field, TOP_ANDROID_BAR, PLAYER_ICON_RADIUS, DENSITY, playerIndex, 
					fieldForCreatePlayer, c, bitmapCanvas, paint, selectionColor, LEFT_MARGIN, TOP_MARGIN);
			
			c.drawBitmap(bitmap, LEFT_MARGIN, TOP_MARGIN, paint);
		}
		
		public boolean onTouch(View v, MotionEvent event) {

			x = (int) (event.getRawX()*DENSITY);
			y = (int) (event.getRawY()*DENSITY);

			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				selectedNewPlayer = DrawingUtils.actionDown(field, fieldForCreatePlayer, TOUCH_SENSITIVITY, x, y, playerIndex);
				if (playerIndex != -1)
				{
					lastPlayerX = field.getAllPlayers().get(playerIndex).getLocation().getX();
					lastPlayerY = field.getAllPlayers().get(playerIndex).getLocation().getY();
					if (selectionColor == TAN_COLOR || selectedNewPlayer || previousPlayerIndex != playerIndex)
					{
						selectionColor = Color.RED;
					}
				}
				else
				{
					selectionColor = TAN_COLOR;
				}
				invalidate(); // redraw
				break;
			case MotionEvent.ACTION_UP:
				if (movePlayer)
				{
					DrawingUtils.actionUp(field, playerIndex, LEFT_MARGIN, PLAYER_ICON_RADIUS, RIGHT_MARGIN, TOP_MARGIN, BOTTOM_MARGIN, TOP_ANDROID_BAR, x, y);
					selectionColor = Color.RED;	
				}
				else if (!movePlayer && previousPlayerIndex == playerIndex)
				{
					if (selectionColor == Color.RED)
					{
						selectionColor = Color.BLUE;
					}
					else if (selectionColor == Color.BLUE && !drawingRoute)
					{
						selectionColor = Color.RED;
					}
				}
				previousPlayerIndex = playerIndex;
				drawingRoute = false;
				movePlayer = false;
				invalidate(); // redraw
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				break;
			case MotionEvent.ACTION_POINTER_UP:
				break;
			case MotionEvent.ACTION_MOVE:
				// update the player location when dragging
				if (!movePlayer)
				{
					float dist = -1;
					if (selectionColor == Color.RED)
					{
						dist = FloatMath.sqrt((x-lastPlayerX)*(x-lastPlayerX) + (y-lastPlayerY)*(y-lastPlayerY));
						if (dist > PLAYER_ICON_RADIUS)
						{
							movePlayer = true;
							DrawingUtils.actionMove(field, playerIndex, x, y);
						}
					}
					else if (selectionColor == Color.BLUE)
					{
						if (x < LEFT_MARGIN + FIELD_LINE_WIDTHS/2)
						{
							x = (int) (LEFT_MARGIN + FIELD_LINE_WIDTHS/2);
						}
						else if (x > RIGHT_MARGIN - FIELD_LINE_WIDTHS/2)
						{
							x = (int) (RIGHT_MARGIN - FIELD_LINE_WIDTHS/2);
						}
						if (y < TOP_MARGIN + TOP_ANDROID_BAR + FIELD_LINE_WIDTHS/2)
						{
							y = (int) (TOP_MARGIN + TOP_ANDROID_BAR + FIELD_LINE_WIDTHS/2);
						}
						
						else if (y > BOTTOM_MARGIN + TOP_ANDROID_BAR - FIELD_LINE_WIDTHS/2)
						{
							y = (int) (BOTTOM_MARGIN + TOP_ANDROID_BAR - FIELD_LINE_WIDTHS/2);
						}
						dist = FloatMath.sqrt((x-lastPlayerX)*(x-lastPlayerX) + (y-lastPlayerY)*(y-lastPlayerY));
						if (dist > PLAYER_ICON_RADIUS)
						{
							drawingRoute = true;
							if (x < LEFT_MARGIN + FIELD_LINE_WIDTHS/2)
							{
								lastPlayerX = (int) (LEFT_MARGIN + FIELD_LINE_WIDTHS/2);
							}
							else if (x > RIGHT_MARGIN - FIELD_LINE_WIDTHS/2)
							{
								lastPlayerX = (int) (RIGHT_MARGIN - FIELD_LINE_WIDTHS/2);
							}
							else
							{
								lastPlayerX = x;
							}
							if (y < TOP_MARGIN + TOP_ANDROID_BAR + FIELD_LINE_WIDTHS/2)
							{
								lastPlayerY = (int) (TOP_MARGIN + TOP_ANDROID_BAR + FIELD_LINE_WIDTHS/2);
							}
							
							else if (y > BOTTOM_MARGIN + TOP_ANDROID_BAR - FIELD_LINE_WIDTHS/2)
							{
								lastPlayerY = (int) (BOTTOM_MARGIN + TOP_ANDROID_BAR - FIELD_LINE_WIDTHS/2);
							}
							else
							{
								lastPlayerY = y;
							}
							Location temp = new Location(lastPlayerX, lastPlayerY);
							field.getAllPlayers().get(playerIndex).addRouteLocation(temp);
						}
					}
				}
				else
				{
					if (selectionColor == Color.RED)
					{
						DrawingUtils.actionMove(field, playerIndex, x, y);
					}
				}
				invalidate(); // redraw
				break;
			default:
				break;
			}
			return true;
		}
	}	
	//used for database to get the field object.
	public static Field getField(){
		return field;
	}
	public void onClick(View v) {
		Intent intent = null;
		if(v.getId() == save.getId()){
			intent = new Intent(v.getContext(),SaveActivity.class);
			startActivity(intent);
		}else if(v.getId() == arrowButton.getId()){
			if(blockRoute == false){
				blockRoute = true;
				arrowButton.setBackgroundResource(R.drawable.perpendicular_line);
			}
			else {
				blockRoute = false;
				arrowButton.setBackgroundResource(R.drawable.right_arrow);
			}
		}else if(v.getId() == dashButton.getId()){
			if(dashLine == false){
				dashLine = true;
				dashButton.setBackgroundResource(R.drawable.dotted_line);
			}
			else {
				dashLine = false;
				dashButton.setBackgroundResource(R.drawable.line);
			}
		}else if(v.getId() == clearPlayerRoute.getId()){
			Player selectedPlayer = field.getPlayer(playerIndex);
			selectedPlayer.clearRoute();
			drawView.invalidate();
		}else if(v.getId() == testButton.getId()){
			field.flip();
			drawView.invalidate();
		}
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (playerIndex != -1)
		{
			field.getAllPlayers().get(playerIndex).changeRoute(DrawingUtils.LookupRoute(arg2));
		}
		drawView.invalidate();
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		
	}

	public void onStartTrackingTouch(SeekBar seekBar) {
		
	}

	public void onStopTrackingTouch(SeekBar seekBar) {
		
	}
}
