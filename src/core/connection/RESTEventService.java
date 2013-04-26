package core.connection;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import util.KillerboneUtils;
import android.util.Log;
import core.connection.https.HttpsConnector;
import core.connection.https.HttpsRequest;
import core.connection.https.HttpsRequestType;
import core.map.MapActivity;
import core.models.Category;

public class RESTEventService {
	public Category category;
	private final static HttpsRequestType requestType = HttpsRequestType.GET;
	public String response;
	private Document xmlDocument;
	private Element rootNode;
	private SAXBuilder builder;
	public List categoryList;
	List<Category> categories;
	public Element node;
	
	public List<Category> getAllCategories() throws DataException, IOException, JDOMException {

		String url = KillerboneUtils.getAllEventCategoriesUrl();
		HttpsRequest authenticateRequest = new HttpsRequest(requestType, url,
				"");
		authenticateRequest.setHeader("Content-Type", "text/xml");

		HttpsConnector httpsConnector = new HttpsConnector(
				MapActivity.getContext());

		try {
			response = httpsConnector.performHttpsRequest(authenticateRequest);
		} catch (DataException e1) {
			throw new DataException("Can't get https data from server"); 
		}

		builder = new SAXBuilder();

		try {
			xmlDocument = builder.build(new StringReader(response));
			rootNode = xmlDocument.getRootElement();
			categoryList = rootNode.getChildren("title");
		} catch (JDOMException e) {
			throw new JDOMException();
		} catch (IOException e) {
			throw new IOException();
		}
		
		for(int i = 0; i < categoryList.size(); i ++){
			node = (Element) categoryList.get(i);
		}
		
		categories = new ArrayList<Category>();

		return categories;
	}
}
