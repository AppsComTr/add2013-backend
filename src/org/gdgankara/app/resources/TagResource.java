package org.gdgankara.app.resources;

import java.util.List;

import javax.ws.rs.Consumes;
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

import org.gdgankara.app.model.Tag;
import org.gdgankara.app.model.TagWrapper;
import org.gdgankara.app.model.Version;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

@Path("/tags")
public class TagResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@POST
	@Path("set")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public TagWrapper setTags(JAXBElement<Tag> jaxbTag) {
		// Firstly set en tags
		Tag tags = jaxbTag.getValue();
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query(Tag.KIND).addSort(Tag.LANG,
				SortDirection.ASCENDING);
		PreparedQuery preparedQuery = dataStore.prepare(query);
		List<Entity> eTagList = preparedQuery.asList(FetchOptions.Builder
				.withLimit(2));
		Entity eTag = null;

		int eTagListSize = eTagList.size();
		if (eTagListSize == 0) {
			eTag = new Entity(Tag.KIND);
			eTag.setProperty(Tag.TAGS, tags.getTags());
			eTag.setProperty(Tag.LANG, tags.getLang());
			dataStore.put(eTag);
		} else if (eTagListSize == 1) {
			if (tags.getLang().equals(Tag.LANG_EN)) {
				eTag = eTagList.get(0);
				eTag.setProperty(Tag.TAGS, tags.getTags());
				dataStore.put(eTag);
			} else if (tags.getLang().equals(Tag.LANG_TR)) {
				eTag = new Entity(Tag.KIND);
				eTag.setProperty(Tag.TAGS, tags.getTags());
				eTag.setProperty(Tag.LANG, tags.getLang());
				dataStore.put(eTag);
			}
		} else if (eTagListSize == 2) {
			if (tags.getLang().equals(Tag.LANG_EN)) {
				eTag = eTagList.get(0);
				eTag.setProperty(Tag.TAGS, tags.getTags());
				dataStore.put(eTag);
			} else if (tags.getLang().equals(Tag.LANG_TR)) {
				eTag = eTagList.get(1);
				eTag.setProperty(Tag.TAGS, tags.getTags());
				dataStore.put(eTag);
			}
		}

		tags.setId(dataStore.put(eTag).getId());
		return new TagWrapper(Version.getVersion(), tags);
	}

	@GET
	@Path("/{lang}")
	@Produces(MediaType.APPLICATION_JSON)
	public TagWrapper getTags(@PathParam("lang") String lang) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query(Tag.KIND).setFilter(new FilterPredicate(
				Tag.LANG, FilterOperator.EQUAL, lang));
		PreparedQuery preparedQuery = dataStore.prepare(query);
		Entity eTag = preparedQuery.asSingleEntity();

		Tag tags = new Tag(eTag.getKey().getId(),
				(String) eTag.getProperty(Tag.LANG),
				(String) eTag.getProperty(Tag.TAGS));
		return new TagWrapper(Version.getVersion(), tags);
	}
}
