package entity;

import java.io.Serializable;

public class Users implements Serializable {
	private String classNO;
	private String className;
	private String img;
	private int isRead;
	private String majorN0;
	private String majorName;
	private String pwd;
	private String role;
	private String smsright;
	private String stuName;
	private String uno;

	public String getClassNO() {
		return classNO;
	}

	public void setClassNO(String classNO) {
		this.classNO = classNO;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public String getMajorN0() {
		return majorN0;
	}

	public void setMajorN0(String majorN0) {
		this.majorN0 = majorN0;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSmsright() {
		return smsright;
	}

	public void setSmsright(String smsright) {
		this.smsright = smsright;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getUno() {
		return uno;
	}

	public void setUno(String uno) {
		this.uno = uno;
	}

	public Users(String classNO, String className, String img, int isRead,
			String majorN0, String majorName, String pwd, String role,
			String smsright, String stuName, String uno) {
		super();
		this.classNO = classNO;
		this.className = className;
		this.img = img;
		this.isRead = isRead;
		this.majorN0 = majorN0;
		this.majorName = majorName;
		this.pwd = pwd;
		this.role = role;
		this.smsright = smsright;
		this.stuName = stuName;
		this.uno = uno;
	}

	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Users(String className, String img, String majorName,
			String stuName, String uno) {
		super();
		this.className = className;
		this.img = img;
		this.majorName = majorName;
		this.stuName = stuName;
		this.uno = uno;
	}

}
