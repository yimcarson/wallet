package com.my.demo.wallet.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Result {
    private Integer code;

    private String msg;

    private Object data;
}
