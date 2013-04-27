package org.gdgankara.app.resources;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@Path("/")
public class BaseResources {

	@GET
	@Path("program/{lang}")
	@Produces(MediaType.APPLICATION_JSON)
	public VersionWrapper getProgram(@PathParam("lang") String lang) {
		JSONArray jsonArray;
		ArrayList<Session> sessionList = new ArrayList<Session>();
		ArrayList<Session> temp14SessionList = new ArrayList<Session>();
		ArrayList<Session> temp15SessionList = new ArrayList<Session>();
		ArrayList<Speaker> speakerList = new ArrayList<Speaker>();
		ArrayList<String> sessionOrderList = new ArrayList<String>();

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
				speaker.setId(speakerObject.getInt("id"));
				speaker.setTitle(speakerObject.getString("title"));
				speaker.setUrl(speakerObject.getString("url"));
				speaker.setLang(lang);

				Element speakerContent = Jsoup.parse(speakerObject
						.getString("content"));
				speakerContent = speakerContent
						.removeClass("div.sharedaddy sd-sharing-enabled");
				speakerContent = speakerContent
						.removeClass("robots-nocontent sd-block sd-social sd-social-icon-text sd-sharing");
				speaker.setBio(speakerContent.text());

				if (!speakerObject.isNull("thumbnail")) {
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
				session.setId(postObject.getInt("id"));
				session.setTitle(postObject.getString("title"));
				session.setLang(lang);

				Element sessionContent = Jsoup.parse(postObject
						.getString("content"));
				if (sessionContent.select("p.session_meta").size() > 0) {
					String date[] = sessionContent.select("span.time").text()
							.split(",");

					if (date.length > 1) {
						session.setDay(date[0] + " ," + date[1] + " ,"
								+ date[2]);
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
						session.setBreak(true);
					} else {
						// This means unscheduled or break
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

					sessionContent
							.removeClass("div.sharedaddy sd-sharing-enabled");
					sessionContent.removeClass("p.session_meta");
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

		try {
			Session breakSession;
			Element rawTimeTable = Jsoup
					.parse(Util
							.doGet(new URL(
									"http://www.androiddeveloperdays.com/sessions/opening/?json=1&lang="
											+ lang)).getJSONObject("post")
							.getString("content")).select("table.grid tbody")
					.first();

			Elements nonSessionElements = rawTimeTable.getElementsByTag("tr");

			for (Element nonSessionElement : nonSessionElements) {
				breakSession = new Session();

				if (nonSessionElement.getElementsByClass("non-session").size() > 0) {
					breakSession.setStartHour(nonSessionElement
							.getElementsByClass("time_slot").get(0)
							.getElementsByTag("a").text());
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"hh:mmaa");
					Date time = dateFormat.parse(breakSession.getStartHour());
					breakSession.setStartHour(dateFormat.format(time)
							.toLowerCase());
					breakSession.setEndHour("");
					breakSession.setDay("14");
					breakSession.setBreak(true);
					if (lang == "tr") {
						breakSession.setTitle("Ara");
					} else {
						breakSession.setTitle("Break");
					}
					breakSession.setLang(lang);
					breakSession.setDescription("");

					if (breakSession.getStartHour() != null) {
						if (!sessionOrderList.contains(breakSession
								.getStartHour())) {
							sessionOrderList.add(breakSession.getStartHour());
						}
					}
					sessionList.add(breakSession);
				}
			}

			ArrayList<Date> dateList = new ArrayList<Date>();
			for (String string : sessionOrderList) {
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"hh:mmaa");
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
				sessionOrderList.add(dateFormat.format(date).toLowerCase());
			}

			for (String startHour : sessionOrderList) {
				for (Session session : sessionList) {
					if (session.getDay() != null) {
						if (session.getDay().contains("14")
								&& !temp14SessionList.contains(session)
								&& (session.getStartHour() == startHour || session
										.getStartHour().equals(startHour))) {
							temp14SessionList.add(session);
						} else if (session.getDay().contains("15")
								&& !temp15SessionList.contains(session)
								&& (session.getStartHour() == startHour || session
										.getStartHour().equals(startHour))) {
							temp15SessionList.add(session);
						}
					}
				}
			}
			sessionList = temp14SessionList;
			sessionList.addAll(temp15SessionList);

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

		return new VersionWrapper(getVersion(), sessionList, speakerList);
	}

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
	@Path("sponsors/{lang}")
	@Produces(MediaType.APPLICATION_JSON)
	public SponsorWrapper getSponsors(@PathParam("lang") String lang) {
		List<Entity> eSponsorList = getEntityListQuery(Sponsor.KIND,
				Sponsor.LANG, lang);
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
