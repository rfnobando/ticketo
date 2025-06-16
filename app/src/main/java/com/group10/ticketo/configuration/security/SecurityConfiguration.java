package com.group10.ticketo.configuration.security;

import com.group10.ticketo.helpers.ViewRouteHelper;
import com.group10.ticketo.services.implementation.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private final UserService userService;

    public SecurityConfiguration(UserService userService) {
        this.userService = userService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            if (request.getRequestURI().startsWith("/api/")) {
                                response.setContentType("application/json");
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                response.getWriter().write("{\"error\": \"Unauthorized\"}");
                            } else {
                                response.sendRedirect("/auth/login");
                            }
                        })
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                            "/css/**",
                            "/img/**",
                            "/js/**",
                            "/libs/**",
                            "/vendor/**",
                            "/webjars/**"
                    ).permitAll();
                    auth.requestMatchers(
                            "/auth/login",
                            "/auth/api-login",
                            "/auth/loginProcess",
                            "/auth/loginSuccess",
                            "/auth/logout",
                            "/auth/register",
                            "/api/v1/**"
                    ).permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin(login -> {
                    login.loginPage("/auth/login");
                    login.loginProcessingUrl("/auth/loginProcess");//POST
                    login.usernameParameter("username");
                    login.passwordParameter("password");
                    login.defaultSuccessUrl("/auth/loginSuccess", true);
                    login.permitAll();
                })
                .logout(logout -> {
                    logout.logoutUrl("/auth/logout");//POST
                    logout.logoutSuccessUrl("/auth/login?logout=true");
                    logout.permitAll();
                })
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userService);
        return provider;
    }

}//fin clase
