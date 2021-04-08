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
import pro.bashkatov.pokerapi.config.filter.TokenAuthenticationFilter;
import pro.bashkatov.pokerapi.model.security.service.TokenProvider;

@Configuration
@EnableWebSecurity
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
                .cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .formLogin().disable()
                .logout().disable()
                .httpBasic().disable()
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
