package fr.miage.resources;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import fr.miage.dao.AddressDAO;
import fr.miage.model.Address;

@Path("/addresses")
public class AddressResource {
	
	private AddressDAO addressDAO = new AddressDAO();
	private static Logger logger = Logger.getRootLogger();
	
	@GET
	@Path("/{id:[0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAddress(@PathParam(value="id")long idAddress){
		Address address = addressDAO.find(idAddress);
		ObjectMapper mapper = new ObjectMapper();
		if(address == null){
			return Response.status(404).build();
		}else{
			String fluxJson;
			try {
				fluxJson = mapper.writeValueAsString(address);
				return Response.ok(fluxJson).build();
			} catch (JsonGenerationException e) {
				logger.error("Erreur lors du parsing json", e);
				return Response.status(500).build();
			} catch (JsonMappingException e) {
				logger.error("Erreur lors du parsing json", e);
				return Response.status(500).build();
			} catch (IOException e) {
				logger.error("Erreur lors du parsing json", e);
				return Response.status(500).build();
			}
		}
	}
	
	@PUT
	@Path("/{id:[0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateAddress(@PathParam(value="id") long id, Address address){
		if (address == null) {
			return Response.status(400)
					.entity("User n'est pas correctement formé").build();
		} else {
			if(!address.getPlace().matches("^[A-Za-z0-9\\.\\-_ éàèêëîïôö]+$")){
				return Response.status(400).entity("Place ne respecte pas le format attendu").build();
			}
			if(!address.getLocation().matches("^[A-Za-z0-9\\.\\-_ ,éàèêëîïôö]+$")){
				return Response.status(400).entity("Location ne respecte pas le format attendu").build();
			}
			address.setIdAddress(id);
			int nb = addressDAO.update(address);
			if (nb == 1) {
				ObjectMapper mapper = new ObjectMapper();
				String fluxJson;
				try {
					fluxJson = mapper.writeValueAsString(address);
					return Response.ok(fluxJson).build();
				} catch (JsonGenerationException e) {
					logger.error("Erreur lors du parsing json", e);
					return Response.status(500).build();
				} catch (JsonMappingException e) {
					logger.error("Erreur lors du parsing json", e);
					return Response.status(500).build();
				} catch (IOException e) {
					logger.error("Erreur lors du parsing json", e);
					return Response.status(500).build();
				}
			} else if(nb == -1){
				return Response.status(400).build();
			} else if (nb == 0){
				return Response.status(404).build();
			} else{
				return Response.status(500).build();
			}
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAddressByUser(@DefaultValue("-1") @QueryParam(value="idUser")long idUser){
		if(idUser == -1){
			return Response.status(400).entity("L'id user doit être renseigné").build();
		}
		Address address = addressDAO.getAddressByUser(idUser);
		ObjectMapper mapper = new ObjectMapper();
		if(address == null){
			return Response.status(404).build();
		}else{
			String fluxJson;
			try {
				fluxJson = mapper.writeValueAsString(address);
				return Response.ok(fluxJson).build();
			} catch (JsonGenerationException e) {
				logger.error("Erreur lors du parsing json", e);
				return Response.status(500).build();
			} catch (JsonMappingException e) {
				logger.error("Erreur lors du parsing json", e);
				return Response.status(500).build();
			} catch (IOException e) {
				logger.error("Erreur lors du parsing json", e);
				return Response.status(500).build();
			}
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addAddress(@Context UriInfo uriInfo, Address address){
		if (address == null) {
			return Response.status(400).entity("L'adresse n'est pas correctement formée").build();
		}else{			
			if(!address.getPlace().matches("^[A-Za-z0-9\\.\\-_ éàèêëîïôö]+$")){
				return Response.status(400).entity("Place ne respecte pas le format attendu").build();
			}else if(!address.getLocation().matches("^[A-Za-z0-9\\.\\-_ ,éàèêëîïôö]+$")){
				return Response.status(400).entity("Location ne respecte pas le format attendu").build();
			}
			long nbRes = addressDAO.create(address);
			if(nbRes == -1){
				return Response.status(409).entity("Cette adresse existe déjà").build();
			}else if(nbRes > 1){
				try {
					return Response.created(new URI(uriInfo.getAbsolutePath()+"/"+nbRes)).build();
				} catch (URISyntaxException e) {
					logger.error("Erreur lors de la generation de l'URI", e);
					return Response.status(500).build();
				}
			}else{
				return Response.status(400).entity("Problème lors de l'insertion en base de données, données incorrectes").build();
			}
		}
	}
	
	
}
