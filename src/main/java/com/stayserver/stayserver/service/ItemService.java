package com.stayserver.stayserver.service;

import com.stayserver.stayserver.dto.CollectionDTO;
import com.stayserver.stayserver.entity.*;
import com.stayserver.stayserver.repository.jpa.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private final NaverUserRepository naverUserRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ItemShareRepository itemShareRepository;
    private final CollectionRepository collectionRepository;
    private final CollectionItemRepository collectionItemRepository;
    private final CollectionShareRepository collectionShareRepository;

    public void setDefaultData(User user) {
        List<Item> defaultItems = itemRepository.findAllByUserId(null);

        for (Item defaultItem : defaultItems) {
            ItemShare itemShare = new ItemShare();
            itemShare.setItemId(defaultItem.getItemId());
            itemShare.setSharedWithUserId(user.getUserId());
            itemShare.setSharedAt(LocalDateTime.now());
            itemShareRepository.save(itemShare);
        }

    }

    public List<CollectionDTO> getCollectionsByUserId(User user){
        try {
            List<CollectionDTO> allCollectionsDTO = new ArrayList<>();

            // 사용자의 컬렉션 추가
            List<Collection> myCollections = collectionRepository.findByUserId(user.getUserId());
            for (Collection collection : myCollections) {
                CollectionDTO dto = CollectionDTO.builder()
                        .collectionId(collection.getCollectionId())
                        .userId(collection.getUserId())
                        .name(collection.getName())
                        .description(collection.getDescription())
                        .backgroundImageData(collection.getBackgroundImageData())
                        .sharable(collection.getSharable())
                        .tag(collection.getTag())
                        .createdAt(collection.getCreatedAt())
                        .updatedAt(collection.getUpdatedAt())
                        .build();

                allCollectionsDTO.add(dto);
            }

            // 공유받은 컬렉션 추가
            List<CollectionShare> sharedCollections = collectionShareRepository.findBySharedWithUserId(user.getUserId());
            for (CollectionShare sharedCollection : sharedCollections) {
                collectionRepository.findById(sharedCollection.getCollectionId())
                        .ifPresent(collection -> {
                            CollectionDTO dto = CollectionDTO.builder()
                                    .collectionId(collection.getCollectionId())
                                    .userId(collection.getUserId())
                                    .name(collection.getName())
                                    .description(collection.getDescription())
                                    .backgroundImageData(collection.getBackgroundImageData())
                                    .sharable(collection.getSharable())
                                    .tag(collection.getTag())
                                    .createdAt(collection.getCreatedAt())
                                    .updatedAt(collection.getUpdatedAt())
                                    .build();
                            allCollectionsDTO.add(dto);
                        });
            }

            return allCollectionsDTO;

        } catch (Exception e) {
            log.error("Error retrieving collections for user {}: {}", user.getUserId(), e.getMessage(), e);
            throw e;
        }
    }
}
