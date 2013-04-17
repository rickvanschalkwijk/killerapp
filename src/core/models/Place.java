package core.models;

import java.io.Serializable;

import org.osmdroid.util.GeoPoint;

import util.KillerboneUtils;

@SuppressWarnings("serial")
public class Place implements Serializable {

	/**
	 * 
	 */
	private String name;
	private String description;
	private GeoPoint location;

	public Place() {
	}

	public Place(String name, String description, GeoPoint location) {
		super();
		this.name = name;
		this.description = description;
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public GeoPoint getLocation() {
		return location;
	}

	public void setLocation(GeoPoint location) {
		this.location = location;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
