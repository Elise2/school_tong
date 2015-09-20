package activity;

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

import entity.Contact;
import entity.Contant;
import gson.StringPostRequest;
import adapter.ContactSelectedAdapter;
import adapter.ContactSelectedAdapter.onContactSelectedListener;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObservable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewDebug.FlagToString;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import application.AppApplication;

public class New_InforActivity extends Activity implements OnClickListener,
		onContactSelectedListener {
	private ActionBar actionbar;
	private TextView information;
	private ImageView search;
	private String editName;
	private ListView listview;
	private List<Contact> mdata;
	private ContactSelectedAdapter myAdapter;
	private ContactSelectedAdapter mdapter;
	private RequestQueue requestQueue;
	private ListView editList;
	private List<Contact> selectData = new ArrayList<Contact>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_info);
		information = (TextView) findViewById(R.id.editName);
		search = (ImageView) findViewById(R.id.search);
		search.setOnClickListener(this);
		actionbar = getActionBar();
		actionbar.setDisplayShowHomeEnabled(false);
		actionbar.setDisplayShowTitleEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setTitle("新建信息");
		editList = (ListView) findViewById(R.id.editLst);
		mdapter = new ContactSelectedAdapter(selectData, this);
		editList.setAdapter(mdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.new_infomain, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		startActivityForResult((new Intent(getApplicationContext(),
				OtherNewsInfo.class)), Contant.NEW_INFO_REQUEST);
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search:
			View contentView = LayoutInflater.from(this).inflate(
					R.layout.list_selected_layout, null);
			listview = (ListView) contentView.findViewById(R.id.MyList);
			initData();
			// 定义popupWindow对象
			final PopupWindow popupWindow = new PopupWindow(contentView,
					ViewGroup.LayoutParams.MATCH_PARENT, 1550);
			// 设置popupWindow的动画效果
			// popupWindow.setAnimationStyle(animationStyle);

			// 点击PopupWindow外部，popupWindow消失
			popupWindow.setFocusable(true);
			popupWindow.setOutsideTouchable(true);
			popupWindow.setBackgroundDrawable(new ColorDrawable());
			popupWindow.setAnimationStyle(R.style.animpopwindow);
			// 所呈现的布局出现在所按下的按钮的下面
			popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
			break;

		default:
			break;
		}

	}

	public void initData() {
		mdata = new ArrayList<Contact>();
		requestQueue = AppApplication.getInstance().getRequestQueue();
		myAdapter = new ContactSelectedAdapter(mdata, this);
		requestData();
		listview.setAdapter(myAdapter);
		myAdapter.setListener(this);
	}

	public void requestData() {
		editName = information.getText().toString();
		StringPostRequest postRequest = new StringPostRequest(UrlUtils.ROOT_URL
				+ "/NewsPushServlet", new Listener<String>() {
			@Override
			public void onResponse(String arg0) {
				if (arg0 != null) {
					Gson gson = new Gson();
					List<Contact> data = gson.fromJson(arg0,
							new TypeToken<ArrayList<Contact>>() {
							}.getType());
					if (data != null) {
						mdata.clear();
						mdata.addAll(data);
						myAdapter.notifyDataSetChanged();
					}
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG)
						.show();
			}
		});
		postRequest.putParams("dataType", "student");
		postRequest.putParams("name", editName);
		requestQueue.add(postRequest);
	}

	boolean flag = true;

	@Override
	public void onContactSelectChanged(Contact contact) {
		// TODO Auto-generated method stub
		if (contact.isSelected()) {
			for (Contact c : selectData) {
				if (c.getStuName().equals(contact.getStuName())) {
					flag = false;
					break;
				} else {
					flag = true;
				}
			}
			if (flag) {
				selectData.add(contact);
			}
		} else {
			selectData.remove(contact);

		}
		mdapter.notifyDataSetChanged();

		mdapter.setListener(new onContactSelectedListener() {

			@Override
			public void onContactSelectChanged(Contact contact) {
				// TODO Auto-generated method stub
				if (contact != null && contact.isSelected() == false) {
					selectData.remove(contact);
					mdapter.notifyDataSetChanged();
				}
			}
		});

	}

	private List<Contact> notisfyDatas = new ArrayList<Contact>();

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Contant.NEW_INFO_REQUEST
				&& resultCode == Contant.NEW_INFO_RESULT) {
			notisfyDatas = data.getParcelableArrayListExtra("message");
			if (notisfyDatas != null) {
				selectData.addAll(notisfyDatas);
			}
		}
		mdapter.notifyDataSetChanged();

	}

}
