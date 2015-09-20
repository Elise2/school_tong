package db.dao;

import java.util.ArrayList;
import java.util.List;

import db.utils.SysConstant.Newtable;
import db.utils.SysConstant.toDayNews;
import db.utils.SysConstant.userTable;
import entity.TodayStatus;
import entity.Users;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class StorageDao {
	private DBHelper dbHelper;

	public StorageDao(Context context) {
		super();
		dbHelper = new DBHelper(context);
	}

	public void addNews(TodayStatus notice, Users user) throws Exception {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		// 操作user数据库
		String insertSQL = "insert into " + Newtable.TABLE_NAME
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		database.execSQL(
				insertSQL,
				new Object[] { user.getUno(), notice.getId(),
						notice.getTitle(), notice.getAdvertisement(),
						notice.getAuthor(), notice.getChannelType(),
						notice.getComment(), notice.getContent(),
						notice.getCommentcount(), notice.getFavor(),
						notice.getFavorTag(), notice.getImg(),
						notice.getMaxDate(), notice.getMainpagetag(),
						notice.getMark(), notice.getMainDate(),
						notice.getPagetag(), notice.getPagetagflag(),
						notice.getShopAddress(), notice.getShopName(),
						notice.getTag(), notice.getTime(), notice.getXls() });
		database.close();
	}

	/**
	 * 全表查询
	 * 
	 * @return
	 */
	public List<TodayStatus> allNews() {
		return findNews(null, null);
	}

	/**
	 * and条件查询
	 * 
	 * @param select
	 *            查询条件
	 * @param values
	 *            条件对应的值
	 * @return
	 */
	public List<TodayStatus> findNews(String[] select, String[] values) {
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		List<TodayStatus> users = new ArrayList<TodayStatus>();
		String findSQL = "select * from " + Newtable.TABLE_NAME;
		if (!(select == null || select.length == 0)) {
			findSQL += " where ";
			for (int i = 0; i < select.length - 1; i++) {
				findSQL += select[i] + "=? and ";
			}
			findSQL += select[select.length - 1] + "=?";
		}
		Cursor cursor = database.rawQuery(findSQL, values);
		while (cursor.moveToNext()) {
			// 取出当前行的值
			String advertisement = cursor.getString(cursor
					.getColumnIndex(Newtable.NEWS_ADVER));
			String author = cursor.getString(cursor
					.getColumnIndex(Newtable.NEWS_AUTHOR));
			String channelType = cursor.getString(cursor
					.getColumnIndex(Newtable.NEWS_CHANNALTYPE));
			String comment = cursor.getString(cursor
					.getColumnIndex(Newtable.NEWS_COMMENT));
			int commentcount = cursor.getInt(cursor
					.getColumnIndex(Newtable.NEWS_COMMENTCOUNT));
			String content = cursor.getString(cursor
					.getColumnIndex(Newtable.NEWS_CONTENT));
			int favor = cursor.getInt(cursor
					.getColumnIndex(Newtable.NEWS_FAVOR));
			int favorTag = cursor.getInt(cursor
					.getColumnIndex(Newtable.NEWS_FAVORTAG));
			int id = cursor.getInt(cursor.getColumnIndex(Newtable.NEWS_ID));
			String img = cursor.getString(cursor
					.getColumnIndex(Newtable.NEWS_IMG));
			String mainDate = cursor.getString(cursor
					.getColumnIndex(Newtable.NEWS_MAINdATE));
			String mainpagetag = cursor.getString(cursor
					.getColumnIndex(Newtable.NEWS_MAIINPAGE));
			int mark = cursor.getInt(cursor.getColumnIndex(Newtable.NEWS_MARK));
			String maxDate = cursor.getString(cursor
					.getColumnIndex(Newtable.NEWS_MAXDATE));
			int pagetag = cursor.getInt(cursor
					.getColumnIndex(Newtable.NEWS_PAGETAG));
			int pagetagflag = cursor.getInt(cursor
					.getColumnIndex(Newtable.NEWS_PAGETAGFLAG));
			String shopAddress = cursor.getString(cursor
					.getColumnIndex(Newtable.NEWS_SHOPAD));
			String shopName = cursor.getString(cursor
					.getColumnIndex(Newtable.NEWS_SHOPNAME));
			int tag = cursor.getInt(cursor.getColumnIndex(Newtable.NEWS_TAG));
			String time = cursor.getString(cursor
					.getColumnIndex(Newtable.NEWS_TIME));
			String title = cursor.getString(cursor
					.getColumnIndex(Newtable.NEWS_TITLE));
			String xls = cursor.getString(cursor
					.getColumnIndex(Newtable.NEWS_XLS));
			users.add(new TodayStatus(advertisement, author, channelType,
					comment, commentcount, content, favor, favorTag, id, img,
					mainDate, mainpagetag, mark, maxDate, pagetag, pagetagflag,
					shopAddress, shopName, tag, time, title, xls));
		}
		cursor.close();
		database.close();
		return users;
	}

	public void deleteSubject(int newsId) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		database.delete(Newtable.TABLE_NAME, Newtable.NEWS_ID + " = ?",
				new String[] { newsId + "" });
	}

}
