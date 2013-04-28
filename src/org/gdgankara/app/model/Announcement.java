package org.gdgankara.app.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Announcement implements Serializable{
	private static final long serialVersionUID = -5242875470684972564L;
	public static final String KIND = "announcements";
	public static final String LANG = "Lang";
	public static final String LANG_TR = "tr";
	public static final String LANG_EN = "en";
	public static final String DESCRIPTION = "Description";
	public static final String IMAGE = "Image";
	public static final String IS_SESSION = "IsSession";
	public static final String LINK = "Link";
	public static final String SESSION_ID = "SessionId";
	public static final String TITLE = "Title";

	private long id;
	private String description;
	private String image;
	private boolean isSession;
	private String lang;
	private String link;
	private Long sessionId;
	private String title;

	public Announcement() {
		super();
	}

	public Announcement(String description, String image, boolean isSession, String lang,
			String link, String title) {
		super();
		this.description = description;
		this.image = image;
		this.isSession = isSession;
		this.lang = lang;
		this.link = link;
		this.title = title;
	}
	
	public Announcement(String description, String image, boolean isSession, String lang,
			String link, Long sessionId, String title) {
		super();
		this.description = description;
		this.image = image;
		this.isSession = isSession;
		this.lang = lang;
		this.link = link;
		this.sessionId = sessionId;
		this.title = title;
	}

	public Announcement(long id, String description, String image, boolean isSession, String lang,
			String link, String title) {
		super();
		this.id = id;
		this.description = description;
		this.image = image;
		this.isSession = isSession;
		this.lang = lang;
		this.link = link;
		this.title = title;
	}
	
	public Announcement(long id, String description, String image, boolean isSession, String lang,
			String link, Long sessionId, String title) {
		super();
		this.id = id;
		this.description = description;
		this.image = image;
		this.isSession = isSession;
		this.lang = lang;
		this.link = link;
		this.sessionId = sessionId;
		this.title = title;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public boolean isSession() {
		return isSession;
	}

	public void setSession(boolean isSession) {
		this.isSession = isSession;
	}

	public Long getSessionId() {
		return sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
