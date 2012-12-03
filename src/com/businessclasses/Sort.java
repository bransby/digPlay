package com.businessclasses;

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

	public PlayAdapter sortPlaysByPlaybook(PlayAdapter adapter, String playbook) {
		return adapter;
		// TODO: implement this from playbook DB
		/*if(playbook.equals("ALL playbooks"))return adapter;
		for(int i = 0;i < adapter.getCount();i++){
			Field play = adapter.getItem(i);
			if(!playbook.equals(play.getPlaybookType())){
				adapter.remove(play);
			}
		}
		return adapter;*/
	}
}
