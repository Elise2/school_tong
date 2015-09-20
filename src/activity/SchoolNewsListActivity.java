package activity;

import java.util.ArrayList;
import java.util.List;

import com.astuetz.PagerSlidingTabStrip;
import com.example.school_tong.R;

import entity.Channel;
import fragment.SchoolNewsFragment;
import adapter.HeaderRadiosAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

public class SchoolNewsListActivity extends FragmentActivity {
	private PagerSlidingTabStrip tabs;
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ccnewslist);
		initView();
		initData();
	}

	private void initView() {
		tabs = (PagerSlidingTabStrip) findViewById(R.id.newsTabs);
		viewPager = (ViewPager) findViewById(R.id.myPager);
	}

	private void initData() {
		// 校园活动大类
		Channel channel = (Channel) getIntent().getSerializableExtra("channel");
		getActionBar().setTitle("title");
		// 大类下面的子类
		List<Channel> children = channel.getChildren();

		// 为ViewPager构造数据
		List<Fragment> fragments = new ArrayList<Fragment>();
		for (int i = 0; i < children.size(); i++) {
			Fragment f = new SchoolNewsFragment();
			// view.setText(children.get(i).getcTitle());
			Bundle bundle = new Bundle();
			bundle.putString("cId", children.get(i).getcId());
			bundle.putString("cTitle", channel.getcTitle());
			f.setArguments(bundle);
			fragments.add(f);
		}

		HeaderRadiosAdapter adapter = new HeaderRadiosAdapter(
				getSupportFragmentManager(), fragments, children);
		viewPager.setAdapter(adapter);
		tabs.setViewPager(viewPager);
	}

}
