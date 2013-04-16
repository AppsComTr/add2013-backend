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
import org.gdgankara.app.model.Sponsor;
import org.gdgankara.app.model.SponsorWrapper;
import org.gdgankara.app.model.Version;
import org.gdgankara.app.utils.Util;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;

@Path("/sponsor")
public class SponsorResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@POST
	@Path("create")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Sponsor newSponsor(JAXBElement<Sponsor> jaxbSponsor) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Sponsor sponsor = jaxbSponsor.getValue();
		Entity eSponsor = new Entity(Sponsor.KIND);

		eSponsor = Util.setSponsorEntityProperties(eSponsor, sponsor);
		sponsor.setId(dataStore.put(eSponsor).getId());

		Version.setVersion();
		return sponsor;
	}

	@GET
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public SponsorWrapper getSponsor(@PathParam("id") Long id)
			throws EntityNotFoundException {
		Entity eSponsor = DatastoreServiceFactory.getDatastoreService().get(
				KeyFactory.createKey(Sponsor.KIND, id));
		Sponsor sponsor = Util.getSponsorFromEntity(eSponsor);

		return new SponsorWrapper(Version.getVersion(), sponsor);
	}

	@POST
	@Path("update/{id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public SponsorWrapper updateSponsorByID(@PathParam("id") Long id,
			JAXBElement<Sponsor> jaxbSponsor) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Entity eSponsor = new Entity(Sponsor.KIND, id);
		Sponsor sponsor = jaxbSponsor.getValue();
		eSponsor = Util.setSponsorEntityProperties(eSponsor, sponsor);
		sponsor.setId(id);
		dataStore.put(eSponsor);
		Version.setVersion();
		return new SponsorWrapper(Version.getVersion(), sponsor);
	}
	
	@DELETE
	@Path("delete/{id}")
	public void deleteSponsorByID(@PathParam("id") Long id) {
		DatastoreServiceFactory.getDatastoreService().delete(
				KeyFactory.createKey(Sponsor.KIND, id));
		Version.setVersion();
	}
}
