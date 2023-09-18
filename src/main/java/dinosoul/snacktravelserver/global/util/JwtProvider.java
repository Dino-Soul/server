package dinosoul.snacktravelserver.global.util;

import dinosoul.snacktravelserver.domain.member.entity.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.*;
import static java.nio.charset.StandardCharsets.*;

@Component
public class JwtProvider {

    private static final String TOKEN_TYPE = "Bearer ";
    private final Key secretKey;
    private final long TOKEN_EXPIRE_IN;
    private final String HEADER_NAME = "Authorization";
    private final String AUTHORIZATION_KEY = "auth";

    public JwtProvider(
            @Value("${jwt.secret.key}") final String secretKey,
            @Value("${jwt.expire.length}") final long tokenExpireIn
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(UTF_8));
        this.TOKEN_EXPIRE_IN = tokenExpireIn;
    }

    public String createAccessToken(Member member) {
        final Date now = new Date();
        final Date validateTime = new Date(now.getTime() + TOKEN_EXPIRE_IN);

        return TOKEN_TYPE + Jwts.builder()
                .setSubject(member.getId().toString())
                .setIssuedAt(now)
                .setExpiration(validateTime)
                .claim("loginId", member.getLoginId())
                .claim("nickname", member.getNickname())
                .claim("profileUrl", member.getProfileUrl())
                .signWith(secretKey, HS256)
                .compact();
    }

    public void addJwtToHeader(String token, HttpServletResponse response) {
        response.addHeader(HEADER_NAME, token);
    }

    public boolean validateToken(String token) {
        try {
            return getClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Claims getUserInfo(String token) {
        return getClaimsJws(token).getBody();
    }

    public Long getId(String token) {
        String idValue = getClaimsJws(token)
                .getBody()
                .getSubject();

        return Long.parseLong(idValue);
    }

    public String getLoginId(String token) {
        return getClaimsJws(token)
                .getBody()
                .get("loginId", String.class);
    }

    public String getNickname(String token) {
        return getClaimsJws(token)
                .getBody()
                .get("nickname", String.class);
    }

    public String getProfileUrl(String token) {
        return getClaimsJws(token)
                .getBody()
                .get("profileUrl", String.class);
    }

    private Jws<Claims> getClaimsJws(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
    }

    public String extractToken(String token) {
        if (isInvalidTokenType(token)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        return token.substring(TOKEN_TYPE.length());
    }

    private boolean isInvalidTokenType(String token) {
        return token == null || !token.startsWith(TOKEN_TYPE);
    }

    public String getToken(HttpServletRequest request) {
        return request.getHeader(HEADER_NAME);
    }
}
