package com.businessclasses;

import java.util.ArrayList;
import com.database.DigPlayDB;
import android.content.ContextWrapper;


public class Sort {
	public PlayAdapter sortPlaysByRunPass(PlayAdapter adapter,String playType){
		if(playType.equals("ALL PLAYS"))return adapter;
		int totalCount = adapter.getCount();
		for(int i = 0; i < totalCount; i++){
			Field play = adapter.getItem(i);
			if(!playType.equalsIgnoreCase(play.getPlayType())){
				adapter.remove(play);
				i--; totalCount--;
			}
		}
		return adapter;
	}

	public PlayAdapter sortPlaysByPlaybook(PlayAdapter adapter, String playbook, ArrayList<String> listOfPlaysInGameplan) {
		if (playbook.equals("All Gameplans"))return adapter;
		int totalCount = adapter.getCount();
		for(int i = 0; i < totalCount; i++)
		{
			Field play = adapter.getItem(i);
			if(!listOfPlaysInGameplan.contains(play.getPlayName().toString())){
				adapter.remove(play);
				i--; totalCount--;
			}
		}
		return adapter;
	}
}
