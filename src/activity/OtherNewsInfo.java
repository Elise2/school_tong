package activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utils.UrlUtils;

import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.example.school_tong.R;
import com.google.gson.JsonArray;

import entity.Contact;
import entity.Contant;
import gson.StringPostRequest;
import adapter.ContactSelectedAdapter;
import adapter.ContactSelectedAdapter.onContactSelectedListener;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.FlagToString;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import application.AppApplication;

public class OtherNewsInfo extends Activity implements OnItemClickListener,
		onContactSelectedListener {
	private ListView listView;
	private ContactSelectedAdapter myAdapter;
	private ContactSelectedAdapter mdapter;
	private List<Contact> mData;
	private ActionBar actionbar;
	private LinearLayout container;
	private TextView contactText;
	private int count = 0;
	private TextView sureText;
	private ArrayList<Contact> selectData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contactlayout);
		listView = (ListView) findViewById(R.id.contactList);
		container = (LinearLayout) findViewById(R.id.liner);
		contactText = (TextView) findViewById(R.id.contactText);
		sureText = new TextView(this);
		actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setDisplayShowHomeEnabled(false);
		actionbar.setDisplayShowTitleEnabled(true);
		actionbar.setDisplayShowCustomEnabled(true);
		actionbar.setTitle("联系人");
		selectData = new ArrayList<Contact>();
		initData();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		finish();
		return super.onOptionsItemSelected(item);
	}

	public void initData() {
		Contact root = new Contact(null, "校园通", Contant.SCHOOL_TONG_TABS);// 定义了根节点
		addNav(root);
		List<Contact> rootChild = new ArrayList<Contact>();
		Contact majorAll = new Contact(null, "专业", Contant.ALL_MAJOR);
		Contact roleAll = new Contact(null, "职务", Contant.ALL_ROLE);

		rootChild.add(majorAll);
		rootChild.add(roleAll);
		root.setNodes(rootChild);// 将子节点与父节点关联起来

		mData = new ArrayList<Contact>();// listView中的集合
		mData.add(majorAll);
		mData.add(roleAll);

		myAdapter = new ContactSelectedAdapter(mData, this);
		listView.setAdapter(myAdapter);
		listView.setOnItemClickListener(this);
		myAdapter.setListener(this);
	}

	private void addNav(Contact root) {
		TextView navRoot = new TextView(this);// 根节点的显示框
		navRoot.setText(root.getStuName() + ">");
		navRoot.setGravity(Gravity.CENTER_VERTICAL);
		navRoot.setPadding(20, 0, 0, 0);
		navRoot.setTag(root);// 将数据保存起来
		navRoot.setTextSize(18);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		lp.gravity = Gravity.CENTER_VERTICAL;
		// 将TextView添加到tabs中
		container.addView(navRoot, lp);

		navRoot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 找到被点TextView在容器中的位置
				int index = container.indexOfChild(v);
				int len = container.getChildCount() - index - 1;
				container.removeViews(index + 1, len);

				// 跟新listView中的数据
				Contact contact = (Contact) v.getTag();
				mData.clear();
				mData.addAll(contact.getNodes());
				myAdapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Contact contact = (Contact) myAdapter.getItem(position);
		if (contact.getType() != Contant.STUDENT) {
			addNav(contact);
			reLoadData(contact);
		}
	}

	public void reLoadData(final Contact contact) {
		String Url = UrlUtils.SCHOOL_CONTACT_URL;
		StringPostRequest postRequest = new StringPostRequest(Url,
				new Listener<String>() {
					@Override
					public void onResponse(String arg0) {
						// TODO Auto-generated method stub
						List<Contact> list = null;
						switch (contact.getType()) {
						case Contant.ALL_MAJOR:
							list = parserMajor(arg0);
							break;
						case Contant.ALL_ROLE:
							list = parserRole(arg0);
							break;
						case Contant.MAJOR:
							list = parserClass(arg0);
							break;
						case Contant.ROLE:
						case Contant.CLZSS:
							list = parseStudent(arg0);
							break;
						default:
							break;
						}

						// 展现在当前的listview中
						if (list != null && list.size() > 0) {
							contact.setNodes(list);// 讲请求来的数据放在下一级的listView中
							mData.clear();
							mData.addAll(list);
							myAdapter.notifyDataSetChanged();
						}

					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub

					}
				});

		setParams(contact, postRequest);
		AppApplication.getInstance().getRequestQueue().add(postRequest);

	}

	public List<Contact> parseStudent(String arg0) {
		// TODO Auto-generated method stub
		List<Contact> list = new ArrayList<Contact>();
		if (TextUtils.isEmpty(arg0)) {
			return list;
		}
		try {
			JSONArray jsonArray = new JSONArray(arg0);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject job = jsonArray.getJSONObject(i);
				Contact contact = new Contact();
				contact.setId(job.getString("uno"));
				contact.setStuName(job.getString("stuName"));
				contact.setType(Contant.STUDENT);
				list.add(contact);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<Contact> parserClass(String arg0) {
		// TODO Auto-generated method stub
		List<Contact> list = new ArrayList<Contact>();
		if (TextUtils.isEmpty(arg0)) {
			return list;
		}
		try {
			JSONArray jsonArray = new JSONArray(arg0);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Contact contact = new Contact();
				contact.setId(jsonObject.getString("id"));
				contact.setStuName(jsonObject.getString("className"));
				contact.setType(Contant.CLZSS);
				list.add(contact);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public List<Contact> parserRole(String arg0) {
		// TODO Auto-generated method stub
		List<Contact> list = new ArrayList<Contact>();
		if (TextUtils.isEmpty(arg0)) {
			return list;
		}
		try {
			JSONArray jsonArray = new JSONArray(arg0);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Contact contact = new Contact();
				contact.setStuName(jsonObject.getString("role"));
				contact.setType(Contant.ROLE);
				list.add(contact);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 根据contact的类型，设置请求参数
	 * 
	 * @param contact
	 * @param postRequest
	 */
	public void setParams(Contact contact, StringPostRequest postRequest) {
		// TODO Auto-generated method stub
		switch (contact.getType()) {
		case Contant.ALL_MAJOR:
			postRequest.putParams("dataType", "major");
			break;
		case Contant.ALL_ROLE:
			postRequest.putParams("dataType", "role");
			break;
		case Contant.MAJOR:
			postRequest.putParams("dataType", "majortoclass");
			postRequest.putParams("majorId", contact.getId());
			break;
		case Contant.ROLE:
			postRequest.putParams("dataType", "roletostudent");
			postRequest.putParams("role", contact.getStuName());
			break;
		case Contant.CLZSS:
			postRequest.putParams("dataType", "classtostudent");
			postRequest.putParams("classNo", contact.getId());
			break;
		default:
			break;
		}

	}

	/**
	 * 用于解析Major的方法，返回contact集合
	 * 
	 * @param arg0
	 * @return
	 */
	public List<Contact> parserMajor(String arg0) {
		// TODO Auto-generated method stub
		List<Contact> list = new ArrayList<Contact>();
		if (TextUtils.isEmpty(arg0)) {
			return list;
		}
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(arg0);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);// 获得中括号的内容
				Contact contact = new Contact();
				contact.setId(jsonObject.getString("id"));
				contact.setStuName(jsonObject.getString("name"));
				contact.setType(Contant.MAJOR);
				list.add(contact);
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}// 获得大括号的内容

		return list;
	}

	private boolean flag = true;

	@Override
	public void onContactSelectChanged(Contact contact) {
		if (contact.getType() != Contant.ALL_MAJOR
				&& contact.getType() != Contant.ALL_ROLE) {
			// TODO Auto-generated method stub
			if (contact.isSelected()) {
				makeSure(contact);
				if (flag) {
					selectData.add(contact);
					count++;
				}
			} else {
				selectData.remove(contact);
				count--;
			}
			addActionBar();
			sureText.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.putParcelableArrayListExtra("message", selectData);
					setResult(Contant.NEW_INFO_RESULT, intent);
					finish();
				}
			});
			StringBuilder sb = new StringBuilder();
			for (Contact c : selectData) {
				sb.append(c.getStuName()).append(",");
			}
			contactText.setText(sb.toString());
		}
	}

	private void addActionBar() {
		if (count > 0) {
			sureText.setText("确定(" + count + ")");
			sureText.setGravity(Gravity.CENTER_VERTICAL);
			sureText.setTextColor(Color.WHITE);
			sureText.setTextSize(20);
			ActionBar.LayoutParams lp = new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
			lp.gravity = Gravity.RIGHT;
			actionbar.setCustomView(sureText, lp);
		}
		if (count == 0) {
			sureText.setText("");

		}
	}

	private void makeSure(Contact contact) {
		for (Contact c : selectData) {
			if (c.getStuName().equals(contact.getStuName())) {
				flag = false;
				break;
			} else {
				flag = true;
			}
		}
	}

}
