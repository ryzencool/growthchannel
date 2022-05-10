package com.kula.growthchannelapi.enums;

import lombok.Getter;

public enum OAuthProviderEnum {
    Local(0), Google(1), Facebook(2), Github(3);

    @Getter
    private final Integer type;

    OAuthProviderEnum(Integer type) {
        this.type = type;
    }

}
