CREATE TABLE `naver_user`
(
    `id`            VARCHAR(255) PRIMARY KEY COMMENT '네이버 사용자 ID',
    `nickname`      VARCHAR(100) NOT NULL COMMENT '사용자 닉네임',
    `profile_image` VARCHAR(255) COMMENT '프로필 이미지 URL',
    `age`           VARCHAR(10) COMMENT '사용자 연령대',
    `gender`        CHAR(1) COMMENT '성별 (M/F)',
    `email`         VARCHAR(100) COMMENT '이메일 주소',
    `mobile`        VARCHAR(20) COMMENT '모바일 번호',
    `mobile_e164`   VARCHAR(20) COMMENT '국제 표준 모바일 번호',
    `name`          VARCHAR(100) COMMENT '사용자 이름',
    `birthday`      VARCHAR(10) COMMENT '생일',
    `birthyear`     INT COMMENT '출생년도'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

CREATE TABLE `user`
(
    `user_id`           INT AUTO_INCREMENT PRIMARY KEY COMMENT '사용자 식별자',
    `naver_user_id`     VARCHAR(255) NOT NULL COMMENT '네이버 사용자 ID',
    `registration_date` DATE NOT NULL COMMENT '가입 날짜',
    `status`            VARCHAR(50) NOT NULL COMMENT '계정 상태',
    FOREIGN KEY (`naver_user_id`) REFERENCES `naver_user` (`id`),
    UNIQUE (`naver_user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

