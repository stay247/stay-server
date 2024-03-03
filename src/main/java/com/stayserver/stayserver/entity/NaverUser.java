package com.stayserver.stayserver.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NaverUser {
    @Id
    private String id;
    private String email;
    private String name;
    private String birthYear;
}
