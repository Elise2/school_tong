package activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import utils.FileUitlity;

import com.example.school_tong.R;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

/**
 * 实现下载功能，使用Service需要开辟子线程
 * 
 * @author Administrator
 *
 */
public class DownLoadService extends Service {
	private Thread thread;
	private NotificationManager nm;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	}

	public void notifyNotification(int progress) {
		// 创建Notification
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);
		builder.setContentTitle("下载");// 下载
		builder.setProgress(100, progress, false);
		builder.setSmallIcon(R.drawable.huba15073100);
		nm.notify(0, builder.build());
	}

	// 待完善
	public void notifyNotification() {
		// 创建Notification
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);
		builder.setContentTitle("提示");// 下载
		builder.setContentText("下载完成，点击查看");
		// builder.setProgress(100, 0, false);
		builder.setSmallIcon(R.drawable.huba15073100);
		// 安装的文件在哪儿
		File parent = FileUitlity.getInstance(this).makeDir("apk");
		File apk = new File(parent, "cc.apk");
		// 使用第三方组件启动真机所带下载功能
		Intent i = new Intent();
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 隐式Intent
		i.setAction(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.fromFile(apk),
				"application/vnd.android.package-archive");
		// 实现跳转
		PendingIntent intent = PendingIntent.getActivity(this, 0, i,
				PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setAutoCancel(true);
		builder.setContentIntent(intent);
		// 发送通知
		nm.notify(0, builder.build());
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		final String url = intent.getStringExtra("url");
		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				downLoad(url);
				stopSelf();

			}
		});
		thread.start();
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 安装APK文件
	 * 
	 * @param urlStr
	 */
	public void installApk() {
		File parent = FileUitlity.getInstance(this).makeDir("apk");
		File apk = new File(parent, "cc.apk");
		Intent i = new Intent();
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setAction(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.fromFile(apk),
				"application/vnd.android.package-archive");
		startActivity(i);
	}

	// 执行下载
	public void downLoad(String urlStr) {
		HttpURLConnection connection = null;
		URL url = null;
		InputStream iS = null;
		OutputStream os = null;
		int contentLength = 0;// 文件总长度
		int curLoadLenght = 0;
		try {
			url = new URL(urlStr);
			File parent = FileUitlity.getInstance(this).makeDir("apk");
			os = new FileOutputStream(new File(parent, "cc.apk"));
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			if (connection.getResponseCode() == 200) {
				// 文件的总长度
				contentLength = connection.getContentLength();
				iS = connection.getInputStream();
				byte[] buffer = new byte[512];
				int len = 0;
				while ((len = iS.read(buffer)) != -1) {
					curLoadLenght += len;
					// 边读边写，向sd卡中写文件
					os.write(buffer, 0, len);// 该方法防止读不完的情况
					// 计算进度
					int progress = (int) ((curLoadLenght * 1.0 / contentLength) * 100);
					notifyNotification(progress);
				}
				os.flush();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (iS != null) {
				try {
					iS.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// connection.disconnect();
		}
		notifyNotification();

	}

}
