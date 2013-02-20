package org.gdgankara.app.resources;

import java.io.IOException;

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

import org.gdgankara.app.model.Speaker;
import org.gdgankara.app.model.Version;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;

@Path("/speaker")
public class SpeakerResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@POST
	@Path("create")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public void newSpeakerJson(JAXBElement<Speaker> jaxbSpeaker)
			throws IOException {
		Entity eSpeaker = new Entity(Speaker.KIND);
		Speaker speaker = jaxbSpeaker.getValue();
		eSpeaker.setProperty(Speaker.BIO, speaker.getBio());
		eSpeaker.setProperty(Speaker.BLOG, speaker.getBlog());
		eSpeaker.setProperty(Speaker.FACEBOOK, speaker.getFacebook());
		eSpeaker.setProperty(Speaker.GPLUS, speaker.getGplus());
		eSpeaker.setProperty(Speaker.LANG, speaker.getLang());
		eSpeaker.setProperty(Speaker.NAME, speaker.getName());
		eSpeaker.setProperty(Speaker.PHOTO, speaker.getPhoto());
		eSpeaker.setProperty(Speaker.TWITTER, speaker.getTwitter());
		DatastoreServiceFactory.getDatastoreService().put(eSpeaker);
		Version.setVersion();
	}

	@POST
	@Path("update/{id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Speaker updateSpeakerByID(@PathParam("id") Long id,
			JAXBElement<Speaker> jaxbSpeaker) throws EntityNotFoundException {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Speaker speaker = jaxbSpeaker.getValue();
		Entity eSpeaker = dataStore.get(KeyFactory.createKey(Speaker.KIND, id));
		eSpeaker.setProperty(Speaker.BIO, speaker.getBio());
		eSpeaker.setProperty(Speaker.BLOG, speaker.getBlog());
		eSpeaker.setProperty(Speaker.FACEBOOK, speaker.getFacebook());
		eSpeaker.setProperty(Speaker.GPLUS, speaker.getGplus());
		eSpeaker.setProperty(Speaker.LANG, speaker.getLang());
		eSpeaker.setProperty(Speaker.NAME, speaker.getName());
		eSpeaker.setProperty(Speaker.PHOTO, speaker.getPhoto());
		eSpeaker.setProperty(Speaker.TWITTER, speaker.getTwitter());
		dataStore.put(eSpeaker);
		Version.setVersion();
		return speaker;
	}

	@DELETE
	@Path("delete/{id}")
	public void deleteSpeakerByID(@PathParam("id") Long id) {
		DatastoreServiceFactory.getDatastoreService().delete(
				KeyFactory.createKey(Speaker.KIND, id));
		Version.setVersion();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Speaker getSpeakerByID(@PathParam("id") Long id)
			throws EntityNotFoundException {

		Entity eSpeaker = DatastoreServiceFactory.getDatastoreService().get(
				KeyFactory.createKey(Speaker.KIND, id));
		Speaker speaker = new Speaker(eSpeaker.getKey().getId(),
				(String) eSpeaker.getProperty(Speaker.BIO),
				(String) eSpeaker.getProperty(Speaker.BLOG),
				(String) eSpeaker.getProperty(Speaker.FACEBOOK),
				(String) eSpeaker.getProperty(Speaker.GPLUS),
				(String) eSpeaker.getProperty(Speaker.LANG),
				(String) eSpeaker.getProperty(Speaker.NAME),
				(String) eSpeaker.getProperty(Speaker.PHOTO),
				(String) eSpeaker.getProperty(Speaker.TWITTER));
		return speaker;
	}
}
