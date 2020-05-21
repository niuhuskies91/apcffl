package org.apcffl.api.persistence.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "CONFERENCE")
@EntityListeners(AuditingEntityListener.class)
public class ConferenceModel implements Serializable {

	/**
	 * STS generated
	 */
	private static final long serialVersionUID = 1235495573071872312L;

	@Id
	@Column(name = "CONFERENCE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long conferenceId;
	
	@Column(name = "CONFERENCE_ABBR")
	private String conferenceAbbr;
	
	@Column(name = "CONFERENCE_NAME")
	private String conferenceName;
	
	@Column(name = "NCAA_DIVISION_TYPE")
	private String ncaaDivisionType;
	
	@OneToMany(mappedBy="conference")
	private Set<SchoolModel> schools;

	public Long getConferenceId() {
		return conferenceId;
	}

	public void setConferenceId(Long conferenceId) {
		this.conferenceId = conferenceId;
	}

	public String getConferenceAbbr() {
		return conferenceAbbr;
	}

	public void setConferenceAbbr(String conferenceAbbr) {
		this.conferenceAbbr = conferenceAbbr;
	}

	public String getConferenceName() {
		return conferenceName;
	}

	public void setConferenceName(String conferenceName) {
		this.conferenceName = conferenceName;
	}

	public String getNcaaDivisionType() {
		return ncaaDivisionType;
	}

	public void setNcaaDivisionType(String ncaaDivisionType) {
		this.ncaaDivisionType = ncaaDivisionType;
	}

	public Set<SchoolModel> getSchools() {
		return schools;
	}

	public void setSchools(Set<SchoolModel> schools) {
		this.schools = schools;
	}

}
