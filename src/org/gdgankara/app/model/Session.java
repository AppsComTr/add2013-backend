package org.gdgankara.app.model;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Session {
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
	public static final String SPEAKER_1 = "Speaker1";
	public static final String SPEAKER_2 = "Speaker2";
	public static final String SPEAKER_3 = "Speaker3";
	public static final String SPEAKER_LIST = "Speaker_List";

	private long id;
	private String lang;
	private String day;
	private String startHour;
	private String endHour;
	private String hall;
	private String title;
	private String description;
	private Boolean isBreak;
	private Speaker speaker1;
	private Speaker speaker2;
	private Speaker speaker3;
	private Long Speaker1ID;
	private Long Speaker2ID;
	private Long Speaker3ID;
	private List<Long> speakerIDList;
	
	public Session() {
	}
	
	public Session(long id, String lang, String day, String startHour,
			String endHour, String hall, String title, String description, Boolean isBreak,
			Speaker speaker1, Speaker speaker2, Speaker speaker3, List<Long> speakerIDList) {
		this.lang = lang;
		this.id = id;
		this.day = day;
		this.startHour = startHour;
		this.endHour = endHour;
		this.hall = hall;
		this.title = title;
		this.description = description;
		this.isBreak = isBreak;
		this.speaker1 = speaker1;
		this.speaker2 = speaker2;
		this.speaker3 = speaker3;
		this.speakerIDList = speakerIDList;
	}

	public Session(String lang, String day, String startHour, String endHour,
			String hall, String title, String description, Boolean isBreak,
			Speaker speaker1, Speaker speaker2, Speaker speaker3) {
		this.lang = lang;
		this.day = day;
		this.startHour = startHour;
		this.endHour = endHour;
		this.hall = hall;
		this.title = title;
		this.description = description;
		this.isBreak = isBreak;
		this.speaker1 = speaker1;
		this.speaker2 = speaker2;
		this.speaker3 = speaker3;
	}
	
	public Session(String lang, String day, String startHour, String endHour,
			String hall, String title, String description, Boolean isBreak, List<Long> speakerIDList){
		this.lang = lang;
		this.day = day;
		this.startHour = startHour;
		this.endHour = endHour;
		this.hall = hall;
		this.title = title;
		this.description = description;
		this.isBreak = isBreak;
		this.speakerIDList = speakerIDList;
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

	public Speaker getSpeaker1() {
		return speaker1;
	}

	public void setSpeaker1(Speaker speaker1) {
		this.speaker1 = speaker1;
	}

	public Speaker getSpeaker2() {
		return speaker2;
	}

	public void setSpeaker2(Speaker speaker2) {
		this.speaker2 = speaker2;
	}

	public Speaker getSpeaker3() {
		return speaker3;
	}

	public void setSpeaker3(Speaker speaker3) {
		this.speaker3 = speaker3;
	}
	
	public Long getSpeaker1ID() {
		return Speaker1ID;
	}

	public void setSpeaker1ID(Long Speaker1ID) {
		this.Speaker1ID = Speaker1ID;
	}

	public Long getSpeaker2ID() {
		return Speaker2ID;
	}

	public void setSpeaker2ID(Long Speaker2ID) {
		this.Speaker2ID = Speaker2ID;
	}

	public Long getSpeaker3ID() {
		return Speaker3ID;
	}

	public void setSpeaker3ID(Long Speaker3ID) {
		this.Speaker3ID = Speaker3ID;
	}

	public List<Long> getSpeakerIDList() {
		return speakerIDList;
	}

	public void setSpeakerIDList(List<Long> speakerIDs) {
		this.speakerIDList = speakerIDs;
	}
}
