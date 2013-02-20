package org.gdgankara.app.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Speaker {
	public static final String KIND = "speakers";
	public static final String LANG = "Lang";
	public static final String LANG_TR = "tr";
	public static final String LANG_EN = "en";
	public static final String BIO = "bio";
	public static final String BLOG = "blog";
	public static final String FACEBOOK = "facebook";
	public static final String GPLUS = "gplus";
	public static final String NAME = "name";
	public static final String PHOTO = "photo";
	public static final String TWITTER = "twitter";

	private long id;
	private String bio;
	private String blog;
	private String facebook;
	private String gplus;
	private String lang;
	private String name;
	private String photo;
	private String twitter;
	
	public Speaker(){
		
	}

	public Speaker(String bio, String blog, String facebook, String gplus,
			String lang, String name, String photo, String twitter) {
		this.bio = bio;
		this.blog = blog;
		this.facebook = facebook;
		this.gplus = gplus;
		this.lang = lang;
		this.name = name;
		this.photo = photo;
		this.twitter = twitter;
	}

	public Speaker(long id, String bio, String blog, String facebook,
			String gplus, String lang, String name, String photo, String twitter) {
		this.id = id;
		this.bio = bio;
		this.blog = blog;
		this.facebook = facebook;
		this.gplus = gplus;
		this.lang = lang;
		this.name = name;
		this.photo = photo;
		this.twitter = twitter;
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

}
