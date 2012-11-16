package com.example.digplay;

import com.businessclasses.Field;
import com.businessclasses.Location;
import com.businessclasses.Player;
import com.businessclasses.Position;
import com.businessclasses.Route;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.FloatMath;

public class DrawingUtils {
	
	public static void drawCreatePlayers(Field fieldForCreatePlayer, Canvas canvas, Paint paint, float DENSITY, 
			float TOP_ANDROID_BAR, float PLAYER_ICON_RADIUS)
	{
		float adjustedHeight = (670*DENSITY)+TOP_ANDROID_BAR; // pixel location we want to draw the 8
		   // created players at. 50 pixels is used
		   // at the top for all android devices

		// add players at bottom of screen, 75dp width between each of them
		Location playerLocQB = new Location((int)(75*DENSITY), (int)(adjustedHeight));
		fieldForCreatePlayer.addPlayer(playerLocQB, Position.QUARTERBACK);
		
		Location playerLocWR = new Location((int)(150*DENSITY), (int)(adjustedHeight));
		fieldForCreatePlayer.addPlayer(playerLocWR, Position.WIDE_RECIEVER);
		
		Location playerLocRB = new Location((int)(225*DENSITY), (int)(adjustedHeight));
		fieldForCreatePlayer.addPlayer(playerLocRB, Position.RUNNING_BACK);
		
		Location playerLocFB = new Location((int)(300*DENSITY), (int)(adjustedHeight));
		fieldForCreatePlayer.addPlayer(playerLocFB, Position.FULLBACK);
		
		Location playerLocTE = new Location((int)(375*DENSITY), (int)(adjustedHeight));
		fieldForCreatePlayer.addPlayer(playerLocTE, Position.TIGHT_END);
		
		Location playerLocC = new Location((int)(450*DENSITY), (int)(adjustedHeight));
		fieldForCreatePlayer.addPlayer(playerLocC, Position.CENTER);
		
		Location playerLocG = new Location((int)(525*DENSITY), (int)(adjustedHeight));
		fieldForCreatePlayer.addPlayer(playerLocG, Position.GUARD);
		
		Location playerLocT = new Location((int)(600*DENSITY), (int)(adjustedHeight));
		fieldForCreatePlayer.addPlayer(playerLocT, Position.TACKLE);

		// orangish color
		paint.setColor(0xFFFF8000);

		// y values are all the same, so just reuse one
		float eightPlayerY = playerLocQB.getY() - TOP_ANDROID_BAR;
		
		// the c does is not exactly the same as the real pixels, because
		// the c is drawn at 50 pixels down from the top of the screen
		canvas.drawCircle(playerLocQB.getX(), eightPlayerY, PLAYER_ICON_RADIUS, paint);
		canvas.drawCircle(playerLocWR.getX(), eightPlayerY, PLAYER_ICON_RADIUS, paint);
		canvas.drawCircle(playerLocRB.getX(), eightPlayerY, PLAYER_ICON_RADIUS, paint);
		canvas.drawCircle(playerLocFB.getX(), eightPlayerY, PLAYER_ICON_RADIUS, paint);
		canvas.drawCircle(playerLocTE.getX(), eightPlayerY, PLAYER_ICON_RADIUS, paint);
		canvas.drawCircle(playerLocC.getX(), eightPlayerY, PLAYER_ICON_RADIUS, paint);
		canvas.drawCircle(playerLocG.getX(), eightPlayerY, PLAYER_ICON_RADIUS, paint);
		canvas.drawCircle(playerLocT.getX(), eightPlayerY, PLAYER_ICON_RADIUS, paint);
					
		paint.setColor(Color.BLACK);
		
		paint.setTextAlign(Align.CENTER);
		paint.setTextSize(PLAYER_ICON_RADIUS);
		
		// descent and ascent are used for centering text vertically
		float height = (playerLocQB.getY()-TOP_ANDROID_BAR)-((paint.descent() + paint.ascent()) / 2);
		
		canvas.drawText("QB", playerLocQB.getX(), height, paint);
		canvas.drawText("WR", playerLocWR.getX(), height, paint);
		canvas.drawText("RB", playerLocRB.getX(), height, paint);
		canvas.drawText("FB", playerLocFB.getX(), height, paint);
		canvas.drawText("TE", playerLocTE.getX(), height, paint);
		canvas.drawText("C", playerLocC.getX(), height, paint);
		canvas.drawText("G", playerLocG.getX(), height, paint);
		canvas.drawText("T", playerLocT.getX(), height, paint);
	}
	
	public static void drawPlayersWithOffset(Field field, float xOffset, float yOffset, Canvas canvas, Paint paint, int playerIndex, int color, float PLAYER_ICON_RADIUS)
	{
		int xpos = -1;
		int ypos = -1;
		float height = -1;
		
		for (int i = 0; i < field.getAllPlayers().size(); i++)
		{
			xpos = (int) (field.getAllPlayers().get(i).getLocation().getX() - xOffset);
			// -50, because 50 pixels are used at the top of the screen on all android devices
			// for the time and app name
			ypos = (int) (field.getAllPlayers().get(i).getLocation().getY() - yOffset);
			// this is the selected player
			System.out.println(playerIndex);
			System.out.println(i);
			if (playerIndex == i)
			{
				paint.setColor(color);
			}
			// draw the player again, but red this time
			canvas.drawCircle(xpos, ypos, PLAYER_ICON_RADIUS, paint);
			// descent and ascent are used for centering text vertically
			height = ypos-((paint.descent() + paint.ascent()) / 2);
			
			paint.setColor(Color.BLACK);
			switch(field.getAllPlayers().get(i).getPosition()) {
			case QUARTERBACK:
				drawCenteredText("QB", xpos, height, canvas, paint);
				break;
			case WIDE_RECIEVER:
				drawCenteredText("WR", xpos, height, canvas, paint);
				break;
			case RUNNING_BACK:
				drawCenteredText("RB", xpos, height, canvas, paint);
				break;
			case FULLBACK:
				drawCenteredText("FB", xpos, height, canvas, paint);
				break;
			case TIGHT_END:
				drawCenteredText("TE", xpos, height, canvas, paint);
				break;
			case CENTER:
				drawCenteredText("C", xpos, height, canvas, paint);
				break;
			case GUARD:
				drawCenteredText("G", xpos, height, canvas, paint);
				break;
			case TACKLE:
				drawCenteredText("T", xpos, height, canvas, paint);
				break;
			default:
				break;
			}
			// reset to orangish color
			paint.setColor(0xFFFF8000);
		}
	}
	
	public static void drawBitmapPlayers(Field field, float TOP_ANDROID_BAR, float PLAYER_ICON_RADIUS,
			int playerIndex, Canvas canvas, Paint paint, int color, float LEFT_MARGIN, float TOP_MARGIN)
	{
		// orangish color
		paint.setColor(0xFFFF8000);

		drawPlayersWithOffset(field, LEFT_MARGIN, TOP_MARGIN + TOP_ANDROID_BAR, canvas, paint, playerIndex, color, PLAYER_ICON_RADIUS);
	}
	
	public static void drawPlayers(Field field, float TOP_ANDROID_BAR, float PLAYER_ICON_RADIUS,
			int playerIndex, Canvas canvas, Paint paint, int color)
	{
		// orangish color
		paint.setColor(0xFFFF8000);
		
		drawPlayersWithOffset(field, 0, TOP_ANDROID_BAR, canvas, paint, playerIndex, color, PLAYER_ICON_RADIUS);
	}
	
	// for drawing the positions on players
	public static void drawCenteredText(String value, int xposition, float yposition, Canvas canvas, Paint paint)
	{
		canvas.drawText(value, xposition, yposition, paint);
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
	
	public static void drawBitmapRoutes(Field field, float FIELD_LINE_WIDTHS, float TOP_ANDROID_BAR, Canvas canvas, 
			Paint paint, float LEFT_MARGIN, float TOP_MARGIN, float PIXELS_PER_YARD, int playerIndex)
	{
		paint.setColor(0xFFFF8000);
		
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(FIELD_LINE_WIDTHS);
		
		for (int i = 0; i < field.getAllPlayers().size(); i++)
		{
			Player tempPlayer = field.getAllPlayers().get(i);
			int playerX = (int) (tempPlayer.getLocation().getX() - LEFT_MARGIN);
			int playerY = (int) (tempPlayer.getLocation().getY() - TOP_ANDROID_BAR - TOP_MARGIN);
			for (int j = 0; j < tempPlayer.getRouteLocations().size(); j++)
			{
				Location tempLocation = tempPlayer.getRouteLocations().get(j);
				canvas.drawLine(playerX, playerY, tempLocation.getX() - LEFT_MARGIN, tempLocation.getY() - TOP_ANDROID_BAR - TOP_MARGIN, paint);
				int tempX = (int) (tempLocation.getX() - LEFT_MARGIN);
				int tempY = (int) (tempLocation.getY() - TOP_ANDROID_BAR - TOP_MARGIN);
				if (j == tempPlayer.getRouteLocations().size()-1)
				{
					int deltaX = playerX - tempX;
					int deltaY = playerY - tempY;
					float differenceAngle = (float)(Math.atan2(deltaY, deltaX) * 180 / Math.PI);
					drawEndOfRoute(canvas, paint, tempX, tempY, PIXELS_PER_YARD, differenceAngle, tempPlayer.getRoute());
				}
				playerX = tempX;
				playerY = tempY;
			}
		}
		// orangish color
		paint.setColor(0xFFFF8000);
	}
	
	public static void drawRoutes(Field field, float FIELD_LINE_WIDTHS, float TOP_ANDROID_BAR, Canvas canvas, 
			Paint paint, float PIXELS_PER_YARD, int playerIndex)
	{
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(FIELD_LINE_WIDTHS);
		
		for (int i = 0; i < field.getAllPlayers().size(); i++)
		{
			Player tempPlayer = field.getAllPlayers().get(i);
			int playerX = (int) (tempPlayer.getLocation().getX());
			int playerY = (int) (tempPlayer.getLocation().getY() - TOP_ANDROID_BAR);
			for (int j = 0; j < tempPlayer.getRouteLocations().size(); j++)
			{
				Location tempLocation = tempPlayer.getRouteLocations().get(j);
				canvas.drawLine(playerX, playerY, tempLocation.getX(), tempLocation.getY() - TOP_ANDROID_BAR, paint);
				int tempX = (int) (tempLocation.getX());
				int tempY = (int) (tempLocation.getY() - TOP_ANDROID_BAR);
				if (j == tempPlayer.getRouteLocations().size()-1)
				{
					int deltaX = playerX - tempX;
					int deltaY = playerY - tempY;
					float differenceAngle = (float)(Math.atan2(deltaY, deltaX) * 180 / Math.PI);
					drawEndOfRoute(canvas, paint, tempX, tempY, PIXELS_PER_YARD, differenceAngle, tempPlayer.getRoute());
				}
				playerX = tempX;
				playerY = tempY;
			}
		}
		// orangish color
		paint.setColor(0xFFFF8000);
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
		paint.setStrokeWidth(6*DENSITY);
		
		// want to draw line of scrimmage at 20 yard line
		float lineOfScrimmageYValue = BOTTOM_MARGIN-(PIXELS_PER_YARD*20)+(FIELD_LINE_WIDTHS/2)*DENSITY;
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
		
		float middleHashOffset = (450*DENSITY);
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
		
		float middleHashOffset = (450*DENSITY);
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
	
	public static String[] getRoutes(){
		Route[] routes = Route.values();
		String[] stringRoutes = new String[routes.length];
		for(int i = 0;i < routes.length;i++){
			stringRoutes[i] = routes[i].toString();
		}
		return stringRoutes;
	}
	
	public static Route LookupRoute(int value)
	{
		Route[] routes = Route.values();
		return routes[value];
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
	
	public static int actionUp(Field field, int playerIndex, float LEFT_MARGIN, float PLAYER_ICON_RADIUS, 
			float RIGHT_MARGIN, float TOP_MARGIN, float BOTTOM_MARGIN, float TOP_ANDROID_BAR, int x, int y)
	{
		if (playerIndex != -1)
		{
			// is player outside of the field?
			if (x < LEFT_MARGIN + PLAYER_ICON_RADIUS || x > RIGHT_MARGIN - PLAYER_ICON_RADIUS 
					|| y < TOP_MARGIN + TOP_ANDROID_BAR + PLAYER_ICON_RADIUS 
					|| y > BOTTOM_MARGIN + TOP_ANDROID_BAR - PLAYER_ICON_RADIUS)
			{
				field.getAllPlayers().remove(playerIndex);
				// disable route possibilities
				return -1;
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
				Location tempLocation = new Location(tempX + (int)(LEFT_MARGIN), tempY + (int)(TOP_MARGIN));
				tempPlayer.setLocation(tempLocation);
				return playerIndex;
			}
		}
		return -1;
	}
	
	public static int actionDown(Field field, Field fieldForCreatePlayer, float TOUCH_SENSITIVITY, int x, int y, int playerIndex, Route route)
	{
		int playerXPos = -1;
		int playerYPos = -1;
		
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
				field.addPlayerAndRoute(temp.getLocation(), temp.getPosition(), route); // add to field
				playerIndex = field.getAllPlayers().size()-1; // this player is the last 
				// player to be added to field
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
						hasBeenSet = true;
					}
				}
			}
			// if not selected, disable the route spinner and reset player index
			if (!hasBeenSet)
			{
				return -1;
			}
			else
			{
				return playerIndex;
			}
		}
		else
		{
			return playerIndex;
		}
	}
}
