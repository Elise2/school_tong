package adapter;

import java.util.List;
import java.util.zip.Inflater;

import utils.ImageLoaderUitil;
import utils.UrlUtils;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.example.school_tong.R;

import entity.TodayStatus;
import entity.ZhuanTi;
import gson.MoreZhuanTiActivity;
import gson.Zt_Content_Activity;
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

//主专题数据
public class ZhuantiAdapter extends BaseAdapter {
	private List<ZhuanTi> mdata;
	private Context mContext;
	private LayoutInflater mInflater;
	private ImageLoader mImageLoader;

	public ZhuantiAdapter(List<ZhuanTi> mdata, Context mContext) {
		super();
		this.mdata = mdata;
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(mContext);
		this.mImageLoader = new ImageLoader(AppApplication.getInstance()
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
			holder.subject_title = (TextView) convertView
					.findViewById(R.id.newsTitle);
			holder.subject_detail = (TextView) convertView
					.findViewById(R.id.newsSummary);
			holder.subject_date = (TextView) convertView
					.findViewById(R.id.newsDate);
			holder.subject_url = (ImageView) convertView
					.findViewById(R.id.zhuanti_img);
			convertView.setTag(holder);
		} else {
			holder = (zhuanTiHolder) convertView.getTag();
		}

		holder.subject_title.setText(mdata.get(position).getSubject_title());
		holder.subject_detail.setText(mdata.get(position).getSubject_detail());
		holder.subject_date.setText(mdata.get(position).getSubject_date());
		String strUrl = UrlUtils.ROOT_URL
				+ mdata.get(position).getSubject_url();
		ImageLoaderUitil.display(strUrl, holder.subject_url);
		return convertView;
	}

	public class zhuanTiHolder {
		public TextView subject_title;
		public TextView subject_detail;
		public TextView subject_date;
		public ImageView subject_url;
	}

}
