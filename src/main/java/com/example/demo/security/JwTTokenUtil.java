package com.example.demo.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwTTokenUtil {

	public static final String PREFIX = "Bearer";

//	Thời gian tồn tại của Token mặc định là 24 tiếng
	@Value("${jwt.duration}")
	public Integer duration;

//	Lấy giá trị key trong application , dùng để giải mã và mã hóa
	@Value("${jwt.secret}")
	private String secret;

	// Sinh token
	public String generationToken(UserDetails userDetails) {
		// Lưu thông tin của user vào claim
		Map<String, Object> claims = new HashMap<>();

//		1. Định nghĩa các claims : issuer,expiration,subject,id
//		2. Mã hóa token sử dụng thuật toán HS512 và key bí mật
//		3. Convert thành chuỗi URL an toàn
//		4. Cộng chuỗi đã sinh ra với tiền tố Bearer
		String token = Jwts.builder()
				.setClaims(claims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + duration * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		return PREFIX + token;
	}

	// Lấy thông tin ở trong token
	public Claims geClaimsFromToken(String token) {
		// Kiểm tra token bằng tiền tố
		if (token == null || !token.startsWith(PREFIX))
			return null;

		token = token.replace(PREFIX, "");
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
}
