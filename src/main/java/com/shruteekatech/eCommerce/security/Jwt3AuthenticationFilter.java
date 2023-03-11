package com.shruteekatech.eCommerce.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class Jwt3AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private Jwt2Helper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException
    {
        // check header is coming or not from Authorization
        String requestHeader = request.getHeader("Authorization");
        logger.info("Header : {} ",requestHeader);

        String username = null;
        String token = null;

        if(requestHeader!=null && requestHeader.startsWith("Bearer"))
        {
                  token = requestHeader.substring(7);
                  try
                  {
                      // get username by token from jwtHelper
                      username = this.jwtHelper.getUserNameFromToken(token);

                  }catch (IllegalArgumentException e){
                      logger.info("Illegal Argument while fetching the username !!");
                      e.printStackTrace();
                  }catch (ExpiredJwtException e){
                      logger.info("Given jwt token is expired !!");
                      e.printStackTrace();
                  }catch (MalformedJwtException e){
                      logger.info("Some changes done in token !!, Invalid Token..!!");
                      e.printStackTrace();
                  }catch (Exception e){
                      e.printStackTrace();
                  }
        }
        else
        {
                  logger.info("Invalid Header Value !!");
        }

        // when we get user then check validation & set authentication
        if (username != null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            // fetch user details from username
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            // check user validation by token & userDetails
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
            if(validateToken)
            {
                // if user validate then set authentication
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else
            {
                logger.info("Validation failed !!");
            }
        }

        filterChain.doFilter(request,response);

    }
}
