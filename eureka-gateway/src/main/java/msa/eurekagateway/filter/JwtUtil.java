package msa.eurekagateway.filter;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    String secret;

    public JwtUtil(Environment env) {
        this.secret = env.getProperty("token.secret");
    }


    private Key getSigningKey() {
        // Base64 인코딩된 secret을 바이트 배열로 변환
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token){
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);

            // claims가 null인지 체크
            if (claims == null) {
                log.error("claims is null. token might be malformed: {}", token);
                return false;
            }

            return !claims.getBody().getExpiration().before(new Date());

        } catch (ExpiredJwtException e) {
            log.error("토큰 만료: {}", e.getMessage());
            return false;
        } catch (JwtException e) {
            log.error("JWT 파싱 실패: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            log.error("알 수 없는 JWT 에러: {}", e.getMessage());
            return false;
        }
    }
}
