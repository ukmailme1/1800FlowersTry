package com.flowers.app.ws.io.entity;

import java.io.Serializable;

public class UserEntityId implements Serializable {

	private static final long serialVersionUID = 1L;
	private String userId;
	private String id;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
