package com.aishpam.esdminiproject.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import com.aishpam.esdminiproject.repository.EmployeeRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JWTUtils {

    private final SecretKey key;
    private static final long EXPIRATION_TIME = 86400000; //24hrs
        public JWTUtils(EmployeeRepo employeeRepo) {
            String secretString = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";
            byte[] keyBytes = Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
            this.key = new SecretKeySpec(keyBytes, "HmacSHA256");
        }
        public String generateToken(UserDetails userDetails) {
            return Jwts.builder()
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis()+ EXPIRATION_TIME))
                    .signWith(key)
                    .compact();
        }
        public String generateRefreshToken(HashMap<String,Object> claims, UserDetails userDetails){
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(key)
                    .compact();
        }
        public String extractUsername(String jwtToken){
            return  extractClaims(jwtToken, Claims::getSubject);
        }

        private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
            final Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claimsTFunction.apply(claims);
        }

        public  boolean isTokenValid(String token, UserDetails userDetails){
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        }

        public  boolean isTokenExpired(String token){
            return extractClaims(token, Claims::getExpiration).before(new Date());
        }
}
