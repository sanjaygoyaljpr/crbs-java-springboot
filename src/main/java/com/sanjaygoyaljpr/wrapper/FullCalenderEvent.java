package com.sanjaygoyaljpr.wrapper;

public class FullCalenderEvent {

	private Long id;
	private String title;
	private String start;
	private String end;

	private String user;
	private String confRoomName;

	public FullCalenderEvent() {
	}

	public FullCalenderEvent(Long id, String title, String start, String end) {
		super();
		this.id = id;
		this.title = title;
		this.start = start;
		this.end = end;
	}

	public FullCalenderEvent(Long id, String title, String start, String end, String user, String confRoomName) {
		super();
		this.id = id;
		this.title = title;
		this.start = start;
		this.end = end;
		this.user = user;
		this.confRoomName = confRoomName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getConfRoomName() {
		return confRoomName;
	}

	public void setConfRoomName(String confRoomName) {
		this.confRoomName = confRoomName;
	}

}
