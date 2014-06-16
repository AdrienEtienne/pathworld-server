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

public class TipsResourceTest extends EmbeddedServer{
	@Test
	public void getTipsTestOK(){
		String url = SERVER_PATH+"tips/1";
		try {
			Response res = RequestHelper.sendGet(url);
			assertEquals(200, res.getStatusCode());
			assertEquals(1, JsonPath.read(res.getFluxJson(), "$.[*].idTips"));
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getTipsTestKO(){
		String url = SERVER_PATH+"tips/154";
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
	public void getTipsByCriteriaTestOK(){
		String url = SERVER_PATH+"tips?idEvent=1";
		try {
			Response res = RequestHelper.sendGet(url);
			assertEquals(200, res.getStatusCode());
			assertEquals(1, JsonPath.read(res.getFluxJson(), "$.[0].idTips"));
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getTipsByCriteriaTestKO(){
		String url = SERVER_PATH+"tips?idEvent=1651";
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
	public void getTipsByCriteriaTestKOParamKO(){
		String url = SERVER_PATH+"tips?unknown=1651";
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
	public void addTipsTestOK(){
		String url = SERVER_PATH+"tips";
		try {
			Response res = RequestHelper.sendPost(url, "{\"titre\":\"Petit cailloux2\",\"details\":\"Mon deuxieme cailloux\",\"idAdresse\":1,\"idCreateur\":2,\"idEvent\":1,\"date\":\"2014-05-27\"}");
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
	public void addTipsTestKOUserKO(){
		String url = SERVER_PATH+"tips";
		try {
			Response res = RequestHelper.sendPost(url, "{\"titre\":\"Petit cailloux2\",\"details\":\"Mon deuxieme cailloux\",\"idAdresse\":1,\"idCreateur\":245,\"idEvent\":1,\"date\":\"2014-05-27\"}");
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
	public void addTipsTestKOAdresseKO(){
		String url = SERVER_PATH+"tips";
		try {
			Response res = RequestHelper.sendPost(url, "{\"titre\":\"Petit cailloux2\",\"details\":\"Mon deuxieme cailloux\",\"idAdresse\":1954,\"idCreateur\":1,\"idEvent\":1,\"date\":\"2014-05-27\"}");
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
	public void addTipsTestKOEventKO(){
		String url = SERVER_PATH+"tips";
		try {
			Response res = RequestHelper.sendPost(url, "{\"titre\":\"Petit cailloux2\",\"details\":\"Mon deuxieme cailloux\",\"idAdresse\":1,\"idCreateur\":1,\"idEvent\":1958,\"date\":\"2014-05-27\"}");
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
	public void deleteTipsOK(){
		String url = SERVER_PATH+"tips/2";
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
	public void deleteTipsKO(){
		String url = SERVER_PATH+"tips/156";
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
	public void updateTipsOK(){
		String url = SERVER_PATH+"tips/1";
		try {
			Response res = RequestHelper.sendPut(url, "{\"titre\":\"Petit cailloux update\",\"details\":\"Mon deuxieme cailloux\",\"idAdresse\":1,\"idCreateur\":2,\"idEvent\":1,\"date\":\"2014-05-27\"}");
			assertEquals(200, res.getStatusCode());
			assertEquals("Petit cailloux update", JsonPath.read(res.getFluxJson(), "$.[*].titre"));
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void updateTipsKO(){
		String url = SERVER_PATH+"tips/1954";
		try {
			Response res = RequestHelper.sendPut(url, "{\"titre\":\"Petit cailloux update\",\"details\":\"Mon deuxieme cailloux\",\"idAdresse\":1,\"idCreateur\":2,\"idEvent\":1,\"date\":\"2014-05-27\"}");
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
