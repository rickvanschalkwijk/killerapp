package core.connection;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import core.models.Category;
import core.models.User;

public class RESTEventService {

	private final static String baseUrl = "https://killerbone.mooo.com/";
	private final static String URL_ALL_CATEGORIES = baseUrl + "development/allcategories";
	
	public List<Category> getAllCategories () throws DataException{
		Type categoryListType = new TypeToken<List<Category>>(){}.getType();
		Connector connector = ServiceFactory.getConnectorInstance();
		String response = connector.performGetRequest(URL_ALL_CATEGORIES);
		Gson gson = new Gson();
		return gson.fromJson(response, categoryListType);
	}
}
