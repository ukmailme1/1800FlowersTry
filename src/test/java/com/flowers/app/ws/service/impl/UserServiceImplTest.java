package com.flowers.app.ws.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.flowers.app.ws.exceptions.UserServiceException;
import com.flowers.app.ws.io.entity.UserEntity;
import com.flowers.app.ws.io.entity.UserEntityId;
import com.flowers.app.ws.io.repository.UserRepository;
import com.flowers.app.ws.service.UserService;
import com.flowers.app.ws.shared.dto.UserDto;
import com.flowers.app.ws.shared.dto.UsersDto;
import com.flowers.app.ws.ui.model.response.ErrorMessages;

class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userService;

	@Mock
	UserRepository userRepository;

	UserEntity userEntity;
	UserDto feed;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		userEntity = new UserEntity();
		userEntity.setBody("body");
		userEntity.setId("id");
		userEntity.setTitle("title");
		userEntity.setUserId("userId");
		feed = new UserDto();
		feed.setBody("body");
		feed.setId("id");
		feed.setTitle("title");
		feed.setUserId("userId");

	}

	@Test
	void testCreateUser() {

		when(userRepository.findById(any(UserEntityId.class))).thenReturn(Optional.ofNullable(null));
		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
		UsersDto feeds = new UsersDto();
		List<UserDto> userDtoListFeed = new ArrayList<UserDto>();
		userDtoListFeed.add(feed);
		feeds.setUsers(userDtoListFeed);

		UsersDto result = userService.createUser(feeds);

		assertTrue(result.isInboundAndOutBoundMatches());
		assertEquals(1, result.getTotalUniqueUser());
		assertEquals(1, result.getTotalUser());
		List<UserDto> resultList = result.getUsers();
		assertEquals(1, resultList.size());
		UserDto resultuser = resultList.get(0);
		assertEquals("title", resultuser.getTitle());
		assertEquals("body", resultuser.getBody());
		assertEquals("id", resultuser.getId());
		assertEquals("userId", resultuser.getUserId());

	}

	@Test
	void testCreateUser_RecordAlreadyExist() {

		when(userRepository.findById(any(UserEntityId.class))).thenReturn(Optional.of(userEntity));
		UsersDto feeds = new UsersDto();
		List<UserDto> userDtoListFeed = new ArrayList<UserDto>();
		userDtoListFeed.add(feed);
		feeds.setUsers(userDtoListFeed);
		UserServiceException ex = assertThrows(UserServiceException.class, () -> userService.createUser(feeds));
		assertEquals(ex.getMessage(), "Record already exists");
	}

	@Test
	void testCreateUser_MissingFields() {
		UsersDto feeds = new UsersDto();
		List<UserDto> userDtoListFeed = new ArrayList<UserDto>();
		UserDto feedLocal = new UserDto();
		userDtoListFeed.add(feedLocal);
		feeds.setUsers(userDtoListFeed);
		UserServiceException ex = assertThrows(UserServiceException.class, () -> userService.createUser(feeds));
		assertEquals(ex.getMessage(), "Missing required field. Please check documentation for required fields");

	}

	@Test
	void testGetUser() {
		UserDto feed = new UserDto();
		feed.setId("id");
		feed.setUserId("userId");

		when(userRepository.findById(any(UserEntityId.class))).thenReturn(Optional.of(userEntity));

		UserDto userDto = userService.getUser(feed);
		assertEquals("title", userDto.getTitle());
		assertEquals("body", userDto.getBody());
		assertEquals("id", userDto.getId());
		assertEquals("userId", userDto.getUserId());
	}

	@Test
	void testUpdateUser_MissingIdAndUserId() {
		UserDto feed = new UserDto();
		UserServiceException ex = assertThrows(UserServiceException.class, () -> userService.updateUser(feed));
		assertEquals(ex.getMessage(), "Missing required field. Please check documentation for required fields");

	}

	@Test
	void testUpdateUser_MissingUserId() {
		UserDto feed = new UserDto();
		feed.setId("id");
		UserServiceException ex = assertThrows(UserServiceException.class, () -> userService.updateUser(feed));
		assertEquals(ex.getMessage(), "Missing required field. Please check documentation for required fields");

	}

	@Test
	void testUpdateUser_MissingId() {
		UserDto feed = new UserDto();
		feed.setUserId("userId");
		UserServiceException ex = assertThrows(UserServiceException.class, () -> userService.updateUser(feed));
		assertEquals(ex.getMessage(), "Missing required field. Please check documentation for required fields");
	}

	@Test
	void testUpdateUser_UserDoesNotExist() {
		when(userRepository.findById(any(UserEntityId.class))).thenReturn(Optional.ofNullable(null));
		UserDto feed = new UserDto();
		feed.setUserId("userId");
		feed.setId("id");
		UserServiceException ex = assertThrows(UserServiceException.class, () -> {
			userService.updateUser(feed);
		});
		assertEquals(ex.getMessage(), "Record with provided id is not found");

	}

	@Test
	void testDeleteUser_UserDoesNotExist() {
		when(userRepository.findById(any(UserEntityId.class))).thenReturn(Optional.ofNullable(null));
		UserDto feed = new UserDto();
		feed.setUserId("userId");
		feed.setId("id");
		UserServiceException ex = assertThrows(UserServiceException.class, () -> {
			userService.deleteUser(feed);
		});
		assertEquals(ex.getMessage(), "Record with provided id is not found");
	}

	@Test
	void testDeleteUser_MissingFields() {
		UserDto feed = new UserDto();
		UserServiceException ex = assertThrows(UserServiceException.class, () -> userService.deleteUser(feed));
		assertEquals(ex.getMessage(), "Missing required field. Please check documentation for required fields");
	}

	@Test
	void testGetUsers() {
		List<UserEntity> companies = new ArrayList<>();
		companies.add(userEntity);
		Page<UserEntity> pagedResponse = new PageImpl<UserEntity>(companies);
		Mockito.when(userRepository.findAll(any(Pageable.class))).thenReturn(pagedResponse);
		Page<UserDto> pagedResponseDto=userService.getUsers(1, 20);
		assertEquals(pagedResponseDto.getTotalElements(), 1);
		assertEquals(pagedResponseDto.getTotalPages(), 1);
		assertEquals(pagedResponseDto.getContent().size(), 1);
		assertEquals(pagedResponseDto.getContent().get(0).getId(), "id");
		assertEquals(pagedResponseDto.getContent().get(0).getUserId(), "userId");
		assertEquals(pagedResponseDto.getContent().get(0).getBody(), "body");
		assertEquals(pagedResponseDto.getContent().get(0).getTitle(), "title");
		
		
	}

	@Test
	void testGetUsers_NoUsers() {
		Mockito.when(userRepository.findAll(any(Pageable.class))).thenReturn(null);
		
		UserServiceException ex = assertThrows(UserServiceException.class, () -> {
			userService.getUsers(1,1);
		});
		assertEquals(ex.getMessage(), "Record with provided id is not found");
		
		
	}
}
