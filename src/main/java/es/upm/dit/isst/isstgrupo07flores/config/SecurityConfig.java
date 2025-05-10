package es.upm.dit.isst.isstgrupo07flores.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import es.upm.dit.isst.isstgrupo07flores.service.UsuarioService;

@Configuration
public class SecurityConfig {

    @Autowired
    private UsuarioService usuarioService; // Inyectar el servicio de usuario

    @Bean
    public UserDetailsService userDetailsService() {
        return usuarioService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(usuarioService); // Inyectar el UserDetailsService
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll() // Permite acceder a la base de datos H2 y Swagger
                .requestMatchers("/catalog/**", "/producto/**").permitAll() // Permite acceso al catalogo y a las imagenes de los productos
                .requestMatchers("/","/login", "/registro/**", "/css/**", "/js/**", "/registro").permitAll() // Permitir login y registro
                .anyRequest().authenticated() // Todo lo demás requiere autenticación
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler((request, response, authentication) -> {
                    // Obtener la URL previa al login
                    String redirectUrl = request.getSession(false) != null && 
                                         request.getSession(false).getAttribute("SPRING_SECURITY_SAVED_REQUEST") != null
                                         ? ((org.springframework.security.web.savedrequest.SavedRequest) request.getSession(false)
                                            .getAttribute("SPRING_SECURITY_SAVED_REQUEST")).getRedirectUrl()
                                         : "/";
                    response.sendRedirect(redirectUrl); // Redirigir a la URL previa o a la raíz
                })
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para la consola H2
            .headers(headers -> headers.frameOptions(frame -> frame.disable())); // Permitir iframes para H2

        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}