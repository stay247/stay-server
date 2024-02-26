package com.stayserver.stayserver.service;

import java.io.InputStream;

public interface MediaStorageService {
    String saveMedia(InputStream mediaStream, String filename);
    InputStream loadMedia(String filename);
    void deleteMedia(String filename);
}