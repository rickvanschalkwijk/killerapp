package core.connection;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import util.KillerboneUtils;

import android.util.Log;

import com.app.killerapp.FriendActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import core.connection.https.HttpsConnector;
import core.connection.https.HttpsRequest;
import core.connection.https.HttpsRequestType;
import core.map.MapActivity;
import core.models.Category;
import core.models.User;

public class RESTEventService {

	private final static String baseUrl = "https://killerbone.mooo.com/";
	private final static String URL_ALL_CATEGORIES = baseUrl
			+ "development/allcategories";
	private final static HttpsRequestType requestType = HttpsRequestType.GET;
	public String response;
	private Document xmlDocument;
	private Element rootNode;
	private SAXBuilder builder;

	public List<Category> getAllCategories() {

		String url = KillerboneUtils.getAllEventCategories();
		HttpsRequest authenticateRequest = new HttpsRequest(requestType, url,
				"");
		authenticateRequest.setHeader("Content-Type", "text/xml");

		HttpsConnector httpsConnector = new HttpsConnector(
				MapActivity.getContext());

		try {
			response = httpsConnector.performHttpsRequest(authenticateRequest);
		} catch (DataException e1) {
			e1.printStackTrace();
		}

		builder = new SAXBuilder();

		try {
			xmlDocument = builder.build(new StringReader(response));
		} catch (JDOMException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		rootNode = xmlDocument.getRootElement();

		List<Category> categories = new ArrayList<Category>();

		return categories;
	}
}
