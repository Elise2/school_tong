package gson;

import db.dao.SubjectDao;
import db.utils.ExecutorManager;
import entity.ZhuanTi;

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
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import adapter.ZhuantiAdapter;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import application.AppApplication;

//更多专题
public class MoreZhuanTiActivity extends Activity implements
		OnRefreshListener2<ListView>, OnItemClickListener {
	private PullToRefreshListView mylist;
	private ZhuantiAdapter mAdapter;
	private List<ZhuanTi> mData;
	private RequestQueue requestQueue;
	private ActionBar actionbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.morezhuanti);
		actionbar = getActionBar();
		actionbar.setDisplayShowHomeEnabled(false);
		actionbar.setDisplayShowTitleEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setTitle("专题");
		mylist = (PullToRefreshListView) findViewById(R.id.mylist);
		mylist.setOnItemClickListener(this);
		mylist.setMode(Mode.BOTH);
		this.mData = new ArrayList<ZhuanTi>();
		requestQueue = AppApplication.getInstance().getRequestQueue();
		mAdapter = new ZhuantiAdapter(mData, this);
		getRequest();
		mylist.setAdapter(mAdapter);
		mylist.setOnRefreshListener(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		finish();
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	private void upLocalData(final List<ZhuanTi> item) {
		ExecutorManager.getInstance().execute(new Runnable() {
			public void run() {
				SubjectDao subjectDao = new SubjectDao(getApplicationContext());
				subjectDao.addMoreSubject(item);
			}
		});
	}

	private void getRequest() {
		SubjectDao subjectDao = new SubjectDao(getApplicationContext());
		List<ZhuanTi> list = subjectDao.findSubject();
		if (list.size() > 0) {
			mData.addAll(list);
			mAdapter.notifyDataSetChanged();
		}
		StringPostRequest postRequest = new StringPostRequest(UrlUtils.ROOT_URL
				+ "/NewsRequestServlet", new Listener<String>() {
			@Override
			public void onResponse(String arg0) {
				mylist.onRefreshComplete();
				if (arg0 != null) {
					Gson gson = new Gson();
					List<ZhuanTi> data = gson.fromJson(arg0,
							new TypeToken<ArrayList<ZhuanTi>>() {
							}.getType());
					if (data != null) {
						mData.clear();
						mData.addAll(data);
						mAdapter.notifyDataSetChanged();
						upLocalData(data);
					}
				}

			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				Toast.makeText(MoreZhuanTiActivity.this, "连接不成功！",
						Toast.LENGTH_LONG).show();

			}
		});
		postRequest.putParams("dataType", "subject");
		postRequest.putParams("pageCount", "50");
		requestQueue.add(postRequest);
	}

	private void loadRefresh() {
		StringPostRequest postRequest = new StringPostRequest(UrlUtils.ROOT_URL
				+ "/NewsRequestServlet", new Listener<String>() {

			@Override
			public void onResponse(String arg0) {
				mylist.onRefreshComplete();
				if (arg0 != null) {
					Gson gson = new Gson();
					List<ZhuanTi> data = gson.fromJson(arg0,
							new TypeToken<ArrayList<ZhuanTi>>() {
							}.getType());
					if (data != null) {
						mData.addAll(data);
						mAdapter.notifyDataSetChanged();
					}
				}

			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				Toast.makeText(MoreZhuanTiActivity.this, "连接不成功！",
						Toast.LENGTH_LONG).show();

			}
		});
		postRequest.putParams("dataType", "subject");
		postRequest.putParams("pageCount", "50");
		requestQueue.add(postRequest);
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		PageCount = 1;
		getRequest();

	}

	private int PageCount = 1;

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		loadRefresh();
		PageCount++;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ZhuanTi subject = (ZhuanTi) mAdapter.getItem(position - 1);
		Intent intent = new Intent(getApplicationContext(),
				Zt_Content_Activity.class);
		intent.putExtra("ztContent", subject);
		startActivity(intent);

	}
}
