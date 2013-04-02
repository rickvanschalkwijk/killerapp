package core.event;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.osmdroid.util.GeoPoint;

import core.models.Event;

public class EventUtil {

	public static ArrayList<Event> getDummyData(){
		ArrayList<Event> events = new ArrayList<Event>();
		
		Event eventA = new Event( 
				"eventA", 
				"This is eventA", 
				"test", 
				new DateTime(2013, 4, 10, 12, 0, 0, 0), 
				new DateTime(2013, 4, 10, 12, 0, 0, 0),
				new GeoPoint( 52.376438, 4.893576 ),
				new BigDecimal(0.00),
				true);
		events.add(eventA);
		
		Event eventB = new Event( 
				"eventB", 
				"This is eventB", 
				"test", 
				new DateTime(2013, 4, 11, 12, 0, 0, 0), 
				new DateTime(2013, 4, 11, 12, 0, 0, 0),
				new GeoPoint( 52.373818, 4.894091 ),
				new BigDecimal(0.00),
				true);
		events.add(eventB);
		
		Event eventC = new Event( 
				"eventC", 
				"This is eventC", 
				"test", 
				new DateTime(2013, 4, 12, 12, 0, 0, 0), 
				new DateTime(2013, 4, 12, 12, 0, 0, 0),
				new GeoPoint( 52.364411, 4.904949 ),
				new BigDecimal(0.00),
				true);
		events.add(eventC);
		
		return events;
	}
}

