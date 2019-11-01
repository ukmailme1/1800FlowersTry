package com.flowers.app.ws.shared.dto;

import java.util.List;


public class UsersDto {
	private List<UserDto> users;
	private int totalUniqueUser; 
	private int totalUser; 
	private boolean isInboundAndOutBoundMatches;
	public List<UserDto> getUsers() {
		return users;
	}
	public void setUsers(List<UserDto> users) {
		this.users = users;
	}
	public int getTotalUniqueUser() {
		return totalUniqueUser;
	}
	public void setTotalUniqueUser(int totalUniqueUser) {
		this.totalUniqueUser = totalUniqueUser;
	}
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
	
}
