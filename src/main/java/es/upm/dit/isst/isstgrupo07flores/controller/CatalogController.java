package es.upm.dit.isst.isstgrupo07flores.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Permite acceso a todas las páginas
            )
            .csrf(csrf -> csrf.disable()) // Desactiva CSRF para H2
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin())); // Permite iframes

        return http.build();
    }
}
