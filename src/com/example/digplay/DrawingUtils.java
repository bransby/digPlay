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
	
	public static void drawPlayers(Field field, float TOP_ANDROID_BAR, float PLAYER_ICON_RADIUS, float DENSITY, 
			int playerIndex, Field fieldForCreatePlayer, Canvas canvas, Paint paint)
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
	
	// for drawing the positions on players
	public static void drawCenteredText(String value, int xposition, float yposition, Canvas canvas, Paint paint)
	{
		canvas.drawText(value, xposition, yposition, paint);
	}
	
	public static void drawLeftArrow(Canvas canvas, Paint paint, float x, float y, float cosArrow)
	{
		canvas.drawLine(x, y, x + cosArrow, y + cosArrow, paint);
		canvas.drawLine(x, y, x + cosArrow, y - cosArrow, paint);
	}
	
	public static void drawUpLeftArrow(Canvas canvas, Paint paint, float x, float y, float doublesinFortyFiveArrow)
	{
		canvas.drawLine(x, y, x, y + doublesinFortyFiveArrow, paint);
		canvas.drawLine(x, y, x + doublesinFortyFiveArrow, y, paint);
	}
	
	public static void drawRightArrow(Canvas canvas, Paint paint, float x, float y, float cosArrow)
	{
		canvas.drawLine(x, y, x - cosArrow, y - cosArrow, paint);
		canvas.drawLine(x, y, x - cosArrow, y + cosArrow, paint);
	}
	
	public static void drawUpRightArrow(Canvas canvas, Paint paint, float x, float y, float doublesinFortyFiveArrow)
	{
		canvas.drawLine(x, y, x, y + doublesinFortyFiveArrow, paint);
		canvas.drawLine(x, y, x - doublesinFortyFiveArrow, y, paint);
	}
	
	public static void drawUpArrow(Canvas canvas, Paint paint, float x, float y, float arrowLength, float FIELD_LINE_WIDTHS)
	{
		float temp = (arrowLength)/FloatMath.cos((float) (Math.PI/4)) - FIELD_LINE_WIDTHS;
		canvas.drawLine(x - temp, y + temp, x- FIELD_LINE_WIDTHS, y- FIELD_LINE_WIDTHS, paint);
		canvas.drawLine(x- FIELD_LINE_WIDTHS, y- FIELD_LINE_WIDTHS, x + temp, y + temp, paint);
	}
	
	public static void drawSeventyFiveUpRightArrow(Canvas canvas, Paint paint, float x, float y, float doublesinFortyFiveArrow)
	{
		canvas.rotate(-15);
		canvas.drawLine(x, y, x, y + doublesinFortyFiveArrow, paint);
		canvas.drawLine(x, y, x - doublesinFortyFiveArrow, y, paint);
		canvas.restore();
	}
	
	public static void drawSeventyFiveUpLeftArrow(Canvas canvas, Paint paint, float x, float y, float doublecosArrow)
	{
		canvas.drawLine(x, y, x, y + doublecosArrow, paint);
		canvas.drawLine(x, y, x + doublecosArrow, y, paint);
	}
	
	public static void drawRoutes(Field field, float LEFT_MARGIN, float RIGHT_MARGIN, float TOP_MARGIN, float BOTTOM_MARGIN, float PIXELS_PER_YARD, 
			float PLAYER_ICON_RADIUS, float TOP_ANDROID_BAR, float FIELD_LINE_WIDTHS, Canvas canvas, Paint paint)
	{
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(FIELD_LINE_WIDTHS);
		
		for (int i = 0; i < field.getAllPlayers().size(); i++)
		{
			Player tempPlayer = field.getAllPlayers().get(i);
			int playerX = tempPlayer.getLocation().getX();
			int playerY = tempPlayer.getLocation().getY();
			
			Position playerPosition = tempPlayer.getPosition();
			Route playerRoute = tempPlayer.getRoute();
			int yardage = tempPlayer.getYardage();
			
			float topOfPlayer = playerY - PLAYER_ICON_RADIUS - TOP_ANDROID_BAR; // y value of the top of the player icon
			float sinFortyFive = FloatMath.cos((float) (Math.PI/4)); // used for arrow hands
			float sinSeventyFive = FloatMath.sin((float) (75*Math.PI/180));
			float cosArrow = PIXELS_PER_YARD / sinFortyFive; // for arrows that go up, left, or right (length of hands of arrow)
			float lineWidthsDivTwo = (FIELD_LINE_WIDTHS/2); // used for getting middle of the line
			float yardsPlusTopOfPlayer = topOfPlayer - PIXELS_PER_YARD * yardage; // y value of top of player with yardage offset
			float endOfRouteYards = PIXELS_PER_YARD * 10; // 10 yards of pixels, needed for many routes
			float xPlusEndOfRoute = playerX + endOfRouteYards; // needed for calculating routes that aren't fly routes.
			float xMinusEndOfRoute = playerX - endOfRouteYards; // needed for calculating routes that aren't fly routes.
			float yardsPlusTopPlayerLineWidths = yardsPlusTopOfPlayer + lineWidthsDivTwo; // offset the drawing line width of the top of the player
			float doublecosArrow = cosArrow / sinFortyFive; // for arrows that go left up or right up.
			
			float playerWithAngle = PLAYER_ICON_RADIUS * sinFortyFive; // used for getting X and Y values of top left or top right of icon
			float yardageWithAngle = yardage*PIXELS_PER_YARD * sinFortyFive; // used for getting X and Y values of yardage
			float endOfRouteYardsFortyFiveAngle = endOfRouteYards * sinFortyFive; // for post and flag.
			float topLeftPlayerX = playerX - playerWithAngle; // top left of player icon X value
			float topLeftPlayerY = playerY - TOP_ANDROID_BAR - playerWithAngle; // top left of player icon Y value
			float topLeftPlayerXMinusYardage = topLeftPlayerX - yardageWithAngle; // top right of player icon X value
			float topLeftPlayerYMinusYardage = topLeftPlayerY - yardageWithAngle; // top right of player icon Y value
			
			switch(playerPosition)
			{
			case QUARTERBACK:
				break;
			case WIDE_RECIEVER:
				switch(playerRoute)
				{
				case FLY:
					if (yardage > 0)
					{
						canvas.drawLine(playerX, topOfPlayer, playerX, 
								yardsPlusTopOfPlayer, paint);
						drawUpArrow(canvas, paint, playerX, yardsPlusTopOfPlayer, cosArrow, FIELD_LINE_WIDTHS);
					}
					break;
				case IN:
					if (yardage > 0)
					{
						canvas.drawLine(playerX, topOfPlayer, playerX, 
								yardsPlusTopOfPlayer, paint);
						
						if (playerX >= (RIGHT_MARGIN + LEFT_MARGIN)/2)
						{
							canvas.drawLine(xMinusEndOfRoute, yardsPlusTopPlayerLineWidths, playerX, 
									yardsPlusTopPlayerLineWidths, paint);

							drawLeftArrow(canvas, paint, xMinusEndOfRoute, yardsPlusTopPlayerLineWidths, cosArrow);
						}
						else
						{
							canvas.drawLine(xPlusEndOfRoute, yardsPlusTopPlayerLineWidths, 
									playerX, yardsPlusTopPlayerLineWidths, paint);
							
							drawRightArrow(canvas, paint, xPlusEndOfRoute, yardsPlusTopPlayerLineWidths, cosArrow);
						}
					}
					break;
				case OUT:
					if (yardage > 0)
					{
						canvas.drawLine(playerX, topOfPlayer, playerX, 
								yardsPlusTopOfPlayer, paint);
						if (playerX >= (RIGHT_MARGIN + LEFT_MARGIN)/2)
						{
							if ((playerX + endOfRouteYards) > RIGHT_MARGIN)
							{
								if (playerX <= RIGHT_MARGIN)
								{
									canvas.drawLine(RIGHT_MARGIN, yardsPlusTopOfPlayer + lineWidthsDivTwo, playerX, 
											yardsPlusTopOfPlayer + lineWidthsDivTwo, paint);
									
									drawRightArrow(canvas, paint, RIGHT_MARGIN, yardsPlusTopOfPlayer + lineWidthsDivTwo, cosArrow);
								}
							}
							else
							{
								canvas.drawLine(xPlusEndOfRoute, yardsPlusTopOfPlayer + lineWidthsDivTwo, playerX, 
										yardsPlusTopOfPlayer + lineWidthsDivTwo, paint);
									
								drawRightArrow(canvas, paint, xPlusEndOfRoute, yardsPlusTopOfPlayer + lineWidthsDivTwo, cosArrow);
							}
						}
						else
						{
							if ((playerX - endOfRouteYards) < LEFT_MARGIN)
							{
								if (playerX >= LEFT_MARGIN)
								{
									canvas.drawLine(LEFT_MARGIN, yardsPlusTopOfPlayer + lineWidthsDivTwo, playerX, 
											yardsPlusTopOfPlayer + lineWidthsDivTwo, paint);
									
									drawLeftArrow(canvas, paint, LEFT_MARGIN, yardsPlusTopOfPlayer + lineWidthsDivTwo, cosArrow);
								}
							}
							else
							{
								canvas.drawLine(xMinusEndOfRoute, yardsPlusTopOfPlayer + lineWidthsDivTwo, playerX, 
										yardsPlusTopOfPlayer + lineWidthsDivTwo, paint);
								
								drawLeftArrow(canvas, paint, xMinusEndOfRoute, yardsPlusTopOfPlayer + lineWidthsDivTwo, cosArrow);
							}
						}
					}
					break;
				case SLANT:
					if (yardage > 0)
					{		
						if (playerX >= (RIGHT_MARGIN + LEFT_MARGIN)/2)
						{
							canvas.drawLine(topLeftPlayerX, topLeftPlayerY, 
									topLeftPlayerXMinusYardage, 
									topLeftPlayerYMinusYardage, paint);
							drawUpLeftArrow(canvas, paint, topLeftPlayerXMinusYardage, topLeftPlayerYMinusYardage, doublecosArrow);
						}
						else
						{
							float topRightPlayerX = playerX + playerWithAngle;
							float topRightPlayerY = playerY - TOP_ANDROID_BAR - playerWithAngle;
							float topRightPlayerXPlusYardage = topRightPlayerX + yardageWithAngle;
							float topRightPlayerYMinusYardage = topRightPlayerY - yardageWithAngle;
							canvas.drawLine(topRightPlayerX, topRightPlayerY, 
									topRightPlayerXPlusYardage, 
									topRightPlayerYMinusYardage, paint);
							drawUpRightArrow(canvas, paint, topRightPlayerXPlusYardage, topRightPlayerYMinusYardage, doublecosArrow);
						}
					}
					break;
				case POST:
					if (yardage > 0)
					{
						drawStraightLine(canvas, paint, playerX, topOfPlayer, yardsPlusTopOfPlayer);
						float topOfArrow = yardsPlusTopOfPlayer - endOfRouteYardsFortyFiveAngle;
						if (playerX >= (RIGHT_MARGIN + LEFT_MARGIN)/2)
						{
							if (topOfArrow < TOP_MARGIN)
							{
								float temp = yardsPlusTopOfPlayer - TOP_MARGIN;
								canvas.drawLine(playerX, yardsPlusTopOfPlayer, playerX - temp, 
										TOP_MARGIN, paint);
								drawUpLeftArrow(canvas, paint, playerX - temp, 
										TOP_MARGIN, doublecosArrow);
							}
							else
							{
								canvas.drawLine(playerX, yardsPlusTopOfPlayer, playerX - endOfRouteYardsFortyFiveAngle, 
										topOfArrow, paint);
								drawUpLeftArrow(canvas, paint, playerX - endOfRouteYardsFortyFiveAngle, 
										topOfArrow, doublecosArrow);
							}
						}
						else
						{
							float diff = topOfPlayer - PIXELS_PER_YARD * yardage - (endOfRouteYards*sinFortyFive);
							if (playerX - endOfRouteYards*sinFortyFive < LEFT_MARGIN)
							{
								
								drawPost(canvas, paint, 45, -endOfRouteYards, PIXELS_PER_YARD, playerX, yardsPlusTopOfPlayer, FIELD_LINE_WIDTHS);
							}
							else if (diff < TOP_MARGIN)
							{
								drawPost(canvas, paint, 45, -(topOfPlayer - PIXELS_PER_YARD * yardage - TOP_MARGIN)/sinFortyFive, PIXELS_PER_YARD, playerX, yardsPlusTopOfPlayer, FIELD_LINE_WIDTHS);
							}
							else
							{
								drawPost(canvas, paint, 45, -endOfRouteYards, PIXELS_PER_YARD, playerX, yardsPlusTopOfPlayer, FIELD_LINE_WIDTHS);
							}
						}
					}
					break;
				case FLAG:
					if (yardage > 0)
					{
						drawStraightLine(canvas, paint, playerX, topOfPlayer, yardsPlusTopOfPlayer);
						float topOfArrow = yardsPlusTopOfPlayer - endOfRouteYardsFortyFiveAngle;
						if (playerX >= (RIGHT_MARGIN + LEFT_MARGIN)/2)
						{
							/*
							if (playerX - endOfRouteYards*sinFortyFive > RIGHT_MARGIN)
							{
								float temp = RIGHT_MARGIN - playerX;
								canvas.drawLine(playerX, yardsPlusTopOfPlayer, RIGHT_MARGIN, 
										yardsPlusTopOfPlayer - temp, paint);
								drawUpRightArrow(canvas, paint, RIGHT_MARGIN, 
										yardsPlusTopOfPlayer - temp, doublecosArrow);
							}
							else if (topOfArrow < TOP_MARGIN)
							{
								float temp = yardsPlusTopOfPlayer - TOP_MARGIN;
								canvas.drawLine(playerX, yardsPlusTopOfPlayer, playerX + temp, 
										TOP_MARGIN, paint);
									drawUpRightArrow(canvas, paint,  playerX + temp, 
										TOP_MARGIN, doublecosArrow);
							}
							else
							{
								canvas.drawLine(playerX, yardsPlusTopOfPlayer, playerX + endOfRouteYardsFortyFiveAngle, 
									topOfArrow, paint);
								drawUpRightArrow(canvas, paint, playerX + endOfRouteYardsFortyFiveAngle, 
									topOfArrow, doublecosArrow);
							}
							*/
						}
						else
						{
							float diff = topOfPlayer - PIXELS_PER_YARD * yardage - (endOfRouteYards*sinFortyFive);
							if (playerX - endOfRouteYards*sinFortyFive < LEFT_MARGIN)
							{
								
								drawPost(canvas, paint, -45, -endOfRouteYards, PIXELS_PER_YARD, playerX, yardsPlusTopOfPlayer, FIELD_LINE_WIDTHS);
								/*
								float temp = playerX - LEFT_MARGIN;
								canvas.drawLine(playerX, yardsPlusTopOfPlayer, LEFT_MARGIN, 
										yardsPlusTopOfPlayer - temp, paint);
								drawUpLeftArrow(canvas, paint, LEFT_MARGIN, 
										yardsPlusTopOfPlayer - temp, doublecosArrow);
										*/
							}
							else if (diff < TOP_MARGIN)
							{
								drawPost(canvas, paint, -45, -(topOfPlayer - PIXELS_PER_YARD * yardage - TOP_MARGIN)/sinFortyFive, PIXELS_PER_YARD, playerX, yardsPlusTopOfPlayer, FIELD_LINE_WIDTHS);
							}
							else
							{
								drawPost(canvas, paint, -45, -endOfRouteYards, PIXELS_PER_YARD, playerX, yardsPlusTopOfPlayer, FIELD_LINE_WIDTHS);
							}
						}
					}
					break;
				case SKINNY_POST:
					if (yardage > 0)
					{
						drawStraightLine(canvas, paint, playerX, topOfPlayer, yardsPlusTopOfPlayer);
						if (playerX >= (RIGHT_MARGIN + LEFT_MARGIN)/2)
						{
							float diff = topOfPlayer - PIXELS_PER_YARD * yardage - (endOfRouteYards*sinSeventyFive);
							if (diff < TOP_MARGIN)
							{
								drawPost(canvas, paint, -15, -(topOfPlayer - PIXELS_PER_YARD * yardage - TOP_MARGIN)/sinSeventyFive, PIXELS_PER_YARD, playerX, yardsPlusTopOfPlayer, FIELD_LINE_WIDTHS);
							}
							else
							{
								drawPost(canvas, paint, -15, -endOfRouteYards, PIXELS_PER_YARD, playerX, yardsPlusTopOfPlayer, FIELD_LINE_WIDTHS);
							}
						}
						else
						{
							float diff = topOfPlayer - PIXELS_PER_YARD * yardage - (endOfRouteYards*sinSeventyFive);
							if (diff < TOP_MARGIN)
							{
								drawPost(canvas, paint, 15, -(topOfPlayer - PIXELS_PER_YARD * yardage - TOP_MARGIN)/sinSeventyFive, PIXELS_PER_YARD, playerX, yardsPlusTopOfPlayer, FIELD_LINE_WIDTHS);
							}
							else
							{
								drawPost(canvas, paint, 15, -endOfRouteYards, PIXELS_PER_YARD, playerX, yardsPlusTopOfPlayer, FIELD_LINE_WIDTHS);
							}
						}
					}
					break;
				default:
					break;
				}
				break;
			case RUNNING_BACK:
				break;
			case FULLBACK:
				break;
			case TIGHT_END:
				break;
			case CENTER:
				break;
			case GUARD:
				break;
			case TACKLE:
				break;
			default:
				break;
			}
		}
		// orangish color
		paint.setColor(0xFFFF8000);
	}
	
	public static void drawPost(Canvas canvas, Paint paint, float degrees, 
			float height, float arrowLength, float xTranslate, float yTranslate, float FIELD_LINE_WIDTHS)
	{
		canvas.save();
			canvas.translate(xTranslate, yTranslate);
			canvas.rotate(degrees);
			drawUpArrow(canvas, paint, 0, height, arrowLength, FIELD_LINE_WIDTHS);
			drawStraightLine(canvas, paint, 0, 0, height);
		canvas.restore();
	}
	
	public static void drawStraightLine(Canvas canvas, Paint paint, float x, float y1, float y2)
	{
		canvas.drawLine(x, y1, x, y2, paint);
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
		
		// 375 = 635(bottom margin) - 260(20 yards * 13 pixels per yard)
		canvas.drawLine(LEFT_MARGIN, 375+(FIELD_LINE_WIDTHS/2)*DENSITY, RIGHT_MARGIN, 375+(FIELD_LINE_WIDTHS/2)*DENSITY, paint);
		
		// fill = fill enclosed shapes with the color, like a circle with the middle one color
		paint.setStyle(Paint.Style.FILL);
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
			field.getAllPlayers().get(playerIndex).setLocation(new Location(x, y));
		}
	}
	
	public static void actionUp(Field field, int playerIndex, float LEFT_MARGIN, float PLAYER_ICON_RADIUS, 
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
				EditorActivity.disableAll();
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
			}
		}
	}
	
	public static void actionDown(Field field, Field fieldForCreatePlayer, float TOUCH_SENSITIVITY, int x, int y, int playerIndex)
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
				field.addPlayerAndRoute(temp.getLocation(), temp.getPosition(), Route.NO_ROUTE); // add to field
				playerIndex = field.getAllPlayers().size()-1; // this player is the last 
				// player to be added to field
				EditorActivity.enableAll(playerIndex);
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
						EditorActivity.enableAll(playerIndex);
						hasBeenSet = true;
					}
				}
			}
			// if not selected, disable the route spinner and reset player index
			if (!hasBeenSet)
			{
				EditorActivity.disableAll();
			}
		}
	}
}
