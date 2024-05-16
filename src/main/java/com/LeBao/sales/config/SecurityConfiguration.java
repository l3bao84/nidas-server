package com.LeBao.sales.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/authenticate","/img/**").permitAll()
                        .requestMatchers("/product/topPicks", "/product/recommendations", "/product/{id}").permitAll()
                        .requestMatchers("/category", "/category/{id}","/category/quickLinks").permitAll()
                        .requestMatchers("/search").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/reviews/{productId}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/reviews").authenticated()
                        .requestMatchers("/my-account/get-personal-info").authenticated()
                        .requestMatchers(HttpMethod.GET,"/my-account/addresses").authenticated()
                        .requestMatchers(HttpMethod.GET,"/my-account/my-orders").authenticated()
                        .requestMatchers(HttpMethod.PATCH,"/my-account/my-orders/{id}/cancel").authenticated()
                        .requestMatchers(HttpMethod.POST,"/my-account/addresses").authenticated()
                        .requestMatchers(HttpMethod.PUT,"/my-account/addresses/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/my-account/addresses/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/carts").authenticated()
                        .requestMatchers(HttpMethod.GET, "/carts").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/carts/{cartItemId}").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/carts/{cartItemId}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/order").authenticated()
                        .requestMatchers(HttpMethod.POST, "/order/execute-payment").authenticated()
                        //admin
                        .requestMatchers(HttpMethod.GET, "/api/v1/admin/orders/{orderStatus}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/admin/orders").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/admin/orders/update").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/admin/totalItemsSold").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/admin/totalEarning").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/admin/orderStatistic").authenticated()
                        .requestMatchers(HttpMethod.GET, "/product/admin").authenticated()
                        .requestMatchers(HttpMethod.POST, "/product/admin/add").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/product/admin/remove/{id}").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/product/admin/update/{id}").authenticated()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);

        List<String> allowedOrigins = Arrays.asList(
                "http://localhost:3000",
                "http://localhost:4000"
        );
        configuration.setAllowedOrigins(allowedOrigins);

        configuration.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT
        ));
        configuration.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.PATCH.name()
        ));
        configuration.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", configuration);

        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(-102);
        return bean;
    }


}
