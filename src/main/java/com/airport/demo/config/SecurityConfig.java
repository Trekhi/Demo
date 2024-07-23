package com.airport.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //Spring Security, ya tiene metodos para establecer seguridda, este metodo
    //Se configura de acuerdo a que seguridad necesitas //.csrf().disable()  REVISAR
    //@Bean es una notación, para obtener el objeto de la clase, FALTA...

    /**
     * Esta metodo restrigue el acceso a otros templates, ES UN FILTRO DE SEGURIDAD a no ser que este Logeado
     * Iniciara desde la URL login.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin(formLogin ->
                        formLogin
                                .successHandler(successHandler()) // URL a donde se va a ir despues de iniciar sesión
                                .permitAll()
                )
                .sessionManagement(sessionManagement -> {
                    sessionManagement //Creación de Sesion si el usuario esta autenticado o no.
                            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) //ALWAYS - IF_REQUIRED -NEVER - STATELLES. Estados de sesión, guardado de información de usuario. Falta
                            .invalidSessionUrl("/login")//En caso de error, de auth, vuelve a login
                            .maximumSessions(1) //El número de sesiones, por usuario
                            .sessionRegistry(sessionRegistry())
                            .expiredUrl("/login"); //En caso de expirar la sesión vuelve al Login
                    sessionManagement.sessionFixation().migrateSession(); //crea una nueva sesión con un nuevo ID de sesión, conservando los atributos de la sesión original. Esto ayuda a prevenir ataques de fijación de sesión. TIPOS migrateSession - newSession -none
                }).build();

    }

    /**
     * Este metodo realiza un rastreo de datos del usuario, para almacenar la información
     */
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    /**
     * Este metodo realiara el redirecionamiento luego de estar autenticado
     */
    public AuthenticationSuccessHandler successHandler() {
        return (((request, response, authentication) -> {
            response.sendRedirect("/prueba");
        }));
    }
}
