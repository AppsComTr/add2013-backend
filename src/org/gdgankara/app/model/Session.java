package org.gdgankara.app.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Session {
	public static final String KIND = "sessions";
	public static final String DAY = "Day";
	public static final String START_HOUR = "Start_Hour";
	public static final String END_HOUR = "End_Hour";
	public static final String HALL = "Hall";
	public static final String TITLE = "Title";
	public static final String DESCRIPTION = "Description";
	public static final String SPEAKER = "Speaker";
	
	private long id;
	private String day;
	private String startHour;
	private String endHour;
	private String hall;
	private String title;
	private String description;
	private String speaker;
		
	
	public Session() {
		// TODO Auto-generated constructor stub
	}

	public Session(long id, String day, String startHour, String endHour,
			String hall, String title, String description, String speaker) {
		this.id = id;
		this.day = day;
		this.startHour = startHour;
		this.endHour = endHour;
		this.hall = hall;
		this.title = title;
		this.description = description;
		this.speaker = speaker;
	}

	public Session(String day, String startHour, String endHour,
			String hall, String title, String description, String speaker) {
		this.day = day;
		this.startHour = startHour;
		this.endHour = endHour;
		this.hall = hall;
		this.title = title;
		this.description = description;
		this.speaker = speaker;
	}

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getDay() {
		return day;
	}


	public void setDay(String day) {
		this.day = day;
	}


	public String getStartTime() {
		return startHour;
	}


	public void setStartTime(String startHour) {
		this.startHour = startHour;
	}


	public String getEndTime() {
		return endHour;
	}


	public void setEndTime(String endHour) {
		this.endHour = endHour;
	}


	public String getHall() {
		return hall;
	}


	public void setHall(String hall) {
		this.hall = hall;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getSpeaker() {
		return speaker;
	}


	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

}

