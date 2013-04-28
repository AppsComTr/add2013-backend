package org.gdgankara.app.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Sponsor implements Serializable {
	private static final long serialVersionUID = -3839323744488426823L;
	public static final String KIND = "sponsors";
	public static final String LANG = "Lang";
	public static final String LANG_TR = "tr";
	public static final String LANG_EN = "en";
	public static final String DESCRIPTION = "Description";
	public static final String IMAGE = "Image";
	public static final String LINK = "Link";
	public static final String NAME = "Name";
	
	private long id;
	private String description;
	private String image;
	private String lang;
	private String link;
	private String name;
	
	public Sponsor() {
		super();
	}
	
	public Sponsor(String description, String image, String lang,
			String link, String name) {
		super();
		this.description = description;
		this.image = image;
		this.lang = lang;
		this.link = link;
		this.name = name;
	}

	public Sponsor(long id, String description, String image, String lang,
			String link, String name) {
		super();
		this.id = id;
		this.description = description;
		this.image = image;
		this.lang = lang;
		this.link = link;
		this.name = name;
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
