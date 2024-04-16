package com.stayserver.stayserver.redis;

import com.stayserver.stayserver.security.dto.RefreshTokenDto;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableRedisRepositories
public interface RefreshTokenRepository extends CrudRepository<RefreshTokenDto, Integer> {

    Optional<RefreshTokenDto> findByAccessToken(String accessToken);

    Optional<RefreshTokenDto> findByUserIdx(Integer userIdx);

}
