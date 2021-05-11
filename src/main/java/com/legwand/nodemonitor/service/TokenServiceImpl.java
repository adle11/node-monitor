package com.legwand.nodemonitor.service;

import com.legwand.nodemonitor.model.User;
import com.legwand.nodemonitor.repository.UsersRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public String getToken(String username, String password) throws IllegalArgumentException {
        User user = usersRepository.getUser(username);
        Validate.isTrue(user != null);
        if (BCrypt.checkpw(password, user.getPassword())) {
            Instant expirationTime = Instant.now().plus(12, ChronoUnit.HOURS);
            Date expirationDate = Date.from(expirationTime);

            return Jwts.builder()
                    .setSubject(user.getUsername())
                    .setExpiration(expirationDate)
                    .signWith(key)
                    .compact();
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public User parseToken(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        String username = claimsJws.getBody().getSubject();
        return usersRepository.getUser(username);
    }
}
