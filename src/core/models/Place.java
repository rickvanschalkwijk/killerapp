package core.models;

import java.io.Serializable;

import org.osmdroid.util.GeoPoint;

@SuppressWarnings("serial")
public class Place implements Serializable {

	/**
	 * 
	 */
	private int id;
	private String title;
	private String description;
	private String category;
	private GeoPoint location;
	private String imageUrl;

	public Place() {
	}

	public Place(String name, String description, GeoPoint location) {
		super();
		this.title = name;
		this.description = description;
		this.location = location;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return title;
	}

	public void setName(String name) {
		this.title = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public GeoPoint getLocation() {
		return location;
	}

	public void setLocation(GeoPoint location) {
		this.location = location;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return title;
	}

}
