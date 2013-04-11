package core.connection;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import android.util.Log;

import com.app.amsterguide.FriendActivity;

import util.KillerboneUtils;
import core.connection.https.HttpsConnector;
import core.connection.https.HttpsRequest;
import core.connection.https.HttpsRequestType;
import core.connection.killerbone.AuthenticationService.AuthToken;
import core.models.Friendship;
import core.models.User;

public class RESTSocialService {
	final private String DEBUG_TAG = "SocialRest";

	public List<Friendship> RetrieveFriendships(long userId, String authToken,
			String friendStatus) {
		HttpsRequestType requestType = HttpsRequestType.GET;
		String url = KillerboneUtils.getFriendships(userId);
		HttpsRequest authenticateRequest = new HttpsRequest(requestType, url,
				"");
		authenticateRequest.setHeader("Content-Type", "text/xml");
		authenticateRequest.setHeader("AuthToken", authToken);

		HttpsConnector httpsConnector = new HttpsConnector(
				FriendActivity.getContext());

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

			List listFriend = rootNode.getChildren("friendship");

			for (int i = 0; i < listFriend.size(); i++) {

				Element node = (Element) listFriend.get(i);

				String status = node.getChildText("status");
				Friendship friendship = new Friendship();
				friendship.setStatus(status);
				friendship.setId(Long.valueOf(
						node.getAttribute("id").getValue()).longValue());

				if (status.trim().equals(friendStatus)) {
					List initiatorRow = node.getChildren("initiator");

					List participantRow = node.getChildren("participant");
					for (int j = 0; j < initiatorRow.size(); j++) {
						Element column = (Element) initiatorRow.get(j);
						String name = column.getChildText("name");
						long id = Long.valueOf(
								column.getAttribute("id").getValue())
								.longValue();

						friendship.setInitiator(new User(id, name));
					}
					for (int j = 0; j < participantRow.size(); j++) {
						Element column = (Element) participantRow.get(j);
						String name = column.getChildText("name");
						long id = Long.valueOf(
								column.getAttribute("id").getValue())
								.longValue();

						friendship.setParticipant(new User(id, name));
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
}
