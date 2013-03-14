package org.gdgankara.app.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Announcement {
	public static final String KIND = "announcements";
	public static final String LANG = "Lang";
	public static final String LANG_TR = "tr";
	public static final String LANG_EN = "en";
	public static final String DESCRIPTION = "Description";
	public static final String IMAGE = "Image";
	public static final String LINK = "Link";

	private long id;
	private String description;
	private String image;
	private String lang;
	private String link;

	public Announcement() {
		super();
	}

	public Announcement(String description, String image, String lang,
			String link) {
		super();
		this.description = description;
		this.image = image;
		this.lang = lang;
		this.link = link;
	}

	public Announcement(long id, String description, String image, String lang,
			String link) {
		super();
		this.id = id;
		this.description = description;
		this.image = image;
		this.lang = lang;
		this.link = link;
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

}
