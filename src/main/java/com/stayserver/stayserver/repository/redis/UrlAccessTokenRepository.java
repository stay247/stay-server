package com.stayserver.stayserver.repository.redis;

import com.stayserver.stayserver.redis.UrlAccessToken;
import org.springframework.data.repository.CrudRepository;

public interface UrlAccessTokenRepository extends CrudRepository<UrlAccessToken, String> {
}
