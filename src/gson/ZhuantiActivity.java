package gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utils.HttpUtils;
import utils.UrlUtils;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.school_tong.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import db.dao.SubjectDao;
import db.utils.ExecutorManager;
import entity.ZhuanTi;
import adapter.ZhuantiAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import application.AppApplication;

public class ZhuantiActivity extends Activity implements OnItemClickListener {
	private ListView mylist;
	private ZhuantiAdapter mAdapter;
	private List<ZhuanTi> mData;
	private RequestQueue requestQueue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhuanti);
		mylist = (ListView) findViewById(R.id.mylist);
		mylist.setOnItemClickListener(this);
		this.mData = new ArrayList<ZhuanTi>();
		requestQueue = AppApplication.getInstance().getRequestQueue();
		getRequest();
		mAdapter = new ZhuantiAdapter(mData, this);
		mylist.setAdapter(mAdapter);

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
				Toast.makeText(ZhuantiActivity.this, "连接不成功！",
						Toast.LENGTH_LONG).show();

			}
		});
		postRequest.putParams("dataType", "subject");
		postRequest.putParams("dataType", "news");
		requestQueue.add(postRequest);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		ZhuanTi subject = (ZhuanTi) mAdapter.getItem(position - 1);
		Intent intent = new Intent(getApplicationContext(),
				Zt_Content_Activity.class);
		intent.putExtra("ztContent", subject);
		startActivity(intent);

	}
}
