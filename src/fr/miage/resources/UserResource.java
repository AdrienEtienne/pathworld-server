package fr.miage.resources;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import fr.miage.dao.EventDAO;
import fr.miage.dao.UserDAO;
import fr.miage.model.Event;
import fr.miage.model.User;

@Path("/users")
public class UserResource {
	private UserDAO userDAO = new UserDAO();
	private EventDAO eventDAO = new EventDAO();
	private static Logger logger = Logger.getRootLogger();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserByCriteria(@QueryParam("login") String login,
			@QueryParam("password") String password,
			@QueryParam("phone") String phone, @QueryParam("name") String name,
			@QueryParam("firstName") String firstName,
			@QueryParam("unknown") List<String> unknown) {

		if (login != null
				&& login.matches("^[A-Za-z0-9\\._-]+@[A-Za-z0-9\\.-_]+.[a-zA-Z]{2,4}$")) {
			User user = userDAO.getByMail(login);
			if (user != null && user.getPwd() != null && user.getPwd().equals(password)) {
				ObjectMapper mapper = new ObjectMapper();
				try {
					String fluxJson = mapper.writeValueAsString(user);
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
			} else if(password == null){
				if(user == null){
					return Response.status(404).build();
				}else{
					return Response.status(200).build();
				}
			} else{
				return Response.status(404).build();
			}
		} else if(login != null){
			return Response.status(400).build();
		}

		Map<String, User> mapUser = new HashMap<String,User>();
		if (unknown != null) {
			for(String token : unknown){
				if(token.matches("[a-zA-Z0-9 -éàèêëîïôö]+")){
					List<User> listeAux = null;
					listeAux = userDAO.getByUnknownCriteria(token);
					if(listeAux != null){
						for(User user : listeAux){
							mapUser.put(String.valueOf(user.getIdUser()), user);
						}
					}
				}
			}
			
		}
		if ((phone == null || phone.matches("[0-9]{10}"))
				&& (name == null || name.matches("[a-zA-Z -éàèêëîïôö]+"))
				&& (firstName == null || firstName
						.matches("[a-zA-Z -éàèêëîïôö]+"))) {
			List<User> listeAux = userDAO.getByMultiCriteria(name, firstName,
					phone);
			if(listeAux != null){
				for(User user : listeAux){
					mapUser.put(String.valueOf(user.getIdUser()), user);
				}
			}
			if (!mapUser.isEmpty()) {
				ObjectMapper mapper = new ObjectMapper();
				try {
					String listeUserJson = mapper.writeValueAsString(mapUser);
					return Response.ok(listeUserJson).build();
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
			} else {
				return Response.status(404).build();
			}
		}
		return Response.status(400).build();

	}

	@Path("/{id : [0-9]+}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("id") long id) {
		User user = userDAO.find(id);
		ObjectMapper mapper = new ObjectMapper();
		if (user == null) {
			return Response.status(404).build();
		} else {
			String fluxJson;
			try {
				fluxJson = mapper.writeValueAsString(user);
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
	public Response addUser(User user, @Context UriInfo uriInfo) {
		if (user == null) {
			return Response.status(400)
					.entity("User n'est pas correctement formé").build();
		} else {
			if (!user.getMail().matches(
					"^[A-Za-z0-9\\._-]+@[A-Za-z0-9\\.-_]+.[a-zA-Z]{2,4}$")) {
				return Response
						.status(400)
						.entity("L'adresse mail contient des caractères non autorisés")
						.build();
			}
			long nbRes = userDAO.create(user);
			if (nbRes == -1) {
				return Response.status(409)
						.entity("Cet identifiant existe déjà").build();
			} else if (nbRes != 1) {
				return Response
						.status(400)
						.entity("Les paramètres entrés sont incorrects (clés étrangères non respectées ou event existe déjà)")
						.build();
			} else {
				try {
					return Response.created(
							new URI(uriInfo.getAbsolutePath()
									+ "/"
									+ userDAO.getByMail(user.getMail())
											.getIdUser())).build();
				} catch (URISyntaxException e) {
					logger.error("Erreur lors de la generation de l'URI", e);
					return Response.status(500).build();
				}
			}
		}
	}

	@PUT
	@Path("/{id : [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(User user, @PathParam("id") long id) {
		if (user == null) {
			return Response.status(400)
					.entity("User n'est pas correctement formé").build();
		} else {
			if (user.getName() == null || !user.getName().matches("[a-zA-Z -éàèêëîïôö]+")) {
				return Response.status(400)
						.entity("Le nom contient des caractères non autorisés")
						.build();
			}
			if (user.getFirstName() == null || !user.getFirstName().matches("[a-zA-Z -éàèêëîïôö]+")) {
				return Response
						.status(400)
						.entity("Le prenom contient des caractères non autorisés")
						.build();
			}
			if (user.getPhone() == null || !user.getPhone().matches("[0-9]{10}")) {
				return Response
						.status(400)
						.entity("Le telephone contient des caractères non autorisés")
						.build();
			}
			if (user.getPwd() != null && !user.getPwd().matches("[a-zA-Z0-9@#$%]{6,20}")) {
				return Response
						.status(400)
						.entity("Le password contient des caractères non autorisés")
						.build();
			}
			UserDAO userDAO = new UserDAO();
			user.setIdUser(id);
			int nb = userDAO.update(user);
			if (nb == 1) {
				return Response.ok().build();
			} else {
				return Response.status(404).build();
			}
		}
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id:[0-9]+}")
	public Response deleteUser(@PathParam(value = "id") long id) {
		int nbRes = userDAO.delete(id);
		if (nbRes != 1) {
			return Response
					.status(400)
					.entity("Les paramètres entrés sont incorrects (id inexistant ?)")
					.build();
		} else {
			return Response.ok().build();
		}
	}

	
	@GET
	@Path("/{idUser:[0-9]+}/events")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllEventsByUser(@PathParam("idUser") long idUser) {
		List<Event> listeEvenements = eventDAO.getAllByUser(idUser, null);
		ObjectMapper mapper = new ObjectMapper();
		if (listeEvenements == null) {
			return Response.status(404).build();
		} else if (listeEvenements.isEmpty()) {
			return Response.status(404).build();
		} else {
			String fluxJson;
			try {
				fluxJson = mapper.writeValueAsString(listeEvenements);
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
}
