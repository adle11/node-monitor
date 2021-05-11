package com.legwand.nodemonitor.rest;

import com.legwand.nodemonitor.dto.TokenRequest;
import com.legwand.nodemonitor.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class TokenRestController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/token")
    public String getToken(@RequestBody TokenRequest tokenRequest) {
        try {
            return tokenService.getToken(tokenRequest.getUsername(), tokenRequest.getPassword());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }
    }

}
