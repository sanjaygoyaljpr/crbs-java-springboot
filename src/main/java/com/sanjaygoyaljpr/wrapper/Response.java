package com.sanjaygoyaljpr.wrapper;

import java.util.List;

public class Response {

	private String msg;

	private List<FullCalenderEvent> events;

	public Response(String msg, List<FullCalenderEvent> events) {
		super();
		this.msg = msg;
		this.events = events;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<FullCalenderEvent> getEvents() {
		return events;
	}

	public void setEvents(List<FullCalenderEvent> events) {
		this.events = events;
	}

}
