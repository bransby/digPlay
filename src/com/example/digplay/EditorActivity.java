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
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class EditorActivity extends Activity {

	private static Field field;
	static Location playerLoc;
	static float density; // density coefficient
	static boolean addingPlayer = false;
	static int playerIndex = -1; // index of array for which player has been selected
	
	static int x;
	static int y;
	
	static DrawView drawView;
	static TextView messageBar;
	
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

		drawView = (DrawView) findViewById(R.id.DrawView);
		
		messageBar = (TextView) findViewById(R.id.message_bar);
		messageBar.setText("Add players to begin");
		
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
			case R.id.add_player:
				messageBar.setText("Select location for player");
				addingPlayer = true;
				break;
			case R.id.delete_player:
				if (addingPlayer)
				{
					break;
				}
				field.getAllPlayers().remove(playerIndex);
				drawView.invalidate();
				disableSpinners();
				disableButtons();
				if (field.getAllPlayers().size()==0)
				{
					messageBar.setText("Add players to begin");
				}
				else
				{
					messageBar.setText("Select or add player");
				}
				break;
			case R.id.clear_routes:
				if (addingPlayer)
				{
					break;
				}
				field.clearRoutes();
				playerIndex = -1;
				messageBar.setText("Select or add player");
				drawView.invalidate();
				break;
			case R.id.clear_field:
				if (addingPlayer)
				{
					break;
				}
				field.clearField();
				messageBar.setText("Add players to begin");
				playerIndex = -1;
				drawView.invalidate();
				break;
			case R.id.add_route:
				break;
			case R.id.remove_route:
				field.getAllPlayers().get(playerIndex).clearRoute();
				break;
			default:
				break;
		}
	}
	
	public static class DrawView extends View implements OnTouchListener {

		Bitmap fieldBitmap;
		Canvas c;
		Paint paint;

		public DrawView(Context context, AttributeSet attrs) {
			super(context, attrs);
			build(context, attrs);
		}

		public void build(Context context, AttributeSet attrs)
		{
			density = getResources().getDisplayMetrics().density;
			field = new Field();

			fieldBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.one_two_eight_zero_by_eight_zero_zero, null);
			paint = new Paint();

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
				c.drawCircle(xpos, ypos, 25*density, paint);
				paint.setColor(0xFFFF8000);
			}
			
			paint.setColor(0xFF000080);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(5*density);
			canvas.drawLine(0, 475*density, 1280*density, 475*density, paint);
			paint.setColor(0xFFFFA000);
			canvas.drawLine(0, 371*density, 1280*density, 371*density, paint);
			paint.setStyle(Paint.Style.FILL);
		}

		public boolean onTouch(View v, MotionEvent event) {

			x = (int) (event.getRawX()*density);
			y = (int) (event.getRawY()*density);
			int playerXPos = -1;
			int playerYPos = -1;

			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				if (addingPlayer)
				{
					playerLoc = new Location(Math.round(x*density), Math.round(y*density));
					field.addPlayer(playerLoc, Position.QUARTERBACK);
					addingPlayer = false;
					playerIndex = field.getAllPlayers().size()-1;
					messageBar.setText("Set route, remove route, or remove player");
					enableSpinners();
					enableButtons();
				}
				else
				{
					boolean hasBeenSet = false;
					for (int i = 0; i < field.getAllPlayers().size(); i++)
					{
						playerXPos = field.getAllPlayers().get(i).getLocation().getX();
						playerYPos = field.getAllPlayers().get(i).getLocation().getY();
						double distance = Math.sqrt(((playerXPos-x)*(playerXPos-x)) 
								+ ((playerYPos-y)*(playerYPos-y)));
						if (distance < 25)
						{
							playerIndex = i;
							enableButtons();
							enableSpinners();
							messageBar.setText("Set route, remove route, or remove player");
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
