package pro.bashkatov.pokerapi.model.security.service;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pro.bashkatov.pokerapi.entity.User;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenProvider {
    private final static String key = "abc123abc123abc123abc123abc123abc123abc123abc123abc123abc123abc123abc123abc123abc123abc123abc123";
    private final UserDetailServiceImp userDetailService;

    public TokenProvider(
            UserDetailServiceImp userDetailService
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

        return "Bearer " + jwtBuilder.signWith(SignatureAlgorithm.HS512, key).compact();
    }

    public Authentication getAuthenticationToken(String jwtToken) {
        Jws<Claims> claims = getClaims(jwtToken);
        String username = (String) claims.getBody().get("username");
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(), userDetails.getAuthorities()
        );
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
}
