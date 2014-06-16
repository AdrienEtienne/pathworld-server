package test.fr.miage.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import com.jayway.jsonpath.JsonPath;

import test.fr.miage.tools.EmbeddedServer;
import test.fr.miage.tools.RequestHelper;
import test.fr.miage.tools.model.Response;

public class EventResourceTest extends EmbeddedServer{
	@Test
	public void addEventOK(){
		String url = SERVER_PATH+"events";
		try {
			Response res = RequestHelper.sendPost(url, "{\"intitule\":\"mon new event\",\"date\":\"2014-05-23\",\"typeEvent\":\"pro\",\"idAdresse\":1,\"idCreateur\":2}");
			assertEquals(201, res.getStatusCode());
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void addEventKOCreateurKO(){
		String url = SERVER_PATH+"events";
		try {
			Response res = RequestHelper.sendPost(url, "{\"intitule\":\"mon new event\",\"date\":\"2014-05-23\",\"typeEvent\":\"pro\",\"idAdresse\":1,\"idCreateur\":251}");
			assertEquals(400, res.getStatusCode());
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void addEventKOAdresseKO(){
		String url = SERVER_PATH+"events";
		try {
			Response res = RequestHelper.sendPost(url, "{\"intitule\":\"mon new event\",\"date\":\"2014-05-23\",\"typeEvent\":\"pro\",\"idAdresse\":195,\"idCreateur\":2}");
			assertEquals(400, res.getStatusCode());
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void deleteEventTestOK(){
		String url = SERVER_PATH+"events/2";
		try {
			Response res = RequestHelper.sendDelete(url);
			assertEquals(200, res.getStatusCode());
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void deleteEventTestKO(){
		String url = SERVER_PATH+"events/542";
		try {
			Response res = RequestHelper.sendDelete(url);
			assertEquals(404, res.getStatusCode());
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getAllUsersByEventOK(){
		String url = SERVER_PATH+"events/1/users";
		try {
			Response res = RequestHelper.sendGet(url);
			assertEquals(200, res.getStatusCode());
			assertEquals(2, JsonPath.read(res.getFluxJson(), "$.[0].idUser"));
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getAllUsersByEventKO(){
		String url = SERVER_PATH+"events/1515/users";
		try {
			Response res = RequestHelper.sendGet(url);
			assertEquals(404, res.getStatusCode());
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getEventTestOK(){
		String url = SERVER_PATH+"events/1";
		try {
			Response res = RequestHelper.sendGet(url);
			assertEquals(200, res.getStatusCode());
			assertEquals(1, JsonPath.read(res.getFluxJson(), "$.[*].idEvent"));
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getEventTestKO(){
		String url = SERVER_PATH+"events/651";
		try {
			Response res = RequestHelper.sendGet(url);
			assertEquals(404, res.getStatusCode());
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getEventTestKOParamKO(){
		String url = SERVER_PATH+"events/fzefze";
		try {
			Response res = RequestHelper.sendGet(url);
			assertEquals(404, res.getStatusCode());
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getEventByCriteriaOK(){
		String url = SERVER_PATH+"events?date=2014-05-27&idUser=2";
		try {
			Response res = RequestHelper.sendGet(url);
			assertEquals(200, res.getStatusCode());
			assertEquals(1, JsonPath.read(res.getFluxJson(), "$.[0].idEvent"));
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getEventByCriteriaKO(){
		String url = SERVER_PATH+"events?date=2014-05-27&idUser=845";
		try {
			Response res = RequestHelper.sendGet(url);
			assertEquals(404, res.getStatusCode());
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getEventByCriteriaKOParamKO(){
		String url = SERVER_PATH+"events?truc=2014-05-27&machin=845";
		try {
			Response res = RequestHelper.sendGet(url);
			assertEquals(400, res.getStatusCode());
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getEventByCriteriaKODateKO(){
		String url = SERVER_PATH+"events?date=07/05/2014&idUser=2";
		try {
			Response res = RequestHelper.sendGet(url);
			assertEquals(400, res.getStatusCode());
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	
	@Test
	public void updateEventOK(){
		String url = SERVER_PATH+"events/1";
		try {
			Response res = RequestHelper.sendPut(url, "{\"intitule\":\"mon event\",\"date\":\"2014-05-27\",\"typeEvent\":\"perso\",\"idAdresse\":1,\"idCreateur\":1}");
			assertEquals(200, res.getStatusCode());
			assertEquals("perso", JsonPath.read(res.getFluxJson(), "$.[*].typeEvent"));
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void updateEventKO(){
		String url = SERVER_PATH+"events/26545";
		try {
			Response res = RequestHelper.sendPut(url, "{\"intitule\":\"mon event\",\"date\":\"2014-05-27\",\"typeEvent\":\"perso\",\"idAdresse\":1,\"idCreateur\":1}");
			assertEquals(404, res.getStatusCode());
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
}
