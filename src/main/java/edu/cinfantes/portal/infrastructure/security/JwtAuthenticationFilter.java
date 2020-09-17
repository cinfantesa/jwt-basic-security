package edu.cinfantes.portal.infrastructure.security;

import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;

@AllArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final AuthenticationManager authenticationManager;
  
  @SneakyThrows
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(obtainUsername(request), obtainPassword(request));
    return authenticationManager.authenticate(token);
  }
  
  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
    String jwt = Jwts.builder()
      .setId("id")
      .setSubject(authResult.getName())
      .claim("claim", "485793")
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + 3000000))
      .signWith(hmacShaKeyFor("NcRfUjXn2r5u8x!A%D*G-KaPdSgVkYp3s6v9y$B?E(H+MbQeThWmZq4t7w!z%C*F".getBytes()), HS512)
      .compact();
    
    System.out.println(jwt);
    response.addHeader("Authorization", "Bearer " + jwt);
  }
}
