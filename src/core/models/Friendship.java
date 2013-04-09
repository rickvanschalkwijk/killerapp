package core.models;

public class Friendship {
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

	long getId() {
		return id;
	}

	void setId(long id) {
		this.id = id;
	}

	User getInitiator() {
		return initiator;
	}

	void setInitiator(User initiator) {
		this.initiator = initiator;
	}

	User getParticipant() {
		return participant;
	}

	void setParticipant(User participant) {
		this.participant = participant;
	}

	String getStatus() {
		return status;
	}

	void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "id = " + id + " initiator = " + initiator.getUsername()
				+ " participant = " + participant.getUsername() + " status = "
				+ status;

	}
}
