package org.gdgankara.app.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Speaker {
	public static final String KIND = "speakers";
	public static final String LANG = "Lang";
	public static final String LANG_TR = "tr";
	public static final String LANG_EN = "en";
	public static final String SESSION_LIST = "Session_List";
	public static final String BIO = "bio";
	public static final String BLOG = "blog";
	public static final String FACEBOOK = "facebook";
	public static final String GPLUS = "gplus";
	public static final String NAME = "name";
	public static final String PHOTO = "photo";
	public static final String TWITTER = "twitter";
	public static final String TITLE = "title";

	private List<Long> sessionIDList;
	private List<Session> sessionList;
	private long id;
	private String bio;
	private String blog;
	private String facebook;
	private String gplus;
	private String lang;
	private String name;
	private String photo;
	private String twitter;
	private String title;
	
	public Speaker(){
		
	}

	public Speaker(String bio, String blog, String facebook, String gplus,
			String lang, String name, String photo, String twitter, String title) {
		this.bio = bio;
		this.blog = blog;
		this.facebook = facebook;
		this.gplus = gplus;
		this.lang = lang;
		this.name = name;
		this.photo = photo;
		this.twitter = twitter;
		this.title = title;
	}

	public Speaker(long id, String bio, String blog, String facebook,
			String gplus, String lang, String name, String photo, String twitter, List<Long> sessionIDList, String title) {
		this.id = id;
		this.bio = bio;
		this.blog = blog;
		this.facebook = facebook;
		this.gplus = gplus;
		this.lang = lang;
		this.name = name;
		this.photo = photo;
		this.twitter = twitter;
		this.sessionIDList = sessionIDList;
		this.title = title;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getBlog() {
		return blog;
	}

	public void setBlog(String blog) {
		this.blog = blog;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getGplus() {
		return gplus;
	}

	public void setGplus(String gplus) {
		this.gplus = gplus;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public List<Long> getSessionIDList() {
		return sessionIDList;
	}

	public void setSessionIDList(List<Long> sessionIDs) {
		this.sessionIDList = sessionIDs;
	}

	public List<Session> getSessionList() {
		return sessionList;
	}

	public void setSessionList(List<Session> sessionList) {
		this.sessionList = sessionList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
