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
@Table(name = "DIVISION")
@EntityListeners(AuditingEntityListener.class)
public class DivisionModel implements Serializable {

	/**
	 * STS generated
	 */
	private static final long serialVersionUID = -3353371068069863406L;

	@Id
	@Column(name = "DIVISION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long divisionId;
	
	@ManyToOne
	@JoinColumn(name = "LEAGUE_ID")
	private LeagueModel league;

	@Column(name = "DIVISION_NAME")
	private String divisionName;

	public Long getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Long divisionId) {
		this.divisionId = divisionId;
	}

	public LeagueModel getLeague() {
		return league;
	}

	public void setLeague(LeagueModel league) {
		this.league = league;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	
	
}
