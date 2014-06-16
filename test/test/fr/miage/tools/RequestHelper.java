package test.fr.miage.tools;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import test.fr.miage.tools.model.Response;

public class RequestHelper {

	private static HttpClient client;

	public static Response sendGet(String url) throws IllegalStateException, IOException{
		HttpGet requete = new HttpGet(url);	 
		client =  HttpClientBuilder.create().build();
		HttpResponse response = client.execute(requete);
		StringWriter writer = new StringWriter();
		IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
		StringBuilder theString = new StringBuilder(writer.toString());
		
		Response resRequete = new Response();
		resRequete.setFluxJson(theString.toString());
		resRequete.setStatusCode(response.getStatusLine().getStatusCode());
		return resRequete;
	}
	
	public static Response sendPost(String url, String fluxJson) throws IllegalStateException, IOException{
		
		HttpPost post = new HttpPost(url);	 
		post.setHeader("Content-Type", "application/json");
		StringEntity params =new StringEntity(fluxJson);
		post.setEntity(params);
		client =  HttpClientBuilder.create().build();
		HttpResponse response = client.execute(post);
		
		Response requete = new Response();
		requete.setStatusCode(response.getStatusLine().getStatusCode());
		
		return requete;
	}
	
	public static Response sendDelete(String url) throws ClientProtocolException, IOException{
		HttpDelete delete = new HttpDelete(url);	 
		client =  HttpClientBuilder.create().build();
		HttpResponse response = client.execute(delete);
		
		Response requete = new Response();
		requete.setStatusCode(response.getStatusLine().getStatusCode());
		
		return requete;
	}
	
	public static Response sendPut(String url, String fluxJson) throws ClientProtocolException, IOException{
		HttpPut put = new HttpPut(url);	 
		put.setHeader("Content-Type", "application/json");
		StringEntity params = new StringEntity(fluxJson);
		put.setEntity(params);
		client =  HttpClientBuilder.create().build();
		HttpResponse response = client.execute(put);
		
		Response requete = new Response();
		StringWriter writer = new StringWriter();
		IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
		StringBuilder theString = new StringBuilder(writer.toString());
		requete.setStatusCode(response.getStatusLine().getStatusCode());
		requete.setFluxJson(theString.toString());
		
		return requete;
	}
}
