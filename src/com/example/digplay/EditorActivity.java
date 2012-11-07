package com.example.digplay;

import com.businessclasses.Field;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
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
	
	private static float DENSITY; // DENSITY coefficient
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		DENSITY = getResources().getDisplayMetrics().density;
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

		ArrayAdapter<String> rtAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, DrawingUtils.getRoutes());
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

		public DrawView(Context context, AttributeSet attrs) {
			super(context, attrs);
			build(context, attrs);
		}

		public void build(Context context, AttributeSet attrs)
		{			
			fieldForCreatePlayer = new Field();
			
			// the main field
			field = new Field();
			
			paint = new Paint();

			this.setOnTouchListener(this);
		}

		@Override 
		protected void onDraw(Canvas canvas) {
			super.onDraw(c);
			
			c = canvas;
			
			// 2 = out of bounds spacing, the number of pixels between out of bounds and the hash mark
			// 18 = length of the hash marks in pixels 
			DrawingUtils.drawField(LEFT_MARGIN, RIGHT_MARGIN, TOP_MARGIN, BOTTOM_MARGIN, DENSITY, FIELD_LINE_WIDTHS, PIXELS_PER_YARD, 
					2*DENSITY, 18*DENSITY, canvas, paint);
	
			DrawingUtils.drawPlayers(field, TOP_ANDROID_BAR, PLAYER_ICON_RADIUS, DENSITY, playerIndex, fieldForCreatePlayer, canvas, paint);
		}
		
		public boolean onTouch(View v, MotionEvent event) {

			x = (int) (event.getRawX()*DENSITY);
			y = (int) (event.getRawY()*DENSITY);

			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				DrawingUtils.actionDown(field, fieldForCreatePlayer, TOUCH_SENSITIVITY, x, y, playerIndex);
				invalidate(); // redraw
				break;
			case MotionEvent.ACTION_UP:
				DrawingUtils.actionUp(field, playerIndex, LEFT_MARGIN, PLAYER_ICON_RADIUS, RIGHT_MARGIN, DENSITY, x, y);
				invalidate(); // redraw
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				break;
			case MotionEvent.ACTION_POINTER_UP:
				break;
			case MotionEvent.ACTION_MOVE:
				// update the player location when dragging
				DrawingUtils.actionMove(field, playerIndex, x, y);
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
			field.getAllPlayers().get(playerIndex).changeRoute(DrawingUtils.LookupRoute(arg2));
		}
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		
	}

	public void onStartTrackingTouch(SeekBar seekBar) {
		
	}

	public void onStopTrackingTouch(SeekBar seekBar) {
		
	}
}
