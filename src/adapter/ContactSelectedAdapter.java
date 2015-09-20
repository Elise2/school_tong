package adapter;

import java.util.List;

import com.example.school_tong.R;

import entity.Contact;
import entity.Contant;
import android.content.Context;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactSelectedAdapter extends BaseAdapter {
	private List<Contact> mdata;
	private Context mContext;
	private onContactSelectedListener listener;

	public void setListener(onContactSelectedListener listener) {
		this.listener = listener;
	}

	public ContactSelectedAdapter(List<Contact> mdata, Context mContext) {
		super();
		this.mdata = mdata;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.mdata.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.mdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		singleHolder holder = null;
		if (convertView == null) {
			holder = new singleHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.list_item_selected, null);
			holder.name = (TextView) convertView.findViewById(R.id.textView1);
			holder.checkBox = (CheckBox) convertView
					.findViewById(R.id.checkBox1);
			convertView.setTag(holder);
		} else {
			holder = (singleHolder) convertView.getTag();
		}
		final Contact contact = (Contact) getItem(position);
		holder.name.setText(contact.getStuName());
		if (contact.getType() == Contant.ALL_MAJOR
				|| contact.getType() == Contant.ALL_ROLE) {
			// /holder.checkBox.setVisibility(IGNORE_ITEM_VIEW_TYPE);
			holder.checkBox.setVisibility(View.GONE);
		} else {
			holder.checkBox.setVisibility(View.VISIBLE);
		}
		if (contact.isSelected()) {
			holder.checkBox.setChecked(true);
		} else {
			holder.checkBox.setChecked(false);
		}
		holder.checkBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CheckBox cBox = (CheckBox) v;
				contact.setSelected(cBox.isChecked());
				if (listener != null) {
					listener.onContactSelectChanged(contact);
				}
			}

		});
		return convertView;
	}

	public class singleHolder {
		public TextView name;
		public CheckBox checkBox;
	}

	// 定义一个接口，对外公布，来降低耦合度
	public interface onContactSelectedListener {
		public void onContactSelectChanged(Contact contact);
	}

}
