package org.gdgankara.app.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SpeakerWrapper {

	private Version version;
	private List<Speaker> speakers;

	public SpeakerWrapper() {

	}

	public SpeakerWrapper(Version version, List<Speaker> speakers) {
		this.version = version;
		this.speakers = speakers;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public List<Speaker> getSpeakers() {
		return speakers;
	}

	public void setSpeakers(List<Speaker> speakers) {
		this.speakers = speakers;
	}
}
