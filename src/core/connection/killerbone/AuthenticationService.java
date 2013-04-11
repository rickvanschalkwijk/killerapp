package core.connection.killerbone;

import java.io.IOException;
import java.io.StringReader;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import android.util.Log;
import com.app.amsterguide.LoginActivity;
import util.KillerboneUtils;
import core.connection.DataException;
import core.connection.https.HttpsConnector;
import core.connection.https.HttpsRequest;
import core.connection.https.HttpsRequestType;

public class AuthenticationService 
{
	final private String DEBUG_TAG = "AuthenticationService";
	
	public class AuthToken
	{
		public String userId;
		public String token;
		
		public AuthToken(String userId, String token)
		{
			this.userId = userId;
			this.token = token;
		}
	}
	
	public AuthToken authenticateWithCredentials(String email, String password)
	{
		HttpsRequestType requestType = HttpsRequestType.POST;
		String url = KillerboneUtils.postAuthTokenRequestUrl();
		String body = KillerboneUtils.composeAuthTokenRequestXml(email, password);
		
		HttpsRequest authenticateRequest = new HttpsRequest(requestType, url, body);
		authenticateRequest.setHeader("Content-Type", "text/xml");		
		HttpsConnector httpsConnector = new HttpsConnector(LoginActivity.getContext());
		
		try {
			String response = httpsConnector.performHttpsRequest(authenticateRequest);
			Log.d(DEBUG_TAG, response);
			
			// Convert response to xml document
			SAXBuilder builder = new SAXBuilder();
			Document xmlDocument = builder.build(new StringReader(response));
			Element rootNode = xmlDocument.getRootElement();
			
			// Compose AuthToken
			String userId = rootNode.getAttribute("userId").getValue();
			String token = rootNode.getText().trim();
			AuthToken authToken = new AuthToken(userId, token);
			
			return authToken;
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
