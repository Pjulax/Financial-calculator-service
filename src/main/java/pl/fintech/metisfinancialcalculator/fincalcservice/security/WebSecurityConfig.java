package pl.fintech.metisfinancialcalculator.fincalcservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Configuration
    @Order(1)
    public static class BasicAuthConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private  JwtTokenProvider jwtTokenProvider;

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            // Disable CSRF (cross site request forgery)
            http.csrf().disable();

            // No session will be created or used by spring security
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            // Entry points
            http.antMatcher("/users/signin")
                .authorizeRequests(authorize -> authorize
                    .anyRequest().hasAnyRole("USER","ADMIN"));
                    // Disallow everything else..

            // If a user try to access a resource without having enough permissions
            http.exceptionHandling().accessDeniedPage("/login");

            // Optional, if you want to test the API from a browser
            http.httpBasic(withDefaults());
        }
    }

    @Configuration
    @Order(2)
    public static class JwtConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Value("${management.server.port}")
        private int managementPort;

        @Autowired
        private  JwtTokenProvider jwtTokenProvider;

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            // Disable CSRF (cross site request forgery)
            http.csrf().disable();

            // No session will be created or used by spring security
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            // Entry points
            http.authorizeRequests()//
                    .antMatchers("/v2/api-docs").permitAll()
                    .antMatchers("/swagger-resources").permitAll()
                    .antMatchers("/swagger-resources/configuration/ui").permitAll()
                    .antMatchers("/swagger-resources/configuration/security").permitAll()
                    .antMatchers("/users/signin").permitAll()//
                    .antMatchers("/users/signup").permitAll()//
                    .antMatchers("/h2-console/**/**").permitAll()
                    .requestMatchers(checkPort(managementPort)).permitAll()
                    // Disallow everything else..
                    .anyRequest().authenticated();

            // If a user try to access a resource without having enough permissions
            http.exceptionHandling().accessDeniedPage("/login");

            // Apply JWT
            http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

            // Optional, if you want to test the API from a browser
            // http.httpBasic();
        }

        private RequestMatcher checkPort(final int port) {
            return (HttpServletRequest request) -> port == request.getLocalPort();
        }
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Allow swagger to be accessed without authentication
        web.ignoring().antMatchers("/v2/api-docs")//
                .antMatchers("/swagger-resources/**")//
                .antMatchers("/swagger-ui.html")//
                .antMatchers("/configuration/**")//
                .antMatchers("/webjars/**")//
                .antMatchers("/public")//;


                // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
                .and()
                .ignoring()
                .antMatchers("/h2-console/**/**")
                .and()
                .ignoring()
                .antMatchers("/portfolios")
                .antMatchers("/investments")
                .antMatchers("/portfolios/**")
                .antMatchers("/investments/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}