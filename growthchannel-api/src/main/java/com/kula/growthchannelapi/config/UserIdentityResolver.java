package com.kula.growthchannelapi.config;


import com.kula.growthchannelapi.utils.BaseBizException;
import com.kula.growthchannelapi.utils.BaseError;
import com.kula.growthchannelapi.utils.JwtHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


/**
 * if controller method contains UserIdentity Object, the value that jwt been parsed will inject into userIdentity
 */
@Slf4j
@Component
public class UserIdentityResolver implements HandlerMethodArgumentResolver {
    private final JwtHelper jwtHelper;

    public UserIdentityResolver(JwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return UserIdentity.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        var token = webRequest.getHeader("Authorization");
        if (token == null) {
            throw new BaseBizException(BaseError.NO_AUTH);
        }
        Claims claims = null;
        try {
            claims = jwtHelper.parse(token);
        } catch (ExpiredJwtException | MalformedJwtException ex) {
            throw new BaseBizException(BaseError.EXPIRE_TOKEN);
        }



        return ((UserIdentity) parameter.getParameterType().getDeclaredConstructor().newInstance()).identity(claims);
    }


}
