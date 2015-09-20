package fragment;

import gson.StringPostRequest;
import gson.XinxiXiangQingActivity;

import java.util.ArrayList;
import java.util.List;

import utils.UrlUtils;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.example.school_tong.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

import db.dao.NewsDao;
import db.dao.UserDao;
import db.utils.ExecutorManager;
import entity.TodayStatus;
import entity.Users;
import activity.LoginActivity;
import activity.NewsActivity;
import adapter.School_Adapter;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.FlagToString;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import application.AppApplication;

public class SchoolNewsFragment extends BaseFragment implements
		OnRefreshListener2<ListView>, OnItemClickListener {
	private PullToRefreshListView mylist;
	private List<TodayStatus> mdata;
	private School_Adapter adapter;
	private RequestQueue requestQueue;
	private String typeId;
	private int pageNo = 1;
	private long startTime;
	private boolean isInit = false;// 当前fragment的控件是否初始化完成
	private ActionBar actionbar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.mdata = new ArrayList<TodayStatus>();
		requestQueue = AppApplication.getInstance().getRequestQueue();
		Bundle bundle = getArguments();
		typeId = bundle.getString("cId");
		actionbar = getActivity().getActionBar();
		actionbar.setDisplayShowHomeEnabled(false);
		actionbar.setDisplayShowTitleEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setTitle(bundle.getString("cTitle"));
		adapter = new School_Adapter(mdata, getActivity());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		getActivity().finish();
		return super.onOptionsItemSelected(item);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pulltorefreshlist, null);
		mylist = (PullToRefreshListView) view.findViewById(R.id.schoolnewslist);
		mylist.setAdapter(adapter);
		mylist.setOnItemClickListener(this);
		mylist.setOnRefreshListener(this);
		mylist.setMode(Mode.BOTH);
		startTime = System.currentTimeMillis();
		isInit = true;
		if (isVisible) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					initView();

				}
			}, 100);
		}
		return view;
	}

	// 异步更新数据的方法，把网络上加载的数据更新到本地中
	private void updateLocalData(final List<TodayStatus> item) {
		ExecutorManager.getInstance().execute(new Runnable() {
			public void run() {
				NewsDao newsDao = new NewsDao(getActivity()
						.getApplicationContext());
				newsDao.addNews(item);
			}
		});
	}

	public void initView() {
		mylist.setRefreshing();
		// 加载本地数据
		NewsDao newsDao = new NewsDao(getActivity().getApplicationContext());

		List<TodayStatus> list = newsDao.findNewsItem(typeId);
		if (list.size() > 0) {
			mdata.addAll(list);
			adapter.notifyDataSetChanged();
		}

		StringPostRequest postRequest = new StringPostRequest(UrlUtils.ROOT_URL
				+ "/NewsRequestServlet", new Listener<String>() {

			@Override
			public void onResponse(String arg0) {
				if (arg0 != null) {
					Gson gson = new Gson();
					// 接收gso数据
					List<TodayStatus> data = gson.fromJson(arg0,
							new TypeToken<ArrayList<TodayStatus>>() {
							}.getType());
					if (data != null && data.size() > 0) {
						mdata.clear();
						mdata.addAll(data);
						adapter.notifyDataSetChanged();

						// 将最新的数据跟新到数据库
						updateLocalData(data);
					}
					mylist.onRefreshComplete();
				}

			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// NewsDao newsDao = new
				// NewsDao(getActivity().getApplicationContext());
				// List<TodayStatus> list = newsDao.findNewsItem(typeId);
				// if (list.size()>0) {
				// mdata.addAll(list);
				// adapter.notifyDataSetChanged();
				// }

			}
		});
		// 传递的参数
		postRequest.putParams("dataType", "news");
		// postRequest.putParams("pageTag", typeId);
		postRequest.putParams("pageTag", typeId);
		postRequest.putParams("pageTagFlag", "0");
		postRequest.putParams("pageNum", "0");
		// 将请求加入到队列中
		// postRequest.setTag("post");
		requestQueue.add(postRequest);

	}

	public void initMoreView() {
		StringPostRequest postRequest = new StringPostRequest(UrlUtils.ROOT_URL
				+ "/NewsRequestServlet", new Listener<String>() {

			@Override
			public void onResponse(String arg0) {
				mylist.onRefreshComplete();
				if (arg0 != null) {
					Gson gson = new Gson();
					List<TodayStatus> data = gson.fromJson(arg0,
							new TypeToken<ArrayList<TodayStatus>>() {
							}.getType());
					if (data != null) {
						mdata.addAll(data);
						adapter.notifyDataSetChanged();
					}
				}

			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
			}
		});
		postRequest.putParams("dataType", "news");
		postRequest.putParams("pageTag", typeId);
		postRequest.putParams("pageTagFlag", "0");
		postRequest.putParams("pageNum", "0");
		requestQueue.add(postRequest);

	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// pageNo=1;
		// initView();

	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// initMoreView();
		// pageNo++;

	}

	@Override
	public void lazyLoadData() {
		long endTime = System.currentTimeMillis();
		// TODO Auto-generated method stub
		if (isVisible && isInit) {
			if ((mdata.size() > 0 && (endTime - startTime) > 1000 * 60)
					|| mdata.size() <= 0) {
				initView();
				startTime = System.currentTimeMillis();
			}
			// 加载数据
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		TodayStatus todayStat = (TodayStatus) adapter.getItem(position - 1);
		Intent intent = new Intent(getActivity(), XinxiXiangQingActivity.class);
		intent.putExtra("xinxiNews", todayStat);
		// adapter.notifyDataSetChanged();
		startActivity(intent);

	}
}
