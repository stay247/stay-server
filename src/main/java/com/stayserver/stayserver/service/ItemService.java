package com.stayserver.stayserver.service;

import com.stayserver.stayserver.entity.ItemShare;
import com.stayserver.stayserver.entity.User;
import com.stayserver.stayserver.repository.jpa.ItemRepository;
import com.stayserver.stayserver.repository.jpa.ItemShareRepository;
import com.stayserver.stayserver.repository.jpa.NaverUserRepository;
import com.stayserver.stayserver.repository.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final NaverUserRepository naverUserRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ItemShareRepository itemShareRepository;

    public void setDefaultData(User user) {
        int[] defaultItems = {1, 2, 3, 4, 5, 6}; // 하드코딩된..

        for (int defaultItem : defaultItems) {
            ItemShare itemShare = new ItemShare();
            itemShare.setItemId(defaultItem);
            itemShare.setSharedAt(LocalDateTime.now());
            itemShare.setSharedWithUserID(user.getUserId());
            itemShareRepository.save(itemShare);
        }
    }
}
