package core.models;

import java.io.Serializable;
import java.math.BigDecimal;

import org.joda.time.DateTime;
import org.osmdroid.util.GeoPoint;

@SuppressWarnings("serial")
public class Event implements Serializable{
	public static final String EXTRA = "com.models.EVENT_EXTRA";
	private String title;
	private String description;
	private String category;
	private DateTime startDate;
	private DateTime endDate;
	private GeoPoint location;
	private BigDecimal price;
	private boolean isFree;
	
	public Event(){
	}

	public Event(String title, String description, String category,
			DateTime startDate, DateTime endDate, GeoPoint location, BigDecimal price,
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



	public DateTime getStartDate() {
		return startDate;
	}



	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}



	public DateTime getEndDate() {
		return endDate;
	}



	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}



	public GeoPoint getLocation() {
		return location;
	}



	public void setLocation(GeoPoint location) {
		this.location = location;
	}



	public BigDecimal getPrice() {
		return price;
	}



	public void setPrice(BigDecimal price) {
		this.price = price;
	}



	public boolean isFree() {
		return isFree;
	}



	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}
	
	
}
