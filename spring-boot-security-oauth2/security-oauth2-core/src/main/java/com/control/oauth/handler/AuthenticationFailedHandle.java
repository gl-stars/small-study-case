package com.control.oauth.handler;

import com.centre.common.model.Result;
import com.centre.common.utils.StringUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * 认证失败处理
 * @author: stars
 * @data: 2020年 10月 06日 18:42
 **/
@Component
public class AuthenticationFailedHandle  extends DefaultWebResponseExceptionTranslator {

    public static final String BAD_MSG = "坏的凭证";

    /***
     * 登录错误处理
     * @return
     */
    @Override
    public ResponseEntity translate(Exception e) throws Exception {
        OAuth2Exception oAuth2Exception;
        if (e.getMessage() != null && e.getMessage().equals(BAD_MSG)) {
            oAuth2Exception = new InvalidGrantException("用户名或密码错误", e);
        } else if (e instanceof InternalAuthenticationServiceException) {
            oAuth2Exception = new InvalidGrantException(e.getMessage(), e);
        } else if (e instanceof RedirectMismatchException) {
            oAuth2Exception = new InvalidGrantException(e.getMessage(), e);
        } else if (e instanceof InvalidScopeException) {
            oAuth2Exception = new InvalidGrantException(e.getMessage(), e);
        } else if (e instanceof OAuth2Exception){
            oAuth2Exception = new OAuth2Exception(e.getMessage());
        }else {
            oAuth2Exception = new UnsupportedResponseTypeException("服务内部错误", e);
        }
        // 判断报错的信息是否中文，避免有些很长的英文文本输出，中文都是自己抛出的异常，直接给提示。
        boolean containChinese = StringUtil.isContainChinese(e.getMessage());
        if (!containChinese){
            Result<Object> failed = Result.failed(oAuth2Exception.getHttpErrorCode(), "服务内部错误");
            return  ResponseEntity.ok(failed);
        }
        Result result = Result.failed(oAuth2Exception.getHttpErrorCode(),oAuth2Exception.getMessage());
        return ResponseEntity.ok(result);
    }
}
