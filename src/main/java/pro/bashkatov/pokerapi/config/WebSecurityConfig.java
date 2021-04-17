package pro.bashkatov.pokerapi.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pro.bashkatov.pokerapi.config.filter.TokenAuthenticationFilter;
import pro.bashkatov.pokerapi.model.security.service.TokenProvider;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    private final UserDetailsService userDetailServiceImp;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final TokenProvider provider;

    public WebSecurityConfig(
            @Qualifier("userDetailService") UserDetailsService userDetailServiceImp,
            TokenAuthenticationFilter tokenAuthenticationFilter,
            TokenProvider provider
    ) {
        super();
        this.userDetailServiceImp = userDetailServiceImp;
        this.tokenAuthenticationFilter = tokenAuthenticationFilter;
        this.provider = provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .headers().frameOptions().sameOrigin().and()
//                .addFilterBefore(CorsFilter(), SessionManagementFilter.class)
//                .cors().disable()
                .cors().and()
//                .cors().configurationSource(corsConfigurationSource()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .formLogin().disable()
                .logout().disable()
                .httpBasic().disable()
                .headers().frameOptions().sameOrigin().and()
                .authorizeRequests()
                    .antMatchers("/ws").permitAll()
                    .antMatchers("/registration").permitAll()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/unsecure-endpoint").permitAll()
                    .antMatchers("/secure-endpoint").hasRole("USER")
                    .anyRequest().authenticated()
                .and()
                    .addFilterAfter(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling()
                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        ;
    }

//    @Bean
//    CORSFilter CorsFilter() {
//        return new CORSFilter(corsConfigurationSource());
//    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/ws**").allowedOriginPatterns("http://localhost:8081");
//                registry.addMapping("/unsecure-endpoint").allowedOriginPatterns("*");
//                registry.addMapping("/**").allowedOriginPatterns("*");
//            }
//        };
//    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        final CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOriginPatterns(List.of("http://jxy.me"));
////        configuration.setAllowedOrigins(List.of("*"));
//        configuration.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
//        configuration.setAllowCredentials(true);
//        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/ws*", configuration);
//        return source;
//    }

//    @Override
//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(provider);
//    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return userDetailServiceImp;
    }
}
