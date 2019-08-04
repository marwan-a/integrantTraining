package com.javatpoint.security;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Autowired
    private DataSource dataSource;
    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
    }
 
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	auth
        .jdbcAuthentication()
            .dataSource(dataSource)
            .passwordEncoder(passwordEncoder())
            .usersByUsernameQuery(
                    "SELECT email as username,password,enabled FROM users where email=?")
            .authoritiesByUsernameQuery(
    "SELECT u.email as username,r.name as user_role from users u inner join users_roles ur on (u.user_id=ur.user_id ) inner join roles r on (ur.role_id=r.role_id) where u.email=?");
    
    }
 
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }
 
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
            .antMatchers("/anonymous*").anonymous()
            .antMatchers("/login*").permitAll()
            .antMatchers("/").permitAll()
            .antMatchers("/user/registration").permitAll()
            .antMatchers("/user/resendRegistrationToken").permitAll()
            .antMatchers("/confirm-account").permitAll()
            .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
            .anyRequest().authenticated()
            .and()
            .formLogin().usernameParameter("username").passwordParameter("password")
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .successHandler(myAuthenticationSuccessHandler());
    }
     
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
