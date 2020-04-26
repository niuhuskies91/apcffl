package org.apcffl.api.persistence.model;

import java.io.Serializable;
import java.sql.Date;

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
@Table(name = "MESSAGE_BOARD")
@EntityListeners(AuditingEntityListener.class)
public class MessageBoardModel implements Serializable {

	/**
	 * STS generated
	 */
	private static final long serialVersionUID = -6065152436038331461L;

	@Id
	@Column(name = "MESSAGE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long messageId;

	@OneToOne
	@JoinColumn(name = "OWNER_ID")
	private OwnerModel ownerModel;

	@OneToOne
	@JoinColumn(name = "LEAGUE_ID")
	private LeagueModel leagueModel;
	
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	@Column(name = "ARCHIVE_DATE")
	private Date archiveDate;
	
	@Column(name = "MESSAGE")
	private String message;

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public OwnerModel getOwnerModel() {
		return ownerModel;
	}

	public void setOwnerModel(OwnerModel ownerModel) {
		this.ownerModel = ownerModel;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getArchiveDate() {
		return archiveDate;
	}

	public void setArchiveDate(Date archiveDate) {
		this.archiveDate = archiveDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LeagueModel getLeagueModel() {
		return leagueModel;
	}

	public void setLeagueModel(LeagueModel leagueModel) {
		this.leagueModel = leagueModel;
	}
	
}
