package com.kula.growthchannelapi.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * oauth google properties
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ConfigurationProperties("oauth.google")
public class OAuthGoogleProperties {

    private String clientId;

    private String clientSecret;

    private String authRedirectUrl;

    private String analyticsRedirectUrl;

    private String redirectFrontPageUrl;



}
