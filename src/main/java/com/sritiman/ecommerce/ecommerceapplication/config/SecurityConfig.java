package com.sritiman.ecommerce.ecommerceapplication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity.authorizeExchange(authorizeExchanges -> authorizeExchanges.anyExchange().authenticated())
                .oauth2ResourceServer(oAuth2 -> oAuth2.jwt(Customizer.withDefaults()));

        return serverHttpSecurity.build();
    }
}
