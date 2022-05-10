package com.kula.growthchannelapi.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseErrorCode {


    private String code;

    private String msg;


}
