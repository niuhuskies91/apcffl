package org.apcffl.api.config;
/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
*/
//@Configuration
//@EnableWebSecurity
public class SecurityConfig {//extends WebSecurityConfigurerAdapter {
 /*   
	@Autowired
    private AuthenticationEntryPoint authEntryPoint;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// require all REST service invocations to be authenticated
		http.authorizeRequests().anyRequest().authenticated()
		
		// use authentication entry point for basic authentication
		.and().httpBasic().authenticationEntryPoint(authEntryPoint)
		
		// specify login page
		.and().formLogin().loginPage("/login").and()
		
		// session timeout
		.and().successHandler(new SessionTimeoutAuthSuccessHandler(Duration.ofHours(8))).permitAll()
	      .and().logout().logoutUrl("/logout").permitAll();
	} 
	
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }*/
    
    
    /**
     * 
     *     @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
         
        String password = "123";
 
        String encrytedPassword = this.passwordEncoder().encode(password);
        System.out.println("Encoded password of 123=" + encrytedPassword);
         
         
        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> //
        mngConfig = auth.inMemoryAuthentication();
 
        // Defines 2 users, stored in memory.
        // ** Spring BOOT >= 2.x (Spring Security 5.x)
        // Spring auto add ROLE_
        UserDetails u1 = User.withUsername("tom").password(encrytedPassword).roles("USER").build();
        UserDetails u2 = User.withUsername("jerry").password(encrytedPassword).roles("USER").build();
 
        mngConfig.withUser(u1);
        mngConfig.withUser(u2);
 
    }
     * 
     */
}
