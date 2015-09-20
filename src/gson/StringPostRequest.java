package gson;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

public class StringPostRequest extends StringRequest {
	private Map<String, String> params;

	public StringPostRequest(int method, String url, Listener<String> listener,
			ErrorListener errorListener) {
		super(method, url, listener, errorListener);
		// TODO Auto-generated constructor stub
		initMap();
	}

	public StringPostRequest(String url, Listener<String> listener,
			ErrorListener errorListener) {
		super(Request.Method.POST, url, listener, errorListener);
		// TODO Auto-generated constructor stub
		initMap();
	}

	private void initMap() {
		params = new HashMap<String, String>();
	}

	// 用于把post提交的数据封装起来
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		// TODO Auto-generated method stub
		return params;

	}

	// 对外添加数据的方法
	public void putParams(String key, String value) {
		this.params.put(key, value);
	}

}
