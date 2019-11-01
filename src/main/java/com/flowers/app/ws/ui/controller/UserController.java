package com.flowers.app.ws.ui.controller;

import java.util.ArrayList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flowers.app.ws.io.repository.UserRepository;
import com.flowers.app.ws.service.UserService;
import com.flowers.app.ws.shared.dto.UserDto;
import com.flowers.app.ws.shared.dto.UsersDto;
import com.flowers.app.ws.ui.model.request.UserDetailsRequestModel;
import com.flowers.app.ws.ui.model.response.OperationStatusModel;
import com.flowers.app.ws.ui.model.response.UserRest;
import com.flowers.app.ws.ui.model.response.UsersRest;

@RestController
@RequestMapping("users")

public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest getUser(@RequestParam(value = "id") String id, @RequestParam(value = "userId") String userId) {
		UserDto userDto = new UserDto();
		userDto.setId(id);
		userDto.setUserId(userId);
		UserDto fromDB = userService.getUser(userDto);
		ModelMapper modelMapper = new ModelMapper();
		UserRest returnValue = modelMapper.map(fromDB, UserRest.class);
		return returnValue;
	}

	@GetMapping(path = "/all", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public Page<UserDto> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "20") int limit) {
		Page<UserDto> users = userService.getUsers(page, limit);
		return users;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UsersRest createUsers(@RequestBody UserDetailsRequestModel[] userDetails) throws Exception {
		ArrayList<UserDto> userList = new ArrayList<UserDto>();
		ModelMapper modelMapper = new ModelMapper();
		UsersDto usersDto = new UsersDto();
		for (int i = 0; i < userDetails.length; i++) {
			if (i == 3) {
				userDetails[i].setBody("1800Flowers");
				userDetails[i].setTitle("1800Flowers");
			}
			UserDto userDto = modelMapper.map(userDetails[i], UserDto.class);
			userList.add(userDto);
		}
		usersDto.setUsers(userList);
		UsersDto userListFromDb = userService.createUser(usersDto);
		UsersRest returnValue = modelMapper.map(userListFromDb, UsersRest.class);
		return returnValue;
	}

	@PutMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public OperationStatusModel updateUser(@RequestBody UserDetailsRequestModel userDetails) {
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		userService.updateUser(userDto);
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName("UpdateAll");
		returnValue.setOperationResult("SUCCESS");
		return returnValue;
	}

	@PutMapping(path = "updateAll", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public OperationStatusModel updateAllUser(@RequestBody UserDetailsRequestModel[] userDetails) {
		ModelMapper modelMapper = new ModelMapper();
		for (int i = 0; i < userDetails.length; i++) {
			UserDto userDto = modelMapper.map(userDetails[i], UserDto.class);
			userService.updateUser(userDto);
		}
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName("UpdateAll");
		returnValue.setOperationResult("SUCCESS");
		return returnValue;
	}

	@DeleteMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public OperationStatusModel deleteUser(@RequestParam(value = "id") String id,
			@RequestParam(value = "userId") String userId) {
		UserDto userDto = new UserDto();
		userDto.setId(id);
		userDto.setUserId(userId);
		userService.deleteUser(userDto);
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName("Delete");
		returnValue.setOperationResult("SUCCESS");
		return returnValue;
	}
}
