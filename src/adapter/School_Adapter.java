package adapter;

import java.util.List;

import utils.ImageLoaderUitil;
import utils.UrlUtils;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.example.school_tong.R;

import entity.TodayStatus;
import gson.XinxiXiangQingActivity;
import android.R.integer;
import android.app.ActionBar;
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

//侧边栏跳转页面对应项的数据加载
public class School_Adapter extends BaseAdapter {
	private List<TodayStatus> mdata;
	private Context mContext;
	private LayoutInflater mInflater;

	public School_Adapter(List<TodayStatus> mdata, Context mContext) {
		super();
		this.mdata = mdata;
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(mContext);
		// this.mImageLoader = new ImageLoader(AppApplication.getInstance()
		// .getRequestQueue(), AppApplication.getInstance()
		// .getImageCache());
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		sHolder holder = null;
		if (convertView == null) {
			holder = new sHolder();
			convertView = mInflater.inflate(R.layout.zhuanti_item_main, null);
			holder.title = (TextView) convertView.findViewById(R.id.newsTitle);
			holder.time = (TextView) convertView.findViewById(R.id.newsDate);
			holder.img = (ImageView) convertView.findViewById(R.id.zhuanti_img);
			convertView.setTag(holder);
		} else {
			holder = (sHolder) convertView.getTag();
		}
		holder.title.setText(mdata.get(position).getTitle());
		holder.time.setText(mdata.get(position).getTime());
		String imgUrl = UrlUtils.ROOT_URL + mdata.get(position).getImg();
		// ImageListener listener = ImageLoader.getImageListener(holder.img,
		// R.drawable.cc_default_news_img,
		// R.drawable.cc_default_news_img_fail);
		ImageLoaderUitil.display(imgUrl, holder.img);

		return convertView;
	}

	public class sHolder {
		public TextView title;
		public TextView time;
		public ImageView img;
	}
}
