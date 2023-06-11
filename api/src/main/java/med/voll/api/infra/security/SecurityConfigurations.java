package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement((session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))) // Le indicamos a spring el tipo de sesion, la politica de creacion es STATELESS
                .authorizeHttpRequests((request -> request.requestMatchers(HttpMethod.POST, "/login") // Cada request que haga match del tipo post y va para el login
                        .permitAll() // Concede todos los permisos
                        .anyRequest() // Los request que vayan despues
                        .authenticated() // Debe ser autenticados
                )).addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // Valida que el usuario que inicia la session inicia y esta autenticado. Agrega un filtro antes del filtro de autenticacion
                .build(); // Al final construye el objeto
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
