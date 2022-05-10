package com.kula.growthchannelapi.controller;

import com.kula.growthchannelapi.config.OAuthGoogleProperties;
import com.kula.growthchannelapi.service.AnalyticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class AnalyticController {

    private final AnalyticsService analyticsService;

    private final OAuthGoogleProperties oAuthGoogleProperties;

    public AnalyticController(AnalyticsService analyticsService, OAuthGoogleProperties oAuthGoogleProperties) {
        this.analyticsService = analyticsService;
        this.oAuthGoogleProperties = oAuthGoogleProperties;
    }

    @GetMapping("analytics/connect")
    public ResponseEntity<Object> connect(@RequestParam("code") String code) {
        analyticsService.connect(code);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(oAuthGoogleProperties.getRedirectFrontPageUrl()))
                .build();
    }
}
