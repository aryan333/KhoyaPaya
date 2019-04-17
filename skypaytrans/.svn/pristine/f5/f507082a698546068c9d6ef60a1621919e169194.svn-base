package com.saifintex.jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.saifintex.domain.TokenTypes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -3301605591108950415L;

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_AUDIENCE = "audience";
    static final String CLAIM_KEY_CREATED = "created";
    static final String CLAIM_KEY_EXPIRED = "exp";

    static final String AUDIENCE_UNKNOWN = "unknown";
    static final String AUDIENCE_WEB = "web";
    static final String AUDIENCE_MOBILE = "mobile";
    static final String AUDIENCE_TABLET = "tablet";


    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;
    
    @Value("${jwt.expiration.refresh}")
    private Long refreshExipration;
    

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = getClaimsFromToken(token);
            audience = (String) claims.get(CLAIM_KEY_AUDIENCE);
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Boolean ignoreTokenExpiration(String token) {
        String audience = getAudienceFromToken(token);
        return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());

        final Date createdDate = new Date();
        claims.put(CLAIM_KEY_CREATED, createdDate);

        return doGenerateToken(claims,TokenTypes.AUTH_TOKEN.name());
    }
    
    public String generateRereshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());

        final Date createdDate = new Date();
        claims.put(CLAIM_KEY_CREATED, createdDate);

        claims.put("tokenType", TokenTypes.REFRESH_TOKEN);
        
        return doGenerateToken(claims,TokenTypes.REFRESH_TOKEN.name());
    }

    private String doGenerateToken(Map<String, Object> claims,String tokenType) {
        final Date createdDate = (Date) claims.get(CLAIM_KEY_CREATED);
        Long relevantExpiration=0L;        
        if(TokenTypes.REFRESH_TOKEN.name().equals(tokenType)) {
        	relevantExpiration=refreshExipration;
        	Date date = new Date(createdDate.getTime() + relevantExpiration * 1000);        
        }
        else {
        	relevantExpiration=expiration;
        	Date date = new Date(createdDate.getTime() + relevantExpiration * 1000);        
        }
        final Date expirationDate = new Date(createdDate.getTime() + relevantExpiration * 1000);        

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        
        final Claims claims = getClaimsFromToken(token);
        
        if(claims.get("tokenType")!=null) {
        		return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
        				&& (!isTokenExpired(token) || ignoreTokenExpiration(token));
        }

        return false;
        
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = doGenerateToken(claims,TokenTypes.AUTH_TOKEN.name());
        } catch (Exception e) {
            refreshedToken = null;
        }
        
        final Claims claims=getClaimsFromToken(refreshedToken);
        System.out.println("Refreshed Auth token type =  "+claims.get("tokenType"));
        return refreshedToken;
    }

	public Boolean validateToken(String token, UserDetails userDetails, boolean isRefreshReq) {
		JwtUser user = (JwtUser) userDetails;
		if (!isRefreshReq) {
			final Claims claims = getClaimsFromToken(token);
			if (claims.get("tokenType") != null) {
				return false;
			}
		}
		final String username = getUsernameFromToken(token);
		final Date created = getCreatedDateFromToken(token);
		return (username.equals(user.getUsername()) && !isTokenExpired(token)
				&& !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()));
	}
}