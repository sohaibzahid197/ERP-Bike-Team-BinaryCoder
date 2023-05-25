package com.soen390.team11.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import static com.soen390.team11.security.SecurityConstant.HEADER_STRING;
import static com.soen390.team11.security.SecurityConstant.SECRET;
import static com.soen390.team11.security.SecurityConstant.TOKEN_PREFIX;

/**
 * This class represents an authorization filter used for validating and processing authorization tokens
 * in the Spring Security framework.
 * It extends the BasicAuthenticationFilter class and overrides the doFilterInternal method to intercept
 * requests and perform token-based authentication.
 *
 * The purpose of this class is to define the authorization behavior and determine what actions a user is allowed to perform.
 * It utilizes a token-based authentication mechanism, where the presence of a valid token in the request header
 * indicates that the user is authenticated.
 *
 * The filter extracts the token from the "Authorization" header, verifies its authenticity using the provided secret key,
 * and retrieves the user information from the token's claims. If the user is authenticated, an
 * instance of UsernamePasswordAuthenticationToken is created and set in the SecurityContextHolder.
 *
 * This class plays a crucial role in securing the application by ensuring that only authorized users can access
 * protected resources and perform permitted actions.
 */

/**
 * authorization help to define what the user are allow to do
 * with the help of token we can verify if the person is authenticated or not
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {

    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            token = token.replace(TOKEN_PREFIX,"");
            String user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}
