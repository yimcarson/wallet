package com.my.demo.wallet.controller;

import com.my.demo.wallet.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "通用", description = "通用的接口")
public class CommonController extends BaseController {
    @Value("${version}")
    private String version;

    @ApiOperation(value = "获取版本号")
    @GetMapping(value = "version")
    public Result version() {
        return Result.builder().code(0).msg("success").data(version).build();
    }
}
