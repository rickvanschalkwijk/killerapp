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

import com.app.killerapp.FriendActivity;

import util.KillerboneUtils;
import core.connection.https.HttpsConnector;
import core.connection.https.HttpsRequest;
import core.connection.https.HttpsRequestType;
import core.connection.killerbone.AuthenticationService.AuthToken;
import core.models.User;


public class RESTSocialService {
	final private String DEBUG_TAG = "SocialRest";
	
	public List<User> RetrieveFriendships(long userId, String authToken){
		HttpsRequestType requestType = HttpsRequestType.GET;
		String url = KillerboneUtils.getFriendships(userId);
		Log.d("friend authtoken", authToken);
		Log.d("friend id", String.valueOf(userId));
		Log.d("url", url);
		HttpsRequest authenticateRequest = new HttpsRequest(requestType, url, "");
		authenticateRequest.setHeader("Content-Type", "text/xml");
		authenticateRequest.setHeader("AuthToken", authToken);		
		
		HttpsConnector httpsConnector = new HttpsConnector(FriendActivity.getContext());
		
		try {
			String response = httpsConnector.performHttpsRequest(authenticateRequest);
			Log.d(DEBUG_TAG, response);
			
			// Convert response to xml document
			SAXBuilder builder = new SAXBuilder();
			Document xmlDocument = builder.build(new StringReader(response));
			Element rootNode = xmlDocument.getRootElement();
			
			List<User> users = new ArrayList<User>();
			Log.d("Hier", "Hier");
			// Parse users
			
			List listFriend = rootNode.getChildren("friendship");
			
			for (int i = 0; i < listFriend.size(); i++) {
			   long id = 0;
			   String name = "";
			   
			   Element node = (Element) listFriend.get(i);
			   Log.d("listfriend", node.getText());
			   String status = node.getChildText("status");
			   Log.d("statusding", status);
			   List listParticipant = node.getChildren("initiator");
               for (int j = 0; j < listParticipant.size(); j++) {
                   Element column = (Element) listParticipant.get(j);

    			   name = column.getChildText("name");
    			   id =  Long.valueOf(column.getAttribute("id").getValue()).longValue();
    			   
    			   
               }
			   
			   
			   User user = new User(id, status, name);
			   users.add(user);
	 
			}
			
			return users;
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
	
}
