package com.example.digplay;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class EmailPlaybook {
	public static void EmailAttachment(Context context, String emailTo, String subject, String emailText, List<String> filePaths)
	{
	    final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND_MULTIPLE);
	    emailIntent.setType("text/xml");
	    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, emailTo);
	    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
	    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, emailText);
	    //create ArrayList from List for attachments
	    ArrayList<Uri> uris = parseFilePaths(filePaths);
	    emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
	    context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
	}
		
	private static ArrayList<Uri> parseFilePaths(List<String> filePaths) 
	{
		ArrayList<Uri> uris = new ArrayList<Uri>();
	    //convert from paths to Android friendly Parcelable Uri's
	    for (String file : filePaths)
	    {
	        File fileIn = new File(file);
	        Uri u = Uri.fromFile(fileIn);
	        uris.add(u);
	    }
		return uris;
	}
	
}

