package com.kula.growthchannelapi.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthUrlRequest {

    private String scope;

    private String prompt;

    private String redirectUrl;
}
