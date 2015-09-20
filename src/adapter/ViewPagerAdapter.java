package adapter;

import java.util.List;

import entity.Channel;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerAdapter extends PagerAdapter {
	private List<Fragment> mData;
	private List<Channel> channels;

	public ViewPagerAdapter(List<Fragment> mData, List<Channel> channels) {
		super();
		this.mData = mData;
		this.channels = channels;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.mData.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return this.channels.get(position).getcTitle();
	}

}
