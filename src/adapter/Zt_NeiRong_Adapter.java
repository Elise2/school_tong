package adapter;

import java.util.List;
import java.util.zip.Inflater;

import utils.ImageLoaderUitil;
import utils.UrlUtils;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.example.school_tong.R;

import entity.TodayStatus;
import entity.ZhuanTi;
import gson.XinxiXiangQingActivity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import application.AppApplication;

//各个专题点击显示的内容
public class Zt_NeiRong_Adapter extends BaseAdapter {
	private List<TodayStatus> mdata;
	private Context mContext;
	private LayoutInflater mInflater;

	public Zt_NeiRong_Adapter(List<TodayStatus> mdata, Context mContext) {
		super();
		this.mdata = mdata;
		this.mContext = mContext;
		mInflater = LayoutInflater.from(mContext);
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
		zhuanTiHolder holder = null;
		if (convertView == null) {

			holder = new zhuanTiHolder();
			convertView = mInflater.inflate(R.layout.zt_content_item, null);
			// convertView.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// // TODO Auto-generated method stub
			// TodayStatus todayStatus = new TodayStatus();
			// todayStatus.setTitle(mdata.get(position).getTitle());
			// todayStatus.setTime(mdata.get(position).getTime());
			// todayStatus.setContent(mdata.get(position).getContent());
			// Intent intent = new Intent(v.getContext(),
			// XinxiXiangQingActivity.class);
			// intent.putExtra("xinxiNews", todayStatus);
			// v.getContext().startActivity(intent);
			//
			// }
			// });
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

		holder.subject_title.setText(mdata.get(position).getTitle());
		holder.subject_date.setText(mdata.get(position).getTime());
		String strUrl = UrlUtils.ROOT_URL + mdata.get(position).getImg();
		// ImageListener listener = ImageLoader.getImageListener(
		// holder.subject_url, R.drawable.cc_default_news_img,
		// R.drawable.cc_default_news_img_fail);
		// mImageLoader.get(strUrl, listener);
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
