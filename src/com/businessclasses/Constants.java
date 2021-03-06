package com.businessclasses;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.example.digplay.R;

public class Constants {
	public static ArrayList<String> getPlayTypes(){
		ArrayList<String> playTypes = new ArrayList<String>();
		playTypes.add("ALL PLAYS");
		playTypes.add("RUN");
		playTypes.add("PASS");
		return playTypes;
	}
	public static ArrayList<String> getGamePlans(){
		ArrayList<String> gameplans = new ArrayList<String>();
		gameplans.add("Play Book");
		gameplans.add("9/7 Marin Catholic");
		gameplans.add("9/14 Drake");
		gameplans.add("9/21 El Molino");
		gameplans.add("9/28 San Marin");
		gameplans.add("10/5 San Rafael");
		gameplans.add("10/12 Terra Linda");
		gameplans.add("10/19 Novoto");
		gameplans.add("10/26 De La Salle");
		gameplans.add("11/3 Tamalpais");
		return gameplans;
	}
	
	public static ArrayList<String>getFormations(){
		ArrayList<String> formations = new ArrayList<String>();
		formations.add("I");
		formations.add("Power I");
		formations.add("Ace");
		formations.add("4 Wide");
		formations.add("5 Wide");
		formations.add("Wishbone");
		formations.add("Strong I");
		formations.add("Weak I");
		formations.add("Wing T");
		formations.add("Pro");
		return formations;
	}
	
	public static int getBackground(){
		return R.drawable.brown_backgroud;
	}
	public static Field dummyField(){
		Field f = new Field();
		Location loc = new Location(500,600);
		Location loc2 = new Location(300, 500);
		Player p1 = new Player(loc,Position.FB);
		Player p2 = new Player(loc2,Position.QB);
		f.addPlayer(loc, Position.G);
		f.addPlayer(loc2, Position.RB);
		return f;
	}
}
