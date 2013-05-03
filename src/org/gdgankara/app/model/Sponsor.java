package org.gdgankara.app.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Sponsor implements Serializable {
	private static final long serialVersionUID = -3839323744488426823L;
	public static final String KIND = "sponsors";
	public static final String DESCRIPTION = "Description";
	public static final String IMAGE = "Image";
	public static final String LINK = "Link";
	public static final String CATEGORY = "Category";

	private long id;
	private String category;
	private String description;
	private String image;
	private String link;
	private String name;

	public Sponsor() {
		super();
	}

	public Sponsor(String image, String link, String name) {
		super();
		this.image = image;
		this.link = link;
		this.name = name;
	}

	public Sponsor(long id, String image, String link,
			String name) {
		super();
		this.id = id;
		this.image = image;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
