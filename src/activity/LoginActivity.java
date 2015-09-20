package activity;

import org.json.JSONException;
import org.json.JSONObject;

import db.dao.UserDao;
import entity.Users;
import gson.StringPostRequest;
import utils.UrlUtils;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import application.AppApplication;

import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.example.school_tong.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LoginActivity extends Activity implements OnClickListener {
	private SharedPreferences.Editor editor;
	private ActionBar actionbar;
	private EditText text1;
	private EditText text2;
	private String userName;
	private String userPwd;
	private CheckBox check1;
	private CheckBox check2;
	private Users data;
	private RequestQueue requestqueue;
	private boolean flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		requestqueue = AppApplication.getInstance().getRequestQueue();
		actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setDisplayShowHomeEnabled(false);
		actionbar.setDisplayShowTitleEnabled(true);
		actionbar.setTitle("校园通");
		text1 = (EditText) findViewById(R.id.text1);
		text2 = (EditText) findViewById(R.id.text2);
		check1 = (CheckBox) findViewById(R.id.check1);
		check2 = (CheckBox) findViewById(R.id.check2);
		findViewById(R.id.login).setOnClickListener(this);
		checkEvent();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		finish();
		return super.onOptionsItemSelected(item);
	}

	private void checkEvent() {
		SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
		// boolean flag = sp.getBoolean("isFirst", true);
		editor = sp.edit();
		String name = sp.getString("userName", "");
		String pwd = sp.getString("userPwd", "");
		if (name == null || pwd == null) {
			check1.setChecked(false);
			check2.setChecked(false);
		} else {
			check1.setChecked(true);
			check2.setChecked(true);
			text1.setText(name);
			text2.setText(pwd);
		}

		sp = getSharedPreferences("userInfo", MODE_PRIVATE);
		boolean remPwd = sp.getBoolean("REM_PWD", true);
		boolean autoLogin = sp.getBoolean("AUTO_LOGIN", true);
		check1.setChecked(remPwd);
		check2.setChecked(autoLogin);
		check1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				SharedPreferences sp = getSharedPreferences("userInfo",
						MODE_PRIVATE);
				SharedPreferences.Editor editor = sp.edit();
				editor.putBoolean("REM_PWD", isChecked);
				editor.commit();
			}
		});
		check2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				SharedPreferences sp = getSharedPreferences("userInfo",
						MODE_PRIVATE);
				SharedPreferences.Editor editor = sp.edit();
				editor.putBoolean("REM_PWD", isChecked);
				editor.commit();
				if (isChecked) {
					check1.setChecked(true);
				}
			}
		});
	}

	public void initView() {
		userName = text1.getText().toString();
		userPwd = text2.getText().toString();
		if (TextUtils.isEmpty(userName)) {
			Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_LONG)
					.show();
		}
		if (TextUtils.isEmpty(userPwd)) {
			Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_LONG)
					.show();
		}
		StringPostRequest postRequest = new StringPostRequest(UrlUtils.ROOT_URL
				+ "/LoginServlet", new Listener<String>() {

			@Override
			public void onResponse(String arg0) {
				if (arg0 != null) {
					Gson gson = new Gson();
					data = gson.fromJson(arg0, Users.class);
					JSONObject obj;
					try {
						obj = new JSONObject(arg0);

						if (obj.has("info")) {
							Toast.makeText(LoginActivity.this, "账号或密码错误",
									Toast.LENGTH_LONG).show();
						} else {
							// Intent intent = null;
							// 在跳转前进行setUser属性
							if (check1.isChecked() || check2.isChecked()) {
								editor.putString("userName", userName);
								editor.putString("userPwd", userPwd);
								editor.commit();
							} else {
								editor.remove("userName");
								editor.remove("userPwd");
								editor.commit();
							}
							AppApplication.getInstance().setUser(data);
							// 保存到本地数据库
							// 先删除已经保存的当前用户信息，然后再插入
							UserDao userDao = new UserDao(
									getApplicationContext());
							userDao.saveOrUpdateUser(data);
							setResult(RESULT_OK);
							finish();

							// intent = new Intent(LoginActivity.this,
							// NewsActivity.class);
							// startActivity(intent);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				UserDao userDao = new UserDao(getApplicationContext());
				Users user = userDao.findUserByUno(userName);
				AppApplication.getInstance().setUser(user);
				Intent intent = new Intent(LoginActivity.this,
						NewsActivity.class);
				startActivity(intent);
				finish();

			}
		});
		postRequest.putParams("userName", userName);
		postRequest.putParams("pwd", userPwd);
		requestqueue.add(postRequest);
	}

	@Override
	public void onClick(View v) {
		initView();
	}

}
