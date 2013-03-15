package org.gdgankara.app.resources;

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
import org.gdgankara.app.model.AnnouncementWrapper;
import org.gdgankara.app.model.Version;
import org.gdgankara.app.utils.Util;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;

@Path("/announcement")
public class AnnouncementResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@POST
	@Path("create")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Announcement newAnnouncement(
			JAXBElement<Announcement> jaxbAnnouncement) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Announcement announcement = jaxbAnnouncement.getValue();
		Entity eAnnouncement = new Entity(Announcement.KIND);
		eAnnouncement = Util.setAnnouncementEntityProperties(eAnnouncement,
				announcement);
		announcement.setId(dataStore.put(eAnnouncement).getId());

		Version.setVersion();
		return announcement;
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public AnnouncementWrapper getAnnouncement(@PathParam("id") Long id)
			throws EntityNotFoundException {
		Entity eAnnouncement = DatastoreServiceFactory.getDatastoreService()
				.get(KeyFactory.createKey(Announcement.KIND, id));
		Announcement announcement = Util
				.getAnnouncementFromEntity(eAnnouncement);

		return new AnnouncementWrapper(Version.getVersion(), announcement);
	}

	@POST
	@Path("update/{id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Announcement updateAnnouncementByID(@PathParam("id") Long id, JAXBElement<Announcement> jaxbAnnouncement) {
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		Entity eAnnouncement = new Entity(Announcement.KIND, id);
		Announcement announcement = jaxbAnnouncement.getValue();
		eAnnouncement = Util.setAnnouncementEntityProperties(eAnnouncement, announcement);
		announcement.setId(id);
		dataStore.put(eAnnouncement);
		
		Version.setVersion();
		return announcement;
	}
	
	@DELETE
	@Path("delete/{id}")
	public void deleteSessionByID(@PathParam("id") Long id) {
		DatastoreServiceFactory.getDatastoreService().delete(
				KeyFactory.createKey(Announcement.KIND, id));
		Version.setVersion();
	}
}
