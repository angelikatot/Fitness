package syksy24.backend.fitness;

import syksy24.backend.fitness.web.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration

// spring security enable
@EnableWebSecurity
public class SecurityConfig {

        private final CustomUserDetailsService customUserDetailsService;

        // user passwords and usernames
        public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
                this.customUserDetailsService = customUserDetailsService;
        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(HttpSecurity http,
                        BCryptPasswordEncoder bCryptPasswordEncoder)
                        throws Exception {
                return http.getSharedObject(AuthenticationManagerBuilder.class)
                                .userDetailsService(customUserDetailsService)
                                .passwordEncoder(bCryptPasswordEncoder)
                                .and()
                                .build();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http

                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/css/**", "/js/**").permitAll()
                                                .requestMatchers("/login", "/signup", "/register", "/h2-console/**")
                                                .permitAll()

                                                .requestMatchers("/api/**").permitAll()

                                                .requestMatchers("/exercises/search", "/exercises/searchResults")
                                                .hasAnyRole("USER", "ADMIN")
                                                .requestMatchers("/exercises/add", "/exercises/{exerciseId}/add-review")
                                                .hasAnyRole("USER", "ADMIN")
                                                .requestMatchers("/exercises/edit/**", "/exercises/delete/**")
                                                .hasRole("ADMIN")
                                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                                .anyRequest().authenticated())

                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .defaultSuccessUrl("/exercises", true)
                                                .permitAll())
                                // logout
                                .logout(logout -> logout
                                                .logoutSuccessUrl("/login?logout")
                                                .permitAll())

                                .csrf(csrf -> csrf
                                                .ignoringRequestMatchers("/api/**", "/h2-console/**"))
                                // Allow H2 console access
                                .headers(headers -> headers.frameOptions().sameOrigin());

                return http.build();
        }
}
