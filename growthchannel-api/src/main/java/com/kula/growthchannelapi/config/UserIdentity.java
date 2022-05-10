package com.kula.growthchannelapi.config;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * universal user login model
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIdentity {

    private Long userId;

    final UserIdentity identity(Claims claims) {
        var userIdentity = extend(claims);
        var userId = Long.valueOf(claims.get("jti").toString());
        userIdentity.setUserId(userId);
        return userIdentity;
    }

    protected UserIdentity extend(Claims claims) {
        return new UserIdentity();
    }
}
