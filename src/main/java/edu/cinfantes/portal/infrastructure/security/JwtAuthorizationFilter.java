package edu.cinfantes.portal.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.util.Objects.isNull;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
  
  public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }
  
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    String authorization = request.getHeader("Authorization");
    
    if (isNull(authorization) || !authorization.startsWith("Bearer ")) {
      chain.doFilter(request, response);
      return;
    }
  
    String jwtToken = authorization.replace("Bearer ", "");
    Jws<Claims> parsedJwt = Jwts.parserBuilder()
      .setSigningKey(hmacShaKeyFor("NcRfUjXn2r5u8x!A%D*G-KaPdSgVkYp3s6v9y$B?E(H+MbQeThWmZq4t7w!z%C*F".getBytes()))
      .build()
      .parseClaimsJws(jwtToken);
  
    PortalUser user = new PortalUser(parsedJwt.getBody().getSubject(), null, List.of());
    user.setClaim(parsedJwt.getBody().get("claim", String.class));
  
    SecurityContextHolder.getContext().setAuthentication(user);
    chain.doFilter(request, response);
  }
}
