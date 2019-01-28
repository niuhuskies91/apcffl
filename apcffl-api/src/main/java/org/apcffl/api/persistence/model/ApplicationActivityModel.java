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
@Table(name = "APPLICATION_ACTIVITY")
@EntityListeners(AuditingEntityListener.class)
public class ApplicationActivityModel implements Serializable {

	/**
	 * STS generated
	 */
	private static final long serialVersionUID = -7058562989023908303L;

	@Id
	@Column(name = "ACTIVITY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long activityId;
	
	@Column(name = "ACTIVITY_NAME")
	private String activityName;
	
	@Column(name = "ACTIVITY_DESC")
	private String activityDesc;

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(activityId, activityName, activityDesc);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicationActivityModel other = (ApplicationActivityModel) obj;
		if (activityDesc == null) {
			if (other.activityDesc != null)
				return false;
		} else if (!activityDesc.equals(other.activityDesc))
			return false;
		if (activityId == null) {
			if (other.activityId != null)
				return false;
		} else if (!activityId.equals(other.activityId))
			return false;
		if (activityName == null) {
			if (other.activityName != null)
				return false;
		} else if (!activityName.equals(other.activityName))
			return false;
		return true;
	}
	
}
