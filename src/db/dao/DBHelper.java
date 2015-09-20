package db.dao;

import db.utils.SysConstant;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "student.db";
	public static final int DB_VERSION = 1;

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public DBHelper(Context context) {
		// TODO Auto-generated constructor stub
		this(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(SysConstant.userTable.getCreateSql());
		db.execSQL(SysConstant.toDayNews.getCreateSql());
		db.execSQL(SysConstant.subjectNews.getCreateSql());
		db.execSQL(SysConstant.Newtable.getCreateSql());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		onCreate(db);
	}

}
