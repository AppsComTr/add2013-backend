package org.gdgankara.app.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TagWrapper {

	private Version version;
	private Tag tag;

	public TagWrapper() {
		super();
	}

	public TagWrapper(Version version, Tag tag) {
		super();
		this.version = version;
		this.tag = tag;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

}
