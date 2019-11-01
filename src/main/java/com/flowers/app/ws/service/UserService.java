package com.flowers.app.ws.service;

import org.springframework.data.domain.Page;

import com.flowers.app.ws.shared.dto.UserDto;
import com.flowers.app.ws.shared.dto.UsersDto;

public interface UserService {
	UsersDto createUser(UsersDto userList);
	UserDto getUser(UserDto userDto);
	void updateUser(UserDto user);
	void deleteUser(UserDto userDto);
	Page<UserDto> getUsers(int page, int limit);
}
