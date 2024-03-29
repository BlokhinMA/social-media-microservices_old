package ru.sstu.users.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/sign_up", "/sign_in", "/user").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        //.anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/sign_in")
                        .loginProcessingUrl("/sign_in")
                        .defaultSuccessUrl("/my_profile")
                )
                .logout((logout) -> logout
                        .logoutUrl("/sign_out")
                        .logoutSuccessUrl("/")
                )
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(8);
    }

}
