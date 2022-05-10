package com.kula.growthchannelapi.service;

import com.kula.growthchannelapi.entity.OAuthUserInfo;
import com.kula.growthchannelapi.enums.OAuthProviderEnum;
import com.kula.growthchannelapi.request.AuthUrlRequest;

public interface OAuthService {

    /**
     * get oauth provider type
     */
    OAuthProviderEnum getType();

    /**
     * generate authentication url
     */
    String newAuthUrl(AuthUrlRequest request);

    /**
     * third party oauth server callback
     */
    String callback(String code);

    /**
     * get userInfo
     */
    OAuthUserInfo getUserInfo(String accessToken);

}
