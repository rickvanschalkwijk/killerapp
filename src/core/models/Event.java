package core.models;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.osmdroid.util.GeoPoint;

import android.text.Html;

import util.KillerboneUtils;

@SuppressWarnings("serial")
public class Event implements Serializable{
	public static final String EXTRA = "com.models.EVENT_EXTRA";
	private int id;
	private String title;
	private String description;
	private String category;
	private DateTime startDate;
	private DateTime endDate;
	private GeoPoint location;
	private String price;
	private boolean isFree;

	public Event() {
	}

	public Event(String title, String description, String category,
			DateTime startDate, DateTime endDate, GeoPoint location,
			String price, boolean isFree) {
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public boolean isFree() {
		return isFree;
	}

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}
	
	@Override
	public String toString() {
		return Html.fromHtml(title) + "\n" + "Start: " + startDate.toString(KillerboneUtils.KILLERBONE_DATE_FORMAT) + "\n" + "End: " + endDate.toString(KillerboneUtils.KILLERBONE_DATE_FORMAT);
	}

}
