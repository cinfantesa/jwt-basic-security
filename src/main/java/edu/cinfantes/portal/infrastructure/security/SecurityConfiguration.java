package edu.cinfantes.portal.infrastructure.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .sessionManagement().sessionCreationPolicy(STATELESS)
    .and()
      .formLogin().disable()
      .authorizeRequests()
        .antMatchers("/login").permitAll()
        .anyRequest().authenticated()
    .and()
      .addFilter(new JwtAuthenticationFilter(authenticationManager()))
      .addFilter(new JwtAuthorizationFilter(authenticationManager()))
      .csrf().disable().cors();
  }
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication().withUser("cinfantes").password("{noop}pass").roles("USER");
  }
}
