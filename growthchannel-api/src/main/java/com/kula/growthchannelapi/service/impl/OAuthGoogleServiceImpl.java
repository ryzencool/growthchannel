package com.kula.growthchannelapi.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kula.growthchannelapi.config.OAuthGoogleProperties;
import com.kula.growthchannelapi.domain.ProviderUser;
import com.kula.growthchannelapi.domain.UserInfo;
import com.kula.growthchannelapi.entity.GoogleTokenResult;
import com.kula.growthchannelapi.entity.OAuthUserInfo;
import com.kula.growthchannelapi.enums.OAuthProviderEnum;
import com.kula.growthchannelapi.repository.ProviderUserRepository;
import com.kula.growthchannelapi.repository.UserInfoRepository;
import com.kula.growthchannelapi.request.AuthUrlRequest;
import com.kula.growthchannelapi.service.OAuthService;
import com.kula.growthchannelapi.utils.JwtHelper;
import com.kula.growthchannelapi.utils.OAuth2GoogleHelper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import static com.kula.growthchannelapi.constant.OAuthConstant.JWT_EXPIRE;

@Slf4j
@Service
public class OAuthGoogleServiceImpl implements OAuthService {

    private final JwtHelper jwtHelper;

    private final UserInfoRepository userInfoRepository;

    private final ProviderUserRepository providerUserRepository;

    private final OAuth2GoogleHelper googleHelper;

    private final OAuthGoogleProperties googleProperties;

    public OAuthGoogleServiceImpl(JwtHelper jwtHelper,
                                  UserInfoRepository userInfoRepository,
                                  ProviderUserRepository providerUserRepository,
                                  OAuth2GoogleHelper googleHelper, OAuthGoogleProperties googleProperties) {
        this.jwtHelper = jwtHelper;
        this.userInfoRepository = userInfoRepository;
        this.providerUserRepository = providerUserRepository;
        this.googleHelper = googleHelper;
        this.googleProperties = googleProperties;
    }

    @Override
    public OAuthProviderEnum getType() {
        return OAuthProviderEnum.Google;
    }

    @Override
    public String newAuthUrl(AuthUrlRequest request) {
        return googleHelper.createAuthenticationUrl(request.getScope(), request.getPrompt(), request.getRedirectUrl());
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public String callback(String code) {
        var tokens = googleHelper.exchangeToken(code, googleProperties.getAuthRedirectUrl());
        var oauthUser = getUserInfo(tokens.getAccessToken());
        var email = oauthUser.getEmail();
        var googleUserOpt = providerUserRepository
                .findByEmailAndProviderTypeAndIsDel(email, getType().getType(), false);
        Long userId;

        if (googleUserOpt.isPresent()) {
            var googleUser = googleUserOpt.get();
            googleUser = updateGoogleToken(tokens, googleUser);
            userId = googleUser.getUserId();
        } else {
            var userOpt = userInfoRepository.findByEmailAndIsDel(email, false);
            if (userOpt.isEmpty()) {
                var user = createUserAndGoogleUser(tokens, oauthUser);
                userId = user.getId();
            } else {
                userId = userOpt.get().getId();
                createGoogleUserOnly(tokens, oauthUser, userId);
            }
        }
        return createJWT(userId, email);

    }


    @SneakyThrows
    @Override
    public OAuthUserInfo getUserInfo(String accessToken) {
        var userInfo = googleHelper.getUserInfo(accessToken);
        var mapper = new ObjectMapper();
        var user = mapper.readValue(userInfo, Map.class);
        return OAuthUserInfo.builder().id((String) user.get("sub"))
                .username((String) user.get("name"))
                .email((String) user.get("email"))
                .avatar((String) user.get("picture"))
                .build();

    }

    private String createJWT(Long userId, String email) {
        var claim = new HashMap<String, Object>();
        return jwtHelper.create(String.valueOf(userId), email, claim, JWT_EXPIRE);
    }


    private ProviderUser updateGoogleToken(GoogleTokenResult tokenBundle, ProviderUser googleUser) {
        googleUser.setAccessToken(tokenBundle.getAccessToken());
        googleUser.setRefreshToken(tokenBundle.getRefreshToken());
        googleUser.setExpireIn(LocalDateTime.now().plus(tokenBundle.getExpiresIn(), ChronoUnit.MINUTES));
        googleUser.setUpdateTime(LocalDateTime.now());
        googleUser = providerUserRepository.save(googleUser);
        return googleUser;
    }

    private void createGoogleUserOnly(GoogleTokenResult tokenBundle, OAuthUserInfo oauthUser, Long userId) {
        var pUser = ProviderUser.builder()
                .accessToken(tokenBundle.getAccessToken())
                .refreshToken(tokenBundle.getRefreshToken())
                .expireIn(LocalDateTime.now().plus(tokenBundle.getExpiresIn(), ChronoUnit.MINUTES))
                .providerType(getType().getType())
                .providerUserId(oauthUser.getId())
                .email(oauthUser.getEmail())
                .avatar(oauthUser.getAvatar())
                .userId(userId)
                .createTime(LocalDateTime.now())
                .isDel(false)
                .build();
        providerUserRepository.save(pUser);
    }

    private UserInfo createUserAndGoogleUser(GoogleTokenResult tokenBundle, OAuthUserInfo oauthUser) {
        var user = UserInfo.builder()
                .email(oauthUser.getEmail())
                .username(oauthUser.getUsername())
                .avatar(oauthUser.getAvatar())
                .createTime(LocalDateTime.now())
                .isConnectGoogleAnalytics(false)
                .isDel(false)
                .build();
        user = userInfoRepository.save(user);
        createGoogleUserOnly(tokenBundle, oauthUser, user.getId());
        return user;
    }
}
