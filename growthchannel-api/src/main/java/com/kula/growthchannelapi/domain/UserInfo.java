package com.kula.growthchannelapi.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column( columnDefinition = "text default ''")
    private String username;

    @Column(columnDefinition = "text default ''")
    private String avatar;

    @Column(columnDefinition = "int2 default 1")
    private Integer gender;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(columnDefinition = "text default ''")
    private String password;

    @Column(columnDefinition = "int2 default 0")
    private Integer status;

    @Column( columnDefinition = "boolean default false")
    private Boolean isConnectGoogleAnalytics;

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
