package com.flowers.app.ws.ui.model.response;

import java.util.List;


public class UsersRest {

	private List<UserRest> users;
	private int totalUniqueUser; 
	private int totalUser; 
	private boolean isInboundAndOutBoundMatches; 
	
	
	public int getTotalUser() {
		return totalUser;
	}
	public void setTotalUser(int totalUser) {
		this.totalUser = totalUser;
	}
	public boolean isInboundAndOutBoundMatches() {
		return isInboundAndOutBoundMatches;
	}
	public void setInboundAndOutBoundMatches(boolean isInboundAndOutBoundMatches) {
		this.isInboundAndOutBoundMatches = isInboundAndOutBoundMatches;
	}
	public List<UserRest> getUsers() {
		return users;
	}
	public void setUsers(List<UserRest> users) {
		this.users = users;
	}
	
	public int getTotalUniqueUser() {
		return totalUniqueUser;
	}
	public void setTotalUniqueUser(int totalUniqueUser) {
		this.totalUniqueUser = totalUniqueUser;
	}

	
}
