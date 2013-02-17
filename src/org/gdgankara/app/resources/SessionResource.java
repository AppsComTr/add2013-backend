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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;

@Path("/session")
public class SessionResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newSession(@FormParam("lang") String lang,
			@FormParam("day") String day,
			@FormParam("start_hour") String startHour,
			@FormParam("end_hour") String endHour,
			@FormParam("title") String title,
			@FormParam("description") String description,
			@FormParam("hall") String hall,
			@FormParam("speaker") String speaker,
			@Context HttpServletResponse servletResponse) throws IOException {
		Entity eSession = new Entity(Session.KIND);
		eSession.setProperty(Session.LANG, lang);
		eSession.setProperty(Session.DAY, day);
		eSession.setProperty(Session.START_HOUR, startHour);
		eSession.setProperty(Session.END_HOUR, endHour);
		eSession.setProperty(Session.HALL, hall);
		eSession.setProperty(Session.SPEAKER, speaker);
		eSession.setProperty(Session.TITLE, title);
		eSession.setProperty(Session.DESCRIPTION, description);
		DatastoreServiceFactory.getDatastoreService().put(eSession);

		servletResponse
				.sendRedirect("/webcontent/create_session.html?success=true");
	}
	
	@POST
	@Path("create")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public void newSessionJson(JAXBElement<Session> jaxbSession) throws IOException {
		Entity eSession = new Entity(Session.KIND);
		Session session = jaxbSession.getValue();
		eSession.setProperty(Session.LANG, session.getLang());
		eSession.setProperty(Session.DAY, session.getDay());
		eSession.setProperty(Session.START_HOUR, session.getStartHour());
		eSession.setProperty(Session.END_HOUR, session.getEndHour());
		eSession.setProperty(Session.HALL, session.getHall());
		eSession.setProperty(Session.SPEAKER, session.getSpeaker());
		eSession.setProperty(Session.TITLE, session.getTitle());
		eSession.setProperty(Session.DESCRIPTION, session.getDescription());
		DatastoreServiceFactory.getDatastoreService().put(eSession);

	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Session getSessionByID(@PathParam("id") Long id)
			throws EntityNotFoundException {

		Entity eSession = DatastoreServiceFactory.getDatastoreService().get(
				KeyFactory.createKey(Session.KIND, id));
		Session session = new Session(eSession.getKey().getId(),
				(String) eSession.getProperty(Session.LANG),
				(String) eSession.getProperty(Session.DAY),
				(String) eSession.getProperty(Session.START_HOUR),
				(String) eSession.getProperty(Session.END_HOUR),
				(String) eSession.getProperty(Session.HALL),
				(String) eSession.getProperty(Session.TITLE),
				(String) eSession.getProperty(Session.DESCRIPTION),
				(String) eSession.getProperty(Session.SPEAKER));
		return session;
	}

	@POST
	@Path("update/{id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Session updateSessionByID(@PathParam("id") Long id,
			JAXBElement<Session> jaxbSession) throws EntityNotFoundException {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction dsTransaction = dataStore.beginTransaction();
		Session session = null;
		try {
			Entity eSession = dataStore.get(KeyFactory.createKey(Session.KIND,
					id));
			session = jaxbSession.getValue();
			eSession.setProperty(Session.LANG, session.getLang());
			eSession.setProperty(Session.DAY, session.getDay());
			eSession.setProperty(Session.DESCRIPTION, session.getDescription());
			eSession.setProperty(Session.END_HOUR, session.getEndHour());
			eSession.setProperty(Session.HALL, session.getHall());
			eSession.setProperty(Session.SPEAKER, session.getSpeaker());
			eSession.setProperty(Session.START_HOUR, session.getStartHour());
			eSession.setProperty(Session.TITLE, session.getTitle());

			dataStore.put(eSession);
			dsTransaction.commit();
		} finally {
			if (dsTransaction.isActive())
				dsTransaction.rollback();
		}
		return session;
	}

	@DELETE
	@Path("{id}/delete")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public void deleteSessionByID(@PathParam("id") Long id) {
		DatastoreServiceFactory.getDatastoreService().delete(
				KeyFactory.createKey(Session.KIND, id));
	}
}
