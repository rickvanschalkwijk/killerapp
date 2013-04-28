package core.databasehandlers;

import core.connection.CheckConnection;
import core.connection.DataException;
import android.content.Context;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;

public class DatabaseLoaderThread implements Runnable {

	EventDataSource eventDataSource;
	PlaceDataSource placeDataSource;
	Context context;

	public DatabaseLoaderThread(Context context) {
		this.context = context;
	}

	@Override
	public void run() {
		eventDataSource = new EventDataSource(context);

		if (CheckConnection.isOnline(context)) {
			eventDataSource.open();
			if (eventDataSource.DatabaseHasRows()) {
				XMLParser parser = new XMLParser();
				try {
					parser.getEventsXML(context, true);
				} catch (DataException e) {
					e.printStackTrace();
				}
				eventDataSource.deleteExpiredEvents();
				eventDataSource.close();
			} else {
				XMLParser parser = new XMLParser();
				try {
					parser.getEventsXML(context, false);
				} catch (DataException e) {
					e.printStackTrace();
				}
				eventDataSource.close();
			}
			placeDataSource = new PlaceDataSource(context);
			placeDataSource.open();
			if (placeDataSource.DatabaseHasRows()) {
				XMLParser parser = new XMLParser();
				try {
					parser.getPlacesXML(context, true);
				} catch (DataException e) {
					e.printStackTrace();
				}
				placeDataSource.close();
			} else {
				XMLParser parser = new XMLParser();
				try {
					parser.getPlacesXML(context, false);
				} catch (DataException e) {
					e.printStackTrace();
				}
				placeDataSource.close();
			}
		} else {
			eventDataSource.open();
			eventDataSource.deleteExpiredEvents();
			eventDataSource.close();
		}
	}
}
