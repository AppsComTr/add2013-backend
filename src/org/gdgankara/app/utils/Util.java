package org.gdgankara.app.utils;

import java.util.List;

import org.gdgankara.app.model.Session;
import org.gdgankara.app.model.Speaker;

import com.google.appengine.api.datastore.Entity;

public class Util {

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
				(List<Long>) eSpeaker.getProperty(Speaker.SESSION_LIST));
		return speaker;
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
				(Boolean) eSession.getProperty(Session.BREAK), null, null,
				null, (List<Long>) eSession.getProperty(Session.SPEAKER_LIST));
		return session;
	}

	public static Entity setSessionEntityProperties(Entity entity,
			Session session, List<Long> speakerIDList) {
		entity.setProperty(Session.LANG, session.getLang());
		entity.setProperty(Session.DAY, session.getDay());
		entity.setProperty(Session.START_HOUR, session.getStartHour());
		entity.setProperty(Session.END_HOUR, session.getEndHour());
		entity.setProperty(Session.HALL, session.getHall());
		entity.setProperty(Session.BREAK, session.isBreak());
		entity.setProperty(Session.SPEAKER_LIST, speakerIDList);
		entity.setProperty(Session.TITLE, session.getTitle());
		entity.setProperty(Session.DESCRIPTION, session.getDescription());
		return entity;
	}

}
