package com.example.Login.security;

import com.example.Login.configs.RateLimiterConfig;
import com.example.Login.dto.responses.JWTClaims;
import com.example.Login.entities.Users;
import com.example.Login.services.JwtService;
import com.example.Login.services.impl.UsersServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {

    private final String AUTH_HEADER = "Authorization";
    private final JwtService jwtService;
    private final UsersServiceImpl userService;
    private final RateLimiterConfig rateLimiter;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    )
            throws ServletException, IOException
    {
        try{
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String clientId = httpRequest.getRemoteAddr();

            if (!rateLimiter.isAllowed(clientId)) {
                response.setStatus(429);
                return;
            }

            //get Token from header
            String token = request.getHeader(AUTH_HEADER);

            if(Objects.nonNull(token) && jwtService.verifyToken(token))
            {

                JWTClaims jwtClaims = jwtService.claimToken(token);

                Users userByID = userService.getById(jwtClaims.getIdUser());

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userByID.getUsername(),
                        null,
                        userByID.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }catch (Throwable throwable)
        {
            log.error("Cannot set user authentication: {}", throwable.getLocalizedMessage());
        }finally {
            filterChain.doFilter(request, response);
        }
    }
}
