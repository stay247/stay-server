package com.stayserver.stayserver.service;

import org.springframework.core.io.Resource;

import java.io.InputStream;

public interface SoundStorageService {
    Boolean saveSound(InputStream soundStream, String filename);
    Resource loadSound(String filename);
    void deleteSound(String filename);
}