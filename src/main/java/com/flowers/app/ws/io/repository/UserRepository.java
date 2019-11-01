package com.flowers.app.ws.io.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.flowers.app.ws.io.entity.UserEntity;
import com.flowers.app.ws.io.entity.UserEntityId;


@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, UserEntityId> {
	
	
	@Modifying
    @Transactional 
    @Query("UPDATE UserEntity u set u.body =:body,u.title =:title where u.userId = :userId and u.id = :id")
    void updateUserEntityByUserIdAndId(
    		@Param("id") String id,
            @Param("userId") String userId,
            @Param("body") String body,
            @Param("title") String title);

}
