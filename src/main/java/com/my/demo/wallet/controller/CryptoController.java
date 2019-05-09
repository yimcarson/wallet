package com.my.demo.wallet.controller;

import com.my.demo.wallet.vo.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(value = "地址", description = "数字货币地址接口")
@RestController(value = "crypto")
public class CryptoController extends BaseController {

    @GetMapping(value = "{symbol}/address/{userId:\\d{20}}")
    public Result address(
            @PathVariable(value = "symbol") String symbol,
            @PathVariable(value = "userId") Long userId) {


        return null;
    }
}
