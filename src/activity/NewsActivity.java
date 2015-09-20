package activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import utils.ImageLoaderUitil;
import utils.UrlUtils;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.example.school_tong.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import db.dao.NewsDao;
import db.dao.SubjectDao;
import db.dao.UserDao;
import db.utils.ExecutorManager;
import entity.Channel;
import entity.DataManager;
import entity.TodayStatus;
import entity.Users;
import entity.ZhuanTi;
import gson.MoreZhuanTiActivity;
import gson.StringPostRequest;
import gson.XinxiXiangQingActivity;
import gson.Zt_Content_Activity;
import adapter.HeadViewPagerAdapter;
import adapter.TodayStatusAdapter;
import adapter.ZhuantiAdapter;
import android.R.string;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import application.AppApplication;

public class NewsActivity extends Activity implements
		OnRefreshListener2<ListView>, OnScrollListener, OnClickListener,
		OnItemClickListener {
	private PullToRefreshListView myList;
	private List<TodayStatus> mData;
	private ViewPager myPager;
	private LinearLayout indecatorContainer;
	private ActionBarDrawerToggle mActionBarDrawerToggle;
	private ActionBar actionbar;
	private RequestQueue requestQueue;
	private TodayStatusAdapter adapter;
	private ZhuantiAdapter mAdapter;
	private DrawerLayout drawerLayout;
	private DataManager datamanager;
	private TextView login;
	private Users user;
	private String username;
	private ImageView denglu;
	private ImageView subImg;
	private TextView subTitle;
	private TextView subSummary;
	private TextView subTime;

	public NewsActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_layout);
		datamanager = new DataManager();
		login = (TextView) findViewById(R.id.loginheh);
		denglu = (ImageView) findViewById(R.id.denglu);
		login.setText("请登录");
		findViewById(R.id.schoolActivity).setOnClickListener(this);
		findViewById(R.id.mindLeader).setOnClickListener(this);
		findViewById(R.id.employee).setOnClickListener(this);
		findViewById(R.id.Studyout).setOnClickListener(this);
		findViewById(R.id.denglu).setOnClickListener(this);
		findViewById(R.id.tuichu).setOnClickListener(this);
		findViewById(R.id.loginheh).setOnClickListener(this);
		findViewById(R.id.infoFlat).setOnClickListener(this);
		user = AppApplication.getInstance().getUser();
		if (user != null) {
			login.setText(user.getUno());
			String urlStr = user.getImg();
			ImageLoaderUitil.display(urlStr, denglu);
		}
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
		mActionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.string.open, R.string.close);
		drawerLayout.setDrawerListener(new DrawerListener() {
			@Override
			public void onDrawerStateChanged(int arg0) {
				mActionBarDrawerToggle.onDrawerStateChanged(arg0);

			}

			@Override
			public void onDrawerSlide(View arg0, float arg1) {
				mActionBarDrawerToggle.onDrawerSlide(arg0, arg1);

			}

			@Override
			public void onDrawerOpened(View arg0) {
				mActionBarDrawerToggle.onDrawerOpened(arg0);

			}

			@Override
			public void onDrawerClosed(View arg0) {
				mActionBarDrawerToggle.onDrawerClosed(arg0);

			}
		});

		actionbar = getActionBar();
		actionbar.setDisplayShowHomeEnabled(false);
		actionbar.setDisplayShowTitleEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setTitle("校园通");

		initview();
		// slideMenu();
		initViewPager();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			// this.finish();//返回按钮
			break;
		}
		return mActionBarDrawerToggle.onOptionsItemSelected(item)
				|| super.onOptionsItemSelected(item);
	}

	private void initview() {
		myList = (PullToRefreshListView) findViewById(R.id.myList);
		myList.setMode(Mode.BOTH);
		myList.setOnItemClickListener(this);
		myList.setOnRefreshListener(this);
		mData = new ArrayList<TodayStatus>();
		initItemNews();
		requestQueue = AppApplication.getInstance().getRequestQueue();
		adapter = new TodayStatusAdapter(mData, this);
		initData();
		myList.setAdapter(adapter);
		myList.setOnScrollListener(this);
	}

	// private void upLocalData(final List<ZhuanTi> item) {
	// ExecutorManager.getInstance().execute(new Runnable() {
	// public void run() {
	// SubjectDao subjectDao = new SubjectDao(getApplicationContext());
	// subjectDao.addMoreSubject(item);
	// }
	// });
	// }

	private void getRequest() {
		StringPostRequest postRequest = new StringPostRequest(UrlUtils.ROOT_URL
				+ "/NewsRequestServlet", new Listener<String>() {

			@Override
			public void onResponse(String arg0) {
				if (arg0 != null) {
					try {
						JSONObject jsonObject = new JSONObject(arg0);
						// subImg.setImageResource(jsonObject.getString("subject_url"));
						subTitle.setText(jsonObject.getString("subject_title"));
						subSummary.setText(jsonObject
								.getString("subject_detail"));
						subTime.setText(jsonObject.getString("subject_date"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				Toast.makeText(NewsActivity.this, "连接不成功！", Toast.LENGTH_LONG)
						.show();

			}
		});
		postRequest.putParams("dataType", "subject");
		postRequest.putParams("pageCount", "1");
		AppApplication.getInstance().getRequestQueue().add(postRequest);
	}

	private void initItemNews() {
		View header = LayoutInflater.from(this).inflate(
				R.layout.home_item_zhuanti, null);
		myList.getRefreshableView().addHeaderView(header);
		subImg = (ImageView) header.findViewById(R.id.subImg);
		subTitle = (TextView) header.findViewById(R.id.subTitle);
		subSummary = (TextView) header.findViewById(R.id.subSum);
		subTime = (TextView) header.findViewById(R.id.subTime);
		getRequest();
		findViewById(R.id.subjectContainer).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

					}
				});
		myPager = (ViewPager) header.findViewById(R.id.myPager);
		// 设置更多主题的onclick事件
		header.findViewById(R.id.status1).setOnClickListener(this);
		indecatorContainer = (LinearLayout) header
				.findViewById(R.id.indicatorContainer);

	}

	//
	// // 异步更新数据的方法，把网络上加载的数据更新到本地中
	// private void updateLocalData(final List<TodayStatus> item) {
	// ExecutorManager.getInstance().execute(new Runnable() {
	// public void run() {
	// NewsDao newsDao = new NewsDao(getApplicationContext());
	// newsDao.addNews(item);
	// }
	// });
	// }

	public void initData() {
		// NewsDao newsDao = new NewsDao(getApplicationContext());
		// List<TodayStatus> list = newsDao.findNews();
		// if (list.size() > 0) {
		// mData.addAll(list);
		// adapter.notifyDataSetChanged();
		// }
		StringPostRequest postRequest = new StringPostRequest(UrlUtils.ROOT_URL
				+ "/NewsRequestServlet", new Listener<String>() {

			@Override
			public void onResponse(String arg0) {
				myList.onRefreshComplete();
				if (arg0 != null) {
					Gson gson = new Gson();
					List<TodayStatus> data = gson.fromJson(arg0,
							new TypeToken<ArrayList<TodayStatus>>() {
							}.getType());
					if (data != null) {
						mData.clear();
						mData.addAll(data);
						adapter.notifyDataSetChanged();
						// updateLocalData(data);

					}
				}

			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				Toast.makeText(getApplicationContext(), "连接不成功",
						Toast.LENGTH_LONG).show();
			}
		});
		postRequest.putParams("dataType", "news");
		requestQueue.add(postRequest);
	}

	private void initViewPager() {
		final List<ImageView> vData = new ArrayList<ImageView>();

		ImageView v = new ImageView(this);
		v.setImageResource(R.drawable.head1);
		v.setScaleType(ScaleType.FIT_XY);
		vData.add(v);

		v = new ImageView(this);
		v.setImageResource(R.drawable.head2);
		v.setScaleType(ScaleType.FIT_XY);
		vData.add(v);

		v = new ImageView(this);
		v.setImageResource(R.drawable.head4);
		v.setScaleType(ScaleType.FIT_XY);
		vData.add(v);

		HeadViewPagerAdapter adapter = new HeadViewPagerAdapter(vData);
		myPager.setAdapter(adapter);

		for (int i = 0; i < vData.size(); i++) {
			ImageView indicator = (ImageView) LayoutInflater.from(this)
					.inflate(R.layout.listview_layout_header_indicator, null);
			if (i == 0) {
				indicator.setImageResource(R.drawable.b);
			} else {
				indicator.setImageResource(R.drawable.a);
			}
			LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			lp.setMargins(50, 0, 0, 50);
			indecatorContainer.addView(indicator, lp);
		}

		myPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

				for (int i = 0; i < vData.size(); i++) {
					ImageView indicator = (ImageView) indecatorContainer
							.getChildAt(i);
					if (i == arg0) {
						indicator.setImageResource(R.drawable.b);
					} else {
						indicator.setImageResource(R.drawable.a);
					}
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private int scrollState = 0;

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		this.scrollState = scrollState;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (firstVisibleItem + visibleItemCount + 1 == totalItemCount) {
			if (this.scrollState == SCROLL_STATE_IDLE) {
				Toast.makeText(
						this,
						"firstVisibleItem:" + firstVisibleItem
								+ ",visibleItemCount:" + visibleItemCount
								+ ",totalItemCount:" + totalItemCount,
						Toast.LENGTH_SHORT).show();
			}

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.status1:
			Intent intent = new Intent();
			intent.setClass(NewsActivity.this, MoreZhuanTiActivity.class);
			startActivity(intent);
			break;
		case R.id.denglu:
			intent = new Intent();
			intent.setClass(NewsActivity.this, TuichuActivity.class);
			startActivity(intent);
			break;
		case R.id.schoolActivity:
			intent = new Intent(NewsActivity.this, SchoolNewsListActivity.class);
			Channel channel = datamanager.getSchoolChannel(0);
			intent.putExtra("channel", channel);
			intent.putExtra("title", channel.getcTitle());
			startActivity(intent);
			break;
		case R.id.mindLeader:
			intent = new Intent(NewsActivity.this, SchoolNewsListActivity.class);
			channel = datamanager.getSchoolChannel(1);
			intent.putExtra("channel", channel);
			intent.putExtra("title", channel.getcTitle());
			startActivity(intent);
			break;
		case R.id.employee:
			intent = new Intent(NewsActivity.this, SchoolNewsListActivity.class);
			channel = datamanager.getSchoolChannel(2);
			intent.putExtra("channel", channel);
			intent.putExtra("title", channel.getcTitle());
			startActivity(intent);
			break;
		case R.id.Studyout:
			intent = new Intent(NewsActivity.this, SchoolNewsListActivity.class);
			channel = datamanager.getSchoolChannel(3);
			intent.putExtra("channel", channel);
			intent.putExtra("title", channel.getcTitle());
			startActivity(intent);
			break;
		case R.id.tuichu:
			intent = new Intent(NewsActivity.this, BianLiGongJuActivity.class);
			startActivity(intent);

			break;
		case R.id.loginheh:
			if (user == null) {
				startActivityForResult(new Intent(NewsActivity.this,
						LoginActivity.class), 0);
			}
			break;
		case R.id.infoFlat:
			startActivity(new Intent(this, InformationActivity.class));
			break;
		default:
			break;
		}

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.right_in, R.anim.left_out);
	}

	@Override
	public void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		initData();

	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0 && resultCode == RESULT_OK) {
			Users user = AppApplication.getInstance().getUser();
			login.setText(user.getUno());
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		TodayStatus todayStat = (TodayStatus) adapter.getItem(position - 2);
		Intent intent = new Intent(this, XinxiXiangQingActivity.class);
		intent.putExtra("xinxiNews", todayStat);
		startActivity(intent);

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		user = AppApplication.getInstance().getUser();
	}

}
