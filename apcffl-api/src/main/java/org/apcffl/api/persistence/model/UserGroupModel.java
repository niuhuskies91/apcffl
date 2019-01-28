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
@Table(name = "USER_GROUP")
@EntityListeners(AuditingEntityListener.class)
public class UserGroupModel implements Serializable {

	/**
	 * STS generated
	 */
	private static final long serialVersionUID = -2130959029766007054L;

	@Id
	@Column(name = "USER_GROUP_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userGroupId;
	
	@Column(name = "USER_GROUP_NAME")
	private String userGroupName;

	public Long getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(Long userGroupId) {
		this.userGroupId = userGroupId;
	}

	public String getUserGroupName() {
		return userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userGroupId, userGroupName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserGroupModel other = (UserGroupModel) obj;
		if (userGroupId == null) {
			if (other.userGroupId != null)
				return false;
		} else if (!userGroupId.equals(other.userGroupId))
			return false;
		if (userGroupName == null) {
			if (other.userGroupName != null)
				return false;
		} else if (!userGroupName.equals(other.userGroupName))
			return false;
		return true;
	}
}
