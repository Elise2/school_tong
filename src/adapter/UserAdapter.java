package adapter;

import entity.Users;

import java.util.List;

import android.content.Context;
import android.text.NoCopySpan.Concrete;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class UserAdapter extends BaseAdapter {
	private List<Users> mdata;
	private Context mContext;
	private LayoutInflater mInflater;

	public UserAdapter(List<Users> mdata, Context mContext) {
		super();
		this.mdata = mdata;
		this.mContext = mContext;
		this.mInflater = mInflater.from(mContext);
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
		return null;
	}

}
