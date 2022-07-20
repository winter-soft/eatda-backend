package com.proceed.swhackathon.config.security.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo,OAuth2UserInfo1{
    private Map<String, Object> attributes;

    public KakaoUserInfo(Map<String, Object> attributes){
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String)attributes.get("profile");
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        return (String)attributes.get("email");
    }

    @Override
    public String getName() {
        return (String)attributes.get("nickname");
    }
}
