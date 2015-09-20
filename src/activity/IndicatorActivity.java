package activity;

import java.util.ArrayList;
import java.util.List;

import com.example.school_tong.R;

import adapter.PicPagerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class IndicatorActivity extends Activity {
	private ViewPager viewPager;
	private List<ImageView> imgs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splite_viewpager);
		viewPager = (ViewPager) findViewById(R.id.myPage);
		PicPagerAdapter adapter = new PicPagerAdapter(initPager());
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if (arg0 == imgs.size() - 1) {
					imgs.get(arg0).setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							SharedPreferences sp = getSharedPreferences("name",
									MODE_PRIVATE);
							SharedPreferences.Editor editor = sp.edit();
							editor.putBoolean("isFirst", false);
							editor.commit();
							startActivity(new Intent(IndicatorActivity.this,
									NewsActivity.class));
							finish();
						}
					});
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	public List<ImageView> initPager() {
		imgs = new ArrayList<ImageView>();
		ImageView view = new ImageView(this);
		view.setImageResource(R.drawable.pic2);
		imgs.add(view);
		view = new ImageView(this);
		view.setImageResource(R.drawable.pic3);
		imgs.add(view);
		view = new ImageView(this);
		view.setImageResource(R.drawable.pic5);
		imgs.add(view);
		view = new ImageView(this);
		view.setImageResource(R.drawable.pic2);
		imgs.add(view);
		return imgs;
	}

}
