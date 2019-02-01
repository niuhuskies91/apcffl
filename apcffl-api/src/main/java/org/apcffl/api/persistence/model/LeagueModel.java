package org.apcffl.api.persistence.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "LEAGUE")
@EntityListeners(AuditingEntityListener.class)
public class LeagueModel implements Serializable {

	/**
	 * STS generated
	 */
	private static final long serialVersionUID = 7829495914558983667L;

	@Id
	@Column(name = "LEAGUE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long leagueId;
	
	@Column(name = "LEAGUE_NAME")
	private String leagueName;
	
	@Column(name = "NUMBER_OF_TEAMS")
	private Integer numberOfTeams;
	
	@Column(name = "NUMBER_OF_DIVISIONS")
	private Integer numberOfDivisions;

	public Long getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(Long leagueId) {
		this.leagueId = leagueId;
	}

	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

	public Integer getNumberOfTeams() {
		return numberOfTeams;
	}

	public void setNumberOfTeams(Integer numberOfTeams) {
		this.numberOfTeams = numberOfTeams;
	}

	public Integer getNumberOfDivisions() {
		return numberOfDivisions;
	}

	public void setNumberOfDivisions(Integer numberOfDivisions) {
		this.numberOfDivisions = numberOfDivisions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(leagueId, leagueName, numberOfTeams, numberOfDivisions);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LeagueModel other = (LeagueModel) obj;
		if (leagueId == null) {
			if (other.leagueId != null)
				return false;
		} else if (!leagueId.equals(other.leagueId))
			return false;
		if (leagueName == null) {
			if (other.leagueName != null)
				return false;
		} else if (!leagueName.equals(other.leagueName))
			return false;
		if (numberOfDivisions == null) {
			if (other.numberOfDivisions != null)
				return false;
		} else if (!numberOfDivisions.equals(other.numberOfDivisions))
			return false;
		if (numberOfTeams == null) {
			if (other.numberOfTeams != null)
				return false;
		} else if (!numberOfTeams.equals(other.numberOfTeams))
			return false;
		return true;
	}
}
