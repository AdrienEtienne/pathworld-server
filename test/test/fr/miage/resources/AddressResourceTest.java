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

public class AddressResourceTest extends EmbeddedServer{
	@Test
	public void getAddressOK(){
		String url = SERVER_PATH+"addresses/1";
		try {
			Response res = RequestHelper.sendGet(url);
			assertEquals(200, res.getStatusCode());
			assertEquals(1, JsonPath.read(res.getFluxJson(), "$.[*].idAddress"));
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getAddressKO(){
		String url = SERVER_PATH+"addresses/1695";
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
	public void addAddressOK(){
		String url = SERVER_PATH+"addresses";
		try {
			Response res = RequestHelper.sendPost(url, "{\"place\":\"ma place\",\"location\":\"My position\",\"latitude\":37.785834,\"longitude\":-122.406417}");
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
	public void addAddressKO(){
		String url = SERVER_PATH+"addresses";
		try {
			Response res = RequestHelper.sendPost(url, "{\"place\":\"ma //45 er* -place\",\"location\":\"My position\",\"latitude\":37.785834,\"longitude\":-122.406417}");
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
	public void getAddressByUserOK(){
		String url = SERVER_PATH+"addresses?idUser=1";
		try {
			Response res = RequestHelper.sendGet(url);
			assertEquals(200, res.getStatusCode());
			assertEquals(1, JsonPath.read(res.getFluxJson(), "$.[*].idAddress"));
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void getAddressByUserKO(){
		String url = SERVER_PATH+"addresses?idUser=654";
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
	public void getAddressByUserKOParamKO(){
		String url = SERVER_PATH+"addresses?unknown=654";
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
	public void updateAddressOK(){
		String url = SERVER_PATH+"addresses/1";
		try {
			Response res = RequestHelper.sendPut(url, "{\"place\":\"ma place\",\"location\":\"My position\",\"latitude\":36,\"longitude\":-122.406417}");
			assertEquals(200, res.getStatusCode());
			assertEquals(36.0, JsonPath.read(res.getFluxJson(), "$.[*].latitude"));
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		} catch (ClientProtocolException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void updateAddressKO(){
		String url = SERVER_PATH+"addresses/1";
		try {
			Response res = RequestHelper.sendPut(url, "{\"place\":\"ma //45 er* -place\",\"location\":\"My position\",\"latitude\":37.785834,\"longitude\":-122.406417}");
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
	public void updateAddressKONotFound(){
		String url = SERVER_PATH+"addresses/54411";
		try {
			Response res = RequestHelper.sendPut(url, "{\"place\":\"ma place\",\"location\":\"My position\",\"latitude\":37.785834,\"longitude\":-122.406417}");
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
