package core.connection.https;

import java.util.HashMap;
import java.util.Map;

public class HttpsRequest 
{
	private HttpsRequestType requestType;
	private String url;
	private String body;
	private Map<String, String> headers;
	
	public HttpsRequest(HttpsRequestType requestType, String url, String body)
	{
		this.requestType = requestType;
		this.url = url;
		this.body = body;
		
		headers = new HashMap<String, String>();
	}
	
	//-----------------------------------------------------------------------//
	
	public HttpsRequestType getRequestType()
	{
		return requestType;
	}
	
	public void setRequestType(HttpsRequestType requestType)
	{
		this.requestType = requestType;
	}
	
	public String getUrl()
	{
		return url;
	}
	
	public void setUrl(String url)
	{
		this.url = url;
	}
	
	public String getBody()
	{
		return body;
	}
	
	public void setBody(String body)
	{
		this.body = body;
	}
	
	public Map<String, String> getHeaders()
	{
		return headers;
	}
	
	public void setHeader(String name, String value)
	{
		headers.put(name, value);
	}
	
	public void setHeaders(Map<String, String> headers)
	{
		headers.putAll(headers);
	}
}
