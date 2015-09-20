package db.dao;

import java.util.ArrayList;
import java.util.List;

import db.utils.SysConstant.subjectNews;
import db.utils.SysConstant.toDayNews;
import entity.TodayStatus;
import entity.ZhuanTi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SubjectDao {
	private DBHelper dbHelper;
	private SQLiteDatabase database;

	public SubjectDao(Context mContext) {
		dbHelper = new DBHelper(mContext);
	}

	public void addSubject(ZhuanTi subject) {
		database = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(subjectNews.SUB_ID, subject.getSubject_id());
		values.put(subjectNews.SUB_TITLE, subject.getSubject_title());
		values.put(subjectNews.SUB_DETAIL, subject.getSubject_detail());
		values.put(subjectNews.SUB_DATE, subject.getSubject_date());
		values.put(subjectNews.SUB_URL, subject.getSubject_url());
		database.insert(subjectNews.TABLE_NAME, null, values);
		database.close();
	}

	public void addMoreSubject(List<ZhuanTi> subjects) {
		for (ZhuanTi zhuanTi : subjects) {
			addSubject(zhuanTi);
		}
	}

	public List<ZhuanTi> findSubject() {
		database = this.dbHelper.getReadableDatabase();
		StringBuilder sb = new StringBuilder();
		sb.append(" select ");
		sb.append(subjectNews.SUB_ID + " , ");
		sb.append(subjectNews.SUB_TITLE + " ,");
		sb.append(subjectNews.SUB_DETAIL + ",");
		sb.append(subjectNews.SUB_DATE + " ,");
		sb.append(subjectNews.SUB_URL);
		sb.append(" from ");
		sb.append(subjectNews.TABLE_NAME);
		Cursor cursor = database.rawQuery(sb.toString(), null);
		List<ZhuanTi> subjects = new ArrayList<ZhuanTi>();
		ZhuanTi subject = null;
		while (cursor.moveToNext()) {
			subject = new ZhuanTi();
			subject.setSubject_id(cursor.getString(cursor
					.getColumnIndex(subjectNews.SUB_ID)));
			subject.setSubject_title(cursor.getString(cursor
					.getColumnIndex(subjectNews.SUB_TITLE)));
			subject.setSubject_detail(cursor.getString(cursor
					.getColumnIndex(subjectNews.SUB_DETAIL)));
			subject.setSubject_date(cursor.getString(cursor
					.getColumnIndex(subjectNews.SUB_DATE)));
			subject.setSubject_url(cursor.getString(cursor
					.getColumnIndex(subjectNews.SUB_URL)));
			subjects.add(subject);
		}
		cursor.close();
		database.close();
		return subjects;
	}

}
