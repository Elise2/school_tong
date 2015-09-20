package gson;

import java.util.List;

import utils.UrlUtils;

import com.example.school_tong.R;

import db.dao.StorageDao;
import entity.Contant;
import entity.TodayStatus;
import entity.Users;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import application.AppApplication;

public class XinxiXiangQingActivity extends Activity {
	private TextView xinxiTitle;
	private TextView xinxiTime;
	private WebView xinxiContent;
	private TodayStatus xinxiNews;
	private ActionBar actionbar;
	private TextView storeView;
	private TextView quiteTextView;
	private ActionBar.LayoutParams lp = null;
	private Users user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xinixi_xiangqing);
		initActionBar();
		user = AppApplication.getInstance().getUser();
		xinxiNews = (TodayStatus) getIntent().getSerializableExtra("xinxiNews");
		xinxiTitle = (TextView) findViewById(R.id.xinxiTitle);
		xinxiTime = (TextView) findViewById(R.id.xinxiDate);
		xinxiContent = (WebView) findViewById(R.id.xinxiContent);
		xinxiTitle.setText(xinxiNews.getTitle());
		xinxiTime.setText(xinxiNews.getTime());
		xinxiContent.loadDataWithBaseURL(UrlUtils.ROOT, xinxiNews.getContent(),
				"text/html", "utf-8", null);

	}

	private void initActionBar() {
		storeView = new TextView(this);
		quiteTextView = new TextView(this);
		storeView.setTextSize(20);
		storeView.setGravity(Gravity.CENTER_VERTICAL);
		quiteTextView.setGravity(Gravity.CENTER_VERTICAL);
		quiteTextView.setTextColor(Color.WHITE);
		quiteTextView.setTextSize(20);
		storeView.setText("收藏");
		quiteTextView.setText("取消收藏");
		storeView.setTextColor(Color.WHITE);
		lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, Gravity.RIGHT);
		actionbar = getActionBar();
		actionbar.setDisplayShowHomeEnabled(false);
		actionbar.setDisplayShowTitleEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setDisplayShowCustomEnabled(true);
		actionbar.setTitle("信息详情");
		actionbar.setCustomView(storeView, lp);
		storeView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				actionbar.setCustomView(quiteTextView, lp);
				final StorageDao storageDao = new StorageDao(
						getApplicationContext());
				try {
					storageDao.addNews(xinxiNews, user);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				quiteTextView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						actionbar.setCustomView(storeView, lp);
						try {
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// 需要完善收藏
						Toast.makeText(XinxiXiangQingActivity.this, "取消收藏成功",
								Toast.LENGTH_LONG).show();
					}
				});
				Toast.makeText(XinxiXiangQingActivity.this, "收藏成功",
						Toast.LENGTH_LONG).show();

			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		finish();
		return super.onOptionsItemSelected(item);
	}

}
