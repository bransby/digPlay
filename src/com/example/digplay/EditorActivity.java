package com.example.digplay;

import com.businessclasses.Field;
import com.businessclasses.Location;
import com.businessclasses.Position;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class EditorActivity extends Activity {

	static Button save;
	static Button addPlayer;
	static Button removeRoute;
	static Button deletePlayer;
	static Button clearRoutes;
	static Button clearField;
	static Button addRoute;

	static Spinner playType;
	static Spinner routeType;
	static Spinner yardage;
	static Spinner position;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editor);

		save = (Button) findViewById(R.id.save);
		addPlayer = (Button) findViewById(R.id.add_player);
		removeRoute = (Button) findViewById(R.id.remove_route);
		deletePlayer = (Button) findViewById(R.id.delete_player);
		clearRoutes = (Button) findViewById(R.id.clear_routes);
		clearField = (Button) findViewById(R.id.clear_field);
		addRoute = (Button) findViewById(R.id.add_route);

		disableButtons();

		playType = (Spinner) findViewById(R.id.play_type);
		routeType = (Spinner) findViewById(R.id.route_type);
		yardage = (Spinner) findViewById(R.id.yardage);
		position = (Spinner) findViewById(R.id.position);
		
		disableSpinners();

		ArrayAdapter<CharSequence> positionAdapter = ArrayAdapter.createFromResource(this, R.array.position_array, R.layout.spinner_layout);
		positionAdapter.setDropDownViewResource(R.layout.spinner_layout);
		position.setAdapter(positionAdapter);

		ArrayAdapter<CharSequence> playTypeAdapter = ArrayAdapter.createFromResource(this, R.array.play_type_array, R.layout.spinner_layout);
		playTypeAdapter.setDropDownViewResource(R.layout.spinner_layout);
		playType.setAdapter(playTypeAdapter);

		ArrayAdapter<CharSequence> routeTypeAdapter = ArrayAdapter.createFromResource(this, R.array.route_type_array, R.layout.spinner_layout);
		routeTypeAdapter.setDropDownViewResource(R.layout.spinner_layout);
		routeType.setAdapter(routeTypeAdapter);

		ArrayAdapter<CharSequence> yardageAdapter = ArrayAdapter.createFromResource(this, R.array.yardage_array, R.layout.spinner_layout);
		yardageAdapter.setDropDownViewResource(R.layout.spinner_layout);
		yardage.setAdapter(yardageAdapter);
	}
	
	public static void disableButtons()
	{
		removeRoute.setEnabled(false);
		removeRoute.setClickable(false);

		deletePlayer.setEnabled(false);
		deletePlayer.setClickable(false);

		addRoute.setEnabled(false);
		addRoute.setClickable(false);
	}
	
	public static void disableSpinners()
	{
		routeType.setEnabled(false);
		routeType.setClickable(false);
		
		yardage.setEnabled(false);
		yardage.setClickable(false);
	}
	
	public static void enableButtons()
	{
		removeRoute.setEnabled(true);
		removeRoute.setClickable(true);

		deletePlayer.setEnabled(true);
		deletePlayer.setClickable(true);

		addRoute.setEnabled(true);
		addRoute.setClickable(true);
	}
	
	public static void enableSpinners()
	{
		routeType.setEnabled(true);
		routeType.setClickable(true);
		
		yardage.setEnabled(true);
		yardage.setClickable(true);
	}
	
	public void onBtnClicked(View v) {
		switch(v.getId()) {
			case R.id.save:
				break;
			default:
				break;
		}
	}
	
	public static class DrawView extends View implements OnTouchListener {

		Field field;

		Bitmap fieldBitmap;
		Canvas c;
		Paint paint;

		Location playerLoc;
		float density = getResources().getDisplayMetrics().density; // density coefficient
		int playerIndex = -1; // index of array for which player has been selected

		public DrawView(Context context, AttributeSet attrs) {
			super(context, attrs);
			build(context, attrs);
		}

		public void build(Context context, AttributeSet attrs)
		{
			field = new Field();

			fieldBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.one_two_eight_zero_by_eight_zero_zero, null);
			paint = new Paint(); 

			addPlayer();

			this.setOnTouchListener(this);
		}

		@Override 
		protected void onDraw(Canvas canvas) {
			super.onDraw(c);

			paint.setColor(0xFFFF8000);

			c = canvas;
			c.drawBitmap(fieldBitmap, 0,
					60*density, null);

			int xpos = -1;
			int ypos = -1;
			for (int i = 0; i < field.getAllPlayers().size(); i++)
			{
				xpos = field.getAllPlayers().get(i).getLocation().getX();
				ypos = field.getAllPlayers().get(i).getLocation().getY()-50;
				if (playerIndex == i)
				{
					paint.setColor(Color.RED);
				}
				c.drawCircle(xpos, ypos, 75*density, paint);
				paint.setColor(0xFFFF8000);
			}

			paint.setColor(Color.BLACK);
			paint.setTextSize(50);
			canvas.drawText("Message Bar", 5*density, 45*density, paint);
		}

		private void addPlayer() {
			playerLoc = new Location(Math.round(70*density), Math.round((70+50)*density));
			field.addPlayer(playerLoc, Position.QUARTERBACK);
			playerLoc = new Location(Math.round(370*density), Math.round((370+50)*density));
			field.addPlayer(playerLoc, Position.QUARTERBACK);
		}

		public boolean onTouch(View v, MotionEvent event) {

			final int x = (int) (event.getRawX()*density);
			final int y = (int) (event.getRawY()*density);
			int playerXPos = -1;
			int playerYPos = -1;

			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				boolean hasBeenSet = false;
				for (int i = 0; i < field.getAllPlayers().size(); i++)
				{
					playerXPos = field.getAllPlayers().get(i).getLocation().getX();
					playerYPos = field.getAllPlayers().get(i).getLocation().getY();
					double distance = Math.sqrt(((playerXPos-x)*(playerXPos-x)) 
							+ ((playerYPos-y)*(playerYPos-y)));
					if (distance < 75)
					{
						playerIndex = i;
						enableButtons();
						enableSpinners();
						hasBeenSet = true;
						break;
					}
				}
				if (!hasBeenSet)
				{
					playerIndex = -1;
					disableButtons();
					disableSpinners();
				}
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
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
			default:
				break;
			}
			return true;
		}
	}
}
