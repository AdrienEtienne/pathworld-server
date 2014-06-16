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
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import fr.miage.dao.EventDAO;
import fr.miage.dao.InvitationDAO;
import fr.miage.dao.UserDAO;
import fr.miage.model.Event;
import fr.miage.model.Invitation;
import fr.miage.model.User;

@Path("/events")
public class EventResource {
		
	private EventDAO eventDAO = new EventDAO();
	private UserDAO userDAO = new UserDAO();
	private InvitationDAO invitDAO = new InvitationDAO();
	private static Logger logger = Logger.getRootLogger();
	
	@GET
	@Path("/{id : [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEvent(@PathParam("id") long id){
		Event evenement = eventDAO.find(id);
		ObjectMapper mapper = new ObjectMapper();
		if(evenement == null){
			return Response.status(404).build();
		}else{
			String fluxJson;
			try {
				fluxJson = mapper.writeValueAsString(evenement);
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
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEventByCriteria(@QueryParam("date") String date, @QueryParam("idUser") long idUser){
		if(date == null || !date.matches("^[0-9]{4}\\-[0-9]{1,2}\\-[0-9]{1,2}$")){
			return Response.status(400).build();
		}
		List<Event> listeEvent = eventDAO.getAllByUser(idUser, date);
		ObjectMapper mapper = new ObjectMapper();
		if(listeEvent == null){
			return Response.status(500).build();
		}
		if(listeEvent.isEmpty()){
			return Response.status(404).build();
		}else{
			String fluxJson;
			try {
				fluxJson = mapper.writeValueAsString(listeEvent);
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
	@Path("/{idEvent : [0-9]+}/users")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsersByEvent(@PathParam(value="idEvent")long idEvent){
		List<User> listeUser = userDAO.getAllUserByEvent(idEvent);
		ObjectMapper mapper = new ObjectMapper();
		if(listeUser == null || listeUser.isEmpty()){
			return Response.status(404).build();
		}else{
			final JsonNode json = mapper.valueToTree(listeUser);
			for(int i=0; i<((ArrayNode) json).size(); i++){
				((ObjectNode)json.get(i)).remove("mail");
				((ObjectNode)json.get(i)).remove("pwd");
			}
			return Response.ok(json.toString()).build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addEvent(Event event, @Context UriInfo uriInfo) {
		if (event == null) {
			return Response.status(400).entity("Event n'est pas correctement formé").build();
		}else{
			if(!event.getIntitule().matches("[a-zA-Z 0-9-<>\\.]+")){
				return Response.status(400).entity("L'intitule contient des caractères non autorisés").build();
			}
			if(!event.getTypeEvent().matches("[a-zA-Z]+")){
				return Response.status(400).entity("Le type event contient des caractères non autorisés").build();
			}
			if(!event.getDate().matches("^[0-9]{4}[/-]((0[1-9])|(1[0-2]))[/-][0-3][0-9]$")){
				return Response.status(400).entity("Le format de la date est incorrect (AAAA/MM/JJ) ou (AAAA-MM-JJ)").build();
			}
			long nbRes = eventDAO.create(event);
			if(nbRes == -1 || nbRes == 0){
				return Response.status(400).entity("Les paramètres entrés sont incorrects (clés étrangères non respectées ou event existe déjà)").build();
			}else{
				try {
					Invitation invit = new Invitation();
					invit.setDate(event.getDate());
					invit.setIdEvent(nbRes);
					invit.setIdUser(event.getIdCreateur());
					invit.setResponse(1);
					invit.setRole("owner");
					long retourInvit = invitDAO.create(invit);
					if(retourInvit != 1){
						eventDAO.delete(nbRes);
						return Response.status(500).entity("Erreur lors de l'invitation du createur a l'event").build();
					}else{
						return Response.created(new URI(uriInfo.getAbsolutePath()+ "/"+nbRes)).build();
					}
				} catch (URISyntaxException e) {
					logger.error("Erreur lors de la generation de l'URI", e);
					return Response.status(500).entity(e).build();
				}
			}
		}
	}
	
	@DELETE
	@Path("/{id:[0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteEvent(@PathParam(value="id") long id){
		int nbRes = eventDAO.delete(id);
		if(nbRes != 1){
			return Response.status(404).entity("Les paramètres entrés sont incorrects (id inexistant ?)").build();
		}else{
			return Response.ok().build();
		}
	}
	
	@PUT
	@Path("/{id:[0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateEvent(@PathParam(value="id")long idEvent, Event event){
		if (event == null) {
			return Response.status(400)
					.entity("User n'est pas correctement formé").build();
		} else {
			if(!event.getIntitule().matches("[a-zA-Z 0-9\\-'<>\\.]+")){
				return Response.status(400).entity("L'intitule contient des caractères non autorisés").build();
			}
			if(!event.getTypeEvent().matches("[a-zA-Z]+")){
				return Response.status(400).entity("Le type event contient des caractères non autorisés").build();
			}
			if(!event.getDate().matches("^[0-9]{4}[/-]((0[1-9])|(1[0-2]))[/-][0-3][0-9]$")){
				return Response.status(400).entity("Le format de la date est incorrect (AAAA/MM/JJ) ou (AAAA-MM-JJ)").build();
			}
			event.setIdEvent(idEvent);
			int nb = eventDAO.update(event);
			if (nb == 1) {
				ObjectMapper mapper = new ObjectMapper();
				String fluxJson;
				try {
					fluxJson = mapper.writeValueAsString(event);
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
}
