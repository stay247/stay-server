package com.stayserver.stayserver.mapper;

import com.stayserver.stayserver.dto.naver.naverUser.NaverUserDto;
import com.stayserver.stayserver.entity.NaverUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NaverUserMapper {
    NaverUserMapper INSTANCE = Mappers.getMapper(NaverUserMapper.class);

    NaverUser toEntity(NaverUserDto dto);
}