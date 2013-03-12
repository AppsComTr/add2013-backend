package org.gdgankara.app.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SessionWrapper {

	private Version version;
	private List<Session> sessions;
	private Session session;

	public SessionWrapper() {
		super();
	}

	public SessionWrapper(Version version, List<Session> sessions) {
		super();
		this.version = version;
		this.sessions = sessions;
	}
	
	public SessionWrapper(Version version, Session session) {
		super();
		this.version = version;
		this.session = session;
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

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	
}
