package pro.bashkatov.pokerapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import pro.bashkatov.pokerapi.model.security.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    private final UserDetailsService userDetailServiceImp;

    public WebSecurityConfig(UserService userDetailServiceImp) {
        this.userDetailServiceImp = userDetailServiceImp;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                    .antMatchers("/ws").permitAll()
                    .antMatchers("/registration").permitAll()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/unsecure-endpoint").permitAll()
                    .antMatchers("/secure-endpoint").hasRole("USER")
                    .anyRequest().authenticated()
                    .and()
                .formLogin().loginPage("/login").permitAll().and()
                .logout().permitAll()
                .and()
//                    .anonymous().disable()
                    .exceptionHandling()
                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        ;
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return userDetailServiceImp;
    }
}
