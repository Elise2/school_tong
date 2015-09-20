package activity;

import java.util.List;

import com.example.school_tong.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;

import db.dao.StorageDao;
import entity.TodayStatus;
import entity.Users;
import gson.XinxiXiangQingActivity;
import adapter.School_Adapter;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import application.AppApplication;

public class StoreInfomation extends Activity implements OnItemClickListener {
	private PullToRefreshListView myList;
	private List<TodayStatus> mdata;
	private Users user;
	private School_Adapter adpter;
	private ActionBar actionbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		initActionBar();
		myList = (PullToRefreshListView) findViewById(R.id.myList);
		myList.setOnItemClickListener(this);
		user = AppApplication.getInstance().getUser();
		if (user!=null) {
			StorageDao storageDao = new StorageDao(getApplicationContext());
			mdata = storageDao.allNews();
			adpter = new School_Adapter(mdata, getApplicationContext());
			myList.setAdapter(adpter);
		}
	}

	private void initActionBar() {
		actionbar = getActionBar();
		actionbar.setDisplayShowHomeEnabled(false);
		actionbar.setDisplayShowTitleEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setTitle("个人收藏");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		finish();
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		TodayStatus todayStat = (TodayStatus) adpter.getItem(position - 1);
		Intent intent = new Intent(this, XinxiXiangQingActivity.class);
		intent.putExtra("xinxiNews", todayStat);
		// adapter.notifyDataSetChanged();
		startActivity(intent);

	}

}
