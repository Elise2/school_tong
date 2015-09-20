package activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import utils.FileUitlity;

import com.example.school_tong.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * 拍照，从相册中获取图片
 * 
 * @author Administrator
 *
 */
public class PhotoActivity extends Activity implements OnClickListener {
	private ImageView photoShow;
	private static final int TAKE_FROM_CAPTURE = 1;// 拍照
	private static final int TAKE_FROM_ALBUM = 2;// 相册
	private static final int RESULT_PHOTO = 3;// 裁剪结果

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_layout);
		findViewById(R.id.btnTakePhoto).setOnClickListener(this);
		findViewById(R.id.btnGallery).setOnClickListener(this);
		photoShow = (ImageView) findViewById(R.id.photoshow);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnTakePhoto:
			takePhoto();
			break;
		case R.id.btnGallery:
			takeAlbum();
		case RESULT_PHOTO:
			break;
		default:
			break;
		}

	}

	private void takeAlbum() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, TAKE_FROM_ALBUM);
	}

	String photoPath;

	public void takePhoto() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		File paren = FileUitlity.getInstance(this).makeDir("jredu_head");
		// File.separator文件分隔符
		photoPath = paren.getPath() + File.separator
				+ System.currentTimeMillis() + ".jpg";
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(photoPath)));
		startActivityForResult(intent, TAKE_FROM_CAPTURE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode != RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case TAKE_FROM_CAPTURE:
			if (photoPath != null) {
				// 从磁盘上拿到照片，将照片放到ImageView
				// photoShow.setImageURI(Uri.fromFile(new File(photoPath)));
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
			photoShow.setImageURI(Uri.fromFile(new File(photoPath)));
			break;
		case RESULT_PHOTO:
			Bundle bundle = data.getExtras();
			if (bundle != null) {
				Bitmap b = bundle.getParcelable("data");
				photoShow.setImageBitmap(b);
				// 询问是否需要上传图片
				// 如果开始上传图片，需要将图片转化为Base64位的字节码
			}
			break;
		default:
			break;
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

	public void updatePhoto(String photoStr) {
		// 地址UrlUtil.LOGIN_URL
		// postRequest.putParams("userName",user.getUno());
		// postRequest.putParams("pwd",user.getPwd);
		// postRequest.putParams("headImage","1");
		// postRequest.putParams("uhead",photoStr);

	}
}
