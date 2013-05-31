package org.gdgankara.app.model;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Session implements Serializable {
	private static final long serialVersionUID = 4442016592043277308L;
	public static final String KIND = "sessions";
	public static final String LANG = "Lang";
	public static final String LANG_TR = "tr";
	public static final String LANG_EN = "en";
	public static final String DAY = "Day";
	public static final String START_HOUR = "Start_Hour";
	public static final String END_HOUR = "End_Hour";
	public static final String HALL = "Hall";
	public static final String TITLE = "Title";
	public static final String DESCRIPTION = "Description";
	public static final String BREAK = "Break";
	public static final String SPEAKER_LIST = "Speaker_List";
	public static final String TAGS = "Tags";

	private long id;
	private String lang;
	private String day;
	private String startHour;
	private String endHour;
	private String hall;
	private String title;
	private String description;
	private Boolean isBreak;
	private List<Long> speakerIDList;
	private List<String> speakerUrlList;
	private List<Speaker> speakerList;
	private String tags;
	private String url;

	public Session() {
	}

	public Session(long id, String lang, String day, String startHour,
			String endHour, String hall, String title, String description,
			Boolean isBreak, List<Long> speakerIDList, String tags) {
		this.lang = lang;
		this.id = id;
		this.day = day;
		this.startHour = startHour;
		this.endHour = endHour;
		this.hall = hall;
		this.title = title;
		this.description = description;
		this.isBreak = isBreak;
		this.speakerIDList = speakerIDList;
		this.tags = tags;
	}

	public Session(String lang, String day, String startHour, String endHour,
			String hall, String title, String description, Boolean isBreak,
			String tags) {
		this.lang = lang;
		this.day = day;
		this.startHour = startHour;
		this.endHour = endHour;
		this.hall = hall;
		this.title = title;
		this.description = description;
		this.isBreak = isBreak;
		this.tags = tags;
	}

	public Session(String lang, String day, String startHour, String endHour,
			String hall, String title, String description, Boolean isBreak,
			List<Long> speakerIDList, String tags) {
		this.lang = lang;
		this.day = day;
		this.startHour = startHour;
		this.endHour = endHour;
		this.hall = hall;
		this.title = title;
		this.description = description;
		this.isBreak = isBreak;
		this.speakerIDList = speakerIDList;
		this.tags = tags;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getStartHour() {
		return startHour;
	}

	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}

	public String getEndHour() {
		return endHour;
	}

	public void setEndHour(String endHour) {
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

	public Boolean isBreak() {
		return isBreak;
	}

	public void setBreak(Boolean isBreak) {
		this.isBreak = isBreak;
	}

	public List<Long> getSpeakerIDList() {
		return speakerIDList;
	}

	public void setSpeakerIDList(List<Long> speakerIDs) {
		this.speakerIDList = speakerIDs;
	}

	public List<Speaker> getSpeakerList() {
		return speakerList;
	}

	public void setSpeakerList(List<Speaker> speakerList) {
		this.speakerList = speakerList;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public List<String> getSpeakerUrlList() {
		return speakerUrlList;
	}

	public void setSpeakerUrlList(List<String> speakerUrlList) {
		this.speakerUrlList = speakerUrlList;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
