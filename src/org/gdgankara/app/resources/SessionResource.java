package org.gdgankara.app.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import org.gdgankara.app.model.SessionWrapper;
import org.gdgankara.app.model.Speaker;
import org.gdgankara.app.model.Version;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

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
			throws IOException, EntityNotFoundException {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Entity eSession = new Entity(Session.KIND);
		Session session = jaxbSession.getValue();
		List<Long> speakerIDList = new ArrayList<Long>();
		speakerIDList.add(session.getSpeaker1ID());
		speakerIDList.add(session.getSpeaker2ID());
		speakerIDList.add(session.getSpeaker3ID());

		eSession.setProperty(Session.LANG, session.getLang());
		eSession.setProperty(Session.DAY, session.getDay());
		eSession.setProperty(Session.START_HOUR, session.getStartHour());
		eSession.setProperty(Session.END_HOUR, session.getEndHour());
		eSession.setProperty(Session.HALL, session.getHall());
		eSession.setProperty(Session.BREAK, session.isBreak());
		eSession.setProperty(Session.SPEAKER_LIST, speakerIDList);
		eSession.setProperty(Session.TITLE, session.getTitle());
		eSession.setProperty(Session.DESCRIPTION, session.getDescription());

		dataStore.put(eSession);

		Version.setVersion();
		return session;
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public SessionWrapper getSessionbyID(@PathParam("id") Long id)
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
				(Boolean) eSession.getProperty(Session.BREAK), null, null,
				null, (List<Long>) eSession.getProperty(Session.SPEAKER_LIST));

		List<Long> speakerIDList = session.getSpeakerIDList();
		for (int i = 0; i < 3; i++) {
			Long speakerID = speakerIDList.get(i);
			if (speakerID != null) {
				Entity eSpeaker = DatastoreServiceFactory.getDatastoreService()
						.get(KeyFactory.createKey(Speaker.KIND, speakerID));
				Speaker speaker = new Speaker(eSpeaker.getKey().getId(),
						(String) eSpeaker.getProperty(Speaker.BIO),
						(String) eSpeaker.getProperty(Speaker.BLOG),
						(String) eSpeaker.getProperty(Speaker.FACEBOOK),
						(String) eSpeaker.getProperty(Speaker.GPLUS),
						(String) eSpeaker.getProperty(Speaker.LANG),
						(String) eSpeaker.getProperty(Speaker.NAME),
						(String) eSpeaker.getProperty(Speaker.PHOTO),
						(String) eSpeaker.getProperty(Speaker.TWITTER));

				if (i == 0) {
					session.setSpeaker1(speaker);
				} else if (i == 1) {
					session.setSpeaker2(speaker);
				} else if (i == 2) {
					session.setSpeaker3(speaker);
				}
			} else {
				speakerIDList.set(i, (long) 0);
			}
		}

		Version version = Version.getVersion();
		return new SessionWrapper(version, session);
	}

	@POST
	@Path("update/{id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Session updateSessionByID(@PathParam("id") Long id,
			JAXBElement<Session> jaxbSession) throws EntityNotFoundException {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Session session = jaxbSession.getValue();
		Entity eSession = dataStore.get(KeyFactory.createKey(Session.KIND, id));

		List<Long> speakerIDList = new ArrayList<Long>();
		speakerIDList.add(session.getSpeaker1ID());
		speakerIDList.add(session.getSpeaker2ID());
		speakerIDList.add(session.getSpeaker3ID());

		eSession.setProperty(Session.LANG, session.getLang());
		eSession.setProperty(Session.DAY, session.getDay());
		eSession.setProperty(Session.START_HOUR, session.getStartHour());
		eSession.setProperty(Session.END_HOUR, session.getEndHour());
		eSession.setProperty(Session.HALL, session.getHall());
		eSession.setProperty(Session.BREAK, session.isBreak());
		eSession.setProperty(Session.SPEAKER_LIST, speakerIDList);
		eSession.setProperty(Session.TITLE, session.getTitle());
		eSession.setProperty(Session.DESCRIPTION, session.getDescription());

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
