package pl.remplewicz.crowding.config.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.remplewicz.crowding.model.User;

import java.util.Arrays;
import java.util.Date;

import static java.lang.String.format;

/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenUtil {

    private static final String ROLES_CLAIM = "roles";
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.issuer}")
    private String jwtIssuer;
    @Value("${jwt.expirationTime}")
    private Long jwtExpirationTime;

    public String generateAccessToken(User user) {
        StringBuilder rolesBuilder = new StringBuilder();
        String roles = "";
        user.getAuthorities().forEach(role -> {
            if(role.isEnabled()) {
                rolesBuilder.append(role.getAuthority());
                rolesBuilder.append(',');
            }
        });
        if(rolesBuilder.length()>0){
            roles = rolesBuilder.substring(0,rolesBuilder.length()-1);
        }
        return Jwts.builder()
                .setSubject(format("%s", user.getUsername()))
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .claim(ROLES_CLAIM, roles)
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTime)) // 1 week
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }
}
