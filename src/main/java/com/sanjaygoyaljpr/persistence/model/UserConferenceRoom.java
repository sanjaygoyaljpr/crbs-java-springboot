package com.sanjaygoyaljpr.persistence.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user_conference_room")
public class UserConferenceRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long conferenceRoomId;

	private Long userId;

	private String title;

	private String startDateOfBooking;

	private String endDateOfBooking;

	private Calendar createdDate;

	private String status;

	private Long cancelledBy;

	private Calendar cancelledDate;

	@Transient
	private String userName;

	@Transient
	private String confRoomName;

	@Transient
	private String cancelledByName;

	@Transient
	private int srNo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getConferenceRoomId() {
		return conferenceRoomId;
	}

	public void setConferenceRoomId(Long conferenceRoomId) {
		this.conferenceRoomId = conferenceRoomId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Calendar getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStartDateOfBooking() {
		return startDateOfBooking;
	}

	public void setStartDateOfBooking(String startDateOfBooking) {
		this.startDateOfBooking = startDateOfBooking;
	}

	public String getEndDateOfBooking() {
		return endDateOfBooking;
	}

	public void setEndDateOfBooking(String endDateOfBooking) {
		this.endDateOfBooking = endDateOfBooking;
	}

	public String getConfRoomName() {
		return confRoomName;
	}

	public void setConfRoomName(String confRoomName) {
		this.confRoomName = confRoomName;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getCancelledBy() {
		return cancelledBy;
	}

	public void setCancelledBy(Long cancelledBy) {
		this.cancelledBy = cancelledBy;
	}

	public String getCancelledByName() {
		return cancelledByName;
	}

	public void setCancelledByName(String cancelledByName) {
		this.cancelledByName = cancelledByName;
	}

	public Calendar getCancelledDate() {
		return cancelledDate;
	}

	public void setCancelledDate(Calendar cancelledDate) {
		this.cancelledDate = cancelledDate;
	}

}
