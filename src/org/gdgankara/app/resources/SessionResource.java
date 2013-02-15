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
	
	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newSession(@FormParam("day") String day,
			@FormParam("time") String time,
			@FormParam("title") String title,
			@FormParam("description") String description,
			@FormParam("hall") String hall,
			@FormParam("speaker") String speaker,
			@Context HttpServletResponse servletResponse) throws IOException {
		Entity eSession = new Entity(Session.KIND);
		eSession.setProperty(Session.DAY, day);
		eSession.setProperty(Session.TIME, time);
		eSession.setProperty(Session.HALL, hall);
		eSession.setProperty(Session.SPEAKER, speaker);
		eSession.setProperty(Session.TITLE, title);
		eSession.setProperty(Session.DESCRIPTION, description);
		
		servletResponse.sendRedirect("../webcontent/create_session.html");
	}

}
