package com.flowers.app.ws.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.flowers.app.ws.exceptions.UserServiceException;
import com.flowers.app.ws.io.entity.UserEntity;
import com.flowers.app.ws.io.entity.UserEntityId;
import com.flowers.app.ws.io.repository.UserRepository;
import com.flowers.app.ws.service.UserService;
import com.flowers.app.ws.shared.dto.UserDto;
import com.flowers.app.ws.shared.dto.UsersDto;
import com.flowers.app.ws.ui.model.response.ErrorMessages;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UsersDto createUser(UsersDto usersDto) {
		UsersDto returnedValue = new UsersDto();
		List<UserDto> returnUserDtoList = new ArrayList<UserDto>();
		List<UserDto> userDtoList = usersDto.getUsers();
		ModelMapper modelMapper = new ModelMapper();
		int inbound = userDtoList.size();
		for (Iterator<UserDto> iterator = userDtoList.iterator(); iterator.hasNext();) {
			UserDto userDto = iterator.next();
			if (userDto.getId() == null || userDto.getId().equalsIgnoreCase(""))
				throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
			if (userDto.getUserId() == null || userDto.getUserId().equalsIgnoreCase(""))
				throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
			if (userDto.getBody() == null || userDto.getBody().equalsIgnoreCase(""))
				throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
			if (userDto.getTitle() == null || userDto.getTitle().equalsIgnoreCase(""))
				throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

			doesExistInDb(userDto);

			UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
			userEntity.setOperationTime();
			UserEntity storedUserDetails = userRepository.save(userEntity);
			UserDto returnedUserDto = modelMapper.map(storedUserDetails, UserDto.class);
			returnUserDtoList.add(returnedUserDto);
		}
		int outbound = returnUserDtoList.size();
		if (inbound == outbound)
			returnedValue.setInboundAndOutBoundMatches(true);
		else
			returnedValue.setInboundAndOutBoundMatches(false);
		// hashset - dint use this
		HashSet<UserDto> uniqueUser = new HashSet<UserDto>(returnUserDtoList);
		// hashmap
		Map<String, ArrayList<UserDto>> uniqueUserMap = returnUserDtoList.stream().collect(
				Collectors.groupingBy(UserDto::getUserId, HashMap::new, Collectors.toCollection(ArrayList::new)));
		returnedValue.setUsers(returnUserDtoList);
		returnedValue.setTotalUniqueUser(uniqueUserMap.size());
		returnedValue.setTotalUser(returnUserDtoList.size());

		return returnedValue;
	}

	@Override
	public UserDto getUser(UserDto userDto) {
		UserEntityId ue = new UserEntityId();
		ue.setId(userDto.getId());
		ue.setUserId(userDto.getUserId());
		Optional<UserEntity> fromDb = Optional.of(userRepository.findById(ue)).orElse(null);
		if (!fromDb.isPresent())
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		ModelMapper modelMapper = new ModelMapper();
		UserDto returnValue = modelMapper.map(fromDb.get(), UserDto.class);
		return returnValue;
	}

	private void doesExistInDb(UserDto user) {
		UserEntityId ue = new UserEntityId();
		ue.setId(user.getId());
		ue.setUserId(user.getUserId());
		Optional<UserEntity> fromDb = Optional.of(userRepository.findById(ue)).orElse(null);
		if (fromDb.isPresent())
			throw new UserServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());

	}

	private void doesNotExistInDb(UserDto user) {
		UserEntityId ue = new UserEntityId();
		ue.setId(user.getId());
		ue.setUserId(user.getUserId());
		Optional<UserEntity> fromDb = Optional.of(userRepository.findById(ue)).orElse(null);
		if (!fromDb.isPresent())
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

	}

	@Override
	public void updateUser(UserDto user) {
		if (user.getId() == null || user.getId().equalsIgnoreCase(""))
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		if (user.getUserId() == null || user.getUserId().equalsIgnoreCase(""))
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		doesNotExistInDb(user);
		userRepository.updateUserEntityByUserIdAndId(user.getId(), user.getUserId(), user.getBody(), user.getTitle());
	}

	@Override
	public void deleteUser(UserDto userDto) {

		if (userDto.getId() == null || userDto.getId().equalsIgnoreCase(""))
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		if (userDto.getUserId() == null || userDto.getUserId().equalsIgnoreCase(""))
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

		doesNotExistInDb(userDto);
		UserEntityId ue = new UserEntityId();
		ue.setId(userDto.getId());
		ue.setUserId(userDto.getUserId());
		userRepository.deleteById(ue);

	}

	@Override
	public Page<UserDto> getUsers(int page, int limit) {

		if (page > 0)
			page = page - 1;

		Pageable pageableRequest = PageRequest.of(page, limit);

		Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
		if (null== usersPage)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		List<UserDto> returnValue = new ArrayList<>();
		List<UserEntity> users = usersPage.getContent();
		long totalCount = usersPage.getTotalElements();
		for (UserEntity userEntity : users) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);
		}
		final Page<UserDto> pageDto = new PageImpl<UserDto>(returnValue, pageableRequest, totalCount);

		return pageDto;

	}

}
