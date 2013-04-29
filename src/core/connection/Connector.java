package core.connection;

public interface Connector {
	
	String performGetRequest(String url) throws DataException;
	
	String performPostRequest(String url, String postBody) throws DataException;

}

