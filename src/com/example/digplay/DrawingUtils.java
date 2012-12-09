package com.example.digplay;

import com.businessclasses.Field;
import com.businessclasses.Location;
import com.businessclasses.Path;
import com.businessclasses.Player;
import com.businessclasses.Position;
import com.businessclasses.Route;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;

public class DrawingUtils {
	
	public static int drawCreatePlayers(Field fieldForCreatePlayer, Canvas canvas, Paint paint, 
			int TOP_ANDROID_BAR, int PLAYER_ICON_RADIUS, int BAR_Y_VALUE)
	{
		int threeTimesIcon = PLAYER_ICON_RADIUS*3;
		int xValue = threeTimesIcon;
		
		// add players at bottom of screen, 75dp width between each of them
		Location playerLocQB = new Location(xValue, BAR_Y_VALUE);
		fieldForCreatePlayer.addPlayer(playerLocQB, Position.QB);
		
		xValue += threeTimesIcon;
		
		Location playerLocWR = new Location(xValue, BAR_Y_VALUE);
		fieldForCreatePlayer.addPlayer(playerLocWR, Position.WR);
		
		xValue += threeTimesIcon;
		
		Location playerLocRB = new Location(xValue, BAR_Y_VALUE);
		fieldForCreatePlayer.addPlayer(playerLocRB, Position.RB);
		
		xValue += threeTimesIcon;
		
		Location playerLocFB = new Location(xValue, BAR_Y_VALUE);
		fieldForCreatePlayer.addPlayer(playerLocFB, Position.FB);
		
		xValue += threeTimesIcon;
		
		Location playerLocTE = new Location(xValue, BAR_Y_VALUE);
		fieldForCreatePlayer.addPlayer(playerLocTE, Position.TE);
		
		xValue += threeTimesIcon;
		
		Location playerLocC = new Location(xValue, BAR_Y_VALUE);
		fieldForCreatePlayer.addPlayer(playerLocC, Position.C);
		
		xValue += threeTimesIcon;
		
		Location playerLocG = new Location(xValue, BAR_Y_VALUE);
		fieldForCreatePlayer.addPlayer(playerLocG, Position.G);
		
		xValue += threeTimesIcon;
		
		Location playerLocT = new Location(xValue, BAR_Y_VALUE);
		fieldForCreatePlayer.addPlayer(playerLocT, Position.T);
		
		xValue += PLAYER_ICON_RADIUS*2;

		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(2);

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
		return xValue;
	}
	
	public static void drawPlayers(Field field, int xOffset, int yOffset, Canvas canvas, 
			Paint paint, int playerIndex, int color, int PLAYER_ICON_RADIUS)
	{
		int xpos = -1;
		int ypos = -1;
		float height = -1;
		
		// for drawing the text
		paint.setTextAlign(Align.CENTER);
		paint.setTextSize(PLAYER_ICON_RADIUS);
		paint.setStrokeWidth(2);
		
		for (int i = 0; i < field.getAllPlayers().size(); i++)
		{
			Player tempPlayer = field.getAllPlayers().get(i);
			Location tempLocation = tempPlayer.getLocation();
			xpos = tempLocation.getX() - xOffset;
			ypos = tempLocation.getY() - yOffset;
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
	
	public static void drawRoutes(Field field, int xOffset, int yOffset, int FIELD_LINE_WIDTHS, Canvas canvas, 
			Paint paint, float PIXELS_PER_YARD, float DENSITY)
	{
		paint.setColor(Color.YELLOW);
		paint.setStrokeWidth(FIELD_LINE_WIDTHS);
		
		for (int i = 0; i < field.getAllPlayers().size(); i++)
		{
			Player tempPlayer = field.getAllPlayers().get(i);
			// set x and y previous values to player's x/y values
			int previousX = tempPlayer.getLocation().getX() - xOffset;
			int previousY = tempPlayer.getLocation().getY() - yOffset;
			if (tempPlayer.getPath() == Path.DOTTED)
			{
				float dashAdjusted = 10/DENSITY;
				paint.setPathEffect(new DashPathEffect(new float[] {dashAdjusted, dashAdjusted}, 0));
			}
			for (int j = 0; j < tempPlayer.getRouteLocations().size(); j++)
			{
				Location tempLocation = tempPlayer.getRouteLocations().get(j);
				int currentX = tempLocation.getX() - xOffset;
				int currentY = tempLocation.getY() - yOffset;
				canvas.drawLine(previousX, previousY, currentX, currentY, paint);
				if (j == tempPlayer.getRouteLocations().size()-1)
				{
					int deltaX = previousX - currentX;
					int deltaY = previousY - currentY;
					float differenceAngle = (float)(Math.atan2(deltaY, deltaX) * 180 / Math.PI);
					// solid line
					paint.setPathEffect(null);
					drawEndOfRoute(canvas, paint, currentX, currentY, PIXELS_PER_YARD, differenceAngle, tempPlayer.getRoute());
				}
				previousX = currentX;
				previousY = currentY;
			}
			// solid line
			paint.setPathEffect(null);
		}
		// solid line
		paint.setPathEffect(null);
	}
	
	public static void drawField(int LEFT_MARGIN, int RIGHT_MARGIN, int TOP_MARGIN, 
			int BOTTOM_MARGIN, float DENSITY, int FIELD_LINE_WIDTHS, float PIXELS_PER_YARD, 
			int outOfBoundsSpacing, int hashLength, Canvas canvas, Paint paint)
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
		float lineOfScrimmageYValue = BOTTOM_MARGIN-(PIXELS_PER_YARD*20)+(FIELD_LINE_WIDTHS/2);
		canvas.drawLine(LEFT_MARGIN, lineOfScrimmageYValue, RIGHT_MARGIN, lineOfScrimmageYValue, paint);
		
		// fill = fill enclosed shapes with the color, like a circle with the middle one color
		paint.setStyle(Paint.Style.FILL);
	}
	
	public static void drawHashLines(int LEFT_MARGIN, int RIGHT_MARGIN, int BOTTOM_MARGIN, 
			float PIXELS_PER_YARD, int FIELD_LINE_WIDTHS, float DENSITY, int outOfBoundsSpacing, 
			int hashLength, Canvas canvas, Paint paint)
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
	
	public static void drawFiveYardLines(int LEFT_MARGIN, int RIGHT_MARGIN, int BOTTOM_MARGIN, 
			float PIXELS_PER_YARD, int FIELD_LINE_WIDTHS, Canvas canvas, Paint paint)
	{
		paint.setColor(Color.WHITE);
		// draw a stroke, not a line
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(FIELD_LINE_WIDTHS);
		
		float pixelsPerFiveYards = PIXELS_PER_YARD*5;
		float halfFieldLineWidths =  FIELD_LINE_WIDTHS/2;
		for (int i = 0; i < 9; i++)
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
	public static boolean[] actionUp(Field field, int playerIndex, int LEFT_MARGIN, int PLAYER_ICON_RADIUS, 
			int RIGHT_MARGIN, int TOP_MARGIN, int BOTTOM_MARGIN, int TOP_ANDROID_BAR, float PIXELS_PER_YARD, 
			int FIELD_LINE_WIDTHS, float DENSITY, int x, int y, boolean moreThanElevenPlayers, boolean movePlayer,
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
					
					int temp = (RIGHT_MARGIN) % PLAYER_ICON_RADIUS;
					int tempX = tempPlayer.getLocation().getX() - LEFT_MARGIN;
					int tempY = tempPlayer.getLocation().getY() - TOP_MARGIN;
					// this is the grid
					float halfPlayerIconRadius = PLAYER_ICON_RADIUS/2;
					
					if(tempX % PLAYER_ICON_RADIUS >= halfPlayerIconRadius)
					{
						tempX = tempX + PLAYER_ICON_RADIUS - tempX % PLAYER_ICON_RADIUS;
					}
					else
					{
						tempX = tempX - tempX % PLAYER_ICON_RADIUS;
					}
					if(tempY % PLAYER_ICON_RADIUS >= halfPlayerIconRadius)
					{
						tempY = tempY + PLAYER_ICON_RADIUS - tempY % PLAYER_ICON_RADIUS;
					}
					else
					{
						tempY = tempY - tempY % PLAYER_ICON_RADIUS;
					}
					
					int tempXLocation = tempX + LEFT_MARGIN;
					int tempYLocation = tempY + TOP_MARGIN;
					float lineOfScrimmageYValue = BOTTOM_MARGIN+PLAYER_ICON_RADIUS + TOP_ANDROID_BAR-(PIXELS_PER_YARD*20)+(FIELD_LINE_WIDTHS/2);
					
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
	// boolean[2] = clicking on path button
	// boolean[3] = clicking on route button
	public static boolean[] actionDown(Field field, Field fieldForCreatePlayer, int TOUCH_SENSITIVITY, int x, int y, 
			int playerIndex, Route route, Path path, int PLAYER_ICON_RADIUS, int BUTTON_Y_VALUE, int BUTTON_X_VALUE,
			int FIELD_LINE_WIDTHS)
	{
		int playerXPos = -1;
		int playerYPos = -1;
		boolean[] retVal = {false, false, false, false};
		
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
				float doubleLineWidths = FIELD_LINE_WIDTHS * 2;
				float quadLineWidths = doubleLineWidths * 2;
				float buttonLength = 5*PLAYER_ICON_RADIUS + quadLineWidths;
				float buttonLowerValue = BUTTON_Y_VALUE - PLAYER_ICON_RADIUS - doubleLineWidths;
				float buttonUpperValue = BUTTON_Y_VALUE + PLAYER_ICON_RADIUS + doubleLineWidths;
				float secondButtonStartPos = BUTTON_X_VALUE + buttonLength + PLAYER_ICON_RADIUS;
				if (x >= BUTTON_X_VALUE && x <= (BUTTON_X_VALUE + buttonLength) && y >= buttonLowerValue && y <= buttonUpperValue)
				{
					retVal[2] = true;
				}
				else if (x >= secondButtonStartPos && x <= (secondButtonStartPos + buttonLength) 
						&& y >= buttonLowerValue && y <= buttonUpperValue)
				{
					retVal[3] = true;
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
	
	public static void drawButtons(Canvas canvas, Paint paint, float DENSITY, int TOP_ANDROID_BAR, int TOP_MARGIN, 
			float PIXELS_PER_YARD, Route route, Path path, int FIELD_LINE_WIDTHS, int PLAYER_ICON_RADIUS, 
			int BUTTON_Y_VALUE, int BUTTON_X_VALUE)
	{
		paint.setStrokeWidth(FIELD_LINE_WIDTHS);
		float value = BUTTON_Y_VALUE-TOP_ANDROID_BAR;
		// gray for buttons
		paint.setColor(0xE7E7E7FF);
		float doubleLineWidths = FIELD_LINE_WIDTHS * 2;
		float quadLineWidths = doubleLineWidths * 2;
		float buttonLength = 5*PLAYER_ICON_RADIUS + quadLineWidths;
		float buttonHeightOffset = PLAYER_ICON_RADIUS + doubleLineWidths;
		float lineLength = 5*PLAYER_ICON_RADIUS + doubleLineWidths;
		float secondButtonStartPos = BUTTON_X_VALUE + buttonLength + PLAYER_ICON_RADIUS;
		canvas.drawRect(BUTTON_X_VALUE, value + buttonHeightOffset, 
				BUTTON_X_VALUE + buttonLength, value - buttonHeightOffset, paint);
		canvas.drawRect(secondButtonStartPos, value + buttonHeightOffset, 
				secondButtonStartPos + buttonLength, value - buttonHeightOffset, paint);
		paint.setColor(Color.BLACK);
		if (path == Path.DOTTED)
		{
			float dashAdjusted = 10/DENSITY;
			paint.setPathEffect(new DashPathEffect(new float[] {dashAdjusted, dashAdjusted}, 0));
		}
		canvas.drawLine(BUTTON_X_VALUE + doubleLineWidths, value, BUTTON_X_VALUE + lineLength, value, paint);
		paint.setPathEffect(null);
		canvas.drawLine(secondButtonStartPos + doubleLineWidths, value, secondButtonStartPos + lineLength, value, paint);
		drawEndOfRoute(canvas, paint, (int)(secondButtonStartPos + lineLength), (int)value, PIXELS_PER_YARD, 180, route);
	}
}
