package org.apcffl.api.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "SCHOOL")
@EntityListeners(AuditingEntityListener.class)
public class SchoolModel implements Serializable {

	/**
	 * STS generated
	 */
	private static final long serialVersionUID = 5452938861236521968L;

	@Id
	@Column(name = "SCHOOL_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long schoolId;
	
	@ManyToOne
	@JoinColumn(name = "CONFERENCE_ID")
	private ConferenceModel conference;

	@Column(name = "SCHOOL_NAME")
	private String schoolName;

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public ConferenceModel getConference() {
		return conference;
	}

	public void setConference(ConferenceModel conference) {
		this.conference = conference;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

}
