package org.gdgankara.app.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.gdgankara.app.model.Session;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@Path("/")
public class BaseResources {

	@GET
	@Path("sessions/{lang}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Session> getSessions(@PathParam("lang") String lang) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Query sessionQuery = new Query(Session.KIND);
		Filter filter = new FilterPredicate(Session.LANG, FilterOperator.EQUAL, lang);
		sessionQuery.setFilter(filter);
		
		PreparedQuery preparedQuery = dataStore.prepare(sessionQuery);
		List<Entity> eSessionList = preparedQuery.asList(FetchOptions.Builder
				.withDefaults());
		List<Session> sessionList = new ArrayList<Session>();
		for (Entity entity : eSessionList) {
			Session session = new Session(entity.getKey().getId(),
					(String) entity.getProperty(Session.LANG),
					(String) entity.getProperty(Session.DAY),
					(String) entity.getProperty(Session.START_HOUR),
					(String) entity.getProperty(Session.END_HOUR),
					(String) entity.getProperty(Session.HALL),
					(String) entity.getProperty(Session.TITLE),
					(String) entity.getProperty(Session.DESCRIPTION),
					(String) entity.getProperty(Session.SPEAKER));
			sessionList.add(session);
		}
		return sessionList;
	}
	
	@GET
	@Path("godkiller")
	@Produces(MediaType.TEXT_PLAIN)
	public String godKiller(){
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		Query godKillerQuery = new Query();
		PreparedQuery preparedGodKillerQuery = dataStore.prepare(godKillerQuery);
		for (Entity entity : preparedGodKillerQuery.asIterable()){
			dataStore.delete(entity.getKey());
		}
		return "hayırlısı be gülüm";
	}
}
