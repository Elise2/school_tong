package activity;

import utils.UrlUtils;

import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.example.school_tong.R;

import entity.Users;
import gson.StringPostRequest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import application.AppApplication;

public class GenGaiMiMaActivity extends Activity implements OnClickListener {
	private TextView name;
	private TextView pwd;
	private TextView rePwd;
	private Users user;
	private ActionBar actionbar;
	private SharedPreferences.Editor editor;
	private Users data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ggaipwd);
		actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setDisplayShowHomeEnabled(false);
		actionbar.setDisplayShowTitleEnabled(true);
		actionbar.setTitle("密码更改");
		user = AppApplication.getInstance().getUser();
		name = (TextView) findViewById(R.id.text1);
		pwd = (TextView) findViewById(R.id.text2);
		rePwd = (TextView) findViewById(R.id.text3);
		findViewById(R.id.login).setOnClickListener(this);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		finish();
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login:
			if (name.getText().toString().equals(user.getStuName())) {
				if (pwd.getText().toString().equals(rePwd.getText().toString())) {
					SharedPreferences sp = getSharedPreferences("userInfo",
							MODE_PRIVATE);
					editor = sp.edit();
					editor.remove("userPwd");
					editor.putString("userPwd", rePwd.getText().toString());
					editor.commit();
					StringPostRequest postRequest = new StringPostRequest(
							UrlUtils.ROOT_URL + "/LoginServlet",
							new Listener<String>() {

								@Override
								public void onResponse(String arg0) {
									// TODO Auto-generated method stub

								}
							}, new ErrorListener() {

								@Override
								public void onErrorResponse(VolleyError arg0) {

								}
							});
					postRequest.putParams("userName", user.getStuName());
					postRequest.putParams("pwd", rePwd.getText().toString());
					postRequest.putParams("update", "1");
					AppApplication.getInstance().getRequestQueue()
							.add(postRequest);
					finish();
				} else {
					Toast.makeText(this, "您输入密码不一致", Toast.LENGTH_LONG).show();
				}

			} else {
				Toast.makeText(this, "您输入的用户名有误", Toast.LENGTH_LONG).show();
			}
			break;

		default:
			break;
		}

	}

}
