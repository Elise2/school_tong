package db.utils;

public class SysConstant {
	// 用户常量
	public static final class userTable {
		public static final String TABLE_NAME = "userInfo";
		public static final String COL_ID = "CLASS_NO";
		public static final String CLISS_NAME = "CLISS_NAME";
		public static final String STU_NAME = "STU_NAME";
		public static final String UNO = "UNO";
		public static final String PWD = "PWD";
		public static final String ROLE = "ROLE";
		public static final String RIGHT = "RIGHT";
		public static final String MAJOR_NO = "MAJOR_NO";
		public static final String MAJOR_NAME = "MAJOR_NAME";

		public static String getCreateSql() {
			StringBuilder sb = new StringBuilder();
			sb.append("create table if not exists ");
			sb.append(TABLE_NAME);
			sb.append("(");
			// 定义列名
			sb.append(UNO);
			sb.append(" varchar(20) primary key ,");
			sb.append(STU_NAME);
			sb.append(" varchar(20) ,");
			sb.append(CLISS_NAME);
			sb.append(" varchar(30) ,");
			sb.append(COL_ID);
			sb.append(" varchar(20) ,");
			sb.append(PWD);
			sb.append(" varchar(30) ,");
			sb.append(ROLE);
			sb.append(" varchar(30) ,");
			sb.append(MAJOR_NAME);
			sb.append(" varchar(30) ,");
			sb.append(MAJOR_NO);
			sb.append(" varchar(30) ,");
			sb.append(RIGHT);
			sb.append(" varchar(30)");
			sb.append(")");
			return sb.toString();
		}
	}

	public static final class toDayNews {
		public static final String TABLE_NAME = "todayNews";
		public static final String NEWS_ID = "id";
		public static final String NEWS_TITLE = "title";
		public static final String NEWS_CONTENT = "content";
		public static final String NEWS_XLS = "xls";
		public static final String NEWS_COMMENT = "comment";
		public static final String NEWS_COMMENTCOUNT = "commentcount";
		public static final String NEWS_AUTHOR = "author";
		public static final String NEWS_CHANNALTYPE = "channelType";
		public static final String NEWS_ADVER = "advertisement";
		public static final String NEWS_FAVOR = "favor";
		public static final String NEWS_FAVORTAG = "favorTag";
		public static final String NEWS_IMG = "img";
		public static final String NEWS_MAINdATE = "mainDate";
		public static final String NEWS_MAIINPAGE = "mainpagetag";
		public static final String NEWS_MARK = "mark";
		public static final String NEWS_MAXDATE = "maxDate";
		public static final String NEWS_PAGETAG = "pagetag";
		public static final String NEWS_PAGETAGFLAG = "pagetagflag";
		public static final String NEWS_SHOPAD = "shopAddress";
		public static final String NEWS_SHOPNAME = "shopName";
		public static final String NEWS_TIME = "time";
		public static final String NEWS_TAG = "tag";

		public static String getCreateSql() {
			StringBuilder sb = new StringBuilder();
			sb.append("create table if not exists ");
			sb.append(TABLE_NAME);
			sb.append("(");
			// 定义列名
			sb.append(NEWS_ID);
			sb.append(" integer primary key autoIncrement ,");
			sb.append(NEWS_TITLE);
			sb.append(" varchar(100) ,");
			sb.append(NEWS_ADVER);
			sb.append(" varchar(30) ,");
			sb.append(NEWS_AUTHOR);
			sb.append(" varchar(20) ,");
			sb.append(NEWS_CHANNALTYPE);
			sb.append(" varchar(10) ,");
			sb.append(NEWS_COMMENT);
			sb.append(" varchar(200) ,");
			sb.append(NEWS_CONTENT);
			sb.append(" varchar(200) ,");
			sb.append(NEWS_COMMENTCOUNT);
			sb.append(" integer ,");
			sb.append(NEWS_FAVOR);
			sb.append(" integer ,");
			sb.append(NEWS_FAVORTAG);
			sb.append(" integer ,");
			sb.append(NEWS_IMG);
			sb.append(" varchar(40) ,");
			sb.append(NEWS_MAXDATE);
			sb.append(" varchar(20) ,");
			sb.append(NEWS_MAIINPAGE);
			sb.append(" varchar(20) ,");
			sb.append(NEWS_MARK);
			sb.append(" integer ,");
			sb.append(NEWS_MAINdATE);
			sb.append(" varchar(20) ,");
			sb.append(NEWS_PAGETAG);
			sb.append(" integer ,");
			sb.append(NEWS_PAGETAGFLAG);
			sb.append(" integer ,");
			sb.append(NEWS_SHOPAD);
			sb.append(" varchar(20) ,");
			sb.append(NEWS_SHOPNAME);
			sb.append(" varchar(20) ,");
			sb.append(NEWS_TAG);
			sb.append(" integer ,");
			sb.append(NEWS_TIME);
			sb.append(" varchar(20) ,");
			sb.append(NEWS_XLS);
			sb.append(" varchar(10)");
			sb.append(" )");
			return sb.toString();
		}
	}

	public static final class subjectNews {
		public static final String TABLE_NAME = "subject";
		public static final String SUB_DATE = "subject_date";
		public static final String SUB_DETAIL = "subject_detail";
		public static final String SUB_ID = "subject_ID";
		public static final String SUB_TITLE = "subject_TITLE";
		public static final String SUB_URL = "subject_URL";

		public static String getCreateSql() {
			StringBuilder sb = new StringBuilder();
			sb.append(" create table if not exists ");
			sb.append(TABLE_NAME);
			sb.append("(");
			sb.append(SUB_ID);
			sb.append(" varchar(20) primary key ,");
			sb.append(SUB_TITLE);
			sb.append(" varchar(50) ,");
			sb.append(SUB_DETAIL);
			sb.append(" varchar(60) ,");
			sb.append(SUB_DATE);
			sb.append(" varchar(20) ,");
			sb.append(SUB_URL);
			sb.append(" varchar(30)");
			sb.append(" )");
			return sb.toString();
		}
	}

	public static final class Newtable {
		public static final String TABLE_NAME = "collection";
		public static final String UNO = "uno";
		public static final String NEWS_ID = "id";
		public static final String NEWS_TITLE = "title";
		public static final String NEWS_CONTENT = "content";
		public static final String NEWS_XLS = "xls";
		public static final String NEWS_COMMENT = "comment";
		public static final String NEWS_COMMENTCOUNT = "commentcount";
		public static final String NEWS_AUTHOR = "author";
		public static final String NEWS_CHANNALTYPE = "channelType";
		public static final String NEWS_ADVER = "advertisement";
		public static final String NEWS_FAVOR = "favor";
		public static final String NEWS_FAVORTAG = "favorTag";
		public static final String NEWS_IMG = "img";
		public static final String NEWS_MAINdATE = "mainDate";
		public static final String NEWS_MAIINPAGE = "mainpagetag";
		public static final String NEWS_MARK = "mark";
		public static final String NEWS_MAXDATE = "maxDate";
		public static final String NEWS_PAGETAG = "pagetag";
		public static final String NEWS_PAGETAGFLAG = "pagetagflag";
		public static final String NEWS_SHOPAD = "shopAddress";
		public static final String NEWS_SHOPNAME = "shopName";
		public static final String NEWS_TIME = "time";
		public static final String NEWS_TAG = "tag";

		public static String getCreateSql() {
			StringBuilder sb = new StringBuilder();
			sb.append("create table if not exists ");
			sb.append(TABLE_NAME);
			sb.append("(");
			// 定义列名
			sb.append(UNO + " varchar(10) not null , ");
			sb.append(NEWS_ID);
			sb.append(" integer not null ,");
			sb.append(NEWS_TITLE);
			sb.append(" varchar(100) ,");
			sb.append(NEWS_ADVER);
			sb.append(" varchar(30) ,");
			sb.append(NEWS_AUTHOR);
			sb.append(" varchar(20) ,");
			sb.append(NEWS_CHANNALTYPE);
			sb.append(" varchar(10) ,");
			sb.append(NEWS_COMMENT);
			sb.append(" varchar(200) ,");
			sb.append(NEWS_CONTENT);
			sb.append(" varchar(200) ,");
			sb.append(NEWS_COMMENTCOUNT);
			sb.append(" integer ,");
			sb.append(NEWS_FAVOR);
			sb.append(" integer ,");
			sb.append(NEWS_FAVORTAG);
			sb.append(" integer ,");
			sb.append(NEWS_IMG);
			sb.append(" varchar(40) ,");
			sb.append(NEWS_MAXDATE);
			sb.append(" varchar(20) ,");
			sb.append(NEWS_MAIINPAGE);
			sb.append(" varchar(20) ,");
			sb.append(NEWS_MARK);
			sb.append(" integer ,");
			sb.append(NEWS_MAINdATE);
			sb.append(" varchar(20) ,");
			sb.append(NEWS_PAGETAG);
			sb.append(" integer ,");
			sb.append(NEWS_PAGETAGFLAG);
			sb.append(" integer ,");
			sb.append(NEWS_SHOPAD);
			sb.append(" varchar(20) ,");
			sb.append(NEWS_SHOPNAME);
			sb.append(" varchar(20) ,");
			sb.append(NEWS_TAG);
			sb.append(" integer ,");
			sb.append(NEWS_TIME);
			sb.append(" varchar(20) ,");
			sb.append(NEWS_XLS);
			sb.append(" varchar(10) ,");
			sb.append(" primary key(");
			sb.append(UNO + ",");
			sb.append(NEWS_ID);
			sb.append(")");
			sb.append(")");
			return sb.toString();
		}
	}
}
