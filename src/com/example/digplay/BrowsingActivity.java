package com.example.digplay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ViewSwitcher.ViewFactory;

public class BrowsingActivity extends Activity implements OnClickListener, ViewFactory {
	private TextView playName;
	private Button editPlay;
	private Integer[] pics = {R.drawable.seq1, R.drawable.seq2, R.drawable.seq3};
	ImageSwitcher iSwitcher;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.browsing);
	    setTextView();
	    setButtons();
	    
	    iSwitcher = (ImageSwitcher) findViewById(R.id.ImageSwitcher01);
	    iSwitcher.setFactory(this);
		iSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_in));
		iSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_out));

		Gallery gallery = (Gallery) findViewById(R.id.Gallery01);
		gallery.setAdapter(new ImageAdapter(this));
		gallery.setOnItemClickListener(new OnItemClickListener() {


			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				iSwitcher.setImageResource(pics[arg2]);
			}
		});
	}

	private void setButtons() {
		editPlay = (Button)findViewById(R.id.browsing_edit_play);
		editPlay.setOnClickListener(this);
	}

	private void setTextView() {
		playName = (TextView)findViewById(R.id.browsing_play_name);
		String thePlayer = getIntent().getExtras().getString("playName");
		playName.setText(thePlayer);
	}

	public void onClick(View v) {
		Intent intent = new Intent(v.getContext(),EditorActivity.class);
		startActivity(intent);
	}

	public class ImageAdapter extends BaseAdapter {

		private Context ctx;

		public ImageAdapter(Context c) {
			ctx = c; 
		}

		public int getCount() {

			return pics.length;
		}


		public Object getItem(int arg0) {

			return arg0;
		}


		public long getItemId(int arg0) {

			return arg0;
		}


		public View getView(int arg0, View arg1, ViewGroup arg2) {

			ImageView iView = new ImageView(ctx);
			iView.setImageResource(pics[arg0]);
			iView.setScaleType(ImageView.ScaleType.FIT_XY);
			iView.setLayoutParams(new Gallery.LayoutParams(150, 150));
			return iView;
		}

	}


	public View makeView() {
		ImageView iView = new ImageView(this);
		iView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		iView.setLayoutParams(new 
				ImageSwitcher.LayoutParams(
						LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		iView.setBackgroundColor(0xFF000000);
		return iView;
	}
}

