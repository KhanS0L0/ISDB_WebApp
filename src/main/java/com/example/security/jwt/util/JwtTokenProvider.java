package com.example.security.jwt.util;

import com.example.entity.users.Role;
import com.example.exceptions.JwtAuthenticationException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Value("${jwt.token.header}")
    private String requestHeader;

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expired}")
    private Long lifeTimeValidityInMilliseconds;

    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

/*
*   не знаю почему но если расположить бин здесь то появляется циклическая зависимость между JwtTokenProvider
*   и UserServiceImpl не могут найти passwordEncoder(). Из-за этого пришлось расположить бин в psvm классе
*
* */
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder(){
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        return bCryptPasswordEncoder;
//    }

    @PostConstruct
    protected void init(){
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String username, List<Role> roleList, long workerId, long userId){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", getRoleNames(roleList));
        claims.put("workerId", workerId);
        claims.put("userId", userId);

        Date now = new Date();
        Date validity = new Date(now.getTime() + lifeTimeValidityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public Long getUserId(String token){
        return (Long) Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("userId");
    }

    public Long getWorkerId(String token){
        return (Long) Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("workerId");
    }

    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(requestHeader);
        if(bearerToken != null && bearerToken.startsWith("Bearer_")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());

        }catch(JwtException | IllegalArgumentException e){
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }

    private List<String> getRoleNames(List<Role> userRoles){
        List<String> result = new ArrayList<>();
        userRoles.forEach(role -> {
            result.add(role.getName());
        });
        return result;
    }

}
