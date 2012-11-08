package com.database;

import java.io.File;
import java.io.IOException;

import android.app.Application;
import android.content.ContextWrapper;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.businessclasses.Field;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.AndroidSupport;
import com.db4o.config.EmbeddedConfiguration;
import com.example.digplay.MainMenuActivity;

public final class testingClass extends Application {
	
	private static ObjectContainer playsDB;
	private static ObjectContainer gamePlanDB;

	public testingClass(){}

	static{
		
		final EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.common().add(new AndroidSupport());
		
		/*
		testingClass test = new testingClass();
		testingClass testingC = (testingClass) test.getInstance().getApplicationContext();
		String appPath = testingC.getApplicationContext().getFilesDir().getAbsolutePath();
		
		new File(appPath + "/PlaysDB.db4o").delete();
		//new File("/data/data/PlaysDB/database/PlaysDB.db4o").delete();
		//new File("/data/data/PlaysDB/database/GamePlanDB.db4o").delete();

		 */
		String appPath = "/sdcard/android/data/digPlay/";

		
		File test = new File(appPath, "PlaysDB.db4o");
		//test.mkdirs();

		Log.d("db", "path >> " + test.getAbsolutePath());
		//test.delete();
		//getFilesDir();

		playsDB = Db4oEmbedded.openFile(config, appPath + "/PlaysDB.db4o");

		//new File(this.getFilesDir().getPath() + "GamePlanDB.db4o").delete();

		//gamePlanDB = Db4oEmbedded.openFile(config, "/data/data/PlaysDB/database/GamePlanDB.db4o");
	}

	public static void storePlay(Field field){
		if(field != null){
			if(playsDB == null){
				Log.d("db", "database is null");
			}
			playsDB.store(field);
			playsDB.commit();
			Log.d("added play", "play added to db");	
		}
		else{
			Log.e("field input null", "play not added to db");
		}
	}

	private ContextWrapper getInstance() {
		return this;
	}
}
