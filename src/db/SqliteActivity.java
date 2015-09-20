package db;

import com.example.school_tong.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SqliteActivity extends Activity implements OnClickListener

{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dblayout);
		findViewById(R.id.insertDb).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.insertDb:

			break;

		default:
			break;
		}

	}

}
