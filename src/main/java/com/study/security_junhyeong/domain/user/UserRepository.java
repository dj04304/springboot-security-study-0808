package com.study.security_junhyeong.domain.user;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
	public int save(User user) throws Exception;
	public User findUserByUsername(String username) throws Exception;
}