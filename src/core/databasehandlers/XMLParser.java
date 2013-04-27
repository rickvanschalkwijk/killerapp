package core.databasehandlers;

import java.io.IOException;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.osmdroid.util.GeoPoint;

import util.KillerboneUtils;

import android.content.Context;
import android.content.SharedPreferences;

import android.util.Log;

import core.connection.DataException;
import core.connection.killerbone.EventLoaderService;
import core.connection.killerbone.PlaceLoaderService;
import core.models.Event;
import core.models.Place;

public class XMLParser {

	private GeoPoint location;
	
	private SharedPreferences sharedPreferences; 

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
	private static final String IMAGEURL_TAG = "imageUrl";
	

	public void getEventsXML(Context context, boolean update)
			throws DataException {
		SAXBuilder builder = new SAXBuilder();
		EventDataSource eventDataSource = new EventDataSource(context);
		try {
			
			sharedPreferences = context.getSharedPreferences("timestamp", 0);
			SharedPreferences.Editor editor= sharedPreferences.edit();
			
			EventLoaderService eventLoaderService = new EventLoaderService();
			Reader reader;
			if (update) {
				Long timestamp = sharedPreferences.getLong("timestamp", 0);
				reader = new StringReader(eventLoaderService.getNewEventsXml(
						context, timestamp));
				editor.putLong("timestamp", System.currentTimeMillis());
				editor.commit();
			} else {
				reader = new StringReader(
						eventLoaderService.getAllEventsXml(context));
				editor.putLong("timestamp", System.currentTimeMillis());
				editor.commit();
			}
			
			Document document = builder.build(reader);
			Element rootnode = document.getRootElement();
			List<Element> list = rootnode.getChildren(EVENT_TAG);

			for (Element node : list) {
				Event event = new Event();
				event.setId(Integer
						.parseInt(node.getAttribute("id").getValue()));
				event.setTitle(node.getChildText(TITLE_TAG));
				event.setDescription(node.getChildText(DESCRIPTION_TAG));
				event.setCategory(node.getChildText(CATEGORY_TAG));

				Double latitude = (Double.parseDouble(node
						.getChild(LOCATION_TAG).getChildText(LOCATION_LATITUDE)
						.trim()));
				Double longitude = (Double.parseDouble(node
						.getChild(LOCATION_TAG)
						.getChildText(LOCATION_LONGITUDE).trim()));
				location = new GeoPoint(latitude, longitude);
				event.setLocation(location);

				DateTime dtStart = new DateTime(parseDateTime(node
						.getChild(DURATION_TAG).getChildText(DURATION_START)
						.trim()));
				event.setStartDate(dtStart);
				DateTime dtEnd = new DateTime(parseDateTime(node
						.getChild(DURATION_TAG).getChildText(DURATION_END)
						.trim()));
				event.setEndDate(dtEnd);
				event.setFree(Boolean.parseBoolean(node.getChildText(FREE_TAG)));
				event.setPrice(node.getChildText(PRICE_TAG));
				eventDataSource.open();
				eventDataSource.addEvent(event);
				eventDataSource.close();
			}
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void getPlacesXML(Context context) throws DataException {
		SAXBuilder builder = new SAXBuilder();
		PlaceDataSource placeDataSource = new PlaceDataSource(context);
		try {
			PlaceLoaderService placeLoaderService = new PlaceLoaderService();
			Reader reader = new StringReader(
					placeLoaderService.getAllLocationsXml(context));
			Document document = builder.build(reader);
			Element rootnode = document.getRootElement();
			List<Element> list = rootnode.getChildren(LOCATION_TAG);

			for (Element node : list) {
				Place place = new Place();
				place.setId(Integer
						.parseInt(node.getAttribute("id").getValue()));
				place.setName(node.getChildText(TITLE_TAG));
				place.setDescription(node.getChildText(DESCRIPTION_TAG));
				Double latitude = (Double.parseDouble(node.getChildText(
						LOCATION_LATITUDE).trim()));
				Double longitude = (Double.parseDouble(node.getChildText(
						LOCATION_LONGITUDE).trim()));
				location = new GeoPoint(latitude, longitude);
				place.setLocation(location);
				place.setImageUrl(node.getChildText(IMAGEURL_TAG));
				placeDataSource.open();
				placeDataSource.addPlace(place);
				placeDataSource.close();
			}
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static DateTime parseDateTime(String input) {
		String pattern = KillerboneUtils.KILLERBONE_DATE_FORMAT;
		DateTime dateTime = DateTime.parse(input,
				DateTimeFormat.forPattern(pattern));
		return dateTime;
	}

}
