package com.hicorp.segment.security.services;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class GrantedAuthorityImpl implements GrantedAuthority {

    private String url;
    private String method;

    public GrantedAuthorityImpl(String url, String method) {
        this.url = url;
        this.method = method;
    }

    @Override
    public String getAuthority() {
        return this.url + ";" + this.method;
    }
}
