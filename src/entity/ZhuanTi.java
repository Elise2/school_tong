package entity;

import java.io.Serializable;

public class ZhuanTi implements Serializable {
	private String subject_date;
	private String subject_detail;
	private String subject_id;
	private String subject_title;
	private String subject_url;

	public String getSubject_date() {
		return subject_date;
	}

	public void setSubject_date(String subject_date) {
		this.subject_date = subject_date;
	}

	public String getSubject_detail() {
		return subject_detail;
	}

	public void setSubject_detail(String subject_detail) {
		this.subject_detail = subject_detail;
	}

	public String getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(String subject_id) {
		this.subject_id = subject_id;
	}

	public String getSubject_title() {
		return subject_title;
	}

	public void setSubject_title(String subject_title) {
		this.subject_title = subject_title;
	}

	public String getSubject_url() {
		return subject_url;
	}

	public void setSubject_url(String subject_url) {
		this.subject_url = subject_url;
	}

	public ZhuanTi(String subject_date, String subject_detail,
			String subject_id, String subject_title) {
		super();
		this.subject_date = subject_date;
		this.subject_detail = subject_detail;
		this.subject_id = subject_id;
		this.subject_title = subject_title;
	}

	public ZhuanTi() {
		super();
		// TODO Auto-generated constructor stub
	}

}
