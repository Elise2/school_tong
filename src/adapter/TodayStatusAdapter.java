package adapter;

import java.util.List;
import java.util.zip.Inflater;

import utils.ImageLoaderUitil;
import utils.UrlUtils;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.ImageRequest;
import com.example.school_tong.R;

import entity.TodayStatus;
import entity.ZhuanTi;
import gson.XinxiXiangQingActivity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import application.AppApplication;

//今日动态
public class TodayStatusAdapter extends BaseAdapter {
	private List<TodayStatus> mdata;
	private Context mContext;
	private LayoutInflater mInflater;
	private ImageLoader mImageLoader;

	public TodayStatusAdapter(List<TodayStatus> mdata, Context mContext) {
		super();
		this.mdata = mdata;
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(mContext);
		mImageLoader = new ImageLoader(AppApplication.getInstance()
				.getRequestQueue(), AppApplication.getInstance()
				.getImageCache());
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mdata.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		zhuanTiHolder holder = null;
		if (convertView == null) {
			holder = new zhuanTiHolder();
			convertView = mInflater.inflate(R.layout.zhuanti_item_main, null);
			holder.title = (TextView) convertView.findViewById(R.id.newsTitle);
			holder.time = (TextView) convertView.findViewById(R.id.newsDate);
			holder.img = (ImageView) convertView.findViewById(R.id.zhuanti_img);
			convertView.setTag(holder);
		} else {
			holder = (zhuanTiHolder) convertView.getTag();
		}

		holder.title.setText(mdata.get(position).getTitle());
		holder.time.setText(mdata.get(position).getTime());
		String resUrl = UrlUtils.ROOT_URL + mdata.get(position).getImg();
		ImageLoaderUitil.display(resUrl, holder.img);
		return convertView;
	}

	public class zhuanTiHolder {
		public TextView title;
		public TextView time;
		public ImageView img;
	}
}
