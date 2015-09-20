package entity;

import java.util.List;

import activity.New_InforActivity;
import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {
	private String id;
	private String stuName;
	private int Type;
	private boolean isSelected = false;
	private List<Contact> nodes;// 存放的时当前索引的下一级内容

	public List<Contact> getNodes() {
		return nodes;
	}

	public void setNodes(List<Contact> nodes) {
		this.nodes = nodes;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

	public Contact(String id, String stuName) {
		super();
		this.id = id;
		this.stuName = stuName;
	}

	public Contact(String id, String stuName, int type) {
		super();
		this.id = id;
		this.stuName = stuName;
		Type = type;
	}

	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Contact(Parcel in) {

	}

	// 描述内容，默认为0
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	// 向实力块中写东西
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(id);
		dest.writeString(stuName);
		dest.writeInt(Type);
		if (isSelected) {
			dest.writeInt(1);

		} else {
			dest.writeInt(0);

		}
		// dest.writeTypedList(nodes);
	}

	public static final Creator<Contact> CREATOR = new Creator<Contact>() {
		// 从实例块中创建属性，属性的创建顺序要和上面的写入的顺序一致
		@Override
		public Contact createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			Contact contact = new Contact();
			contact.setId(source.readString());
			contact.setStuName(source.readString());
			contact.setType(source.readInt());

			if (source.readInt() == 1) {
				contact.setSelected(true);

			} else {
				contact.setSelected(false);

			}
			return contact;
		}

		@Override
		public Contact[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Contact[size];
		}

	};

}
