package org.example.sj_jws_indv_inlamning.configurations;

import org.example.sj_jws_indv_inlamning.converters.JwtAuthConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    @Autowired
    public SecurityConfig(JwtAuthConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/h2-console/**").permitAll()

                                .requestMatchers(HttpMethod.GET, "/api/v2/posts").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/v2/post/{id}").authenticated()

                                .requestMatchers(HttpMethod.POST, "/api/v2/newpost").hasRole("myclient_USER")
                                .requestMatchers(HttpMethod.PUT, "/api/v2/updatepost").hasRole("myclient_USER")

                                .requestMatchers(HttpMethod.DELETE, "/api/v2/deletepost/{id}").hasAnyRole("myclient_USER", "myclient_ADMIN")

                                .requestMatchers(HttpMethod.GET,"/api/v2/count").hasRole("myclient_ADMIN")

                                .anyRequest().authenticated()
                )

                .headers(h -> h.frameOptions(f -> f.disable()))
                .sessionManagement(sm -> sm
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
                )

                .exceptionHandling(eh -> eh
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(401);
                            response.setContentType("application/json");
                            response.getWriter().write("""
                                    {
                                    "Status": 401,
                                    "Error": "Unauthorized",
                                    "Message": "Missing Token",
                                    "Path": %s"
                                    }
                                    """.formatted(request.getRequestURI()));
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(403);
                            response.setContentType("application/json");
                            response.getWriter().write("""
                                    {
                                    "Status": 403,
                                    "Error": "Forbidden",
                                    "Message": "Missing permission",
                                    "Path": %s"
                                    }
                                    """.formatted(request.getRequestURI()));
                        })
                );
        return http.build();
    }
}
