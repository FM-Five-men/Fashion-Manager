package fashionmanager.kim.develop.security;

import fashionmanager.kim.develop.dto.MemberDTO;
import fashionmanager.kim.develop.dto.MemberRightDTO;
import fashionmanager.kim.develop.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {


    @Value("${token.secret}")
    private String secret;

    @Value("${token.expiration_time}")
    private long expirationTime;

    private Key getSigningKey() {
        // Base64 인코딩된 secret을 바이트 배열로 변환
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(MemberRightDTO memberRight){
        //MemberRightDTO memberRight = memberService.selectMemberRightById(memberId);
        Claims claims = Jwts.claims().setSubject(memberRight.getMemberId());
        claims.put("role",memberRight.getMemberStateName());
        claims.put("name",memberRight.getMemberName());
        claims.put("email",memberRight.getMemberEmail());
        claims.put("id",memberRight.getMemberId());
        Date now = new Date();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        log.info("expirationTime: {}", expirationTime);
        log.info("now: {}", now);
        return token;
    }

//    public Authentication getAuthentication(String token){
//        MemberRightDTO memberRight = memberService.selectMemberRightById(this.getMemberIdFromToken(token));
//
//        return new UsernamePasswordAuthenticationToken(memberRight, null,memberRight.getAuthorities());
//    }

    public String getMemberIdFromToken(String token){
        String memberId = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token).getBody().getSubject();

        return memberId;
    }

    public String getMemberStateFromToken(String token){
        String memberState = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token).getBody().get("role",String.class);
        return memberState;
    }

    public String resolveToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");

        if(StringUtils.hasText(token) && token.startsWith("Bearer ")){
            return token.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token){
        try{
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        }catch(Exception e){
            log.error("에러발생: {}",e.getMessage());
            return false;
        }
    }
}
