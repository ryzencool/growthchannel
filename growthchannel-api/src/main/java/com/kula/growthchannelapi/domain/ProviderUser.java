package com.kula.growthchannelapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author alexey
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ProviderUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "int8 default 0")
    private Long userId;

    @Column( columnDefinition = "text default ''")
    private String email;

    @Column(columnDefinition = "text default ''")
    private String avatar;

    @Column(columnDefinition = "int2 default 0")
    private Integer providerType;

    @Column(columnDefinition = "text default ''")
    private String providerUserId;

    @Column(name = "access_token")
    private String accessToken;

    @Column(columnDefinition = "text default ''")
    private String refreshToken;


    private LocalDateTime expireIn;

    @Column(columnDefinition = "timestamp default now()")
    private LocalDateTime createTime;

    @Column(columnDefinition = "timestamp default now()")
    private LocalDateTime updateTime;

    @Column(columnDefinition = "int8 default 0")
    private Integer createId;

    @Column(columnDefinition = "int8 default 0")
    private Integer updateId;

    @Column(columnDefinition = "boolean default false")
    private Boolean isDel;
}
