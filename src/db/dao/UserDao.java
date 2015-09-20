package db.dao;

import db.utils.SysConstant.userTable;
import entity.Users;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDao {
	private DBHelper dbHelper;
	private SQLiteDatabase database;

	public UserDao(Context mContext) {
		dbHelper = new DBHelper(mContext);
	}

	public void addUser(Users user) {
		database = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(userTable.UNO, user.getUno());
		values.put(userTable.STU_NAME, user.getStuName());
		values.put(userTable.PWD, user.getPwd());
		values.put(userTable.MAJOR_NAME, user.getMajorName());
		values.put(userTable.MAJOR_NO, user.getMajorN0());
		values.put(userTable.COL_ID, user.getClassNO());
		values.put(userTable.ROLE, user.getRole());
		values.put(userTable.RIGHT, user.getSmsright());
		values.put(userTable.CLISS_NAME, user.getClassName());
		database.insert(userTable.TABLE_NAME, null, values);
		// StringBuilder sb = new StringBuilder();
		// sb.append(" insert into " + userTable.TABLE_NAME);
		// sb.append(" (");
		// sb.append(userTable.UNO + ",");
		// sb.append(userTable.STU_NAME + ",");
		// sb.append(userTable.PWD);
		// sb.append(" ) values(?,?,?) ");
		// try {
		// database.execSQL(
		// sb.toString(),
		// new String[] { user.getUno() + user.getStuName()
		// + user.getPwd() });
		// } catch (Exception e) {
		// // TODO: handle exception
		// } finally {
		// database.close();
		// }
		database.close();
	}

	// 检测到当前用户是否存在
	public boolean checkUser(String uno) {
		boolean flag = false;
		database = this.dbHelper.getReadableDatabase();
		Cursor cursor = database.query(userTable.TABLE_NAME,
				new String[] { userTable.UNO }, userTable.UNO + " = ?",
				new String[] { uno }, null, null, null);

		if (cursor.moveToNext()) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	public void saveOrUpdateUser(Users user) {
		if (checkUser(user.getUno())) {
			// 更新,先删除再插入
			deleteUser(user);
		}
		addUser(user);
	}

	public void deleteUser(Users user) {
		database = dbHelper.getWritableDatabase();
		database.delete(userTable.TABLE_NAME, userTable.UNO + " = ?",
				new String[] { user.getUno() });
	}

	// public List<Users> findUser() {
	// database = dbHelper.getReadableDatabase();
	// StringBuilder sb = new StringBuilder();
	// sb.append(" select ");
	// sb.append(userTable.UNO + ",");
	// sb.append(userTable.STU_NAME + ",");
	// sb.append(userTable.PWD);
	// sb.append(" from ");
	// sb.append(userTable.TABLE_NAME);
	// Cursor cursor = database.rawQuery(sb.toString(), null);
	// List<Users> list = new ArrayList<Users>();
	// while (cursor.moveToNext()) {
	// Users user = new Users();
	// user.setUno(cursor.getString(cursor.getColumnIndex(userTable.UNO)));
	// user.setStuName(cursor.getString(cursor.getColumnIndex(user
	// .getStuName())));
	// user.setPwd(cursor.getString(cursor.getColumnIndex(userTable.PWD)));
	// list.add(user);
	// }
	// cursor.close();
	// database.close();
	// return list;
	//
	// }

	// 根据用户名来查找用户信息
	public Users findUserByUno(String uno) {
		database = this.dbHelper.getReadableDatabase();
		Cursor cursor = database.query(userTable.TABLE_NAME,
				new String[] { userTable.UNO }, userTable.UNO + " = ?",
				new String[] { uno }, null, null, null);
		Users user = null;
		if (cursor.moveToNext()) {
			user = new Users();
			user.setUno(cursor.getString(cursor.getColumnIndex(userTable.UNO)));
			user.setStuName(cursor.getString(cursor
					.getColumnIndex(userTable.STU_NAME)));
			user.setPwd(cursor.getString(cursor.getColumnIndex(userTable.PWD)));
		}
		cursor.close();
		database.close();
		return user;

	}

}
