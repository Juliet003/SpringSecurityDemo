package com.example.springsecuritydemo1.security;

import com.example.springsecuritydemo1.enums.Roles;
import com.example.springsecuritydemo1.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import static org.yaml.snakeyaml.tokens.Token.ID.Key;
@Component
public class JwtService {
//    @Value("${jwt.secretToken}")
//    private  String secretToken;
      private final String secretToken = "sdfghjklsdfghjksdfghjdfghjertyucvbertyxcvertyxcvtyvertyertyuertyuxcvxcertertdfgxcvsdfgdf";
    private Key getSigningKey(){
        byte[] KeyBytes = Decoders.BASE64.decode(secretToken);
       return Keys.hmacShaKeyFor(KeyBytes);
    }

    public String generateToken(Authentication authentication, Roles roles){
        String email = authentication.getName();
        String fullName = ((MyUserDetails) authentication.getPrincipal()).getFullName();

        Date currentDate = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(System.currentTimeMillis() + 6000 * 120);

       return Jwts.builder()
                .setSubject(email)
                .claim("name", fullName)
                .claim("email", email)
                .claim("role", (roles.name()))
                .setIssuedAt(currentDate)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(),SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUserNameFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (Exception e){
            throw new CustomException("Invalid Token",HttpStatus.BAD_REQUEST);
        }
    }

    public Claims getClaimsFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
