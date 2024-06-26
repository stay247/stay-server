CREATE TABLE `naver_user`
(
    `id`         VARCHAR(255) PRIMARY KEY COMMENT '네이버 사용자 ID',
    `email`      VARCHAR(100) COMMENT '이메일 주소',
    `name`       VARCHAR(100) COMMENT '사용자 이름',
    `birth_year` VARCHAR(100) COMMENT '출생년도'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

CREATE TABLE `google_user`
(
    `id`    VARCHAR(255) PRIMARY KEY COMMENT '구글 사용자 고유 ID',
    `email` VARCHAR(100) COMMENT '이메일 주소',
    `name`  VARCHAR(100) COMMENT '사용자 이름'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

CREATE TABLE `kakao_user`
(
    `id`    VARCHAR(255) PRIMARY KEY COMMENT '카카오 사용자 고유 ID',
    `name`  VARCHAR(100) COMMENT '사용자 이름'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

CREATE TABLE `user`
(
    `user_id`        INT AUTO_INCREMENT PRIMARY KEY COMMENT '사용자 식별자',
    `oauth_provider` VARCHAR(50)  NOT NULL COMMENT 'oauth 제공자',
    `oauth_id`       VARCHAR(255) NOT NULL COMMENT 'oauth 고유 ID',
    `status`         VARCHAR(50)  NOT NULL COMMENT '계정 상태',
    `role`           VARCHAR(50)  NOT NULL COMMENT '계정 역할',
    `created_at`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '가입 시간'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

CREATE TABLE item
(
    `item_id`     INT AUTO_INCREMENT PRIMARY KEY COMMENT '아이템 식별자',
    `user_id`     INT COMMENT '등록 사용자 식별자, NULL -> publicItem',
    `name`        VARCHAR(255) NOT NULL COMMENT '아이템 이름',
    `description` TEXT         NOT NULL COMMENT '아이템 설명',
    `icon_data`   VARCHAR(255) NOT NULL COMMENT '아이콘',
    `sound_data`  VARCHAR(255) NOT NULL COMMENT '소리 데이터',
    `sharable`    BOOLEAN      NOT NULL COMMENT '공유 여부',
    `tag`         TEXT         NOT NULL COMMENT '아이템 태그',
    `created_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
    `updated_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정 시간',
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

CREATE TABLE `item_share`
(
    `item_share_id`       INT AUTO_INCREMENT PRIMARY KEY COMMENT '아이템 공유 식별자',
    `item_id`             INT      NOT NULL COMMENT '공유된 아이템 식별자',
    `shared_with_user_id` INT      NOT NULL COMMENT '공유받은 사용자 식별자',
    `shared_at`           DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '공유 시간',
    FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`),
    FOREIGN KEY (`shared_with_user_id`) REFERENCES `user` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

CREATE TABLE `collection`
(
    `collection_id`         INT AUTO_INCREMENT PRIMARY KEY COMMENT '컬렉션 식별자',
    `user_id`               INT          NOT NULL COMMENT '컬렉션을 생성한 사용자 식별자',
    `name`                  VARCHAR(255) NOT NULL COMMENT '컬렉션 이름',
    `description`           TEXT         NOT NULL COMMENT '컬렉션 설명',
    `background_image_data` VARCHAR(255) NOT NULL COMMENT '배경 이미지 데이터',
    `sharable`              BOOLEAN      NOT NULL COMMENT '공유 여부',
    `tag`                   TEXT         NOT NULL COMMENT '컬렉션 태그',
    `created_at`            DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
    `updated_at`            DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정 시간',
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

CREATE TABLE `collection_item`
(
    `collection_item_id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '컬렉션 아이템 식별자',
    `collection_id`      INT NOT NULL COMMENT '컬렉션 식별자',
    `item_id`            INT NOT NULL COMMENT '아이템 식별자',
    `order`              INT NOT NULL COMMENT '아이템의 순서',
    `volume`             INT NOT NULL COMMENT '아이템의 볼륨 설정',
    FOREIGN KEY (`collection_id`) REFERENCES `collection` (`collection_id`),
    FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

CREATE TABLE `collection_share`
(
    `collection_share_id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '컬렉션 공유 식별자',
    `collection_id`       INT      NOT NULL COMMENT '공유된 컬렉션 식별자',
    `shared_with_user_id` INT      NOT NULL COMMENT '컬렉션을 공유받은 사용자 식별자',
    `shared_at`           DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '공유 시간',
    FOREIGN KEY (`collection_id`) REFERENCES `collection` (`collection_id`),
    FOREIGN KEY (`shared_with_user_id`) REFERENCES `user` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

INSERT INTO `item` (`item_id`, `user_id`, `name`, `description`, `icon_data`, `sound_data`, `sharable`, `tag`, `created_at`, `updated_at`)
VALUES
    (1, NULL, 'rain', 'rain', 'rain', 'rain.mp3', 1, 'rain', '2024-03-17 20:49:17', '2024-03-17 20:49:17'),
    (2, NULL, 'fire', 'fire', 'fire', 'fire.mp3', 1, 'fire', '2024-03-17 20:49:37', '2024-03-17 20:49:37');

