package db.dao;

import java.util.ArrayList;
import java.util.List;

import db.utils.SysConstant.toDayNews;
import db.utils.SysConstant.userTable;
import entity.TodayStatus;
import entity.Users;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NewsDao {
	private DBHelper dbHelper;
	private SQLiteDatabase database;

	public NewsDao(Context mContext) {
		dbHelper = new DBHelper(mContext);
	}

	public void addNews(List<TodayStatus> items) {
		for (TodayStatus todayStatus : items) {
			addNews(todayStatus);
		}
	}

	public void addNews(TodayStatus item) {
		database = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(toDayNews.NEWS_ID, item.getId());
		values.put(toDayNews.NEWS_ADVER, item.getAdvertisement());
		values.put(toDayNews.NEWS_AUTHOR, item.getAuthor());
		values.put(toDayNews.NEWS_CHANNALTYPE, item.getChannelType());
		values.put(toDayNews.NEWS_COMMENT, item.getComment());
		values.put(toDayNews.NEWS_COMMENTCOUNT, item.getCommentcount());
		values.put(toDayNews.NEWS_CONTENT, item.getContent());
		values.put(toDayNews.NEWS_FAVOR, item.getFavor());
		values.put(toDayNews.NEWS_FAVORTAG, item.getFavorTag());
		values.put(toDayNews.NEWS_IMG, item.getImg());
		values.put(toDayNews.NEWS_MAIINPAGE, item.getMainpagetag());
		values.put(toDayNews.NEWS_MAINdATE, item.getMainDate());
		values.put(toDayNews.NEWS_MARK, item.getMark());
		values.put(toDayNews.NEWS_MAXDATE, item.getMaxDate());
		values.put(toDayNews.NEWS_PAGETAG, item.getPagetag());
		values.put(toDayNews.NEWS_PAGETAGFLAG, item.getPagetagflag());
		values.put(toDayNews.NEWS_SHOPAD, item.getShopAddress());
		values.put(toDayNews.NEWS_TITLE, item.getTime());
		values.put(toDayNews.NEWS_XLS, item.getXls());
		values.put(toDayNews.NEWS_TAG, item.getTag());
		values.put(toDayNews.NEWS_TITLE, item.getTitle());
		values.put(toDayNews.NEWS_SHOPNAME, item.getShopName());
		values.put(toDayNews.NEWS_TIME, item.getTime());
		database.insert(toDayNews.TABLE_NAME, null, values);
		database.close();
	}

	public List<TodayStatus> findNews() {
		database = this.dbHelper.getReadableDatabase();
		StringBuilder sb = new StringBuilder();
		sb.append(" select ");
		sb.append(toDayNews.NEWS_ID + " , ");
		sb.append(toDayNews.NEWS_TITLE + " ,");
		sb.append(toDayNews.NEWS_CONTENT);
		sb.append(" from ");
		sb.append(toDayNews.TABLE_NAME);
		Cursor cursor = database.rawQuery(sb.toString(), null);
		List<TodayStatus> news = new ArrayList<TodayStatus>();
		TodayStatus itemNew = null;
		while (cursor.moveToNext()) {
			itemNew = new TodayStatus();
			itemNew.setId(cursor.getInt(cursor
					.getColumnIndex(toDayNews.NEWS_ID)));
			itemNew.setTitle(cursor.getString(cursor
					.getColumnIndex(toDayNews.NEWS_TITLE)));
			itemNew.setContent(cursor.getString(cursor
					.getColumnIndex(toDayNews.NEWS_CONTENT)));
			news.add(itemNew);
		}
		cursor.close();
		database.close();
		return news;
	}

	public void deleteNews(TodayStatus news) {
		database = dbHelper.getWritableDatabase();
		database.delete(toDayNews.TABLE_NAME, toDayNews.NEWS_ID + " = ?",
				new String[] { news.getId() + " " });

	}

	public List<TodayStatus> findNewsItem() {
		List<TodayStatus> news = new ArrayList<TodayStatus>();
		return news;
	}

	public List<TodayStatus> findNewsItem(String pageTag) {
		database = this.dbHelper.getReadableDatabase();
		StringBuilder sb = new StringBuilder();
		sb.append(" select ");
		sb.append(toDayNews.NEWS_ID + " , ");
		sb.append(toDayNews.NEWS_TITLE + " ,");
		sb.append(toDayNews.NEWS_CONTENT);
		sb.append(" from ");
		sb.append(toDayNews.TABLE_NAME);
		sb.append(" where pagetag = ?");
		Cursor cursor = database.rawQuery(sb.toString(),
				new String[] { pageTag });
		List<TodayStatus> news = new ArrayList<TodayStatus>();
		TodayStatus itemNew = null;
		if (cursor.moveToNext()) {
			itemNew = new TodayStatus();
			itemNew.setId(cursor.getInt(cursor
					.getColumnIndex(toDayNews.NEWS_ID)));
			itemNew.setTitle(cursor.getString(cursor
					.getColumnIndex(toDayNews.NEWS_TITLE)));
			itemNew.setContent(cursor.getString(cursor
					.getColumnIndex(toDayNews.NEWS_CONTENT)));
			news.add(itemNew);
		}
		cursor.close();
		database.close();
		return news;
	}

}
