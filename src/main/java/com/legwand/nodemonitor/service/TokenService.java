package com.legwand.nodemonitor.service;

import com.legwand.nodemonitor.model.User;

public interface TokenService {

    String getToken(String username, String password);
    User parseToken(String token);
}
