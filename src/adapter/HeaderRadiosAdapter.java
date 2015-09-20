package adapter;

import java.util.List;

import entity.Channel;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;

public class HeaderRadiosAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;
	private List<Channel> channels;

	public HeaderRadiosAdapter(FragmentManager fm, List<Fragment> fragments,
			List<Channel> channels) {
		super(fm);
		this.fragments = fragments;
		this.channels = channels;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return this.fragments.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.fragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return this.channels.get(position).getcTitle();
	}

}
