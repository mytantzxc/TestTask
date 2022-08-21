package com.vaneezy.TestTask.Config.Security;

import com.vaneezy.TestTask.Entities.ApplicationUser.Role;
import com.vaneezy.TestTask.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final SessionRegistry sessionRegistry;


    @Autowired
    public SecurityConfig(UserService userService,
                          PasswordEncoder passwordEncoder,
                          SessionRegistry sessionRegistry) throws Exception {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.sessionRegistry = sessionRegistry;
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/registration").permitAll()
                .antMatchers("/api/v1/**").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().permitAll()
                    .passwordParameter("password")
                    .usernameParameter("username")
                    .defaultSuccessUrl("/success")
                .and()
                .logout()
                    .logoutUrl("logout")
                    .addLogoutHandler(logoutHandler())
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .sessionRegistry(sessionRegistry)
                .maxSessionsPreventsLogin(true);

        http.authenticationProvider(daoAuthenticationProvider());
        return http.build();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }

    @Bean
    public LogoutHandler logoutHandler(){
        return new CustomLogoutHandler(sessionRegistry);
    }
}
