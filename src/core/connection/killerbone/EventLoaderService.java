package core.connection.killerbone;

import util.KillerboneUtils;
import android.content.Context;
import android.util.Log;


import core.connection.DataException;
import core.connection.https.HttpsConnector;
import core.connection.https.HttpsRequest;
import core.connection.https.HttpsRequestType;

public class EventLoaderService 
{
	final private String DEBUG_TAG = "EventLoaderService";
	
	public String getAllEventsXml(Context context) throws DataException
	{
		HttpsRequestType requestType = HttpsRequestType.GET;
		String url = KillerboneUtils.getAllEventsUrl();
		
		HttpsRequest allEventsRequest = new HttpsRequest(requestType, url, "");
		HttpsConnector httpsConnector = new HttpsConnector(context);
		
		try {
			String response = httpsConnector.performHttpsRequest(allEventsRequest);
			
			return response;
		} catch (Exception e) {
			Log.e(DEBUG_TAG, e.toString());
		}
		return null;
	}
	
	public String getNewEventsXml(Context context, long timestamp)
	{
		HttpsRequestType requestType = HttpsRequestType.GET;
		String url = KillerboneUtils.getNewEventsUrl(timestamp);
		
		HttpsRequest newEventsRequest = new HttpsRequest(requestType, url, "");
		HttpsConnector httpsConnector = new HttpsConnector(context);
		
		try {
			String response = httpsConnector.performHttpsRequest(newEventsRequest);
			return response;
		} catch (DataException e) {
			Log.e(DEBUG_TAG, e.getMessage());
		} catch (Exception e) {
			Log.e(DEBUG_TAG, e.toString());
		}
		return null;
	}
}
