package com.example.demo.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwTTokenUtil jwtTokenUtil;

	@Autowired
	private JwTUserDetailService userDetailsService;

	private static String HEADER = "Authorization";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
//		Lấy token từ header
		String token = request.getHeader(HEADER);

//		Parse thông tin từ token
		Claims claims = jwtTokenUtil.geClaimsFromToken(token);
		if (claims == null) {
			filterChain.doFilter(request, response);
			return;
		}

//		kiểm tra hạn của token
		Date expiration = claims.getExpiration();
		if (expiration.before(new Date())) {
			filterChain.doFilter(request, response);
			return;
		}

//		Tạo object Authentication
		UsernamePasswordAuthenticationToken authObject = getAuthentication(claims);
		if (authObject == null) {
			filterChain.doFilter(request, response);
			return;
		}
		// Xác thực thành công, lưu object Authentication vào SecurityContextHolder
		SecurityContextHolder.getContext().setAuthentication(authObject);
		filterChain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(Claims claims) {
		String username = claims.getSubject();
		if (username != null) {
			UserDetails user = userDetailsService.loadUserByUsername(username);
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		return null;
	}
}
