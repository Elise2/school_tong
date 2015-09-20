package activity;

import com.example.school_tong.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class InformationActivity extends Activity {
	private ActionBar actionbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.infoflat);
		actionbar = getActionBar();
		actionbar.setDisplayShowHomeEnabled(false);
		actionbar.setDisplayShowTitleEnabled(true);
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setTitle("消息平台");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			startActivity(new Intent(getApplicationContext(),
					New_InforActivity.class));
			break;

		default:
			break;
		}
		finish();
		return super.onOptionsItemSelected(item);

	}

}
