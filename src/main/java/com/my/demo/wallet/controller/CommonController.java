package com.my.demo.wallet.controller;

import com.my.demo.wallet.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@Api(value = "通用", description = "通用的接口")
@RestController(value = "common")
public class CommonController extends BaseController {
    @Value("${version}")
    private String version;

    @ApiOperation(value = "获取版本号")
    @GetMapping(value = "version")
    public Result version() {
        return Result.builder().code(0).msg("success").data(version).build();
    }

    @ApiOperation(value = "测试签名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sign", value = "签名", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", dataType = "Long", paramType = "query", required = true)})
    @GetMapping(value = "test")
    public Result test(
            @RequestParam(value = "sign") String sign,
            @RequestParam(value = "timestamp") Long timestamp) {
        Map<String, String> params = new HashMap<>();
        params.put("sign", sign);
        params.put("timestamp", String.valueOf(timestamp));
        if (checkSign(params)) {
            return Result.builder().code(0).msg("").data(true).build();
        } else {
            return Result.builder().code(0).msg("").data(false).build();
        }
    }
}
