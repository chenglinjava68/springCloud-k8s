package com.weilus.config;

import com.feign.clients.OauthClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * Created by liutq on 2018/12/21.
 */
@Component
public class RemoteTokenServices implements ResourceServerTokenServices {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();

    @Autowired
    private OauthClient oauthClient;

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        Map map = oauthClient.checkToken(accessToken);
        if(map.containsKey("error")) {
            this.logger.debug("check_token returned error: " + map.get("error"));
            throw new InvalidTokenException(accessToken);
        } else {
            Assert.state(map.containsKey("client_id"), "Client id must be present in response from auth server");
            return this.tokenConverter.extractAuthentication(map);
        }
    }

    @Override
    public OAuth2AccessToken readAccessToken(String s) {
        throw new UnsupportedOperationException("Not supported: read access token");
    }
}
