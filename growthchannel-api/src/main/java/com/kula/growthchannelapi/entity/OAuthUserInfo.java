package com.kula.growthchannelapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OAuthUserInfo {

    private String id;

    private String username;

    private String email;

    private String avatar;
}
