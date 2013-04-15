package org.gdgankara.app.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VersionWrapper {
	private Version version;
	private List<Session> sessions;
	private List<Speaker> speakers;
	private List<Announcement> announcements;
	private List<Sponsor> sponsors;
	
	public VersionWrapper() {
		super();
	}

	public VersionWrapper(Version version, List<Session> sessions,
			List<Speaker> speakers) {
		super();
		this.version = version;
		this.sessions = sessions;
		this.speakers = speakers;
	}

	public VersionWrapper(Version version, List<Session> sessions,
			List<Speaker> speakers, List<Announcement> announcements, List<Sponsor> sponsors) {
		super();
		this.version = version;
		this.sessions = sessions;
		this.speakers = speakers;
		this.announcements = announcements;
		this.sponsors = sponsors;
	}
	
	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public List<Session> getsessions() {
		return sessions;
	}

	public void setsessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	public List<Speaker> getspeakers() {
		return speakers;
	}

	public void setspeakers(List<Speaker> speakers) {
		this.speakers = speakers;
	}

	public List<Announcement> getAnnouncements() {
		return announcements;
	}

	public void setAnnouncements(List<Announcement> announcements) {
		this.announcements = announcements;
	}

	public List<Sponsor> getSponsors() {
		return sponsors;
	}

	public void setSponsors(List<Sponsor> sponsors) {
		this.sponsors = sponsors;
	}
	
}
