package com.saifintex.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.saifintex.jwt.JwtAuthenticationEntryPoint;
import com.saifintex.jwt.JwtAuthenticationTokenFilter;

@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	public void configureGLobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder);
	}

	// configure security for rest apis
	@Configuration
	public static class RestApiCongurationAdapter extends WebSecurityConfigurerAdapter {

		@Autowired
		private JwtAuthenticationEntryPoint unauthorizedHandler;

		@Bean
		public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
			return new JwtAuthenticationTokenFilter();
		}

		@Override
		protected void configure(HttpSecurity httpSecurity) throws Exception {
			httpSecurity

					// don't create session
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
					.authorizeRequests()
					// .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
					// allow anonymous resource requests
					.antMatchers("/resources/**", "/auth/**", "/rest/public/**", "/file/download", "/rest/profileImage")
					.permitAll().anyRequest().authenticated();
			// Custom JWT based security filter
			httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

			httpSecurity.exceptionHandling().authenticationEntryPoint(unauthorizedHandler);

			// we don't need CSRF because our token is invulnerable
			httpSecurity.csrf().disable();

			// disable page caching
			httpSecurity.headers().cacheControl();
		}
	}

	// configure security for web application
	// @Order(1) is a preference. Server will check this first and then rest api
	// configuration

	@Configuration
	@Order(1)
	public static class WebApiCongurationAdapter extends WebSecurityConfigurerAdapter {

		@Autowired
		private AuthenticationSuccessHandler authenticationSuccessHandler;
		@Autowired
		private AjaxCallEntryPoint ajaxCallEntryPoint;
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/admin/**").authorizeRequests()
					.antMatchers("/admin/registerWebUser","/admin/registrationForm","/admin/login", "/admin/", "/admin/logout", "/admin/loginerror", "/admin/sessionout",
							"/admin/forgotPassword**", "/resources/**")
				
					.permitAll().antMatchers("/admin/resetPassword")/* .permitAll() */
					.hasAuthority("CHANGE_PASSWORD_PRIVILEGE").anyRequest().authenticated().and().formLogin()
					.loginPage("/admin/login").successHandler(authenticationSuccessHandler)
					.failureUrl("/admin/loginerror").and().logout().deleteCookies("JSESSIONID").permitAll();
			http.sessionManagement().enableSessionUrlRewriting(false).maximumSessions(2)
					.expiredUrl("/admin/sessionExpired")
					
					.maxSessionsPreventsLogin(true)
					
					;
		http.exceptionHandling().authenticationEntryPoint(ajaxCallEntryPoint);
			
			//.and()
					//.invalidSessionUrl("/admin/sessionout").sessionFixation().newSession();

		}
	}
	@Configuration
	@Order(2)
	public static class WebUsersConfigurationAdapter extends WebApiCongurationAdapter{
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/user/**").authorizeRequests().
			antMatchers("/user/login","/user/logout","/user/forgotPassword").permitAll().anyRequest().authenticated().and().formLogin().loginPage("/user/login");
			
		}
	}

}
