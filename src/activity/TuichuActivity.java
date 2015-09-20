package activity;

import org.json.JSONException;
import org.json.JSONObject;

import utils.AppManager;
import utils.ImageLoaderUitil;
import utils.UrlUtils;

import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.example.school_tong.R;

import entity.Users;
import gson.StringPostRequest;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import application.AppApplication;

public class TuichuActivity extends BaseActivity implements OnClickListener {
	private ImageView img;
	private TextView text1;
	private TextView text2;
	private TextView updateVersion;
	private ImageView pic;
	private Users user;
	private ActionBar actionbar;
	private TextView versionCode;
	private boolean flag = true;
	private String url;
	private int vCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tuichulayout);
		actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setDisplayShowHomeEnabled(false);
		actionbar.setDisplayShowTitleEnabled(true);
		actionbar.setTitle("设置");
		text1 = (TextView) findViewById(R.id.text1);
		text2 = (TextView) findViewById(R.id.text2);
		updateVersion = (TextView) findViewById(R.id.updateversion);
		versionCode = (TextView) findViewById(R.id.versionCode);
		pic = (ImageView) findViewById(R.id.pic);
		img = (ImageView) findViewById(R.id.icButton);
		user = AppApplication.getInstance().getUser();
		if (user != null) {
			text1.setText(user.getUno());
			text2.setText(user.getStuName());
			String urlStr = user.getImg();
			ImageLoaderUitil.display(urlStr, pic);
		}
		findViewById(R.id.text3).setOnClickListener(this);
		findViewById(R.id.queit).setOnClickListener(this);
		findViewById(R.id.storeInfo).setOnClickListener(this);
		findViewById(R.id.pic).setOnClickListener(this);
		findViewById(R.id.icButton).setOnClickListener(this);
		getVersion();
		versionCode.setText(vCode + "");
		updateVersion.setOnClickListener(this);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		finish();
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img:
			Intent intent = new Intent(TuichuActivity.this, GeRenZhongXin.class);
			startActivity(intent);
			break;
		case R.id.icButton:
			if (flag) {
				img.setImageResource(R.drawable.icon_switch_off);
				flag = false;
			} else {
				img.setImageResource(R.drawable.icon_switch_on);
				flag = true;
			}
			break;
		case R.id.text3:
			intent = new Intent(TuichuActivity.this, GenGaiMiMaActivity.class);
			startActivity(intent);
			break;
		case R.id.storeInfo:
			intent = new Intent(TuichuActivity.this, StoreInfomation.class);
			startActivity(intent);
			break;
		case R.id.queit:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("退出登录").setMessage("确定退出当前登录用户?");
			builder.setPositiveButton("确定",
					new android.content.DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 删除所有的activity
							AppManager.getAppManager().finishiAllActivity();
							// 结束进程
							android.os.Process.killProcess(android.os.Process
									.myPid());
							onDestroy();
						}
					});

			builder.setNegativeButton("取消",
					new android.content.DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
			AlertDialog dialog = builder.create();
			dialog.setCancelable(false);
			dialog.show();
			break;
		case R.id.updateversion:
			PackageManager pm = getPackageManager();
			try {
				PackageInfo pki = pm.getPackageInfo("com.example.school_tong",
						0);
				int curCode = pki.versionCode;
				if (vCode > curCode) {
					intent = new Intent(this, DownLoadService.class);
					intent.putExtra("url",
							"http://192.168.56.1:8080/SchoolLife/apk");
					startService(intent);
				}

			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		user = AppApplication.getInstance().getUser();
	}

	public void getVersion() {
		StringPostRequest postRequest = new StringPostRequest(UrlUtils.ROOT_URL
				+ "/apk", new Listener<String>() {

			@Override
			public void onResponse(String arg0) {
				// TODO Auto-generated method stub
				try {
					JSONObject jsonObject = new JSONObject(arg0);
					vCode = jsonObject.getInt("vid");
					url = jsonObject.getString("url");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub

			}
		});
		AppApplication.getInstance().getRequestQueue().add(postRequest);
	}

}
