package boco.service.security;

import boco.model.profile.Profile;
import boco.repository.profile.ProfileRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * The java web token utility
 */
@Service
public class JwtUtil {
    private final String SECRET_KEY = "secret";
    private final ProfileRepository profileRepository;

    @Autowired
    public JwtUtil(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    private String createToken(Map<String, Object> claims, String subject){
        int VALID_TIME_MS = 1000 * 60 * 60 * 10;
        Date now = new Date();
        Date exp = new Date(System.currentTimeMillis() + VALID_TIME_MS);

        return Jwts.builder().setClaims(claims).setSubject(subject).
                setIssuedAt(now).setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    /**
     * Validates a java web token
     *
     * @param token       JWT, java web token
     * @param userDetails user detail extracted from profileDetailsServive
     * @return boolean token valid or not
     */
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Generate token string.
     *
     * @param userDetails the user details
     * @return the string
     */
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * Extract claims
     *
     * @param <T>            the type parameter
     * @param token          the token
     * @param claimsResolver the claims resolver
     * @return the type parameter
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extract expiration date.
     *
     * @param token the token
     * @return the date
     */
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extract username.
     *
     * @param token the token
     * @return the string
     */
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts profile object from an HTTP Authorization header
     *
     * @param authHeader Authorization header value. This field should include "Bearer "
     * @return Profile of Authorization header.
     * Null is returned if there is no profile belonging to authHeader
     */
    public Profile extractProfileFromAuthHeader(String authHeader) {
        String token = authHeader.substring(7);
        String username = extractClaim(token, Claims::getSubject);
        Optional<Profile> profile = profileRepository.findProfileByUsername(username);
        if (profile.isEmpty()) return null;
        return profile.get();
    }

}
