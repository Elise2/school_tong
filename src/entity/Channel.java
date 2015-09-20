package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Channel implements Serializable {
	private String cId;
	private String cTitle;
	private String cPId;
	private List<Channel> children;

	public Channel() {
		super();
		this.children = new ArrayList<Channel>();
	}

	public List<Channel> getChildren() {
		return children;
	}

	public String getcId() {
		return cId;
	}

	public void setcId(String cId) {
		this.cId = cId;
	}

	public String getcTitle() {
		return cTitle;
	}

	public void setcTitle(String cTitle) {
		this.cTitle = cTitle;
	}

	public String getcPId() {
		return cPId;
	}

	public void setcPId(String cPId) {
		this.cPId = cPId;
	}

	public Channel(String cId, String cTitle, String cPId) {
		super();
		this.cId = cId;
		this.cTitle = cTitle;
		this.cPId = cPId;
		this.children = new ArrayList<Channel>();
	}

}
