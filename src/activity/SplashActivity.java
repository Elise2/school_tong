package activity;

import com.example.school_tong.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends Activity {
	private ImageView img;
	private Handler mhandle = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashview_layout);
		img = (ImageView) findViewById(R.id.firstPic);
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
		img.startAnimation(animation);
		mhandle.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				SharedPreferences sp = getSharedPreferences("name",
						MODE_PRIVATE);
				boolean flag = sp.getBoolean("isFirst", true);
				Intent intent = null;
				if (flag) {
					intent = new Intent(SplashActivity.this,
							IndicatorActivity.class);
				} else {
					intent = new Intent(SplashActivity.this, NewsActivity.class);
				}
				startActivity(intent);
			}
		}, 1000);
	}

}
