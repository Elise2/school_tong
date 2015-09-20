package gson;

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

import db.dao.NewsDao;
import db.dao.SubjectDao;
import db.utils.ExecutorManager;
import entity.TodayStatus;
import entity.ZhuanTi;
import adapter.Zt_NeiRong_Adapter;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import application.AppApplication;

//专题中的具体内容
public class Zt_Content_Activity extends Activity implements
		OnItemClickListener {
	private ListView mylist;
	private Zt_NeiRong_Adapter mDapter;
	private List<TodayStatus> mdata;
	private RequestQueue requestQueue;
	private ZhuanTi ztnews;
	private ActionBar actionbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhuanticontent);
		actionbar = getActionBar();
		actionbar.setDisplayShowHomeEnabled(false);
		actionbar.setDisplayShowTitleEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setTitle("专题");
		mylist = (ListView) findViewById(R.id.zt_mylist);
		mylist.setOnItemClickListener(this);
		initData();
		mDapter = new Zt_NeiRong_Adapter(mdata, this);
		mylist.setAdapter(mDapter);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		finish();
		return super.onOptionsItemSelected(item);
	}

	public void initData() {
		this.mdata = new ArrayList<TodayStatus>();
		ztnews = (ZhuanTi) getIntent().getSerializableExtra("ztContent");
		requestQueue = AppApplication.getInstance().getRequestQueue();
		// 数据的加载
		getRequest();
		View headerView = LayoutInflater.from(this).inflate(
				R.layout.zt_content_header, null);
		TextView ztTitle = (TextView) headerView.findViewById(R.id.newsTitle);
		ztTitle.setText(ztnews.getSubject_title());
		TextView ztDetail = (TextView) headerView
				.findViewById(R.id.newsSummary);
		ztDetail.setText(ztnews.getSubject_detail());
		ImageView ztImage = (ImageView) headerView
				.findViewById(R.id.zhuanti_img);
		// ztImage.setImageResource(ztnews.getSubject_url());
		mylist.addHeaderView(headerView);

	}

	private void updateLocalData(final List<TodayStatus> item) {
		ExecutorManager.getInstance().execute(new Runnable() {
			public void run() {
				NewsDao newsDao = new NewsDao(getApplicationContext());
				newsDao.addNews(item);
			}
		});
	}

	private void getRequest() {
		NewsDao newsDao = new NewsDao(getApplicationContext());
		List<TodayStatus> list = newsDao.findNewsItem();
		if (list.size() > 0) {
			mdata.addAll(list);
			mDapter.notifyDataSetChanged();
		}
		// 数据加载
		StringPostRequest postRequest = new StringPostRequest(UrlUtils.ROOT_URL
				+ "/NewsRequestServlet", new Listener<String>() {

			@Override
			public void onResponse(String arg0) {
				if (arg0 != null) {
					Gson gson = new Gson();
					List<TodayStatus> data = gson.fromJson(arg0,
							new TypeToken<ArrayList<TodayStatus>>() {
							}.getType());
					if (data != null) {
						mdata.clear();
						mdata.addAll(data);
						mDapter.notifyDataSetChanged();
						updateLocalData(data);
					}
				}

			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				Toast.makeText(Zt_Content_Activity.this, "连接不成功！",
						Toast.LENGTH_LONG).show();

			}
		});
		postRequest.putParams("dataType", "news");
		postRequest.putParams("pageTag", ztnews.getSubject_id() + "");
		postRequest.putParams("pageTagFlag", "1");
		requestQueue.add(postRequest);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		TodayStatus todayNews = (TodayStatus) mDapter.getItem(position - 1);
		Intent intent = new Intent(getApplicationContext(),
				XinxiXiangQingActivity.class);
		intent.putExtra("xinxiNews", todayNews);
		startActivity(intent);

	}
}
