package pro.bashkatov.pokerapi.model.security.service;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pro.bashkatov.pokerapi.entity.User;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenProvider extends AbstractUserDetailsAuthenticationProvider {
    private final static String key = "abc123abc123abc123abc123abc123abc123abc123abc123abc123abc123abc123abc123abc123abc123abc123abc123";
    private final UserService userDetailService;

    public TokenProvider(
            UserService userDetailService
    ) {
        this.userDetailService = userDetailService;
    }

    public String getJwtToken(User user) {
        Map<String,Object> tokenData = new HashMap<>();
        tokenData.put("clientType", "user");
        tokenData.put("userID", user.getId());
        tokenData.put("username", user.getUsername());
        tokenData.put("token_create_date", new Date().getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1440);
        tokenData.put("token_expiration_date", calendar.getTime());
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setExpiration(calendar.getTime());
        jwtBuilder.setClaims(tokenData);

        return jwtBuilder.signWith(SignatureAlgorithm.HS512, key).compact();
    }

    public JwtTokenAuthentication getAuthenticationToken(String jwtToken) {
        Jws<Claims> claims = getClaims(jwtToken);
        String username = (String) claims.getBody().get("username");
        UserDetails userDetails = userDetailService.loadUserByUsername(username);

        return new JwtTokenAuthentication(userDetails, userDetails.getAuthorities());
    }

    private Jws<Claims> getClaims(String token) {
        return Jwts
            .parserBuilder()
//            .setSigningKey(keyPair.public)
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
        ;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtTokenAuthentication.class.isAssignableFrom(authentication));
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        final Object token = authentication.getCredentials();
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        if(userDetails == null) {
            throw new UsernameNotFoundException("Cannot find user with authentication token=" + token);
        }
        return userDetails;
    }
}
