package com.findjobbe.findjobbe.security;

import com.findjobbe.findjobbe.service.impl.UserDetailsServiceImpl;
import com.findjobbe.findjobbe.utils.SecurityConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtTokenManager jwtTokenManager;
  private final UserDetailsServiceImpl userDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String authHeader = request.getHeader(SecurityConstants.HEADER_STRING);
    String email = null, authToken = null;

    if (Objects.nonNull(authHeader) && authHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
      authToken = authHeader.replace(SecurityConstants.TOKEN_PREFIX, "");
      try {
        email = jwtTokenManager.getEmailFromToken(authToken).toString();
      } catch (Exception e) {
        log.error("An error occurred while parsing token", e);
        filterChain.doFilter(request, response);
        return;
      }
    }

    final SecurityContext securityContext = SecurityContextHolder.getContext();

    final boolean canBeStartTokenValidation =
        Objects.nonNull(email) && Objects.isNull(securityContext.getAuthentication());

    if (!canBeStartTokenValidation) {
      filterChain.doFilter(request, response);
      return;
    }
    final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
    Boolean validToken = jwtTokenManager.validateToken(authToken, email);
    if (!validToken) {
      filterChain.doFilter(request, response);
      return;
    }
    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    securityContext.setAuthentication(authentication);
    filterChain.doFilter(request, response);
  }
}
