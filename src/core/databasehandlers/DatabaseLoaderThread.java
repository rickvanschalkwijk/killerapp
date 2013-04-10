package core.databasehandlers;

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
		eventDataSource.clearTable();
		XMLParser parser = new XMLParser();		
		parser.getEventsXML(context);
		eventDataSource.close();
	}

}
