package fr.miage.resources;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

import fr.miage.dao.TipsDAO;
import fr.miage.model.Tips;

@Path("/tips")
public class TipsResource {
	
	private TipsDAO tipsDAO = new TipsDAO();
	private static Logger logger = Logger.getRootLogger();
	
	@GET
	@Path("/{id : [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTips(@PathParam("id") long id){
		Tips tips = tipsDAO.find(id);
		ObjectMapper mapper = new ObjectMapper();
		if(tips == null){
			return Response.status(404).build();
		}else{
			String fluxJson;
			try {
				fluxJson = mapper.writeValueAsString(tips);
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
	public Response getTipsByCriteria(@DefaultValue("-1") @QueryParam(value="idEvent") long idEvent){
		if(idEvent == -1){
			return Response.status(400).entity("L'id event doit être renseigné").build();
		}else{
			List<Tips> listeTips = tipsDAO.findByCriteria(idEvent);
			if(!listeTips.isEmpty()){
				String fluxJson;
				try {
					ObjectMapper mapper = new ObjectMapper();
					fluxJson = mapper.writeValueAsString(listeTips);
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
			}else{
				return Response.status(404).build();
			}
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTips(Tips tips, @Context UriInfo uriInfo) {
		if (tips == null) {
			return Response.status(400).entity("Event n'est pas correctement formé").build();
		}else{
			if(!tips.getTitre().matches("[a-zA-Z 0-9-éàèêëîïôö\\.!]+")){
				return Response.status(400).entity("Le titre contient des caractères non autorisés").build();
			}
			if(!tips.getDetails().matches("[a-zA-Z 0-9-éàèêëîïôö\\.!]+")){
				return Response.status(400).entity("Le detail contient des caractères non autorisés").build();
			}
			long nbRes = tipsDAO.create(tips);
			if(nbRes == -1 || nbRes == 0){
				return Response.status(400).entity("Les paramètres entrés sont incorrects (clés étrangères non respectées ou event existe déjà)").build();
			}else{
				try {
					return Response.created(new URI(uriInfo.getAbsolutePath()+ "/"+nbRes)).build();
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
	public Response deleteTips(@PathParam(value="id") long id){
		int nbRes = tipsDAO.delete(id);
		if(nbRes != 1){
			return Response.status(404).entity("Les paramètres entrés sont incorrects (id inexistant ?)").build();
		}else{
			return Response.ok().build();
		}
	}
	
	@PUT
	@Path("/{id:[0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateTips(@PathParam(value="id")long idTips, Tips tips){
		if (tips == null) {
			return Response.status(400)
					.entity("User n'est pas correctement formé").build();
		} else {
			if(!tips.getTitre().matches("[a-zA-Z 0-9-éàèêëîïôö\\.!]+")){
				return Response.status(400).entity("Le titre contient des caractères non autorisés").build();
			}
			if(!tips.getDetails().matches("[a-zA-Z 0-9-éàèêëîïôö\\.!]+")){
				return Response.status(400).entity("Le detail contient des caractères non autorisés").build();
			}
			tips.setIdTips(idTips);
			int nb = tipsDAO.update(tips);
			if (nb == 1) {
				ObjectMapper mapper = new ObjectMapper();
				String fluxJson;
				try {
					fluxJson = mapper.writeValueAsString(tips);
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
