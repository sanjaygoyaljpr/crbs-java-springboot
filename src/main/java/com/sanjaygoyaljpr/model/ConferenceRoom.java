package com.sanjaygoyaljpr.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "conference_room")
public class ConferenceRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "conf_room_name")
	@NotEmpty(message = "*Please provide conference room name")
	@Length(max = 50, message = "The field must be less than 50 characters")
	private String confRoomName;

	@Column(name = "seat_capacity")
	private Integer seatCapacity;

	@Column(name = "projector", columnDefinition = "tinyint", length = 1)
	private Boolean projector;

	@Column(name = "voip_available", columnDefinition = "tinyint", length = 1)
	private Boolean voipAvailable;

	@Column(name = "created_date")
	private Calendar createdDate;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "is_active", columnDefinition = "tinyint default true", length = 1)
	private Boolean isActive;

	@Transient
	private String userName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConfRoomName() {
		return confRoomName;
	}

	public void setConfRoomName(String confRoomName) {
		this.confRoomName = confRoomName;
	}

	public Integer getSeatCapacity() {
		return seatCapacity;
	}

	public void setSeatCapacity(Integer seatCapacity) {
		this.seatCapacity = seatCapacity;
	}

	public Boolean getProjector() {
		return projector;
	}

	public void setProjector(Boolean projector) {
		this.projector = projector;
	}

	public Boolean getVoipAvailable() {
		return voipAvailable;
	}

	public void setVoipAvailable(Boolean voipAvailable) {
		this.voipAvailable = voipAvailable;
	}

	public Calendar getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
