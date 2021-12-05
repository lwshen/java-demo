import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;

public class JwtDemo {

    private final long time = 1000 * 60 * 60 * 24;
    private final String secret = "admin-secret";

    public String jwt() {
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                //header
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //payload
                .claim("username", "ryo")
                .claim("role", "admin")
                .setSubject("admin-test")
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .setId(UUID.randomUUID().toString())
                //signature
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        System.out.println(jwtToken);
        return jwtToken;
    }

    public void parse(String token) {
        JwtParser jwtParser = Jwts.parser();
        Jwt<JwsHeader, Claims> claimsJwt = jwtParser.setSigningKey(secret).parseClaimsJws(token);
        Header header = claimsJwt.getHeader();
        System.out.println(header.getType());
        System.out.println(header.getCompressionAlgorithm());
        System.out.println(header.getContentType());
        Claims claims = claimsJwt.getBody();
        System.out.println(claims.get("username"));
        System.out.println(claims.get("role"));
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println(claims.getExpiration());
    }

    public static void main(String[] args) {
        JwtDemo demo = new JwtDemo();
        String token = demo.jwt();
        demo.parse(token);
    }
}
