package core.connection;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import android.content.Context;
import android.util.Log;

import com.app.amsterguide.FriendActivity;
import com.app.amsterguide.FriendDetailActivity;
import com.app.killerapp.FriendShipRequestsActivity;

import util.KillerboneUtils;
import core.connection.https.HttpsConnector;
import core.connection.https.HttpsRequest;
import core.connection.https.HttpsRequestType;
import core.map.MapActivity;
import core.models.Friendship;
import core.models.User;

public class RESTSocialService {
	final private String DEBUG_TAG = "SocialRest";

	public List<Friendship> RetrieveFriendships(long userId, String authToken,
			String friendStatus, Context context) {
		HttpsRequestType requestType = HttpsRequestType.GET;
		String url = KillerboneUtils.getFriendships(userId);
		HttpsRequest authenticateRequest = new HttpsRequest(requestType, url,
				"");
		authenticateRequest.setHeader("Content-Type", "text/xml");
		authenticateRequest.setHeader("AuthToken", authToken);

		HttpsConnector httpsConnector = new HttpsConnector(context);

		try {
			String response = httpsConnector
					.performHttpsRequest(authenticateRequest);
			Log.d(DEBUG_TAG, response);

			// Convert response to xml document
			SAXBuilder builder = new SAXBuilder();
			Document xmlDocument = builder.build(new StringReader(response));
			Element rootNode = xmlDocument.getRootElement();

			List<Friendship> friendships = new ArrayList<Friendship>();
			// Parse users

			List<Element> listFriend = rootNode.getChildren("friendship");

			for (int i = 0; i < listFriend.size(); i++) {

				Element node = (Element) listFriend.get(i);

				String status = node.getChildText("status");
				Friendship friendship = new Friendship();
				friendship.setStatus(status);
				friendship.setId(Long.valueOf(
						node.getAttribute("id").getValue()).longValue());

				if (status.trim().equals(friendStatus)) {
					List<Element> initiatorRow = node.getChildren("initiator");
					List<Element> participantRow = node
							.getChildren("participant");

					for (int j = 0; j < initiatorRow.size(); j++) {
						Element column = (Element) initiatorRow.get(j);
						String name = column.getChildText("name");
						long id = Long.valueOf(
								column.getAttribute("id").getValue())
								.longValue();
						User user = new User(id, name);
						try{
						Element locationColumn = column.getChild("location");
						double latitude = Double.valueOf(locationColumn
								.getChildText("latitude"));
						double longtitude = Double.valueOf(locationColumn
								.getChildText("longitude"));
						user.setLatitude(latitude);
						user.setLongtitude(longtitude);
						Log.d("initiator user lat", latitude + "");
						Log.d("initiator user long", longtitude + "");
						 }catch(Exception e){
						 Log.d("exception", e.toString());
						 }
						friendship.setInitiator(user);

					}
					for (int j = 0; j < participantRow.size(); j++) {
						Element column = (Element) participantRow.get(j);
						String name = column.getChildText("name");
						long id = Long.valueOf(
								column.getAttribute("id").getValue())
								.longValue();

						User user = new User(id, name);
						try {
							double latitude = Double.valueOf(column.getChild(
									"location").getChildText("latitude"));
							double longtitude = Double.valueOf(column.getChild(
									"location").getChildText("longitude"));
							user.setLatitude(latitude);
							user.setLongtitude(longtitude);
							Log.d("participant user lat", latitude + "");
							Log.d("participant user long", longtitude + "");
						} catch (Exception e) {
							Log.d("exception", e.toString());
						}

						friendship.setParticipant(user);
					}

					friendships.add(friendship);
				}
			}
			return friendships;
		} catch (DataException e) {
			Log.e(DEBUG_TAG, e.getMessage());
		} catch (JDOMException e) {
			Log.e(DEBUG_TAG, e.getMessage());
		} catch (IOException e) {
			Log.e(DEBUG_TAG, e.getMessage());
		} catch (Exception e) {
			Log.e(DEBUG_TAG, e.toString());
		}

		return null;
	}

	public boolean AddFriendship(long userId, String authToken,
			String friendEmail) {

		String url = KillerboneUtils.postFrienshipCreateUrl();
		String body = KillerboneUtils.composeFriendshipRequestXml(friendEmail,
				userId);
		HttpsRequestType type = HttpsRequestType.POST;
		HttpsRequest authenticateRequest = new HttpsRequest(type, url, body);

		authenticateRequest.setHeader("Content-Type", "text/xml");
		authenticateRequest.setHeader("AuthToken", authToken);

		HttpsConnector httpsConnector = new HttpsConnector(
				FriendActivity.getContext());

		Log.d("BODY", authenticateRequest.getBody());

		try {

			String response = httpsConnector
					.performHttpsRequest(authenticateRequest);

			Log.d("Response: ", response);

			return true;
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public void ApproveFriendship(long userId, String authToken,
			long friendshipID) {
		String url = KillerboneUtils.putFriendshipAcceptRequestUrl(
				friendshipID, userId);

		HttpsRequestType type = HttpsRequestType.PUT;
		HttpsRequest authenticateRequest = new HttpsRequest(type, url, "");

		// authenticateRequest.setHeader("Content-Type", "text/xml");
		authenticateRequest.setHeader("AuthToken", authToken);

		HttpsConnector httpsConnector = new HttpsConnector(
				FriendShipRequestsActivity.getContext());
		// 3 /1
		Log.d("URL", url);

		try {

			String response = httpsConnector
					.performHttpsRequest(authenticateRequest);

			Log.d("Response: ", response);

		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void DeclineFriendship(long userId, String authToken,
			long friendshipID) {
		String url = KillerboneUtils.putFriendshipDeclineRequestUrl(
				friendshipID, userId);

		HttpsRequestType type = HttpsRequestType.PUT;
		HttpsRequest authenticateRequest = new HttpsRequest(type, url, "");

		// authenticateRequest.setHeader("Content-Type", "text/xml");
		authenticateRequest.setHeader("AuthToken", authToken);

		HttpsConnector httpsConnector = new HttpsConnector(
				FriendShipRequestsActivity.getContext());
		// 3 /1
		Log.d("URL", url);

		try {

			String response = httpsConnector
					.performHttpsRequest(authenticateRequest);

			Log.d("Response: ", response);

		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void DeleteFriendship(long userId, String authToken,
			long friendshipId) {
		String url = KillerboneUtils.deleteFriendshipEndRequestUrl(
				friendshipId, userId);

		HttpsRequestType type = HttpsRequestType.DELETE;
		HttpsRequest authenticateRequest = new HttpsRequest(type, url, "");

		authenticateRequest.setHeader("AuthToken", authToken);
		Log.d("authtoken delete", authToken);
		HttpsConnector httpsConnector = new HttpsConnector(
				FriendDetailActivity.getContext());
		Log.d("URL", url);

		try {

			String response = httpsConnector
					.performHttpsRequest(authenticateRequest);

			Log.d("Response deletefriend ", response);

		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setFriendshipCoordinates(long userId, Friendship friendship,
			String authToken, double latitude, double longtitude,
			Context context) {
		String url = KillerboneUtils.putFriendshipUpdateLocationRequestUrl(
				friendship.getId(), userId);

		HttpsRequestType type = HttpsRequestType.PUT;
		String body = KillerboneUtils.composeFriendshipLocationUpdateXml(
				latitude, longtitude);

		Log.d("setFriendshipCoordinates body", body);
		HttpsRequest authenticateRequest = new HttpsRequest(type, url, body);

		authenticateRequest.setHeader("Content-Type", "text/xml");
		authenticateRequest.setHeader("AuthToken", authToken);

		HttpsConnector httpsConnector = new HttpsConnector(context);

		Log.d("setFriendshipCoordinates URL", url);

		try {

			String response = httpsConnector
					.performHttpsRequest(authenticateRequest);

			Log.d("Response: ", response);

		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
