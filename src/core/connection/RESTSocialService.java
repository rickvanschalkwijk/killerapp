package core.connection;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import core.models.User;

public class RESTSocialService {

	private final static String baseUrl = "https://killerbone.mooo.com/";
	private final static String URL_ALL_FRIENDS = baseUrl + "development/allfriendships";
	private final static String URL_ALL_USERS = baseUrl + "development/allusers";

	public List<User> getAllUsers() throws DataException{
		Type userListType = new TypeToken<List<User>>(){}.getType();
		Connector connector = ServiceFactory.getConnectorInstance();
		String response = connector.performGetRequest(URL_ALL_USERS);
		Gson gson = new Gson();
		return gson.fromJson(response, userListType);
	}
}
