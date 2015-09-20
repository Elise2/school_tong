package activity;

import com.example.school_tong.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

public class BianLiGongJuActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bianligongju);
		findViewById(R.id.blgongju).setOnClickListener(this);
		findViewById(R.id.tx1).setOnClickListener(this);
		findViewById(R.id.tx2).setOnClickListener(this);
		findViewById(R.id.tx3).setOnClickListener(this);
		findViewById(R.id.tx4).setOnClickListener(this);
		findViewById(R.id.tx5).setOnClickListener(this);
		findViewById(R.id.tx6).setOnClickListener(this);
		findViewById(R.id.tx7).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.blgongju:
			break;
		case R.id.tx1:
			Intent intent = new Intent(this, Convince1.class);
			startActivity(intent);
			break;

		case R.id.tx2:

			break;

		case R.id.tx3:

			break;

		case R.id.tx4:

			break;

		case R.id.tx5:

			break;

		case R.id.tx6:

			break;
		case R.id.tx7:

			break;

		default:
			break;
		}

	}

}
