package com.flowers.app.ws.ui.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import com.flowers.app.ws.service.UserService;
import com.flowers.app.ws.shared.dto.UserDto;
import com.flowers.app.ws.ui.model.response.UserRest;

class UserControllerTest {
	@InjectMocks
	UserController userController;

	@Mock
	UserService userService;

	UserDto feed;
	final String USER_ID = "1";

	final String ID = "1";

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		feed = new UserDto();
		feed.setBody("body");
		feed.setId("1");
		feed.setTitle("title");
		feed.setUserId("1");

	}

	@Test
	void testGetUser() {
		when(userService.getUser(any(UserDto.class))).thenReturn(feed);
		UserRest userRest = userController.getUser(USER_ID, ID);
		assertNotNull(userRest);
		assertEquals(feed.getBody(), userRest.getBody());
		assertEquals(feed.getTitle(), userRest.getTitle());

	}

	@Test
	void testGetUsers() {
		List<UserDto> companies = new ArrayList<>();
		companies.add(feed);
		Page<UserDto> pagedResponse = new PageImpl<UserDto>(companies);
		Mockito.when(userService.getUsers(anyInt(), anyInt())).thenReturn(pagedResponse);
		Page<UserDto> pagedResponseDto = userController.getUsers(1, 20);
		assertEquals(pagedResponseDto.getTotalElements(), 1);
		assertEquals(pagedResponseDto.getTotalPages(), 1);
		assertEquals(pagedResponseDto.getContent().size(), 1);
		assertEquals(pagedResponseDto.getContent().get(0).getId(), "1");
		assertEquals(pagedResponseDto.getContent().get(0).getUserId(), "1");
		assertEquals(pagedResponseDto.getContent().get(0).getBody(), "body");
		assertEquals(pagedResponseDto.getContent().get(0).getTitle(), "title");

	}

	@Test
	void testCreateUsers() {
		// fail("Not yet implemented");
	}

	@Test
	void testUpdateUser() {
		// fail("Not yet implemented");
	}

	@Test
	void testUpdateAllUser() {
		// fail("Not yet implemented");
	}

	@Test
	void testDeleteUser() {
		// fail("Not yet implemented");
	}

}
