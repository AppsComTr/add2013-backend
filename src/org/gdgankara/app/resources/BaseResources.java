package org.gdgankara.app.resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.gdgankara.app.model.Announcement;
import org.gdgankara.app.model.AnnouncementWrapper;
import org.gdgankara.app.model.Session;
import org.gdgankara.app.model.Speaker;
import org.gdgankara.app.model.SpeakerWrapper;
import org.gdgankara.app.model.Sponsor;
import org.gdgankara.app.model.Version;
import org.gdgankara.app.model.VersionWrapper;
import org.gdgankara.app.utils.Util;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@Path("/")
public class BaseResources {

	@GET
	@Path("sessions/{lang}")
	@Produces(MediaType.APPLICATION_JSON)
	public VersionWrapper getSessions(@PathParam("lang") String lang) {
		List<Announcement> announcementList = new ArrayList<Announcement>();
		List<Session> sessionsList = new ArrayList<Session>();
		List<Session> tempSessionsList = new ArrayList<Session>();
		List<String> sessionOrderList = new ArrayList<String>();
		List<Speaker> speakerList = new ArrayList<Speaker>();
		List<Sponsor> sponsorList = new ArrayList<Sponsor>();

		List<Entity> eAnnouncementList = getEntityListQuery(Announcement.KIND,
				Announcement.LANG, lang);
		List<Entity> eSessionList = getEntityListQuery(Session.KIND,
				Session.LANG, lang);
		List<Entity> eSpeakerList = getEntityListQuery(Speaker.KIND,
				Speaker.LANG, lang);
		List<Entity> eSponsorList = getEntityListQuery(Sponsor.KIND,
				Sponsor.LANG, lang);

		for (Entity eSpeaker : eSpeakerList) {
			Speaker speaker = Util.getSpeakerFromEntity(eSpeaker);
			speakerList.add(speaker);
		}

		for (Entity entity : eSessionList) {
			Session session = Util.getSessionFromEntity(entity);
			sessionsList.add(session);

			if (!sessionOrderList.isEmpty()
					&& !sessionOrderList.contains(session.getStartHour())) {
				sessionOrderList.add(session.getStartHour());
			} else {
				sessionOrderList.add(session.getStartHour());
			}
		}
		Collections.sort(sessionOrderList);

		for (Entity entity : eAnnouncementList) {
			Announcement announcement = Util.getAnnouncementFromEntity(entity);
			announcementList.add(announcement);
		}

		for (Entity entity : eSponsorList) {
			Sponsor sponsor = Util.getSponsorFromEntity(entity);
			sponsorList.add(sponsor);
		}

		for (String startHour : sessionOrderList) {
			for (Session session : sessionsList) {
				if (session.getDay().startsWith("14")
						&& session.getStartHour() == startHour) {
					tempSessionsList.add(session);
				}
			}
		}

		for (String startHour : sessionOrderList) {
			for (Session session : sessionsList) {
				if (session.getDay().startsWith("15")
						&& session.getStartHour() == startHour) {
					tempSessionsList.add(session);
				}
			}
		}

		sessionsList = tempSessionsList;
		Version version = Version.getVersion();
		return new VersionWrapper(version, sessionsList, speakerList,
				announcementList, sponsorList);
	}

	@GET
	@Path("speakers/{lang}")
	@Produces(MediaType.APPLICATION_JSON)
	public SpeakerWrapper getSpeakers(@PathParam("lang") String lang) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Query spekaerQuery = new Query(Speaker.KIND)
				.setFilter(new FilterPredicate(Speaker.LANG,
						FilterOperator.EQUAL, lang));
		PreparedQuery preparedQuery = dataStore.prepare(spekaerQuery);

		List<Entity> eSpeakerList = preparedQuery.asList(FetchOptions.Builder
				.withDefaults());
		List<Speaker> speakerList = new ArrayList<Speaker>();

		for (Entity eSpeaker : eSpeakerList) {
			Speaker speaker = Util.getSpeakerFromEntity(eSpeaker);
			speakerList.add(speaker);
		}

		Version version = Version.getVersion();
		return new SpeakerWrapper(version, speakerList);
	}

	@GET
	@Path("announcements/{lang}")
	@Produces(MediaType.APPLICATION_JSON)
	public AnnouncementWrapper getAnnouncements(@PathParam("lang") String lang) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query(Announcement.KIND)
				.setFilter(new FilterPredicate(Session.LANG,
						FilterOperator.EQUAL, lang));
		PreparedQuery preparedQuery = dataStore.prepare(query);
		List<Entity> eAnnouncementList = preparedQuery
				.asList(FetchOptions.Builder.withDefaults());
		List<Announcement> announcementList = new ArrayList<Announcement>();
		for (Entity eAnnouncement : eAnnouncementList) {
			Announcement announcement = Util
					.getAnnouncementFromEntity(eAnnouncement);
			announcementList.add(announcement);
		}

		return new AnnouncementWrapper(Version.getVersion(), announcementList);
	}

	@GET
	@Path("version/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Version getVersion() {
		return Version.getVersion();
	}

	@GET
	@Path("version/set")
	@Produces(MediaType.APPLICATION_JSON)
	public Version setVersion() {
		return Version.setVersion();
	}

	public List<Entity> getEntityListQuery(String kind, String kindLang,
			String queryLang) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();

		Query query = new Query(kind).setFilter(new FilterPredicate(kindLang,
				FilterOperator.EQUAL, queryLang));
		PreparedQuery preparedQuery = dataStore.prepare(query);
		List<Entity> entityList = preparedQuery.asList(FetchOptions.Builder
				.withDefaults());
		return entityList;
	}

	// @GET
	// @Path("godkiller")
	// @Produces(MediaType.TEXT_PLAIN)
	// public String godKiller() {
	// DatastoreService dataStore = DatastoreServiceFactory
	// .getDatastoreService();
	// Query godKillerQuery = new Query();
	// PreparedQuery preparedGodKillerQuery = dataStore
	// .prepare(godKillerQuery);
	// for (Entity entity : preparedGodKillerQuery.asIterable()) {
	// dataStore.delete(entity.getKey());
	// }
	// return "hayırlısı be gülüm";
	// }
}
