package org.apcffl.api.league.dto;

import java.util.List;

public class Conference {

	private String conferenceName;
	private String conferenceAbbr;
	private String conferenceType;
	private List<School> schools;
	
	public String getConferenceName() {
		return conferenceName;
	}
	public void setConferenceName(String conferenceName) {
		this.conferenceName = conferenceName;
	}
	public String getConferenceAbbr() {
		return conferenceAbbr;
	}
	public void setConferenceAbbr(String conferenceAbbr) {
		this.conferenceAbbr = conferenceAbbr;
	}
	
	public String getConferenceType() {
		return conferenceType;
	}
	public void setConferenceType(String conferenceType) {
		this.conferenceType = conferenceType;
	}
	public List<School> getSchools() {
		return schools;
	}
	public void setSchools(List<School> schools) {
		this.schools = schools;
	}
	
}
