package fr.miage.resources.authentication;

import java.security.SignatureException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import fr.miage.dao.UserDAO;
import fr.miage.model.User;
import fr.miage.tools.Cryptography;

@Path("/connect")
public final class Authentication {/*
	
	private UserDAO userDAO = new UserDAO();
	private Logger logger = Logger.getRootLogger();
	
	@GET
	public Response authenticate(@QueryParam("login") String login, @QueryParam("timestamp") String timestamp, @QueryParam("sign") String signature){
		if(login == null || login.equals("") || timestamp == null || timestamp.equals("") || signature == null || signature.equals("")){
			return Response.status(400).build();
		}
		if(!login.matches("^[A-Za-z0-9\\._-]+@[A-Za-z0-9\\.-_]+.[a-zA-Z]{2,4}$") || !timestamp.matches("^[0-9]*$")){
			return Response.status(400).build();
		}
		User user = userDAO.getByMail(login);
		String pass = user.getPwd();
		//TODO : generate and handle exception
		pass = Cryptography.getInstance().decryptWithAES(pass);
		String content = login+timestamp;
		String calculatedSignature = "";
		try {
			calculatedSignature = Cryptography.getInstance().calculateRFC2104HMAC(content, pass);
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
			return Response.status(500).build();
		}
		if(calculatedSignature == null || calculatedSignature.equals("")){
			logger.error("la signature calculee par le serveur est null ou vide");
			return Response.status(500).build();
		}
		
		if(!calculatedSignature.equals(signature)){
			logger.info("L'authentification a échoué pour le login "+login);
			return Response.status(401).build();
		}else{			
			return Response.ok().build();
		}
	}*/
}
