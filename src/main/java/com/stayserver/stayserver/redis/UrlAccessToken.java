package com.stayserver.stayserver.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.net.URL;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "UrlAccess", timeToLive = 600)
public class UrlAccessToken {

    @Id
    private URL url;  // url 식별자

    private boolean available = true;  // url 접근 가능 식별자

}
