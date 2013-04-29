package core.connection.https;

import java.io.IOException;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;

import core.connection.DataException;

import android.content.Context;

public class HttpsConnector 
{
	private HttpClient httpsClient;
	
	public HttpsConnector (Context appContext)
	{
		httpsClient = new HttpsClient(appContext);
	}
	
	public String performHttpsRequest(HttpsRequest request) throws DataException
	{
		String response = null;
		switch (request.getRequestType())
		{
			case GET:
				response = performGetRequest(request);
				break;
			case POST:
				response = performPostRequest(request);
				break;
			case PUT:
				response = performPutRequest(request);
				break;
			case DELETE:
				response = performDeleteRequest(request);
				break;
		}
		return response;
	}
	
	private String performGetRequest(HttpsRequest request) throws DataException 
	{
		HttpGet getRequest = new HttpGet(request.getUrl());
		for (Entry<String, String> header : request.getHeaders().entrySet())
		{
			getRequest.addHeader(header.getKey(), header.getValue());
		}		

		try {
			ResponseHandler<String> handler = new BasicResponseHandler();

			String response = httpsClient.execute(getRequest, handler);
			return response;
		} catch (ClientProtocolException e) {
			throw defaultException(e);
		} catch (IOException e) {
			throw defaultException(e);
		}
	}

	private String performPostRequest(HttpsRequest request) throws DataException 
	{
		HttpPost postRequest = new HttpPost(request.getUrl());
		for (Entry<String, String> header : request.getHeaders().entrySet())
		{
			postRequest.addHeader(header.getKey(), header.getValue());
		}
		
		try {
			ResponseHandler<String> handler = new BasicResponseHandler();
			HttpEntity entity = new StringEntity(request.getBody());
			postRequest.setEntity(entity);
			
			String response = httpsClient.execute(postRequest, handler);
			return response;
		} catch (ClientProtocolException e) {
			throw defaultException(e);
		} catch (IOException e) {
			throw defaultException(e);
		}
	}
	
	private String performPutRequest(HttpsRequest request) throws DataException 
	{
		HttpPut putRequest = new HttpPut(request.getUrl());
		for (Entry<String, String> header : request.getHeaders().entrySet())
		{
			putRequest.addHeader(header.getKey(), header.getValue());
		}		
		
		try {
			ResponseHandler<String> handler = new BasicResponseHandler();
			HttpEntity entity = new StringEntity(request.getBody());
			putRequest.setEntity(entity);
			
			String response = httpsClient.execute(putRequest, handler);
			return response;
		} catch (ClientProtocolException e) {
			throw defaultException(e);
		} catch (IOException e) {
			throw defaultException(e);
		}
	}	
	
	private String performDeleteRequest(HttpsRequest request) throws DataException 
	{
		HttpDelete deleteRequest = new HttpDelete(request.getUrl());
		for (Entry<String, String> header : request.getHeaders().entrySet())
		{
			deleteRequest.addHeader(header.getKey(), header.getValue());
		}		
		
		try {
			ResponseHandler<String> handler = new BasicResponseHandler();

			String response = httpsClient.execute(deleteRequest, handler);
			return response;
		} catch (ClientProtocolException e) {
			throw defaultException(e);
		} catch (IOException e) {
			throw defaultException(e);
		}
	}	

	//-----------------------------------------------------------------------//
	
	private DataException defaultException(Exception innerException) {
		return new DataException("HttpsConnector exception: " + innerException.getMessage());
	}
}