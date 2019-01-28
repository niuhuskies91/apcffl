package org.apcffl.api.persistence.model;

import java.io.Serializable;
import java.util.Objects;

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
@Table(name = "USER")
@EntityListeners(AuditingEntityListener.class)
public class UserModel implements Serializable {

	/**
	 * STS generated
	 */
	private static final long serialVersionUID = -7094405692097845801L;

	@Id
	@Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@Column(name = "USER_NAME")
	private String userName;
	
	@Column(name = "PASSWORD")
	private String password;

	@OneToOne
	@JoinColumn(name = "USER_GROUP_ID")
	private UserGroupModel userGroupModel;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserGroupModel getUserGroupModel() {
		return userGroupModel;
	}

	public void setUserGroupModel(UserGroupModel userGroupModel) {
		this.userGroupModel = userGroupModel;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserModel other = (UserModel) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userGroupModel == null) {
			if (other.userGroupModel != null)
				return false;
		} else if (!userGroupModel.equals(other.userGroupModel))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, userName, password, userGroupModel);
	}
	

}
