package com.sparksys.authorization.interfaces.controller;

import com.sparksys.commons.core.constant.CoreConstant;
import com.sparksys.commons.core.entity.AuthUser;
import com.sparksys.commons.security.entity.AuthToken;
import com.sparksys.commons.security.request.AuthRequest;
import com.sparksys.commons.security.service.AbstractSecurityAuthDetailService;
import com.sparksys.commons.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * description：登录接口
 *
 * @Author zhouxinlei
 * @Date 2020/6/6 9:08 上午
 */
@RestController
@RequestMapping("/oauth")
@ResponseResult
@Api(tags = "登录管理")
public class OauthController {

    private final AbstractSecurityAuthDetailService abstractSecurityAuthDetailService;

    public OauthController(AbstractSecurityAuthDetailService abstractSecurityAuthDetailService) {
        this.abstractSecurityAuthDetailService = abstractSecurityAuthDetailService;
    }


    @ApiOperation("系统登录")
    @PostMapping("/login")
    public AuthToken login(@Validated @RequestBody AuthRequest authRequest) {
        return abstractSecurityAuthDetailService.login(authRequest);
    }

    @ApiOperation("获取登录用户信息")
    @GetMapping("/getAuthUserInfo")
    public AuthUser getUserInfo(HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader(CoreConstant.JwtTokenConstant.JWT_TOKEN_HEADER);
        String accessToken = StringUtils.removeStart(header, CoreConstant.JwtTokenConstant.JWT_TOKEN_HEAD);
        return abstractSecurityAuthDetailService.getUserInfo(accessToken);
    }
}
