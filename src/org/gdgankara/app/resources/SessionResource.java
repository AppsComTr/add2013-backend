package org.gdgankara.app.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import org.gdgankara.app.model.Announcement;
import org.gdgankara.app.model.Session;
import org.gdgankara.app.model.SessionWrapper;
import org.gdgankara.app.model.Speaker;
import org.gdgankara.app.model.Version;
import org.gdgankara.app.utils.Util;

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
			throws IOException, EntityNotFoundException {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Entity eSession = new Entity(Session.KIND);
		Session session = jaxbSession.getValue();
		
		List<Long> speakerIDList = new ArrayList<Long>();
		speakerIDList.add(session.getSpeaker1ID());
		speakerIDList.add(session.getSpeaker2ID());
		speakerIDList.add(session.getSpeaker3ID());
		
		eSession = Util.setSessionEntityProperties(eSession, session, speakerIDList);
		session.setId(dataStore.put(eSession).getId());
		
		if (!session.isBreak()) {
			for (int i = 0; i < 3; i++) {
				Long speakerID = speakerIDList.get(i);
				if (speakerID != null) {
					Entity eSpeaker = DatastoreServiceFactory.getDatastoreService()
							.get(KeyFactory.createKey(Speaker.KIND, speakerID));
					Speaker speaker = Util.getSpeakerFromEntity(eSpeaker);

					List<Long> sessionIDList = speaker.getSessionIDList();
					if (sessionIDList == null) {
						sessionIDList = new ArrayList<Long>();
					}

					sessionIDList.add(session.getId());
					speaker.setSessionIDList(sessionIDList);

					eSpeaker.setProperty(Speaker.SESSION_LIST,
							speaker.getSessionIDList());
					dataStore.put(eSpeaker);
				} else {
					speakerIDList.set(i, null);
				}
			}
		}
		
		Version.setVersion();
		return session;
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public SessionWrapper getSessionbyID(@PathParam("id") Long id)
			throws EntityNotFoundException {
		Entity eSession = DatastoreServiceFactory.getDatastoreService().get(
				KeyFactory.createKey(Session.KIND, id));

		Session session = Util.getSessionFromEntity(eSession);
		List<Long> speakerIDList = session.getSpeakerIDList();
		for (int i = 0; i < 3; i++) {
			Long speakerID = speakerIDList.get(i);
			if (speakerID != null) {
				Entity eSpeaker = DatastoreServiceFactory.getDatastoreService()
						.get(KeyFactory.createKey(Speaker.KIND, speakerID));
				Speaker speaker = Util.getSpeakerFromEntity(eSpeaker);

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
		
		Entity eSession = new Entity(Session.KIND, id);
		
		Session session = jaxbSession.getValue();
		session.setId(id);

		List<Long> speakerIDList = new ArrayList<Long>();
		speakerIDList.add(session.getSpeaker1ID());
		speakerIDList.add(session.getSpeaker2ID());
		speakerIDList.add(session.getSpeaker3ID());
			
		for (int i = 0; i < 3; i++) {
			Long speakerID = speakerIDList.get(i);
			if (speakerID != null) {
				Entity eSpeaker = DatastoreServiceFactory.getDatastoreService()
						.get(KeyFactory.createKey(Speaker.KIND, speakerID));
				Speaker speaker = Util.getSpeakerFromEntity(eSpeaker);

				List<Long> sessionIDList = speaker.getSessionIDList();
				if (sessionIDList == null) {
					sessionIDList = new ArrayList<Long>();
					sessionIDList.add(session.getId());
				}else if(!sessionIDList.contains(session.getId())) {
					sessionIDList.add(session.getId());
				}
				
				speaker.setSessionIDList(sessionIDList);
				eSpeaker.setProperty(Speaker.SESSION_LIST,
						speaker.getSessionIDList());
				dataStore.put(eSpeaker);
			} else {
				speakerIDList.set(i, null);
			}
		}
		
		eSession = Util.setSessionEntityProperties(eSession, session, speakerIDList);
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
