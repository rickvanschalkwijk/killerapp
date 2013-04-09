package core.databasehandlers;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.osmdroid.util.GeoPoint;

import util.KillerboneUtils;

import com.app.killerapp.R;

import android.content.Context;
import android.util.Log;

import core.models.Event;

public class XMLParser {

	private GeoPoint location;

	private static final String LOGTAG = "IP13HVA";

	private static final String EVENT_TAG = "event";
	private static final String TITLE_TAG = "title";
	private static final String DESCRIPTION_TAG = "description";
	private static final String CATEGORY_TAG = "category";
	private static final String LOCATION_TAG = "location";
	private static final String LOCATION_LATITUDE = "latitude";
	private static final String LOCATION_LONGITUDE = "longitude";
	private static final String DURATION_TAG = "duration";
	private static final String DURATION_START = "start";
	private static final String DURATION_END = "end";
	private static final String FREE_TAG = "free";
	private static final String PRICE_TAG = "price";

	public List<Event> getEventsXML(Context context) {
		InputStream stream = context.getResources().openRawResource(
				R.raw.dummyevents);
		SAXBuilder builder = new SAXBuilder();
		List<Event> events = new ArrayList<Event>();

		try {
			Document document = builder.build(stream);
			Element rootnode = document.getRootElement();
			List<Element> list = rootnode.getChildren(EVENT_TAG);

			for (Element node : list) {
				Event event = new Event();
				event.setTitle(node.getChildText(TITLE_TAG));
				event.setDescription(node.getChildText(DESCRIPTION_TAG));
				event.setCategory(node.getChildText(CATEGORY_TAG));
				Double latitude = (Double.parseDouble(node.getChild(
						LOCATION_TAG).getChildText(LOCATION_LATITUDE)));
				Double longitude = (Double.parseDouble(node.getChild(
						LOCATION_TAG).getChildText(LOCATION_LONGITUDE)));
				location = new GeoPoint(latitude, longitude);
				event.setLocation(location);
				
				DateTime dtStart = new DateTime(parseDateTime(node.getChild(DURATION_TAG)
						.getChildText(DURATION_START)));
				event.setStartDate(dtStart);
				DateTime dtEnd = new DateTime(parseDateTime(node.getChild(DURATION_TAG)
						.getChildText(DURATION_END)));
				event.setEndDate(dtEnd);
				event.setFree(Boolean.parseBoolean(node.getChildText(FREE_TAG)));
				BigDecimal price = new BigDecimal(node.getChildText(PRICE_TAG));
				event.setPrice(price);
				events.add(event);
				Log.i(LOGTAG, "Item aan events table toegevoegd");
			}
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return events;

	}
	
	private static DateTime parseDateTime(String input){
		Log.i(LOGTAG, input);
	     String pattern = KillerboneUtils.KILLERBONE_DATE_FORMAT;
	     DateTime dateTime  = DateTime.parse(input, DateTimeFormat.forPattern(pattern));
	     return dateTime;
	}
	
	

}
