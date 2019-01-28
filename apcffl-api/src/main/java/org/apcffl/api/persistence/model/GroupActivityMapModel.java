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
@Table(name = "GROUP_ACTIVITY_MAP")
@EntityListeners(AuditingEntityListener.class)
public class GroupActivityMapModel implements Serializable {

	/**
	 * STS generated
	 */
	private static final long serialVersionUID = 1304250632928479295L;

	@Id
	@Column(name = "GROUP_ACTIVITY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long groupActivityId;

	@OneToOne
	@JoinColumn(name = "USER_GROUP_ID")
	private UserGroupModel userGroupModel;
	
	@OneToOne
	@JoinColumn(name = "ACTIVITY_ID")
	private ApplicationActivityModel applicationActivityModel;

	public Long getGroupActivityId() {
		return groupActivityId;
	}

	public void setGroupActivityId(Long groupActivityId) {
		this.groupActivityId = groupActivityId;
	}

	public UserGroupModel getUserGroupModel() {
		return userGroupModel;
	}

	public void setUserGroupModel(UserGroupModel userGroupModel) {
		this.userGroupModel = userGroupModel;
	}

	public ApplicationActivityModel getApplicationActivityModel() {
		return applicationActivityModel;
	}

	public void setApplicationActivityModel(ApplicationActivityModel applicationActivityModel) {
		this.applicationActivityModel = applicationActivityModel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((applicationActivityModel == null) ? 0 : applicationActivityModel.hashCode());
		result = prime * result + ((groupActivityId == null) ? 0 : groupActivityId.hashCode());
		result = prime * result + ((userGroupModel == null) ? 0 : userGroupModel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupActivityMapModel other = (GroupActivityMapModel) obj;
		if (applicationActivityModel == null) {
			if (other.applicationActivityModel != null)
				return false;
		} else if (!applicationActivityModel.equals(other.applicationActivityModel))
			return false;
		if (groupActivityId == null) {
			if (other.groupActivityId != null)
				return false;
		} else if (!groupActivityId.equals(other.groupActivityId))
			return false;
		if (userGroupModel == null) {
			if (other.userGroupModel != null)
				return false;
		} else if (!userGroupModel.equals(other.userGroupModel))
			return false;
		return true;
	}
}
