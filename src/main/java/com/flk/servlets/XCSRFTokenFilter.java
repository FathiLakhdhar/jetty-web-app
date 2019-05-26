package com.flk.servlets;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * https://stormpath.com/blog/angular-xsrf
 *
 */

public class XCSRFTokenFilter implements Filter{

	private SecureRandom prng = null;
	
	
	public void init(FilterConfig filterConfig) throws ServletException {

		// Init secure random
		try {
			this.prng = SecureRandom.getInstance("SHA1PRNG");
		}
		catch (NoSuchAlgorithmException e) {
			throw new ServletException(e);
		}	
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = ((HttpServletRequest) request);
		HttpServletResponse httpResponse = ((HttpServletResponse) response);
		
		//GENERATE CSRF TOKEN
		String randomNum = new Integer(this.prng.nextInt()).toString();
		// --Get its digest
		MessageDigest sha;
		try {
			sha = MessageDigest.getInstance("SHA-256");
		}
		catch (NoSuchAlgorithmException e) {
			throw new ServletException(e);
		}
		byte[] digest = sha.digest(randomNum.getBytes());
		
		//TODO CSRF token (with a random, un-guessable string), and associate it with the user session.
		
		String randomCsrfToken = Base64.getMimeEncoder().encodeToString(digest);
		Cookie csrfCookie = new Cookie("XSRF-TOKEN", randomCsrfToken);
		//csrf_cookie.setSecure(true);//SSL & Https
		int minutes = 15;
		csrfCookie.setMaxAge(60 * 60);
		httpResponse.addCookie(csrfCookie);
		
		
		//READ X-CSRF-TOKEN
		Optional<String> xcsrf_token = readCookie(httpRequest, "XSRF-TOKEN");
		if (xcsrf_token.isPresent()){
			//System.out.println(xcsrf_token.get());
		}
		
		chain.doFilter(httpRequest, httpResponse);
		
	}
	
	
	public Optional<String> readCookie(HttpServletRequest request, String key) {
	    return Arrays.stream(request.getCookies())
	      .filter(c -> key.equals(c.getName()))
	      .map(Cookie::getValue)
	      .findAny();
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
