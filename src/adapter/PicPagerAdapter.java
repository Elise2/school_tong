package adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class PicPagerAdapter extends PagerAdapter {
	private List<ImageView> mdata;

	public PicPagerAdapter(List<ImageView> mdata) {
		super();
		this.mdata = mdata;
	}

	public PicPagerAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.mdata.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		ImageView imageView = this.mdata.get(position);
		container.removeView(imageView);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		ImageView view = this.mdata.get(position);
		container.addView(view);
		return view;
	}

}
