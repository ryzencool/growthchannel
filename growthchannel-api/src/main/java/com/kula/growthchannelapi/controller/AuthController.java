package com.kula.growthchannelapi.controller;

import com.kula.growthchannelapi.config.OAuthGoogleProperties;
import com.kula.growthchannelapi.config.UserIdentity;
import com.kula.growthchannelapi.domain.UserInfo;
import com.kula.growthchannelapi.enums.OAuthProviderEnum;
import com.kula.growthchannelapi.request.AuthUrlRequest;
import com.kula.growthchannelapi.service.OAuthService;
import com.kula.growthchannelapi.service.UserService;
import com.kula.growthchannelapi.utils.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
@RestController
public class AuthController {

    private final Map<OAuthProviderEnum, OAuthService> authServiceMap;

    private final UserService userService;

    private final OAuthGoogleProperties oAuthGoogleProperties;


    public AuthController(List<OAuthService> authServices,
                          UserService userService,
                          OAuthGoogleProperties oAuthGoogleProperties) {
        this.authServiceMap = authServices.stream()
                .collect(Collectors.toMap(OAuthService::getType, Function.identity()));
        this.userService = userService;
        this.oAuthGoogleProperties = oAuthGoogleProperties;
    }


    /**
     * generate google authentication url
     */
    @GetMapping("/oauth/{provider}/url")
    public BaseResponse<String> oauthUrl(@PathVariable("provider") OAuthProviderEnum provider,
                                         AuthUrlRequest request) {
        return BaseResponse.success(authServiceMap.get(provider).newAuthUrl(request));
    }

    /**
     * google server's callback
     */
    @RequestMapping("/oauth/{provider}/login")
    public ResponseEntity<Object> oauthCallback(@RequestParam("code") String code,
                                                @PathVariable("provider") OAuthProviderEnum provider,
                                                HttpServletResponse response) {
        var token = authServiceMap.get(provider).callback(code);
        var headers = new HttpHeaders();

        headers.add("Set-Cookie","token="+ token + "; Max-Age=604800; Path=/;");

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(oAuthGoogleProperties.getRedirectFrontPageUrl()))
                .headers(headers)
                .build();
    }

    @GetMapping("/userInfo")
    public BaseResponse<UserInfo> userInfo(UserIdentity identity) {
        return BaseResponse.success(userService.userInfo(identity.getUserId()));
    }

    @PostMapping("/signIn")
    public void SignIn() {

    }

    @PostMapping("/signUp")
    public void signUp() {

    }

    @PostMapping("/signOut")
    public void SignOut() {

    }

}
