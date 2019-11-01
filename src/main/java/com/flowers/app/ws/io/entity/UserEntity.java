package com.flowers.app.ws.io.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@IdClass(UserEntityId.class)
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 5313493413859894403L;

	// @Id
	// @GeneratedValue
	// private long privateId;

	@Id
	private String userId;


	@Id
	private String id;
	
	
	@Column(nullable = false)
	private String body;

	@Column(nullable = false, length = 150)
	private String title;


	@Column(nullable = true)
	private Date operationTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime() {
		this.operationTime = new Date();
	}

}
