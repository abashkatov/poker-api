package pro.bashkatov.pokerapi.config.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;
import pro.bashkatov.pokerapi.model.security.service.JwtAuthenticationException;
import pro.bashkatov.pokerapi.model.security.service.JwtTokenAuthentication;
import pro.bashkatov.pokerapi.model.security.service.TokenAuthenticationManager;
import pro.bashkatov.pokerapi.model.security.service.TokenProvider;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final TokenProvider tokenProvider;

    public TokenAuthenticationFilter(TokenProvider tokenProvider, TokenAuthenticationManager authenticationManager) {
        super("/**");
        this.tokenProvider = tokenProvider;
        setAuthenticationSuccessHandler((request, response, authentication) -> {});
        setAuthenticationFailureHandler((request, response, authenticationException) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        });
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        String authToken = header.substring(7);
        JwtTokenAuthentication jwtToken;
        try {
            jwtToken = tokenProvider.getAuthenticationToken(authToken);
        } catch (Throwable e) {
            throw new JwtAuthenticationException("JWT token in wrong");
        }
        Authentication authentication = getAuthenticationManager().authenticate(jwtToken);
        authentication.setAuthenticated(true);

        return authentication;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        super.doFilter(req, res, chain);
    }
}