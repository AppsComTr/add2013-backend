package org.gdgankara.app.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.gdgankara.app.model.Announcement;
import org.gdgankara.app.model.Session;
import org.gdgankara.app.model.Speaker;
import org.gdgankara.app.model.Sponsor;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class Util {

	public static JSONObject doGet(URL url) throws JSONException {
		JSONObject jsonObject = null;
		try {

			URLConnection urlConnection = url.openConnection();
			urlConnection.setConnectTimeout(50000);
			jsonObject = new JSONObject(
					convertInputStreamToString(urlConnection.getInputStream()));
		} catch (Exception e) {
			System.err.println("errür" + e);
		}
		return jsonObject;
	}

	public static String convertInputStreamToString(InputStream inputStream) {
		String result = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream), 8);
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}
			inputStream.close();
			result = stringBuilder.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Announcement getAnnouncementFromEntity(Entity eAnnouncement) {
		Announcement announcement = new Announcement(eAnnouncement.getKey()
				.getId(),
				(String) eAnnouncement.getProperty(Announcement.DESCRIPTION),
				(String) eAnnouncement.getProperty(Announcement.IMAGE),
				(Boolean) eAnnouncement.getProperty(Announcement.IS_SESSION),
				(String) eAnnouncement.getProperty(Announcement.LANG),
				(String) eAnnouncement.getProperty(Announcement.LINK),
				(Long) eAnnouncement.getProperty(Announcement.SESSION_ID),
				(String) eAnnouncement.getProperty(Announcement.TITLE));
		return announcement;
	}

	@SuppressWarnings("unchecked")
	public static Session getSessionFromEntity(Entity eSession) {
		Session session = new Session(eSession.getKey().getId(),
				(String) eSession.getProperty(Session.LANG),
				(String) eSession.getProperty(Session.DAY),
				(String) eSession.getProperty(Session.START_HOUR),
				(String) eSession.getProperty(Session.END_HOUR),
				(String) eSession.getProperty(Session.HALL),
				(String) eSession.getProperty(Session.TITLE),
				(String) eSession.getProperty(Session.DESCRIPTION),
				(Boolean) eSession.getProperty(Session.BREAK),
				(List<Long>) eSession.getProperty(Session.SPEAKER_LIST),
				(String) eSession.getProperty(Session.TAGS));
		return session;
	}

	@SuppressWarnings("unchecked")
	public static Speaker getSpeakerFromEntity(Entity eSpeaker) {
		Speaker speaker = new Speaker(eSpeaker.getKey().getId(),
				(String) eSpeaker.getProperty(Speaker.BIO),
				(String) eSpeaker.getProperty(Speaker.BLOG),
				(String) eSpeaker.getProperty(Speaker.FACEBOOK),
				(String) eSpeaker.getProperty(Speaker.GPLUS),
				(String) eSpeaker.getProperty(Speaker.LANG),
				(String) eSpeaker.getProperty(Speaker.NAME),
				(String) eSpeaker.getProperty(Speaker.PHOTO),
				(String) eSpeaker.getProperty(Speaker.TWITTER),
				(List<Long>) eSpeaker.getProperty(Speaker.SESSION_LIST),
				(String) eSpeaker.getProperty(Speaker.TITLE));
		return speaker;
	}

	public static Sponsor getSponsorFromEntity(Entity eSponsor) {
		Sponsor sponsor = new Sponsor(eSponsor.getKey().getId(),
				(String) eSponsor.getProperty(Sponsor.DESCRIPTION),
				(String) eSponsor.getProperty(Sponsor.IMAGE),
				(String) eSponsor.getProperty(Sponsor.LANG),
				(String) eSponsor.getProperty(Sponsor.LINK),
				(String) eSponsor.getProperty(Sponsor.NAME));
		return sponsor;
	}

	public static Entity setAnnouncementEntityProperties(Entity entity,
			Announcement announcement) {
		entity.setProperty(Announcement.LANG, announcement.getLang());
		entity.setProperty(Announcement.DESCRIPTION,
				announcement.getDescription());
		entity.setProperty(Announcement.IMAGE, announcement.getImage());
		entity.setProperty(Announcement.IS_SESSION, announcement.isSession());
		entity.setProperty(Announcement.LINK, announcement.getLink());
		entity.setProperty(Announcement.SESSION_ID, announcement.getSessionId());
		entity.setProperty(Announcement.TITLE, announcement.getTitle());
		return entity;
	}

	public static Entity setSessionEntityProperties(Entity entity,
			Session session) {
		entity.setProperty(Session.LANG, session.getLang());
		entity.setProperty(Session.DAY, session.getDay());
		entity.setProperty(Session.START_HOUR, session.getStartHour());
		entity.setProperty(Session.END_HOUR, session.getEndHour());
		entity.setProperty(Session.HALL, session.getHall());
		entity.setProperty(Session.BREAK, session.isBreak());
		entity.setProperty(Session.SPEAKER_LIST, session.getSpeakerIDList());
		entity.setProperty(Session.TITLE, session.getTitle());
		entity.setProperty(Session.DESCRIPTION, session.getDescription());
		entity.setProperty(Session.TAGS, session.getTags());
		return entity;
	}

	public static Entity setSpeakerEntityProperties(Entity entity,
			Speaker speaker) {
		entity.setProperty(Speaker.BIO, speaker.getBio());
		entity.setProperty(Speaker.BLOG, speaker.getBlog());
		entity.setProperty(Speaker.FACEBOOK, speaker.getFacebook());
		entity.setProperty(Speaker.GPLUS, speaker.getGplus());
		entity.setProperty(Speaker.LANG, speaker.getLang());
		entity.setProperty(Speaker.NAME, speaker.getName());
		entity.setProperty(Speaker.PHOTO, speaker.getPhoto());
		entity.setProperty(Speaker.TWITTER, speaker.getTwitter());
		entity.setProperty(Speaker.SESSION_LIST, speaker.getSessionIDList());
		entity.setProperty(Speaker.TITLE, speaker.getTitle());
		return entity;
	}

	public static Entity setSponsorEntityProperties(Entity entity,
			Sponsor sponsor) {
		entity.setProperty(Sponsor.DESCRIPTION, sponsor.getDescription());
		entity.setProperty(Sponsor.IMAGE, sponsor.getImage());
		entity.setProperty(Sponsor.LANG, sponsor.getLang());
		entity.setProperty(Sponsor.LINK, sponsor.getLink());
		entity.setProperty(Sponsor.NAME, sponsor.getName());
		return entity;
	}

}
