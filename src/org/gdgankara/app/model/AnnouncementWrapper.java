package org.gdgankara.app.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class AnnouncementWrapper {

	private Version version;
	private List<Announcement> announcements;
	private Announcement announcement;

	public AnnouncementWrapper() {
		super();
	}

	public AnnouncementWrapper(Version version, Announcement announcement) {
		super();
		this.version = version;
		this.announcement = announcement;
	}

	public AnnouncementWrapper(Version version,
			List<Announcement> announcementList) {
		super();
		this.version = version;
		this.announcements = announcementList;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public List<Announcement> getAnnouncements() {
		return announcements;
	}

	public void setAnnouncements(List<Announcement> announcements) {
		this.announcements = announcements;
	}

	public Announcement getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(Announcement announcement) {
		this.announcement = announcement;
	}

}
