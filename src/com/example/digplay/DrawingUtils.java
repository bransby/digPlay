package com.example.digplay;

import com.businessclasses.Field;
import com.businessclasses.Location;
import com.businessclasses.Path;
import com.businessclasses.Player;
import com.businessclasses.Position;
import com.businessclasses.Route;
import com.example.digplay.EditorActivity.DrawView;

import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.FloatMath;

public class DrawingUtils {
	
	public static void drawCreatePlayers(Field fieldForCreatePlayer, Canvas canvas, Paint paint, float DENSITY, 
			float TOP_ANDROID_BAR, float PLAYER_ICON_RADIUS, float BAR_Y_VALUE)
	{
		float adjustedHeight = BAR_Y_VALUE; // pixel location we want to draw the 8
		   // created players at. 50 pixels is used
		   // at the top for all android devices

		// add players at bottom of screen, 75dp width between each of them
		Location playerLocQB = new Location((int)(75/DENSITY), (int)(adjustedHeight));
		fieldForCreatePlayer.addPlayer(playerLocQB, Position.QB);
		
		Location playerLocWR = new Location((int)(150/DENSITY), (int)(adjustedHeight));
		fieldForCreatePlayer.addPlayer(playerLocWR, Position.WR);
		
		Location playerLocRB = new Location((int)(225/DENSITY), (int)(adjustedHeight));
		fieldForCreatePlayer.addPlayer(playerLocRB, Position.RB);
		
		Location playerLocFB = new Location((int)(300/DENSITY), (int)(adjustedHeight));
		fieldForCreatePlayer.addPlayer(playerLocFB, Position.FB);
		
		Location playerLocTE = new Location((int)(375/DENSITY), (int)(adjustedHeight));
		fieldForCreatePlayer.addPlayer(playerLocTE, Position.TE);
		
		Location playerLocC = new Location((int)(450/DENSITY), (int)(adjustedHeight));
		fieldForCreatePlayer.addPlayer(playerLocC, Position.C);
		
		Location playerLocG = new Location((int)(525/DENSITY), (int)(adjustedHeight));
		fieldForCreatePlayer.addPlayer(playerLocG, Position.G);
		
		Location playerLocT = new Location((int)(600/DENSITY), (int)(adjustedHeight));
		fieldForCreatePlayer.addPlayer(playerLocT, Position.T);

		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(2/DENSITY);

		// y values are all the same, so just reuse one
		float eightPlayerY = playerLocQB.getY() - TOP_ANDROID_BAR;
		
		// for drawing the text
		paint.setTextAlign(Align.CENTER);
		paint.setTextSize(PLAYER_ICON_RADIUS);
		// descent and ascent are used for centering text vertically
		float height = (playerLocQB.getY()-TOP_ANDROID_BAR)-((paint.descent() + paint.ascent()) / 2);

		
		for (int i = 0; i < fieldForCreatePlayer.getAllPlayers().size(); i++)
		{
			paint.setColor(Color.WHITE);
			Player tempPlayer = fieldForCreatePlayer.getPlayer(i);
			float playerXLocation = tempPlayer.getLocation().getX();
			canvas.drawCircle(playerXLocation, eightPlayerY, PLAYER_ICON_RADIUS, paint);
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(Color.BLACK);
			canvas.drawCircle(playerXLocation, eightPlayerY, PLAYER_ICON_RADIUS, paint);
			paint.setStyle(Paint.Style.FILL);
			canvas.drawText(tempPlayer.getPosition().toString(), playerXLocation, height, paint);
		}
	}
	
	public static void drawPlayers(Field field, float xOffset, float yOffset, Canvas canvas, 
			Paint paint, int playerIndex, int color, float PLAYER_ICON_RADIUS, float DENSITY)
	{
		int xpos = -1;
		int ypos = -1;
		float height = -1;
		
		// for drawing the text
		paint.setTextAlign(Align.CENTER);
		paint.setTextSize(PLAYER_ICON_RADIUS);
		paint.setStrokeWidth(2/DENSITY);
		
		for (int i = 0; i < field.getAllPlayers().size(); i++)
		{
			Player tempPlayer = field.getAllPlayers().get(i);
			Location tempLocation = tempPlayer.getLocation();
			xpos = (int) (tempLocation.getX() - xOffset);
			ypos = (int) (tempLocation.getY() - yOffset);
			// this is the selected player
			if (playerIndex == i)
			{
				paint.setColor(color);
			}
			else
			{
				paint.setColor(Color.WHITE);
			}
			// draw the player again, but red this time
			canvas.drawCircle(xpos, ypos, PLAYER_ICON_RADIUS, paint);
			// descent and ascent are used for centering text vertically
			height = ypos-((paint.descent() + paint.ascent()) / 2);
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(Color.BLACK);
			canvas.drawCircle(xpos, ypos, PLAYER_ICON_RADIUS, paint);
			paint.setStyle(Paint.Style.FILL);
			canvas.drawText(tempPlayer.getPosition().toString(), xpos, height, paint);
		}
	}
	
	public static void drawArrow(Canvas canvas, Paint paint, float PIXELS_PER_YARD)
	{
		float temp = (int)(Math.cos(Math.PI/4)*PIXELS_PER_YARD)*2;
		canvas.drawLine(0, 0, -temp, temp, paint);
		canvas.drawLine(0, 0, temp, temp, paint);
	}
	
	public static void drawBlock(Canvas canvas, Paint paint, float PIXELS_PER_YARD)
	{
		canvas.drawLine(0, 0, -PIXELS_PER_YARD*2, 0, paint);
		canvas.drawLine(0, 0, PIXELS_PER_YARD*2, 0, paint);
	}
	
	public static void drawEndOfRoute(Canvas canvas, Paint paint, int x, int y, float PIXELS_PER_YARD, float differenceAngle, Route route)
	{
		if (route == Route.ARROW)
		{
			canvas.save();
				canvas.translate(x, y);
				canvas.rotate(differenceAngle-90);
				drawArrow(canvas, paint, PIXELS_PER_YARD);
			canvas.restore();
		}
		// draw block
		else
		{
			canvas.save();
				canvas.translate(x, y);
				canvas.rotate(differenceAngle-90);
				drawBlock(canvas, paint, PIXELS_PER_YARD);
			canvas.restore();
		}
	}
	
	public static void drawRoutes(Field field, float xOffset, float yOffset, float FIELD_LINE_WIDTHS, Canvas canvas, 
			Paint paint, float PIXELS_PER_YARD)
	{
		paint.setColor(Color.YELLOW);
		paint.setStrokeWidth(FIELD_LINE_WIDTHS);
		
		for (int i = 0; i < field.getAllPlayers().size(); i++)
		{
			Player tempPlayer = field.getAllPlayers().get(i);
			// set x and y previous values to player's x/y values
			int previousX = (int) (tempPlayer.getLocation().getX() - xOffset);
			int previousY = (int) (tempPlayer.getLocation().getY() - yOffset);
			if (tempPlayer.getPath() == Path.DOTTED)
			{
				paint.setPathEffect(new DashPathEffect(new float[] {10,10}, 0));
			}
			for (int j = 0; j < tempPlayer.getRouteLocations().size(); j++)
			{
				Location tempLocation = tempPlayer.getRouteLocations().get(j);
				int currentX = (int) (tempLocation.getX() - xOffset);
				int currentY = (int) (tempLocation.getY() - yOffset);
				canvas.drawLine(previousX, previousY, currentX, currentY, paint);
				int tempX = (int) (tempLocation.getX() - xOffset);
				int tempY = (int) (tempLocation.getY() - yOffset);
				if (j == tempPlayer.getRouteLocations().size()-1)
				{
					int deltaX = previousX - tempX;
					int deltaY = previousY - tempY;
					float differenceAngle = (float)(Math.atan2(deltaY, deltaX) * 180 / Math.PI);
					// solid line
					paint.setPathEffect(null);
					drawEndOfRoute(canvas, paint, tempX, tempY, PIXELS_PER_YARD, differenceAngle, tempPlayer.getRoute());
				}
				previousX = tempX;
				previousY = tempY;
			}
			// solid line
			paint.setPathEffect(null);
		}
		// solid line
		paint.setPathEffect(null);
	}
	
	public static void drawField(float LEFT_MARGIN, float RIGHT_MARGIN, float TOP_MARGIN, 
			float BOTTOM_MARGIN, float DENSITY, float FIELD_LINE_WIDTHS, float PIXELS_PER_YARD, 
			float outOfBoundsSpacing, float hashLength, Canvas canvas, Paint paint)
	{
		// green color
		paint.setColor(0xFF007900);
		
		// draw the field
		canvas.drawRect(LEFT_MARGIN, TOP_MARGIN, RIGHT_MARGIN, BOTTOM_MARGIN, paint);
		
		// 2 = number of pixels between out of bounds and hash mark
		// 18 = length of the hash mark
		drawHashLines(LEFT_MARGIN, RIGHT_MARGIN, BOTTOM_MARGIN, PIXELS_PER_YARD, FIELD_LINE_WIDTHS, DENSITY, 
				outOfBoundsSpacing, hashLength, canvas, paint);
		
		// PIXELS_PER_YARD * 5, because we are drawing 5 yard lines
		drawFiveYardLines(LEFT_MARGIN, RIGHT_MARGIN, BOTTOM_MARGIN, PIXELS_PER_YARD, FIELD_LINE_WIDTHS, canvas, paint);
		
		// blue color
		paint.setColor(0xFF000080);
		
		// draw line of scrimmage
		paint.setStrokeWidth(6/DENSITY);
		
		// want to draw line of scrimmage at 20 yard line
		float lineOfScrimmageYValue = BOTTOM_MARGIN-(PIXELS_PER_YARD*20)+(FIELD_LINE_WIDTHS/2)/DENSITY;
		canvas.drawLine(LEFT_MARGIN, lineOfScrimmageYValue, RIGHT_MARGIN, lineOfScrimmageYValue, paint);
		
		// fill = fill enclosed shapes with the color, like a circle with the middle one color
		paint.setStyle(Paint.Style.FILL);
	}
	
	public static void drawBitmapHashLines(float LEFT_MARGIN, float RIGHT_MARGIN, float BOTTOM_MARGIN, 
			float PIXELS_PER_YARD, float FIELD_LINE_WIDTHS, float DENSITY, float outOfBoundsSpacing, 
			float hashLength, Canvas canvas, Paint paint)
	{
		paint.setColor(Color.WHITE);
		// draw a stroke, not a line
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(FIELD_LINE_WIDTHS);
		
		float middleHashOffset = (450/DENSITY);
		for (int i = 0; i < 45; i++)
		{
			if (!(i % 5 == 0))
			{
				float temp = BOTTOM_MARGIN - (PIXELS_PER_YARD*i) + (FIELD_LINE_WIDTHS/2);
				float leftMarginPlusOutOfBounds = LEFT_MARGIN + outOfBoundsSpacing;
				float rightMarginPlusHashLength = RIGHT_MARGIN - middleHashOffset;
				float leftMarginPlusHashLength = LEFT_MARGIN + middleHashOffset;
				canvas.drawLine(leftMarginPlusOutOfBounds, temp, 
						leftMarginPlusOutOfBounds + hashLength, temp, paint);
				canvas.drawLine(RIGHT_MARGIN - hashLength, temp, RIGHT_MARGIN-outOfBoundsSpacing, temp, paint);
				canvas.drawLine(leftMarginPlusHashLength - hashLength/2, temp, 
						leftMarginPlusHashLength + hashLength/2, temp, paint);
				canvas.drawLine(rightMarginPlusHashLength - hashLength/2, temp, 
						rightMarginPlusHashLength + hashLength/2, temp, paint);
			}
		}
	}
	
	public static void drawHashLines(float LEFT_MARGIN, float RIGHT_MARGIN, float BOTTOM_MARGIN, 
			float PIXELS_PER_YARD, float FIELD_LINE_WIDTHS, float DENSITY, float outOfBoundsSpacing, 
			float hashLength, Canvas canvas, Paint paint)
	{
		paint.setColor(Color.WHITE);
		// draw a stroke, not a line
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(FIELD_LINE_WIDTHS);
		
		float middleHashOffset = (LEFT_MARGIN+RIGHT_MARGIN)*0.375f;
		for (int i = 0; i < 45; i++)
		{
			if (!(i % 5 == 0))
			{
				float temp = BOTTOM_MARGIN - (PIXELS_PER_YARD*i) + (FIELD_LINE_WIDTHS/2);
				float leftMarginPlusOutOfBounds = LEFT_MARGIN + outOfBoundsSpacing;
				float rightMarginPlusHashLength = RIGHT_MARGIN - middleHashOffset;
				float leftMarginPlusHashLength = LEFT_MARGIN + middleHashOffset;
				canvas.drawLine(leftMarginPlusOutOfBounds, temp, 
						leftMarginPlusOutOfBounds + hashLength, temp, paint);
				canvas.drawLine(RIGHT_MARGIN - hashLength, temp, RIGHT_MARGIN-outOfBoundsSpacing, temp, paint);
				canvas.drawLine(leftMarginPlusHashLength - hashLength/2, temp, 
						leftMarginPlusHashLength + hashLength/2, temp, paint);
				canvas.drawLine(rightMarginPlusHashLength - hashLength/2, temp, 
						rightMarginPlusHashLength + hashLength/2, temp, paint);
			}
		}
	}
	
	public static void drawFiveYardLines(float LEFT_MARGIN, float RIGHT_MARGIN, float BOTTOM_MARGIN, 
			float PIXELS_PER_YARD, float FIELD_LINE_WIDTHS, Canvas canvas, Paint paint)
	{
		paint.setColor(Color.WHITE);
		// draw a stroke, not a line
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(FIELD_LINE_WIDTHS);
		
		float pixelsPerFiveYards = PIXELS_PER_YARD*5;
		float halfFieldLineWidths =  FIELD_LINE_WIDTHS/2;
		for (int i = 0; i < 8; i++)
		{
			float temp = BOTTOM_MARGIN - pixelsPerFiveYards + halfFieldLineWidths - i*pixelsPerFiveYards;
			canvas.drawLine(LEFT_MARGIN, temp, RIGHT_MARGIN, temp, paint);
		}
	}
	
	public static void actionMove(Field field, int playerIndex, int x, int y)
	{
		if (playerIndex != -1)
		{
			Player thisPlayer =  field.getPlayer(playerIndex);
			Location thisLocation = thisPlayer.getLocation();
			int deltaX = x - thisLocation.getX();
			int deltaY = y - thisLocation.getY();
			for (int i = 0; i < thisPlayer.getRouteLocations().size(); i++)
			{
				Location routeLocation = thisPlayer.getRouteLocations().get(i);
				routeLocation.changeLocation(routeLocation.getX() + deltaX, routeLocation.getY() + deltaY);
			}
			field.getPlayer(playerIndex).setLocation(new Location(x, y));
		}
	}
	
	// boolean[0] = added more than 11 players
	// boolean[1] = player added on other side of line of scrimmage
	// boolean[2] = player put on top of another player
	public static boolean[] actionUp(Field field, int playerIndex, float LEFT_MARGIN, float PLAYER_ICON_RADIUS, 
			float RIGHT_MARGIN, float TOP_MARGIN, float BOTTOM_MARGIN, float TOP_ANDROID_BAR, float PIXELS_PER_YARD, 
			float FIELD_LINE_WIDTHS, float DENSITY, int x, int y, boolean moreThanElevenPlayers, boolean movePlayer,
			boolean addingPlayer)
	{
		boolean retVal[] = {moreThanElevenPlayers, false, false};
		if (playerIndex != -1)
		{
			if (movePlayer || addingPlayer)
			{
				// is player outside of the field?
				if (x < LEFT_MARGIN + PLAYER_ICON_RADIUS || x > RIGHT_MARGIN - PLAYER_ICON_RADIUS 
						|| y < TOP_MARGIN + TOP_ANDROID_BAR + PLAYER_ICON_RADIUS 
						|| y > BOTTOM_MARGIN + TOP_ANDROID_BAR - PLAYER_ICON_RADIUS)
				{
					field.getAllPlayers().remove(playerIndex);
					retVal[0] = false;
					// disable route possibilities
					EditorActivity.setPlayerIndex(-1);
				}
				else
				{
					Player tempPlayer = field.getAllPlayers().get(playerIndex);
					int tempX = tempPlayer.getLocation().getX() - (int)(LEFT_MARGIN);
					int tempY = tempPlayer.getLocation().getY() - (int)(TOP_MARGIN);
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
					
					int tempXLocation = tempX + (int)(LEFT_MARGIN);
					int tempYLocation = tempY + (int)(TOP_MARGIN);
					float lineOfScrimmageYValue = BOTTOM_MARGIN+(PLAYER_ICON_RADIUS/2)+TOP_ANDROID_BAR-(PIXELS_PER_YARD*20)+(FIELD_LINE_WIDTHS/2)/DENSITY;
					
					if (tempYLocation <= lineOfScrimmageYValue)
					{
						retVal[1] = true;
					}
					for (int i = 0; i < field.getAllPlayers().size(); i++)
					{
						if (playerIndex != i)
						{
							Location playerLoc = field.getPlayer(i).getLocation();
							int playerX = playerLoc.getX();
							int playerY = playerLoc.getY();
							
							if ((tempXLocation + PLAYER_ICON_RADIUS == playerX || tempXLocation == playerX || tempXLocation - PLAYER_ICON_RADIUS == playerX) &&
									(tempYLocation + PLAYER_ICON_RADIUS == playerY || tempYLocation == playerY || tempYLocation - PLAYER_ICON_RADIUS == playerY))
							{
								retVal[2] = true;
								break;
							}
						}
					}
					Location tempLocation = new Location(tempXLocation, tempYLocation);
					tempPlayer.setLocation(tempLocation);
					EditorActivity.setPlayerIndex(playerIndex);
				}
			}
			else
			{
				EditorActivity.setPlayerIndex(playerIndex);
			}
		}
		else
		{
			EditorActivity.setPlayerIndex(-1);
		}
		return retVal;
	}
	
	// boolean[0] = added more than 11 players
	// boolean[1] = adding a new player
	// boolean[2] = clicking on a button
	public static boolean[] actionDown(Field field, Field fieldForCreatePlayer, float TOUCH_SENSITIVITY, int x, int y, 
			int playerIndex, Route route, Path path, float PLAYER_ICON_RADIUS, float BUTTON_Y_VALUE, float BUTTON_X_VALUE)
	{
		int playerXPos = -1;
		int playerYPos = -1;
		boolean[] retVal = {false, false, false};
		
		double playerIndexDistance = Float.MAX_VALUE;
		
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
				field.addPlayer(temp.getLocation(), temp.getPosition(), route, path); // add to field
				playerIndex = field.getAllPlayers().size()-1; // this player is the last 
				// player to be added to field
				staticPlayerSelected = true; // flag to say that one of the 8 players has been selected
				retVal[1] = true;
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
						hasBeenSet = true;
					}
				}
			}
			// if not selected reset player index
			if (!hasBeenSet)
			{
				float buttonLowerValue = BUTTON_Y_VALUE - PLAYER_ICON_RADIUS;
				float buttonUpperValue = BUTTON_Y_VALUE + PLAYER_ICON_RADIUS;
				if (x >= BUTTON_X_VALUE && x <= BUTTON_X_VALUE+5*PLAYER_ICON_RADIUS && y >= buttonLowerValue && y <= buttonUpperValue)
				{
					retVal[2] = true;
				}
				else if (x >= BUTTON_X_VALUE+6*PLAYER_ICON_RADIUS && x <= BUTTON_X_VALUE+11*PLAYER_ICON_RADIUS && y >= buttonLowerValue && y <= buttonUpperValue)
				{
					retVal[2] = true;
				}
				else
				{
					EditorActivity.setPlayerIndex(-1);
				}
			}
			else
			{
				EditorActivity.setPlayerIndex(playerIndex);
			}
		}
		else
		{
			EditorActivity.setPlayerIndex(playerIndex);
			if (field.getAllPlayers().size() > 11)
			{
				retVal[0] = true;
			}
		}
		return retVal;
	}
	
	public static void drawButtons(Canvas canvas, Paint paint, float DENSITY, float TOP_ANDROID_BAR, float TOP_MARGIN, 
			float PIXELS_PER_YARD, Route route, Path path, float FIELD_LINE_WIDTHS, float PLAYER_ICON_RADIUS, 
			float BUTTON_Y_VALUE, float BUTTON_X_VALUE)
	{
		paint.setStrokeWidth(FIELD_LINE_WIDTHS);
		paint.setColor(Color.BLACK);
		if (path == Path.DOTTED)
		{
			paint.setPathEffect(new DashPathEffect(new float[] {10,10}, 0));
		}
		float value = BUTTON_Y_VALUE-TOP_ANDROID_BAR;
		canvas.drawLine(BUTTON_X_VALUE, value, BUTTON_X_VALUE+5*PLAYER_ICON_RADIUS, value, paint);
		paint.setPathEffect(null);
		canvas.drawLine(BUTTON_X_VALUE+6*PLAYER_ICON_RADIUS, value, BUTTON_X_VALUE+11*PLAYER_ICON_RADIUS, value, paint);
		drawEndOfRoute(canvas, paint, (int)(BUTTON_X_VALUE+11*PLAYER_ICON_RADIUS), (int)value, PIXELS_PER_YARD, 180, route);
	}
}
