package test.fr.miage.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import test.fr.miage.tools.EmbeddedServer;
import test.fr.miage.tools.RequestHelper;
import test.fr.miage.tools.model.Response;

import com.jayway.jsonpath.JsonPath;

public class InvitationResourceTest extends EmbeddedServer{
	
	
	@Test
	public void addInvitationTestKOUserKO(){
		String url = SERVER_PATH+"invitations";
		  
		try {
			Response response = RequestHelper.sendPost(url,"{\"idEvent\":1,\"idUser\":98799,\"role\":\"guest\",\"date\":\"2014-04-09\"}");
			assertEquals(400, response.getStatusCode());			
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getInvitationOK(){
		String url = SERVER_PATH+"invitations/1";
		try {
			Response res = RequestHelper.sendGet(url);
			assertEquals(200, res.getStatusCode());
			assertEquals(1, JsonPath.read(res.getFluxJson(), "$.[*].idInvitation"));
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getInvitationKO(){
		String url = SERVER_PATH+"invitations/16584";
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
	public void addInvitationTestOK(){
		
		String url = SERVER_PATH+"invitations";
		 
		try {
			Response response = RequestHelper.sendPost(url, "{\"idEvent\":1,\"idUser\":1,\"role\":\"guest\",\"date\":\"2014-04-10\"}");
			assertEquals(201, response.getStatusCode());			
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void addInvitationTestKO(){
		String url = SERVER_PATH+"invitations";
		  
		try {
			Response response = RequestHelper.sendPost(url, "{\"idEvent\":1,\"idUser\":2,\"role\":\"guest\",\"date\":\"2014-04-09\"}");
			assertEquals(400, response.getStatusCode());			
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
}
