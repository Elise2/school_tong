package activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import utils.AppManager;
import utils.FileUitlity;
import utils.ImageLoaderUitil;
import utils.UrlUtils;

import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.example.school_tong.R;

import db.dao.UserDao;
import entity.Users;
import gson.StringPostRequest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import application.AppApplication;

//个人信息主页
public class GeRenZhongXin extends Activity implements OnClickListener {
	private Users user;
	private TextView xm;
	private TextView xh;
	private TextView bj;
	private TextView zy;
	private ImageView img;
	private ActionBar actionbar;
	private static final int TAKE_FROM_CAPTURE = 1;// 拍照
	private static final int TAKE_FROM_ALBUM = 2;// 相册
	private static final int RESULT_PHOTO = 3;// 裁剪结果

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gerenxinxi);
		actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setDisplayShowHomeEnabled(false);
		actionbar.setDisplayShowTitleEnabled(true);
		actionbar.setTitle("个人信息");
		user = AppApplication.getInstance().getUser();
		xm = (TextView) findViewById(R.id.xm);
		xh = (TextView) findViewById(R.id.xh);
		bj = (TextView) findViewById(R.id.bj);
		zy = (TextView) findViewById(R.id.zy);
		img = (ImageView) findViewById(R.id.img);
		if (user != null) {
			bj.setText(user.getClassName());
			zy.setText(user.getMajorName());
			xh.setText(user.getUno());
			xm.setText(user.getStuName());
			// img.setImageResource(user.getImg());
			String url = user.getImg();
			ImageLoaderUitil.display(url, img);
		}
		findViewById(R.id.img).setOnClickListener(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		finish();
		return super.onOptionsItemSelected(item);
	}

	private String photoPath;

	@Override
	public void onClick(View v) {
		View contentView = LayoutInflater.from(this).inflate(
				R.layout.gerenxinxi_popwindows, null);
		// 定义popupWindow对象
		final PopupWindow popupWindow = new PopupWindow(contentView,
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		// 设置popupWindow的动画效果
		// popupWindow.setAnimationStyle(animationStyle);
		TextView take_photo = (TextView) contentView
				.findViewById(R.id.zhaoxiang);
		// 在相册
		contentView.findViewById(R.id.xiangce).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(Intent.ACTION_PICK);
						intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						startActivityForResult(intent, TAKE_FROM_ALBUM);
					}
				});
		// 点击取消按钮
		contentView.findViewById(R.id.tuichuk).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						popupWindow.dismiss();
					}
				});

		take_photo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent getImageByCamera = new Intent(
						"android.media.action.IMAGE_CAPTURE");
				File parent = FileUitlity.getInstance(getApplicationContext())
						.makeDir("head_img");
				// 以时间命名的jpg格式
				photoPath = parent.getPath() + File.separatorChar
						+ System.currentTimeMillis() + ".jpg";
				getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(new File(photoPath)));
				getImageByCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
				startActivityForResult(getImageByCamera, TAKE_FROM_CAPTURE);
			}
		});
		// 点击PopupWindow外部，popupWindow消失
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new ColorDrawable());
		popupWindow.setAnimationStyle(R.style.animpopwindow);
		// 所呈现的布局出现在所按下的按钮的下面
		popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case TAKE_FROM_CAPTURE:
			if (photoPath != null) {
				startPhotoZoom(Uri.fromFile(new File(photoPath)));
			}
			break;
		case TAKE_FROM_ALBUM:
			Cursor cursor = this.getContentResolver().query(data.getData(),
					new String[] { MediaStore.Images.Media.DATA }, null, null,
					null);
			cursor.moveToFirst();
			// 取
			photoPath = cursor.getString(cursor
					.getColumnIndex(MediaStore.Images.Media.DATA));
			cursor.close();
			img.setImageURI(Uri.fromFile(new File(photoPath)));
			break;
		case RESULT_PHOTO:
			sendPhoto(data);
			break;
		default:
			break;
		}
	}

	private void sendPhoto(Intent data) {
		Bundle bundle = data.getExtras();
		if (bundle != null) {
			final Bitmap b = bundle.getParcelable("data");
			img.setImageBitmap(b);
			// 询问是否需要上传图片
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示").setMessage("确定上传图片吗?");
			builder.setPositiveButton("确定",
					new android.content.DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							StringPostRequest postRequest = new StringPostRequest(
									UrlUtils.LOGIN_URL, new Listener<String>() {

										@Override
										public void onResponse(String arg0) {
											// TODO Auto-generated method
											user.setImg(arg0);
											AppApplication.getInstance()
													.setUser(user);
											UserDao userDao = new UserDao(
													getApplicationContext());
											userDao.checkUser(user.getUno());
										}
									}, new ErrorListener() {

										@Override
										public void onErrorResponse(
												VolleyError arg0) {
											// TODO Auto-generated method
											// stub

										}
									});
							postRequest.putParams("userName", user.getUno());
							postRequest.putParams("pwd", user.getPwd());
							postRequest.putParams("headImage", "1");
							postRequest.putParams("uhead",
									convertBitmap2String(b));
							AppApplication.getInstance().getRequestQueue()
									.add(postRequest);
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
			// 如果开始上传图片，需要将图片转化为Base64位的字节码
		}
	}

	// 处理图片
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("ouyputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, RESULT_PHOTO);
	}

	public String convertBitmap2String(Bitmap b) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		b.compress(Bitmap.CompressFormat.JPEG, 100, os);
		try {
			os.flush();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 将图片转化为字符串编码
		byte[] bf = os.toByteArray();
		byte[] stringByte = Base64.encode(bf, Base64.DEFAULT);// 拿到新的字节数组
		return new String(stringByte);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		user = AppApplication.getInstance().getUser();
	}

}
