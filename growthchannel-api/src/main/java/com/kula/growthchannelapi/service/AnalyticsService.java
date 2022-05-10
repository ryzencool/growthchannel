package com.kula.growthchannelapi.service;

import com.kula.growthchannelapi.config.OAuthGoogleProperties;
import com.kula.growthchannelapi.enums.OAuthProviderEnum;
import com.kula.growthchannelapi.repository.ProviderUserRepository;
import com.kula.growthchannelapi.repository.UserInfoRepository;
import com.kula.growthchannelapi.service.impl.OAuthGoogleServiceImpl;
import com.kula.growthchannelapi.utils.OAuth2GoogleHelper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class AnalyticsService {

    private final OAuth2GoogleHelper oAuth2GoogleHelper;

    private final UserInfoRepository userInfoRepository;

    private final OAuthGoogleServiceImpl oAuthGoogleService;

    private final ProviderUserRepository providerUserRepository;

    private final OAuthGoogleProperties googleProperties;

    public AnalyticsService(OAuth2GoogleHelper oAuth2GoogleHelper,
                            UserInfoRepository userInfoRepository,
                            OAuthGoogleServiceImpl oAuthGoogleService,
                            ProviderUserRepository providerUserRepository,
                            OAuthGoogleProperties googleProperties) {
        this.oAuth2GoogleHelper = oAuth2GoogleHelper;
        this.userInfoRepository = userInfoRepository;
        this.oAuthGoogleService = oAuthGoogleService;
        this.providerUserRepository = providerUserRepository;
        this.googleProperties = googleProperties;
    }


    /**
     * connect google analytics
     */
    @SneakyThrows
    public void connect(String code) {
        var tokenBundle = oAuth2GoogleHelper.exchangeToken(code, googleProperties.getAnalyticsRedirectUrl());
        var userInfo = oAuthGoogleService.getUserInfo(tokenBundle.getAccessToken());
        userInfoRepository.findByEmailAndIsDel(userInfo.getEmail(), false)
                .ifPresent(user -> {
                    user.setIsConnectGoogleAnalytics(true);
                    user.setUpdateTime(LocalDateTime.now());
                    userInfoRepository.save(user);
                });
        providerUserRepository.findByEmailAndProviderTypeAndIsDel(userInfo.getEmail(), OAuthProviderEnum.Google.getType(), false)
                .ifPresent(user -> {
                    user.setRefreshToken(tokenBundle.getRefreshToken());
                    user.setExpireIn(LocalDateTime.now().plus(tokenBundle.getExpiresIn(), ChronoUnit.MINUTES));
                    user.setAccessToken(tokenBundle.getAccessToken());
                    user.setUpdateTime(LocalDateTime.now());
                    providerUserRepository.save(user);
                });
    }
}
