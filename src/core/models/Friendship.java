package core.models;

import java.io.Serializable;

import android.os.Debug;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

@SuppressWarnings("serial")
public class Friendship implements Serializable {
	public static final String EXTRA = "com.models.FRIENDSHIP_EXTRA";
	private long id;
	private User initiator;
	private User participant;
	private String status;

	public Friendship(long id, User initiator, User participant, String status) {
		this.id = id;
		this.initiator = initiator;
		this.participant = participant;
		this.status = status;
	}

	
	public Friendship() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getInitiator() {
		return initiator;
	}

	public void setInitiator(User initiator) {
		this.initiator = initiator;
	}

	public User getParticipant() {
		return participant;
	}

	public void setParticipant(User participant) {
		this.participant = participant;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public User getOtherUser(long userId){
		Log.d( String.valueOf(this.getId()) + " id", String.valueOf(this.getId()) + " id");
		if (this.initiator.getId() == userId){
			return this.participant;
		}
		return this.initiator;
	}

	@Override
	public String toString() {
		return "id = " + id + " initiator = " + initiator.getUsername()
				+ " participant = " + participant.getUsername() + " status = "
				+ status;

	}
	/*
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(status);
	    dest.writeLong(id);
	    dest.writeParcelable(initiator, flags);
	    dest.writeParcelable(participant, flags);
	}
	
	// this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Friendship> CREATOR = new Parcelable.Creator<Friendship>() {
        public Friendship createFromParcel(Parcel in) {
            return new Friendship(in);
        }

        public Friendship[] newArray(int size) {
            return new Friendship[size];
        }
    };
    
 // example constructor that takes a Parcel and gives you an object populated with it's values
    private Friendship(Parcel in) {
    	id = in.readLong();
    	initiator = in.readParcelable(getClass().getClassLoader());
    	participant = in.readParcelable(getClass().getClassLoader());
    	status = in.readString();
    	
    }
*/
}
