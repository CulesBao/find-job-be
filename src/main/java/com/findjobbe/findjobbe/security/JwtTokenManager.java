package com.findjobbe.findjobbe.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.findjobbe.findjobbe.model.Account;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenManager {
  private final JwtProperties jwtProperties;

  public String generateToken(Account account) {
    return JWT.create()
        .withSubject(account.getEmail())
        .withClaim("role", account.getRole().name())
        .withExpiresAt(
            new Date(System.currentTimeMillis() + jwtProperties.getExpirationMinutes() * 60 * 1000))
        .sign(Algorithm.HMAC256(jwtProperties.getSecretKey().getBytes()));
  }

  private DecodedJWT decode(String token) {
    return JWT.require(Algorithm.HMAC256(jwtProperties.getSecretKey().getBytes()))
        .build()
        .verify(token);
  }

  public String getEmailFromToken(String token) {
    final DecodedJWT jwt = decode(token);
    return jwt.getSubject();
  }

  public String getRoleFromToken(String token) {
    final DecodedJWT jwt = decode(token);
    return jwt.getClaim("role").asString();
  }

  private Date getExpirationDateFromToken(String token) {
    final DecodedJWT jwt = decode(token);
    return jwt.getExpiresAt();
  }

  public boolean validateToken(String token, String email) {
    final String emailFromToken = getEmailFromToken(token).toString();
    return emailFromToken.equals(emailFromToken) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }
}
