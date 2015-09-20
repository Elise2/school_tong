package activity;

import utils.AppManager;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		AppManager.getAppManager().finishActivity(this);
	}

	// public boolean isNetWorkValidate(){
	// ConnectivityManager cm = (ConnectivityManager)
	// getSystemService(Context.CONNECTIVITY_SERVICE);
	// if (cm!=null) {
	// NetworkInfo ni = cm.getActiveNetworkInfo();
	// if (null!=ni && ni.isAvailable()) {
	// return true;
	// }else {
	// return false;
	// }
	// }
	// return false;
	// }

}
