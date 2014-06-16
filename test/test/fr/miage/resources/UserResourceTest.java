package test.fr.miage.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import test.fr.miage.tools.EmbeddedServer;

public class UserResourceTest extends EmbeddedServer{

	@Test
	public void addUserOK(){
		String url = SERVER_PATH+"users";
		String fluxJson = "{\"mail\":\"totototo@live.fr\",\"pwd\":\"1234567890\"}";
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);	 
		try {
			post.setHeader("Content-Type", "application/json");
			StringEntity params =new StringEntity(fluxJson);
			post.setEntity(params);
			HttpResponse response = client.execute(post);
			assertEquals(201, response.getStatusLine().getStatusCode());			
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void modifyUserOK(){
		String url = SERVER_PATH+"users/2";
		String fluxJson = "{\"name\":\"toto\",\"firstName\":\"toto\",\"phone\":\"1234567890\", \"idCurrentAddress\":\"1\", \"idHomeAddress\":\"1\"}";
		HttpClient client = HttpClientBuilder.create().build();
		HttpPut put = new HttpPut(url);	 
		try {
			put.setHeader("Content-Type", "application/json");
			StringEntity params =new StringEntity(fluxJson);
			put.setEntity(params);
			HttpResponse response = client.execute(put);
			assertEquals(200, response.getStatusLine().getStatusCode());			
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test 
	public void deleteUserOK(){
		String url = SERVER_PATH+"users/1";
		HttpClient client = HttpClientBuilder.create().build();
		HttpDelete delete = new HttpDelete(url);	 
		try {
			delete.setHeader("Content-Type", "application/json");
			HttpResponse response = client.execute(delete);
			assertEquals(200, response.getStatusLine().getStatusCode());			
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getAllEventsByUserTestKO(){
		
		String url = SERVER_PATH+"users/5/events";
		 
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		HttpResponse response;
		try {
			response = client.execute(request);
			assertEquals(404, response.getStatusLine().getStatusCode());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
}
