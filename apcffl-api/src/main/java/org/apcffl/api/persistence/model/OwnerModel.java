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
@Table(name = "OWNER")
@EntityListeners(AuditingEntityListener.class)
public class OwnerModel implements Serializable {

	/**
	 * STS generated
	 */
	private static final long serialVersionUID = 7964190100907900542L;

	@Id
	@Column(name = "OWNER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ownerId;

	@OneToOne
	@JoinColumn(name = "USER_ID")
	private UserModel userModel;

	@OneToOne
	@JoinColumn(name = "TEAM_ID")
	private TeamModel teamModel;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "EMAIL1")
	private String email1;
	
	@Column(name = "EMAIL2")
	private String email2;
	
	@Column(name = "EMAIL3")
	private String email3;
	
	@Column(name = "ACTIVE_FLAG")
	private Boolean activeFlag;
	
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	@Column(name = "UPDATE_DATE")
	private Date updateDate;

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getEmail3() {
		return email3;
	}

	public void setEmail3(String email3) {
		this.email3 = email3;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
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

	public TeamModel getTeamModel() {
		return teamModel;
	}

	public void setTeamModel(TeamModel teamModel) {
		this.teamModel = teamModel;
	}

}
