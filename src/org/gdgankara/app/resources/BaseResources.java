package org.gdgankara.app.resources;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.gdgankara.app.model.Announcement;
import org.gdgankara.app.model.AnnouncementWrapper;
import org.gdgankara.app.model.Session;
import org.gdgankara.app.model.SessionWrapper;
import org.gdgankara.app.model.Speaker;
import org.gdgankara.app.model.SpeakerWrapper;
import org.gdgankara.app.model.Sponsor;
import org.gdgankara.app.model.SponsorWrapper;
import org.gdgankara.app.model.Version;
import org.gdgankara.app.model.VersionWrapper;
import org.gdgankara.app.utils.Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@Path("/")
public class BaseResources {
	public static final String WRAPPER_CACHE = "wrapper_cache_";

	@GET
	@Path("program/update/{lang}")
	@Produces(MediaType.APPLICATION_JSON)
	public VersionWrapper updateProgram(@PathParam("lang") String lang)
			throws EntityNotFoundException {
		JSONArray jsonArray;
		int tempSessionId = 0;
		ArrayList<Announcement> announcementList = new ArrayList<Announcement>();
		ArrayList<Session> sessionList = new ArrayList<Session>();
		ArrayList<Session> breakSessionList = new ArrayList<Session>();
		ArrayList<Session> temp14SessionList = new ArrayList<Session>();
		ArrayList<Session> temp15SessionList = new ArrayList<Session>();
		ArrayList<Speaker> speakerList = new ArrayList<Speaker>();
		ArrayList<String> sessionOrderList = new ArrayList<String>();
		ArrayList<Sponsor> sponsorList = new ArrayList<Sponsor>();

		try {
			JSONObject announcementObjects = Util.doGet(new URL(
					"http://www.androiddeveloperdays.com/api/get_category_posts/?id=18&lang="
							+ lang));
			jsonArray = announcementObjects.getJSONArray("posts");
			JSONObject announcementObject;
			Announcement announcement;
			int announcementLength = jsonArray.length();

			for (int i = 0; i < announcementLength; i++) {
				announcementObject = (JSONObject) jsonArray.get(i);
				announcement = new Announcement();
				announcement.setId(announcementObject.getLong("id"));
				announcement.setDescription(Jsoup.parse(
						announcementObject.getString("content")).text());
				announcement.setLang(lang);
				announcement.setLink(announcementObject.getString("url"));
				announcement.setTitle(Jsoup.parse(
						announcementObject.getString("title")).text());
				JSONArray attachmentArray = (JSONArray) announcementObject
						.getJSONArray("attachments");
				if (attachmentArray.length() > 0) {
					String image = ((JSONObject) attachmentArray.get(0))
							.getString("url");
					announcement.setImage(image);
				} else if (announcementObject.has("thumbnail")
						&& !announcementObject.isNull("thumbnail")) {
					String image = announcementObject.getString("thumbnail");
					announcement.setImage(image);
				}
				announcementList.add(announcement);
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

		try {
			JSONObject sponsorsObject = Util.doGet(new URL(
					"http://www.androiddeveloperdays.com/sponsors/?json=1&lang="
							+ lang));
			Element eSponsors = Jsoup.parse(sponsorsObject
					.getJSONObject("page").getString("content"));
			int categorySize = eSponsors.select("h3.sponsors").size();
			int sponsorId = sponsorsObject.getJSONObject("page").getInt("id");

			for (int i = 0; i < categorySize; i++) {
				Element sponsorsTable = eSponsors.select("table").get(i);
				String category = eSponsors.select("h3").get(i).text();
				Sponsor sponsor = null;

				int rowCount = sponsorsTable.select("tr").size();
				for (int j = 0; j < rowCount; j++) {
					Element sponsorsRow = sponsorsTable.select("tr").get(j);

					int columnCount = sponsorsRow.select("td").size();
					for (int k = 0; k < columnCount; k++) {
						sponsor = new Sponsor();
						Element eSponsor = sponsorsRow.select("td").get(k);
						if (eSponsor.getElementsByTag("a").attr("href") != null
								&& eSponsor.getElementsByTag("a").attr("href") != "") {
							sponsorId++;
							sponsor.setId(((long) sponsorId));
							sponsor.setLink(eSponsor.getElementsByTag("a")
									.attr("href"));
							sponsor.setImage(eSponsor.select("a > img").attr(
									"src"));
							sponsor.setCategory(category);
							sponsorList.add(sponsor);
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

		try {
			JSONObject speakerObjects = Util
					.doGet(new URL(
							"http://www.androiddeveloperdays.com/api/get_recent_posts/?post_type=speaker&count=99&lang="
									+ lang));
			jsonArray = speakerObjects.getJSONArray("posts");
			JSONObject speakerObject;
			Speaker speaker;
			int speakerLength = jsonArray.length();

			for (int i = 0; i < speakerLength; i++) {
				speakerObject = (JSONObject) jsonArray.get(i);
				speaker = new Speaker();
				speaker.setPostId(speakerObject.getInt("id"));
				speaker.setId(speakerObject.getInt("id"));
				speaker.setName(Jsoup.parse(speakerObject.getString("title"))
						.text());
				speaker.setUrl(speakerObject.getString("url"));
				speaker.setLang(lang);

				Element speakerContent = Jsoup.parse(speakerObject
						.getString("content"));
				speakerContent.select("div.sharedaddy").remove();
				speakerContent.select("p.session_meta").remove();
				speaker.setBio(speakerContent.text());

				JSONArray attachmentArray = (JSONArray) speakerObject
						.getJSONArray("attachments");
				if (attachmentArray.length() > 0) {
					String speakerThumbnail = ((JSONObject) attachmentArray
							.get(0)).getJSONObject("images")
							.getJSONObject("medium").getString("url");
					speaker.setPhoto(speakerThumbnail);
				} else if (!speakerObject.isNull("thumbnail")) {
					speaker.setPhoto(speakerObject.getString("thumbnail"));
				} else {
					speaker.setPhoto("http://www.gdgankara.org/wp-content/themes/alltuts2/images/ico_author.png");
				}

				speakerList.add(speaker);
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

		try {
			JSONObject postsObject = Util
					.doGet(new URL(
							"http://www.androiddeveloperdays.com/api/get_recent_posts/?post_type=session&count=99&lang="
									+ lang));
			jsonArray = postsObject.getJSONArray("posts");
			JSONObject postObject;
			Session session;
			int postLength = jsonArray.length();

			for (int i = 0; i < postLength; i++) {
				postObject = (JSONObject) jsonArray.get(i);
				session = new Session();
				String tags = "";
				tempSessionId = postObject.getInt("id") + 2000;
				session.setId(postObject.getInt("id"));
				session.setTitle(Jsoup.parse(postObject.getString("title"))
						.text());
				session.setLang(lang);

				JSONArray tagsArray = postObject.getJSONArray("tags");
				if (tagsArray.length() > 0) {
					for (int j = 0; j < tagsArray.length(); j++) {
						tags += tagsArray.getJSONObject(j).getString("title");
						tags += j == tagsArray.length() - 1 ? "" : ",";
					}
					session.setTags(tags);
				} else {
					session.setTags(null);
				}

				Element sessionContent = Jsoup.parse(postObject
						.getString("content"));
				if (sessionContent.select("p.session_meta").size() > 0) {
					String date[] = sessionContent.select("span.time").text()
							.split(",");

					if (date.length > 1) {
						session.setDay(date[0] + "," + date[1] + "," + date[2]);
						session.setStartHour(date[3].split("–")[0].trim());
						session.setEndHour(date[3].split("–")[1].trim());

						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"hh:mmaa");
						Date time = dateFormat.parse(session.getStartHour());
						session.setStartHour(dateFormat.format(time)
								.toLowerCase());

						time = dateFormat.parse(session.getEndHour());
						session.setEndHour(dateFormat.format(time)
								.toLowerCase());

						String hall = sessionContent.select("span.room > a")
								.text();
						if (hall.contains("D")) {
							hall = "D";
						} else if (hall.contains("C")) {
							hall = "C";
						} else if (hall.contains("B")) {
							hall = "B";
						} else {
							hall = "A";
						}
						session.setHall(hall);
						session.setBreak(false);
					} else {
						session.setDay(null);
						session.setStartHour(null);
						session.setEndHour(null);
						session.setHall(null);
						session.setBreak(true);
					}

					if (sessionContent.select("span.speakers > a")
							.select("[href]").size() > 0) {
						List<String> speakerUrlList = new ArrayList<String>();
						Elements speakers = sessionContent
								.select("span.speakers > a");
						for (Element element : speakers) {
							speakerUrlList.add(element.attr("href"));
						}

						session.setSpeakerUrlList(speakerUrlList);
					}

					sessionContent.select("div.sharedaddy").remove();
					sessionContent.select("p.session_meta").remove();

					session.setDescription(sessionContent.text());
					sessionList.add(session);

					if (session.getDay() != null
							&& session.getStartHour() != null) {
						sessionOrderList.add(session.getStartHour());
					}
				}
			}

		} catch (MalformedURLException e) {
			System.out.println(e);
			e.printStackTrace();
		} catch (JSONException e) {
			System.out.println(e);
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println(e);
			e.printStackTrace();
		}

		try {
			Session breakSession;
			String day = "";

			Element rawTimeTable = Jsoup
					.parse(Util
							.doGet(new URL(
									"http://www.androiddeveloperdays.com/schedule/?json=1&lang="
											+ lang)).getJSONObject("page")
							.getString("content")).select("table.grid tbody")
					.first();

			Elements sessionElements = rawTimeTable.getElementsByTag("tr");
			SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mmaa");

			for (Element sessionElement : sessionElements) {
				if (sessionElement.getElementsByClass("day").size() > 0) {
					if (sessionElement.text().contains("14")) {
						day = "14";
					} else {
						day = "15";
					}
				} else if (sessionElement.getElementsByClass("non-session")
						.size() > 0) {
					breakSession = new Session();
					breakSession.setBreak(true);
					String[] hours = sessionElement.select("td.time_slot > a")
							.text().trim().split("–");
					breakSession.setStartHour(dateFormat.format(
							dateFormat.parse(hours[0])).toLowerCase());
					breakSession.setEndHour(dateFormat.format(
							dateFormat.parse(hours[1])).toLowerCase());

					breakSession.setTitle(sessionElement.select(
							"td.sessions > p > a").text());
					breakSession.setHall("");
					breakSession.setLang(lang);
					breakSession.setDescription("");
					breakSession.setDay(day);
					breakSession.setTags("");
					breakSession.setUrl("");

					if (lang.equals("tr")) {
						if (!breakSession.getTitle().toString().equals("Kayıt")) {
							breakSessionList.add(breakSession);
							sessionOrderList.add(breakSession.getStartHour());
						}
					} else {
						if (!breakSession.getTitle().toString()
								.equals("Registration")) {
							breakSessionList.add(breakSession);
							sessionOrderList.add(breakSession.getStartHour());
						}
					}

				}
			}

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

		ArrayList<Date> dateList = new ArrayList<Date>();
		for (String string : sessionOrderList) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mmaa");
				Date date = dateFormat.parse(string);
				dateList.add(date);
			} catch (ParseException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
		Collections.sort(dateList);
		sessionOrderList = new ArrayList<String>();
		for (Date date : dateList) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mmaa");
			if (!sessionOrderList.contains(dateFormat.format(date)
					.toLowerCase())) {
				sessionOrderList.add(dateFormat.format(date).toLowerCase());
			}
		}

		sessionList.addAll(breakSessionList);
		for (String startHour : sessionOrderList) {
			for (Session session : sessionList) {
				if (session.getDay() != null) {
					if (session.getDay().contains("14")
							&& (session.getStartHour() == startHour || session
									.getStartHour().equals(startHour))) {
						temp14SessionList.add(session);
					} else if (session.getDay().contains("15")
							&& (session.getStartHour() == startHour || session
									.getStartHour().equals(startHour))) {
						temp15SessionList.add(session);
					}
				}
			}
		}
		sessionList = temp14SessionList;
		sessionList.addAll(temp15SessionList);

		for (Session session : sessionList) {
			List<Long> tempSpeakerIdList = new ArrayList<Long>();
			if (session.getSpeakerUrlList() != null) {
				for (String speakerUrl : session.getSpeakerUrlList()) {
					for (Speaker speaker : speakerList) {
						if (speaker.getUrl() == speakerUrl
								|| speaker.getUrl().equals(speakerUrl)) {
							tempSpeakerIdList.add(speaker.getId());
						}
					}
				}
				session.setSpeakerIDList(tempSpeakerIdList);
			}
		}

		//

		for (Sponsor sponsor : sponsorList) {
			sponsor.setId(KeyFactory.createKey(Sponsor.KIND, sponsor.getId())
					.getId());
		}

		for (Announcement announcement : announcementList) {
			announcement.setId(KeyFactory.createKey(Announcement.KIND,
					announcement.getId()).getId());
		}

		for (Speaker speaker : speakerList) {
			speaker.setId(KeyFactory.createKey(Speaker.KIND, speaker.getId())
					.getId());
		}

		for (Session session : sessionList) {
			List<Long> speakerPostIDList = session.getSpeakerIDList();
			List<Long> speakerIDList = new ArrayList<Long>();

			if (session.getId() != 0) {
				session.setId(KeyFactory.createKey(Session.KIND,
						session.getId()).getId());
			} else {
				session.setId(KeyFactory.createKey(Session.KIND,
						tempSessionId++).getId());
			}

			if (!session.isBreak() && speakerPostIDList != null) {
				int length = speakerPostIDList.size();
				for (int i = 0; i < length; i++) {
					Long speakerPostID = speakerPostIDList.get(i);
					if (speakerPostID != null) {
						for (Speaker speaker : speakerList) {
							if (speaker.getPostId() == speakerPostID) {
								speakerIDList.add(speaker.getId());

								List<Long> sessionIDList = speaker
										.getSessionIDList();
								if (sessionIDList == null) {
									sessionIDList = new ArrayList<Long>();
								}

								sessionIDList.add(session.getId());
								speaker.setSessionIDList(sessionIDList);
							}
						}
						session.setSpeakerIDList(speakerIDList);
					} else {
						speakerPostIDList.set(i, null);
					}
				}
				speakerIDList = new ArrayList<Long>();
			}
		}

		VersionWrapper versionWrapper = new VersionWrapper(getVersion(),
				sessionList, speakerList, announcementList, sponsorList);
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		syncCache.setErrorHandler(ErrorHandlers
				.getConsistentLogAndContinue(Level.INFO));
		syncCache.put(WRAPPER_CACHE + lang, versionWrapper);

		return versionWrapper;
	}

	@GET
	@Path("program/{lang}")
	@Produces(MediaType.APPLICATION_JSON)
	public VersionWrapper getProgram(@PathParam("lang") String lang)
			throws EntityNotFoundException {
		VersionWrapper versionWrapper;
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		syncCache.setErrorHandler(ErrorHandlers
				.getConsistentLogAndContinue(Level.INFO));
		versionWrapper = (VersionWrapper) syncCache.get(WRAPPER_CACHE + lang);
		if (versionWrapper == null) {
			versionWrapper = (VersionWrapper) updateProgram(lang);
		}
		return versionWrapper;
	}

	@GET
	@Path("sessions/{lang}")
	@Produces(MediaType.APPLICATION_JSON)
	public SessionWrapper getSessions(@PathParam("lang") String lang) {
		List<Session> sessionsList = new ArrayList<Session>();
		List<Session> tempSessionsList = new ArrayList<Session>();
		List<String> sessionOrderList = new ArrayList<String>();

		List<Entity> eSessionList = getEntityListQuery(Session.KIND,
				Session.LANG, lang);

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
		return new SessionWrapper(version, sessionsList);
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
	@Path("sponsors")
	@Produces(MediaType.APPLICATION_JSON)
	public SponsorWrapper getSponsors() {
		List<Entity> eSponsorList;

		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();

		Query query = new Query(Sponsor.KIND);
		PreparedQuery preparedQuery = dataStore.prepare(query);
		eSponsorList = preparedQuery
				.asList(FetchOptions.Builder.withDefaults());

		List<Sponsor> sponsorList = new ArrayList<Sponsor>();

		for (Entity entity : eSponsorList) {
			Sponsor sponsor = Util.getSponsorFromEntity(entity);
			sponsorList.add(sponsor);
		}
		return new SponsorWrapper(Version.getVersion(), sponsorList);
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
	// @Path("godkiller/{arg}")
	// @Produces(MediaType.TEXT_PLAIN)
	// public String godKiller(@PathParam("arg") String arg) {
	// if (arg == "db") {
	// DatastoreService dataStore = DatastoreServiceFactory
	// .getDatastoreService();
	// Query godKillerQuery = new Query();
	// PreparedQuery preparedGodKillerQuery = dataStore
	// .prepare(godKillerQuery);
	// for (Entity entity : preparedGodKillerQuery.asIterable()) {
	// dataStore.delete(entity.getKey());
	// }
	// } else if (arg == "cache") {
	// MemcacheService syncCache = MemcacheServiceFactory
	// .getMemcacheService();
	// syncCache.setErrorHandler(ErrorHandlers
	// .getConsistentLogAndContinue(Level.INFO));
	// syncCache.clearAll();
	// }
	//
	// return "hayırlısı be gülüm";
	// }
}
