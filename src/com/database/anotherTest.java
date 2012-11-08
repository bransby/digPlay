package com.database;

import java.io.File;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.businessclasses.Field;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.AndroidSupport;
import com.db4o.config.EmbeddedConfiguration;

public final class anotherTest extends Application {
	private static ObjectContainer playsDB;
	private static ObjectContainer gamePlanDB;

	private static anotherTest instance;

	private anotherTest(){
		
		PackageManager m = getPackageManager();
		String s = getPackageName();
		try {
		    PackageInfo p = m.getPackageInfo(s, 0);
		    s = p.applicationInfo.dataDir;
		} catch (NameNotFoundException e) {
		    Log.w("yourtag", "Error Package name not found ", e);
		}
		
		final EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.common().add(new AndroidSupport());
		//instance.getFilesDir().getPath()
		new File(s, "PlaysDB.db4o").delete();
		
		Log.d("db",	"" + s);
		//getFilesDir().getPath();
		//String appPath = "/data/data/PlaysDB/database";

		playsDB = Db4oEmbedded.openFile(config, s + "PlaysDB.db4o");
		//File test = new File(appPath + "/PlaysDB.db4o");
		//if(test != null){
		//	Log.d("db", "file was created");
		//	Log.d("db", "path >> " + test.getAbsolutePath());
			//test.delete();
		//}

		//playsDB = Db4oEmbedded.openFile(config, appPath + "/PlaysDB.db4o");
	}
	
	public static synchronized anotherTest getInstance(){
		if(instance == null){
			instance = new anotherTest();
		}
		return instance;
	}
	
	public void storePlay(Field field){
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
}
