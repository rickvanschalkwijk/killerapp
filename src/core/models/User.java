package core.models;

import java.io.Serializable;


import android.os.Parcel;
import android.os.Parcelable;
@SuppressWarnings("serial")
public class User implements Serializable{
	public static final String EXTRA = "com.models.USER_EXTRA";
	private long id;
	private String email;
	private String username;
	private String password;
	
	
	
	public User(long id, String email, String username, String password) {
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	public User(long id, String email, String username) {
		this.id = id;
		this.email = email;
		this.username = username;
	}
	
	public User(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
	}
	public User(long id, String username) {
		this.id = id;
		this.username = username;
	}
	
	
	
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/*@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeLong(id);
		dest.writeString(email);
		dest.writeString(username);
		dest.writeString(password);
	}
	
	public static final Parcelable.Creator<User> CREATOR
    = new Parcelable.Creator<User>() {
	public User createFromParcel(Parcel in) {
	    return new User(in);
	}
	
	public User[] newArray(int size) {
	    return new User[size];
	}
	};
	
	private User(final Parcel in) {
		id = in.readLong();
		email = in.readString();
		username = in.readString();
		password = in.readString();
	}
	
	*/

}
