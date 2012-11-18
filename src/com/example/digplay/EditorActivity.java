package com.example.digplay;

import java.io.IOException;

import com.businessclasses.Field;
import com.businessclasses.Location;
import com.businessclasses.Player;
import com.businessclasses.Route;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.opengl.Matrix;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
public class EditorActivity extends Activity implements OnClickListener  {

	private static boolean blockRoute;
	private static boolean dashLine;
	private static boolean movePlayer;
	private static int selectionColor;
	private static boolean drawingRoute;
	private static boolean drawCreatedPlayers;
	
	private static Route arrowRoute;
	
	private static final int TAN_COLOR = 0xFFFF8000;

	private static Field field; // the one field
	private static int playerIndex = -1; // index of array for which player has been selected
	private static int previousPlayerIndex = -1;
	
	private static int x; // for locating screen X value
	private static int y; // for locating screen Y value
	
	private static int lastPlayerX;
	private static int lastPlayerY;
	
	private static DrawView drawView; // custom view
	
	private static Button save;
	private static Button clearRoutes;
	private static Button clearField;
	
	private static Button arrowButton;
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
		selectionColor = TAN_COLOR;
		drawCreatedPlayers = true;
		arrowRoute = Route.ARROW;

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
		
		playerIndex = 1;
		
		trashCan.setBackgroundResource(R.drawable.trashcan);
		save.setBackgroundResource(R.drawable.floppy_disk);
	}
	
	public static Bitmap getBitmap()
	{
		return drawView.getBitmap();
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
		
		Picture picture; 
		Canvas tempCanvas;
		
		private Bitmap bitmap = Bitmap.createBitmap((int)(RIGHT_MARGIN-LEFT_MARGIN), (int)(FIELD_HEIGHT), Bitmap.Config.ARGB_8888);
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
			
			picture = new Picture();
			
			tempCanvas = picture.beginRecording((int)(LEFT_MARGIN + RIGHT_MARGIN), (int)(800));
			
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
			
			
			paint.setColor(Color.BLACK);
			
			c = canvas;
			
			// 2 = out of bounds spacing, the number of pixels between out of bounds and the hash mark
			// 18 = length of the hash marks in pixels 
			DrawingUtils.drawField(LEFT_MARGIN, RIGHT_MARGIN, TOP_MARGIN, BOTTOM_MARGIN, DENSITY, FIELD_LINE_WIDTHS, PIXELS_PER_YARD, 
					2*DENSITY, 18*DENSITY, c, paint);
			
			DrawingUtils.drawRoutes(field, FIELD_LINE_WIDTHS, TOP_ANDROID_BAR, c, paint, LEFT_MARGIN, TOP_MARGIN, PIXELS_PER_YARD, playerIndex);
	
			if (drawCreatedPlayers)
			{
				DrawingUtils.drawCreatePlayers(fieldForCreatePlayer, tempCanvas, paint, DENSITY, TOP_ANDROID_BAR, PLAYER_ICON_RADIUS);
				picture.endRecording();
				drawCreatedPlayers = false;
			}
	
			c.drawPicture(picture);
			DrawingUtils.drawPlayers(field, TOP_ANDROID_BAR, PLAYER_ICON_RADIUS, playerIndex, 
					c, paint, selectionColor);
		}
		
		private void drawToBitmap()
		{
			paint.setColor(Color.BLACK);
			
			// 2 = out of bounds spacing, the number of pixels between out of bounds and the hash mark
			// 18 = length of the hash marks in pixels 
			DrawingUtils.drawBitmapField(LEFT_MARGIN, RIGHT_MARGIN, TOP_MARGIN, BOTTOM_MARGIN, DENSITY, FIELD_LINE_WIDTHS, PIXELS_PER_YARD, 
					2*DENSITY, 18*DENSITY, bitmapCanvas, paint);
			
			DrawingUtils.drawBitmapRoutes(field, FIELD_LINE_WIDTHS, TOP_ANDROID_BAR, bitmapCanvas, paint, LEFT_MARGIN, TOP_MARGIN, PIXELS_PER_YARD, playerIndex);
	
			DrawingUtils.drawBitmapPlayers(field, TOP_ANDROID_BAR, PLAYER_ICON_RADIUS, playerIndex, 
					bitmapCanvas, paint, selectionColor, LEFT_MARGIN, TOP_MARGIN);
			
			bitmapCanvas.drawBitmap(bitmap, 0, 0, paint);
		}
		
		public boolean onTouch(View v, MotionEvent event) {

			x = (int) (event.getRawX()*DENSITY);
			y = (int) (event.getRawY()*DENSITY);

			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				playerIndex = DrawingUtils.actionDown(field, fieldForCreatePlayer, TOUCH_SENSITIVITY, x, y, playerIndex, arrowRoute);
				if (playerIndex != -1)
				{
					Player selectedPlayer = field.getPlayer(playerIndex);
					boolean blockRouteForPlayer;
					if (selectedPlayer.getRoute() == Route.ARROW)
					{
						blockRouteForPlayer = false;
					}
					// block route
					else
					{
						blockRouteForPlayer = true;
					}
					
					if (field.getPlayer(playerIndex).getRouteLocations().size() != 0)
					{
						if (!blockRouteForPlayer && blockRoute || blockRouteForPlayer && !blockRoute)
						{
							blockRoute = EditorActivity.toggleArrowButton(blockRoute);
						}
					}
					else
					{
						if (blockRoute)
						{
							field.getPlayer(playerIndex).changeRoute(Route.BLOCK);
						}
						else
						{
							field.getPlayer(playerIndex).changeRoute(Route.ARROW);
						}
					}
					lastPlayerX = selectedPlayer.getLocation().getX();
					lastPlayerY = selectedPlayer.getLocation().getY();
					if (selectionColor == TAN_COLOR || previousPlayerIndex != playerIndex)
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
					playerIndex = DrawingUtils.actionUp(field, playerIndex, LEFT_MARGIN, PLAYER_ICON_RADIUS, RIGHT_MARGIN, TOP_MARGIN, BOTTOM_MARGIN, TOP_ANDROID_BAR, x, y);
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
							field.getPlayer(playerIndex).addRouteLocation(temp);
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

		public static void flipField() {
			field.flip((int)(LEFT_MARGIN + RIGHT_MARGIN));
		}
	}	
	//used for database to get the field object.
	public static Field getField(){
		return field;
	}
	
	public static boolean toggleArrowButton(boolean blockRoute)
	{
		if(blockRoute == false){
			arrowButton.setBackgroundResource(R.drawable.perpendicular_line);
			arrowRoute = Route.BLOCK;
			return true;
		}
		else {
			arrowButton.setBackgroundResource(R.drawable.right_arrow);
			arrowRoute = Route.ARROW;
			return false;
		}
	}
	public void onClick(View v) {
		Intent intent = null;
		int id = v.getId();
		if(id == save.getId()){
			drawView.drawToBitmap();
			intent = new Intent(v.getContext(),SaveActivity.class);
			startActivity(intent);
		}else if(id == arrowButton.getId()){
			blockRoute = toggleArrowButton(blockRoute);
			if (playerIndex != -1)
			{
				field.getPlayer(playerIndex).changeRoute(arrowRoute);
				drawView.invalidate();
			}
		}else if(id == dashButton.getId()){
			if(dashLine == false){
				dashLine = true;
				dashButton.setBackgroundResource(R.drawable.dotted_line);
			}
			else {
				dashLine = false;
				dashButton.setBackgroundResource(R.drawable.line);
			}
		}else if(id == clearPlayerRoute.getId()){
			if (playerIndex != -1)
			{
				Player selectedPlayer = field.getPlayer(playerIndex);
				selectedPlayer.clearRoute();
				drawView.invalidate();
			}
		}else if(id== testButton.getId()){
			DrawView.flipField();
			drawView.invalidate();
		}
		else if (id == clearRoutes.getId())
		{
			field.clearRoutes(arrowRoute);
			field.clearRouteLocations();
			playerIndex = -1;
			previousPlayerIndex = -1;
			drawView.invalidate(); // redraw the screen
		}
		else if (id == clearField.getId())
		{
			field.clearField();
			playerIndex = -1;
			previousPlayerIndex = -1;
			drawView.invalidate(); // redraw the screen
		}
	}
}
