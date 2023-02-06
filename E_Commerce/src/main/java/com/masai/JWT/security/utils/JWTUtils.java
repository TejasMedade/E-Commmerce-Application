/**
 * 
 */
package com.masai.JWT.security.utils;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * @author tejas
 *
 */
@Component
public class JWTUtils {

	// Second Point of JWT Filtration

	@Value("${jwtSecret}")
	private String secret;

	@Value("${jwtTokenValidity}")
	private int tokenValidity;

	@Value("${jwtCookieName}")
	private String jwtCookie;

	private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);

	public String getJwtFromCookies(HttpServletRequest request) {

		Cookie cookie = WebUtils.getCookie(request, jwtCookie);

		if (cookie != null) {
			return cookie.getValue();
		} else {
			return null;
		}

	}

	public ResponseCookie generateJwtCookie(UserDetails userDetails) {

		String jwt = generateTokenFromUsername(userDetails.getUsername());

		return ResponseCookie.from(jwtCookie, jwt).path("/bestbuy").maxAge(1200).httpOnly(true)
				.build();
	}

	public ResponseCookie getCleanJwtCookie() {

		ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/bestbuy").build();

		return cookie;
	}

	public String getUserNameFromJwtToken(String token) {

		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();

	}

	public boolean validateJwtToken(String authToken) {

		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}

	public String generateTokenFromUsername(String username) {

		Calendar calender = Calendar.getInstance();

		long timeInSecs = calender.getTimeInMillis();

		Date date = new Date(timeInSecs + (20 * 60 * 1000));

		return Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(date)
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
}
