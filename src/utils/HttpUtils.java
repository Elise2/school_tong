package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Key;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class HttpUtils {
	private static StringBuffer sBuffer;
	static HttpURLConnection connection;

	public static String doGet() {
		// 建立URL对象
		String urlString = "http://192.168.56.1:8080/AppServer/readme.txt";

		try {
			URL url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			// 设置访问参数
			connection.setConnectTimeout(5 * 1000);// 连接超时参数设定
			connection.setReadTimeout(5 * 1000);// 设置访问超时参数
			// 判断服务状态并读取数据：
			// connection.getResponseCode();//判断服务状态，已经建立连接了
			// 200说明正常连接，正常返回
			if (connection.getResponseCode() == 200) {
				// 读取返回的数据流
				InputStream is = connection.getInputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				sBuffer = new StringBuffer();
				while ((len = is.read(buffer)) != -1) {
					String tmp = new String(buffer, 0, len, "utf-8");
					sBuffer.append(tmp);
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		String result = sBuffer.toString();
		return result;
	}

	public static String doGet(String strurls) {
		HttpURLConnection conn = null;
		URL url;
		try {
			url = new URL(strurls);
			conn = (HttpURLConnection) url.openConnection();
			// 设置参数
			conn.setConnectTimeout(5 * 1000);
			if (conn.getResponseCode() == 200) {
				// 接收从后台返回的数据
				InputStream is = conn.getInputStream();
				byte[] buffer = new byte[2014];
				int len = 0;
				sBuffer = new StringBuffer();
				while ((len = is.read(buffer)) != -1) {
					sBuffer.append(new String(buffer, 0, len));
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		String result = sBuffer.toString();
		return result;

	}

	public static String doPost(String strurls) {
		// 定义Connection
		HttpURLConnection conn = null;
		URL url;
		try {
			url = new URL(strurls);
			conn = (HttpURLConnection) url.openConnection();
			// 设置参数
			conn.setConnectTimeout(5 * 1000);

			// 是否缓存
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 建立连接
			conn.connect();
			// 向后台传递数据 需要使用流
			OutputStream os = conn.getOutputStream();
			String params = "userName=admin&userPwd=ffffff";
			os.write(params.getBytes());
			// 将缓冲区的数据取出
			os.flush();
			os.close();
			// 接收从后台返回的数据
			InputStream is = conn.getInputStream();
			byte[] buffer = new byte[512];
			int len = 0;
			sBuffer = new StringBuffer();
			while ((len = is.read(buffer)) != -1) {
				sBuffer.append(new String(buffer, 0, len));
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		String result = sBuffer.toString();
		return result;

	}

	public static Bitmap downLoadBitmap(String photoUrl) {
		HttpURLConnection connection = null;
		URL url;
		try {
			url = new URL(photoUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5 * 1000);
			connection.setReadTimeout(10 * 1000);
			if (connection.getResponseCode() == 200) {
				InputStream stream = connection.getInputStream();
				Bitmap bitmap = BitmapFactory.decodeStream(stream);
				return bitmap;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String doPost(String strUrl, Map<String, String> params) {
		// 定义Connection
		HttpURLConnection conn = null;
		URL url;
		try {
			url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			// 设置参数
			conn.setConnectTimeout(5 * 1000);

			// 是否缓存
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 建立连接
			conn.connect();
			// 向后台传递数据 需要使用流
			String useInfo = "";
			OutputStream os = conn.getOutputStream();
			Set<String> keys = params.keySet();
			for (String obj : keys) {
				useInfo += obj + "=" + params.get(obj) + "&";
			}
			useInfo.substring(1, useInfo.length() - 1);
			os.write(useInfo.getBytes());
			// 将缓冲区的数据取出
			os.flush();
			os.close();
			// 接收从后台返回的数据
			InputStream is = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			sBuffer = new StringBuffer();
			while ((len = is.read(buffer)) != -1) {
				sBuffer.append(new String(buffer, 0, len));
			}
			is.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		String result = sBuffer.toString();
		return result;

	}

	public String doHttpClientGet(String urlStr) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequestGet = new HttpGet(urlStr);
		try {
			HttpResponse response = httpClient.execute(getRequestGet);
			// HttpStatus.SC_OK=200
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 返回了客户端返回的信息
				String resultString = EntityUtils
						.toString(response.getEntity());
				return resultString;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
