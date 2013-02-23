package org.gdgankara.app.resources;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import javax.xml.bind.JAXBElement;

import org.gdgankara.app.model.Session;
import org.gdgankara.app.model.Version;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;

@Path("/session")
public class SessionResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@POST
	@Path("create")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Session newSessionJson(JAXBElement<Session> jaxbSession)
			throws IOException {
		Entity eSession = new Entity(Session.KIND);
		Session session = jaxbSession.getValue();
		eSession.setProperty(Session.LANG, session.getLang());
		eSession.setProperty(Session.DAY, session.getDay());
		eSession.setProperty(Session.START_HOUR, session.getStartHour());
		eSession.setProperty(Session.END_HOUR, session.getEndHour());
		eSession.setProperty(Session.HALL, session.getHall());
		eSession.setProperty(Session.BREAK, session.isBreak());
		eSession.setProperty(Session.SPEAKER_1, session.getSpeaker1ID());
		eSession.setProperty(Session.SPEAKER_2, session.getSpeaker2ID());
		eSession.setProperty(Session.SPEAKER_3, session.getSpeaker3ID());
		eSession.setProperty(Session.TITLE, session.getTitle());
		eSession.setProperty(Session.DESCRIPTION, session.getDescription());
		
		DatastoreServiceFactory.getDatastoreService().put(eSession);
		Version.setVersion();
		return session;
	}

	@POST
	@Path("update/{id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Session updateSessionByID(@PathParam("id") Long id,
			JAXBElement<Session> jaxbSession) throws EntityNotFoundException {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Session session = jaxbSession.getValue();
		Entity eSession = dataStore.get(KeyFactory.createKey(Session.KIND, id));
		eSession.setProperty(Session.LANG, session.getLang());
		eSession.setProperty(Session.DAY, session.getDay());
		eSession.setProperty(Session.DESCRIPTION, session.getDescription());
		eSession.setProperty(Session.END_HOUR, session.getEndHour());
		eSession.setProperty(Session.HALL, session.getHall());
		eSession.setProperty(Session.BREAK, session.isBreak());
		eSession.setProperty(Session.SPEAKER_1, session.getSpeaker1());
		eSession.setProperty(Session.SPEAKER_2, session.getSpeaker2());
		eSession.setProperty(Session.SPEAKER_3, session.getSpeaker3());
		eSession.setProperty(Session.START_HOUR, session.getStartHour());
		eSession.setProperty(Session.TITLE, session.getTitle());
		dataStore.put(eSession);

		Version.setVersion();
		return session;
	}

	@DELETE
	@Path("delete/{id}")
	public void deleteSessionByID(@PathParam("id") Long id) {
		DatastoreServiceFactory.getDatastoreService().delete(
				KeyFactory.createKey(Session.KIND, id));
		Version.setVersion();
	}
}
