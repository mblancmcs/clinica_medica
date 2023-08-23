package blancoder.biz.clinica_medica.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // para personalizar as configurações de segurança web
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    @Autowired // injetando o filtro criado para ser executado antes do filtro do Spring
    private SecurityFilter securityFilter;

    @Bean // expondo o retorno do metodo e devolvendo o objeto ao Spring para injetar em algum controller ou classe Service
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())// desabilitando, pois o proprio token ja protege contra esse ataque
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // modificando a autenticacao para STATELESS
                .authorizeHttpRequests(req -> { // metodo para configurar como vai ser a configuracao das requisicoes
                    req.requestMatchers(HttpMethod.POST, "/auth/login").permitAll(); // permitindo o acesso a requisicao de login e para o Spring Docs sem estar logado
                    req.requestMatchers(HttpMethod.POST, "/auth/register").hasRole("ADMIN");
                    req.requestMatchers(HttpMethod.GET, "/auth/listar").hasRole("ADMIN");
                    req.requestMatchers("/paciente").hasRole("ATENDENTE");
                    req.requestMatchers("/consulta").hasRole("ATENDENTE");
                    req.requestMatchers("/atendimento").hasRole("MEDICO");
                    req.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll();
                    req.anyRequest().authenticated(); // outras requisicoes deverao ser autorizadas
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // ordenando para o filtro criado ser executado primeiro que o do Spring
                .build();
    }

    @Bean // para usar injecao de dependencias ou que ele proprio usara internamente
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager(); // metodo dessa classe que cria o authentication manager
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // indicando ao Spring que usaremos o BCrypt para hash de senha
    }

}
