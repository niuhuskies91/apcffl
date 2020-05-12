package org.apcffl.api.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "TEAM")
@EntityListeners(AuditingEntityListener.class)
public class TeamModel implements Serializable {

	/**
	 * STS generated
	 */
	private static final long serialVersionUID = -3041881017803718917L;

	@Id
	@Column(name = "TEAM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long teamId;

	@OneToOne
	@JoinColumn(name = "LEAGUE_ID")
	private LeagueModel leagueModel;

	@OneToOne
	@JoinColumn(name = "DIVISION_ID")
	private DivisionModel divisionModel;
	
	@Column(name = "TEAM_NAME")
	private String teamName;
	
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	@Column(name = "UPDATE_DATE")
	private Date updateDate;

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public DivisionModel getDivisionModel() {
		return divisionModel;
	}

	public void setDivisionModel(DivisionModel divisionModel) {
		this.divisionModel = divisionModel;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public LeagueModel getLeagueModel() {
		return leagueModel;
	}

	public void setLeagueModel(LeagueModel leagueModel) {
		this.leagueModel = leagueModel;
	}
	
}
