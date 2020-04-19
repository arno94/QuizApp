package com.quizapp.quizapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(getAuthProvider());
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/index").hasAnyRole("ADMIN", "USER")
                .antMatchers("/login").hasAnyRole("ADMIN", "USER")
                .antMatchers("/quiz").hasAnyRole("ADMIN", "USER")
                .antMatchers("/generate_quiz").hasAnyRole("ADMIN", "USER")
                .antMatchers("/").hasAnyRole("ADMIN", "USER")
                .and().formLogin().
                loginPage("/login").
                defaultSuccessUrl("/index").
                failureUrl("/login?error").
                permitAll()
                .and().logout().permitAll();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider getAuthProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(getPasswordEncoder());
        return authProvider;
    }

}
