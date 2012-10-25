package com.example.digplay;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity implements OnClickListener {
	
	private Button  myButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        myButton = (Button) findViewById(R.id.button1);
        myButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

	public void onClick(View arg0) {
			// TODO Auto-generated method stub
		Intent intent = new Intent(arg0.getContext(),MainMenu.class);
		startActivity(intent);
	}
	//test
}
