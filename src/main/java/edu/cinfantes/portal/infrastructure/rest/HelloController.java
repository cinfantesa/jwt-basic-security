package edu.cinfantes.portal.infrastructure.rest;

import edu.cinfantes.portal.infrastructure.security.PortalUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
  
  @GetMapping(path = "/hello")
  public ResponseEntity<String> hello() {
    PortalUser user = (PortalUser) SecurityContextHolder.getContext().getAuthentication();
    System.out.println(user.getName() + " ---- " + user.getClaim());
  
    return ResponseEntity.ok("Hello World");
  }
}
