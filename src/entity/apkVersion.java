package entity;

import java.io.Serializable;

public class apkVersion implements Serializable {
	private String code;
	private String name;
	private String url;
	private int vid;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getVid() {
		return vid;
	}

	public void setVid(int vid) {
		this.vid = vid;
	}

	public apkVersion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public apkVersion(String code, String name, String url, int vid) {
		super();
		this.code = code;
		this.name = name;
		this.url = url;
		this.vid = vid;
	}

}
