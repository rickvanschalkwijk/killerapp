package core.connection.killerbone;

import util.KillerboneUtils;
import android.content.Context;
import android.util.Log;
import core.connection.DataException;
import core.connection.https.HttpsConnector;
import core.connection.https.HttpsRequest;
import core.connection.https.HttpsRequestType;

public class PlaceLoaderService {
final private String DEBUG_TAG = "EventLoaderService";
	
	public String getAllLocationsXml(Context context) throws DataException
	{
		HttpsRequestType requestType = HttpsRequestType.GET;
		String url = KillerboneUtils.getAllLocationsUrl();
		
		HttpsRequest allLocationsRequest = new HttpsRequest(requestType, url, "");
		HttpsConnector httpsConnector = new HttpsConnector(context);
		
		try {
			String response = httpsConnector.performHttpsRequest(allLocationsRequest);
			
			return response;
		} catch (DataException e) {
			Log.e(DEBUG_TAG, e.getMessage());
		} catch (Exception e) {
			Log.e(DEBUG_TAG, e.toString());
		}
		return null;
	}
	
	public String getNewLocationsXml(Context context, long timestamp)
	{
		HttpsRequestType requestType = HttpsRequestType.GET;
		String url = KillerboneUtils.getNewLocationsUrl(timestamp);
		
		HttpsRequest newLocationsRequest = new HttpsRequest(requestType, url, "");
		HttpsConnector httpsConnector = new HttpsConnector(context);
		
		try {
			String response = httpsConnector.performHttpsRequest(newLocationsRequest);
			return response;
		} catch (DataException e) {
			Log.e(DEBUG_TAG, e.getMessage());
		} catch (Exception e) {
			Log.e(DEBUG_TAG, e.toString());
		}
		return null;
	}

}
