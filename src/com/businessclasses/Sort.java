package com.businessclasses;

import java.util.ArrayList;
import com.database.DigPlayDB;
import android.content.ContextWrapper;


public class Sort {
	public PlayAdapter sortPlaysByRunPass(PlayAdapter adapter,String playType){
		if(playType.equals("ALL PLAYS"))return adapter;
		for(int i = 0;i < adapter.getCount();i++){
			Field play = adapter.getItem(i);
			if(!playType.equals(play.getPlayType())){
				adapter.remove(play);
			}
		}
		return adapter;
	}

	public PlayAdapter sortPlaysByPlaybook(PlayAdapter adapter, String playbook, ArrayList<String> listOfGamePlans) {
		if (playbook.equals("All Gameplans"))return adapter;
		for(int i = 0; i < adapter.getCount(); i++)
		{
			Field play = adapter.getItem(i);
			if(!listOfGamePlans.contains(play));
				adapter.remove(play);
		}
		return adapter;
	}
}
