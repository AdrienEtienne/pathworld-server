package fr.miage.resources;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

import fr.miage.dao.InvitationDAO;
import fr.miage.model.Invitation;

@Path("/invitations")
public class InvitationResource {
	private InvitationDAO invitDAO = new InvitationDAO();
	private static Logger logger = Logger.getRootLogger();
	
	@GET
	@Path("/{idInvitation : [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInvitation(@PathParam(value="idInvitation") long idInvitation){
		Invitation invit = invitDAO.find(idInvitation);
		ObjectMapper mapper = new ObjectMapper();
		if(invit == null){
			return Response.status(404).build();
		}else{
			String fluxJson;
			try {
				fluxJson = mapper.writeValueAsString(invit);
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
	
	@GET
	@Path("/events/{idEvent : [0-9]+}/users/{idUser : [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInvitation(@PathParam(value="idUser") long idUser, @PathParam(value="idEvent") long idEvent){
		Invitation invit = invitDAO.getById(idUser, idEvent);
		ObjectMapper mapper = new ObjectMapper();
		if(invit == null){
			return Response.status(404).build();
		}else{
			String fluxJson;
			try {
				fluxJson = mapper.writeValueAsString(invit);
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
	@Path("/events/{idEvent : [0-9]+}/users/{idUser : [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateInvitation(@PathParam(value="idUser") long idUser, @PathParam(value="idEvent") long idEvent, Invitation invit){
		if (invit == null) {
			return Response.status(400).entity("Invitation n'est pas correctement formé").build();
		}else{
			if(invit.getRole() != null && !invit.getRole().matches("[a-zA-Z]+")){
				return Response.status(400).entity("Le role contient des caractères non autorisés").build();
			}
			if(invit.getGeolocalization() < 0 || invit.getGeolocalization() > 1){
				return Response.status(400).entity("La géolocatisation doit être comprise entre 0 et 1").build();
			}
			invit.setIdEvent(idEvent);
			invit.setIdUser(idUser);
			int nb = invitDAO.update(invit);
			if (nb == 1) {
				return Response.ok().build();
			} else if(nb == -1){
				return Response.status(400).build();
			} else if (nb == 0){
				return Response.status(404).build();
			} else{
				return Response.status(500).build();
			}
		}
	}
	
	@DELETE
	@Path("/events/{idEvent : [0-9]+}/users/{idUser : [0-9]+}")
	public Response deleteInvitation(@PathParam(value="idUser") long idUser, @PathParam(value="idEvent") long idEvent){
		int res = invitDAO.deleteInvitation(idEvent, idUser);
		if (res == -1) {
			return Response
					.status(400)
					.entity("Les paramètres entrés sont incorrects")
					.build();
		} else if(res == 0){
			return Response.status(404).build();
		} else{
			return Response.status(204).build();
		}
	}
	
	@GET
	public Response getAllInvitationByUser(@QueryParam(value="idUser")String idUser){
		List<Invitation> listeInvit = invitDAO.getAllInvitByUser(idUser);
		ObjectMapper mapper = new ObjectMapper();
		if(listeInvit == null){
			return Response.status(404).build();
		}else if(listeInvit.isEmpty()){
			return Response.status(204).build();
		}else{
			String fluxJson;
			try {
				fluxJson = mapper.writeValueAsString(listeInvit);
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
	public Response addInvitation(Invitation invit, @Context UriInfo uriInfo){
		if (invit == null) {
			return Response.status(400).entity("Invitation n'est pas correctement formé").build();
		}else{
			if(!invit.getDate().matches("^[0-9]{4}\\-[0-9]{1,2}\\-[0-9]{1,2}$")){
				return Response.status(400).entity("la date contient des caractères non autorisés").build();
			}
			if(!invit.getRole().matches("[a-zA-Z]+")){
				return Response.status(400).entity("Le role contient des caractères non autorisés").build();
			}
			if(invit.getGeolocalization() < 0 || invit.getGeolocalization() > 1){
				return Response.status(400).entity("La géolocatisation doit être comprise entre 0 et 1").build();
			}
			//On force le response à 0 (no response) lors d'une création d'invitation 
			invit.setResponse(0);
			long nbRes = invitDAO.create(invit);
			if(nbRes != 1){
				return Response.status(400).entity("Les paramètres entrés sont incorrects (clés étrangères non respectées ou event existe déjà)").build();
			}else{
				try {
					return Response.created(
							new URI(uriInfo.getAbsolutePath()
									+ "/events/"+invit.getIdEvent()+"/users/"+invit.getIdUser())).build();
				} catch (URISyntaxException e) {
					logger.error("Erreur lors de la generation de l'URI", e);
					return Response.status(500).build();
				}
			}
		}
	}
	
}
