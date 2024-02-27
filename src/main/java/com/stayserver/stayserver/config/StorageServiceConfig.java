package com.stayserver.stayserver.config;

import com.stayserver.stayserver.service.LocalSoundStorageService;
import com.stayserver.stayserver.service.SoundStorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class StorageServiceConfig {

    @Bean
    @Profile("local")
    public SoundStorageService localSoundStorageService() {
        return new LocalSoundStorageService();
    }

//    @Bean
//    @Profile("aws")
//    public AudioStorageService s3AudioStorageService() {
//        return new S3AudioStorageService(/* AWS S3 클라이언트 구성 */);
//    }
}
