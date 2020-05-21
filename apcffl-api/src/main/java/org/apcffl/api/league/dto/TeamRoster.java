package org.apcffl.api.league.dto;

public class TeamRoster {
	
	private School school;
	private Integer scholarshipPoints;
	
	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}
	public Integer getScholarshipPoints() {
		return scholarshipPoints;
	}
	public void setScholarshipPoints(Integer scholarshipPoints) {
		this.scholarshipPoints = scholarshipPoints;
	}

}
