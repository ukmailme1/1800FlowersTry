package com.flowers.app.ws.ui.model.response;

public class UserRest {
	private int userId;
	private int id;
	private String title;
	private String body;


	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null || obj.getClass() != this.getClass())
			return false;

		// type casting of the argument.
		UserRest userRest = (UserRest) obj;

		// comparing the state of argument with
		// the state of 'this' Object.
		return (userRest.userId==this.userId);
	}

	@Override
	public int hashCode() {

		// We are returning the Geek_id
		// as a hashcode value.
		// we can also return some
		// other calculated value or may
		// be memory address of the
		// Object on which it is invoked.
		// it depends on how you implement
		// hashCode() method.
		return this.userId;
	}

}
