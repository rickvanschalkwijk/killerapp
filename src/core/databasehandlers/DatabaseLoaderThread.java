package core.databasehandlers;

import core.connection.DataException;
import android.content.Context;

public class DatabaseLoaderThread implements Runnable {

	EventDataSource eventDataSource;
	Context context;

	public DatabaseLoaderThread(Context context) {
		this.context = context;
	}

	@Override
	public void run() {
		eventDataSource = new EventDataSource(context);
		eventDataSource.open();
		if (eventDataSource.DatabaseHasRows()) {
			eventDataSource.close();
		} else {
			XMLParser parser = new XMLParser();
			try {
				parser.getEventsXML(context);
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			eventDataSource.close();
		}
	}
}
