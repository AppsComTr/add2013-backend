package org.gdgankara.app.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SessionWrapper {

	private Version version;
	private List<Session> sessions;
	private List<Session> breaks;

	public SessionWrapper() {
		super();
	}

	public SessionWrapper(Version version, List<Session> sessions,
			List<Session> breaks) {
		super();
		this.version = version;
		this.sessions = sessions;
		this.breaks = breaks;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	public List<Session> getBreaks() {
		return breaks;
	}

	public void setBreaks(List<Session> breaks) {
		this.breaks = breaks;
	}

}
