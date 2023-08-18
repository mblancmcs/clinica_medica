package blancoder.biz.clinica_medica.infra.security;

import blancoder.biz.clinica_medica.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("clinica_medica.security.token.secret") // indicando que vem do application.properties
    private String secret;

    public String gerarToken(Usuario usuario) { // modificando do repositorio do auth0 do github
        try {
            var algoritmo = Algorithm.HMAC256(secret); // modificando para criptografar com a HMA256
            return JWT.create()
                    .withIssuer("API clinica medica") // autor / quem esta gerando o token
                    .withSubject(usuario.getLogin()) // passando / armazenando o usuario que esta fazendo o login
                    .withClaim("id", usuario.getId()) // opcional, mas enviando o id tambem
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar o token JWT", exception); // apenas lancando uma runtime inicialmente
        }

    }

    public String getSubject(String tokenJWT) {

        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API clinica medica")
                    .build()
                    .verify(tokenJWT) // verifica de acordo com o algoritmo e o Issuer configurado ao gerar o token
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token JWT inválido no cabeçalho Authorization");
        }

    }

    private Instant dataExpiracao() { // 2 horas de acordo com o fuso horario

        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"));
    }

}
