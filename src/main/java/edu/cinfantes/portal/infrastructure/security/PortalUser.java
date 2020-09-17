package edu.cinfantes.portal.infrastructure.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PortalUser extends UsernamePasswordAuthenticationToken {
  @Getter @Setter
  private String claim;
  
  public PortalUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
  }
}
