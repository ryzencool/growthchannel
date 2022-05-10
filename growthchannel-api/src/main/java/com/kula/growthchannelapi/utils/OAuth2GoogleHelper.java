package com.kula.growthchannelapi.utils;


import cn.hutool.http.HttpRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kula.growthchannelapi.config.OAuthGoogleProperties;
import com.kula.growthchannelapi.entity.GoogleTokenResult;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class OAuth2GoogleHelper {


    private final OAuthGoogleProperties authGoogleProperties;

    public OAuth2GoogleHelper(OAuthGoogleProperties authGoogleProperties) {
        this.authGoogleProperties = authGoogleProperties;
    }


    public String createAuthenticationUrl(String scope, String prompt, String redirectUrl) {
        var state = UUID.randomUUID().toString();
        var params = Map.of(
                "scope", scope,
                "access_type", "offline",
                "prompt", prompt,
                "include_granted_scopes", "true",
                "response_type", "code",
                "state", state,
                "redirect_uri", redirectUrl,
                "client_id", authGoogleProperties.getClientId()
        );
        return HttpUtil.buildGetUrl("https://accounts.google.com/o/oauth2/v2/auth", params);
    }

    public GoogleTokenResult exchangeToken(String code, String redirectUrl) {
        String resp = HttpUtil.postForm("https://oauth2.googleapis.com/token", Map.of(
                "code", code,
                "client_id", authGoogleProperties.getClientId(),
                "client_secret", authGoogleProperties.getClientSecret(),
                "redirect_uri", redirectUrl,
                "grant_type", "authorization_code"
        ));
        var mapper = new ObjectMapper();
        GoogleTokenResult result = null;
        try {
            result = mapper.readValue(resp, GoogleTokenResult.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("");
        }

        return result;
    }

    public String getUserInfo(String assessToken) {
        return HttpRequest.get("https://www.googleapis.com/oauth2/v3/userinfo?access_token=" + assessToken)
                .execute().body();
    }

    public void refreshToken() {

    }


}
