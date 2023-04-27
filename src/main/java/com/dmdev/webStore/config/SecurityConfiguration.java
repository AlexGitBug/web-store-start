package com.dmdev.webStore.config;

import com.dmdev.webStore.entity.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(urlConfig -> urlConfig
                        .antMatchers("/login", "/users/registration/**", "/users/**").permitAll()
                        .antMatchers("/catalogs", "/orders/registration", "/orders/{\\d+}", "/orders/{\\d+}/update",
                                "/products", "/catalogs/{\\d+}/onecatalog", "/products/{\\d+}",
                                "/shoppingcarts/{\\d+}/add", "/shoppingcarts/{\\d+}").hasAnyAuthority(Role.USER.getAuthority(), Role.ADMIN.getAuthority())
                        .anyRequest().authenticated())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/catalogs"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
