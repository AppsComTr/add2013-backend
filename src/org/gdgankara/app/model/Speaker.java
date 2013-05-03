package org.gdgankara.app.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Speaker implements Serializable{
	private static final long serialVersionUID = 5840198077787061911L;
	public static final String KIND = "speakers";
	public static final String LANG = "Lang";
	public static final String LANG_TR = "tr";
	public static final String LANG_EN = "en";
	public static final String SESSION_LIST = "Session_List";
	public static final String BIO = "bio";
	public static final String NAME = "name";
	public static final String PHOTO = "photo";
	public static final String URL = "url";

	
	private long id;
	private long postId;
	private String bio;
	private String lang;
	private String name;
	private String photo;
	private List<Long> sessionIDList;
	private String url;
	
	public Speaker(){
		
	}

	public Speaker(String bio, String lang, String name, String photo, String url) {
		this.bio = bio;
		this.lang = lang;
		this.name = name;
		this.photo = photo;
		this.url = url;
	}

	public Speaker(long id, String bio, String lang, String name, String photo, List<Long> sessionIDList, String url) {
		this.id = id;
		this.bio = bio;
		this.lang = lang;
		this.name = name;
		this.photo = photo;
		this.sessionIDList = sessionIDList;
		this.url = url;
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

	public List<Long> getSessionIDList() {
		return sessionIDList;
	}

	public void setSessionIDList(List<Long> sessionIDs) {
		this.sessionIDList = sessionIDs;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

}
