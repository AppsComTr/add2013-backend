package org.gdgankara.app.model;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SponsorWrapper {

	private Version version;
	private List<Sponsor> sponsors;
	private Sponsor sponsor;

	public SponsorWrapper() {
		super();
	}

	public SponsorWrapper(Version version, List<Sponsor> sponsors) {
		super();
		this.version = version;
		this.sponsors = sponsors;
	}

	public SponsorWrapper(Version version, Sponsor sponsor) {
		super();
		this.version = version;
		this.sponsor = sponsor;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public List<Sponsor> getSponsors() {
		return sponsors;
	}

	public void setSponsors(List<Sponsor> sponsors) {
		this.sponsors = sponsors;
	}

	public Sponsor getSponsor() {
		return sponsor;
	}

	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}

}
