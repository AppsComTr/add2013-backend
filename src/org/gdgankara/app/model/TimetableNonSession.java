package org.gdgankara.app.model;

public class TimetableNonSession {

	private String timeSlot;
	private String url;
	private String date;

	public TimetableNonSession(String timeSlot, String sessionUrl,
			String speakerUrl) {
		super();
		this.timeSlot = timeSlot;
		url = sessionUrl;
	}

	public TimetableNonSession() {
		super();
	}

	public String getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
