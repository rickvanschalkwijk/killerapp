package core.models;

import java.util.Date;

import org.osmdroid.util.GeoPoint;

public class Event {
	private String title;
	private String description;
	private String category;
	private Date startDate;
	private Date endDate;
	private GeoPoint location;
	private float price;
	private boolean isFree;
	
	public Event(){
	}

	
	public Event(String title, String description, String category,
			Date startDate, Date endDate, GeoPoint location, float price,
			boolean isFree) {
		super();
		this.title = title;
		this.description = description;
		this.category = category;
		this.startDate = startDate;
		this.endDate = endDate;
		this.location = location;
		this.price = price;
		this.isFree = isFree;
	}

	
	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
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



	public Date getStartDate() {
		return startDate;
	}



	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}



	public Date getEndDate() {
		return endDate;
	}



	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}



	public GeoPoint getLocation() {
		return location;
	}



	public void setLocation(GeoPoint location) {
		this.location = location;
	}



	public float getPrice() {
		return price;
	}



	public void setPrice(float price) {
		this.price = price;
	}



	public boolean isFree() {
		return isFree;
	}



	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}
	
	
}
