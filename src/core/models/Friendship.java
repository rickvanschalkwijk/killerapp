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
		if (this.initiator.getId() == userId){
			return participant;
		}
		return initiator;
	}

	@Override
	public String toString() {
		return "id = " + id + " initiator = " + initiator.getUsername()
				+ " participant = " + participant.getUsername() + " status = "
				+ status;
	}
}
