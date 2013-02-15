package org.gdgankara.app.resources;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.gdgankara.app.model.Session;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

@Path("/session")
public class SessionResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;

//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	public void createSession(JAXBElement<Session> session) {
//		// TODO create new session in datastore
//	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newSession(@FormParam("id") String id,
			@FormParam("title") String title,
			@FormParam("description") String description,
			@Context HttpServletResponse servletResponse) throws IOException {
		Entity eSession = new Entity("Session");
		eSession.setProperty("title", title);
		eSession.setProperty("description", description);
		Key dumur = DatastoreServiceFactory.getDatastoreService().put(eSession);
		System.out.println(dumur.toString());
		servletResponse.sendRedirect("../webcontent/create_session.html");
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Session getSessionByID(@PathParam("id") int id){
		//TODO return = datastore.getSessionByID(id);
		return null;
	}
}
