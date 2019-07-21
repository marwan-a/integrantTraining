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
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.javatpoint.services.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {
 
	@Autowired
	private MyUserDetailsService userDetailsService;
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
    "SELECT u.email as username,r.name as user_role from users u inner join users_roles ur on (u.user_id=ur.user_id ) inner join role r on (ur.role_id=r.role_id) where u.email=?");
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
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//         .userDetailsService(userDetailsService)
//         .passwordEncoder(passwordEncoder());
//    }
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http         
//         .headers()
//          .frameOptions().sameOrigin()
//          .and()
//            .authorizeRequests()
//             .antMatchers("/resources/**", "/webjars/**","/assets/**").permitAll()
//                .antMatchers("/").permitAll()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .anyRequest().authenticated()
//                .and()
//            .formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/home")
//                .failureUrl("/login?error")
//                .permitAll()
//                .and()
//            .logout()
//             .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//             .logoutSuccessUrl("/login?logout")
//             .deleteCookies("my-remember-me-cookie")
//                .permitAll()
//                .and()
//             .rememberMe()
//              //.key("my-secure-key")
//              .rememberMeCookieName("my-remember-me-cookie")
//              .tokenRepository(persistentTokenRepository())
//              .tokenValiditySeconds(24 * 60 * 60)
//              .and()
//            .exceptionHandling()
//              ;
//    }
//    PersistentTokenRepository persistentTokenRepository(){
//        JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
//        tokenRepositoryImpl.setDataSource(dataSource);
//        return tokenRepositoryImpl;
//       }
    
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	 
//	@Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider((AuthenticationProvider) userDetailsService());
//    }
//	@Override
//    protected void configure(HttpSecurity security) throws Exception
//    {
//     security.httpBasic().disable();
//    }
//	@Bean
//	public DaoAuthenticationProvider authProvider() {
//	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//	    authProvider.setUserDetailsService(userDetailsService);
//	    authProvider.setPasswordEncoder(passwordEncoder);
//	    return authProvider;
//	}
//    @Override
//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//          .withUser("user1").password(passwordEncoder().encode("user1Pass")).roles("USER")
//          .and()
//          .withUser("user2").password(passwordEncoder().encode("user2Pass")).roles("USER")
//          .and()
//          .withUser("admin").password(passwordEncoder().encode("adminPass")).roles("ADMIN");
//    }
 
//    @Override
//    protected void configure(final HttpSecurity http) throws Exception {
//        http
//          .csrf().disable()
//          .authorizeRequests()
//          .antMatchers("/admin/**").hasRole("ADMIN")
//          .antMatchers("/anonymous*").anonymous()
//          .antMatchers("/login*").permitAll()
//          .anyRequest().authenticated()
//          .and()
//          .formLogin()
//          //.loginPage("/login.html")
//          .loginProcessingUrl("/perform_login")
//          .defaultSuccessUrl("/", true)
//          //.failureUrl("/login.html?error=true")
//          .and()
//          .logout()
//          .logoutUrl("/perform_logout")
//          .deleteCookies("JSESSIONID");   
//    
//    }
     
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
