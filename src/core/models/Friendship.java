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

	@Override
	public String toString() {
		return "id = " + id + " initiator = " + initiator.getUsername()
				+ " participant = " + participant.getUsername() + " status = "
				+ status;

	}
}
