package com.java.Esociety;

import com.java.Esociety.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@EnableWebSecurity
@SpringBootApplication

public class EsocietyApplication extends WebSecurityConfigurerAdapter{

	public static void main(String[] args) {
		SpringApplication.run(EsocietyApplication.class, args);
	}

	@Autowired
	userService userService;

	@Bean
 	PasswordEncoder bcryptPasswordEncoder(){
		return new BCryptPasswordEncoder();

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.authenticationProvider(daoAuthenticationProvider());

	}

	@Bean
	DaoAuthenticationProvider daoAuthenticationProvider(){
		DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(bcryptPasswordEncoder());
		daoAuthenticationProvider.setUserDetailsService(this.userService);
		return daoAuthenticationProvider;
	}

	/*@Bean
	public DataSource dataSource()
	{
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("org.postgresql.Driver");
		dataSourceBuilder.url("jdbc:postgres://localhost:5432/esociety");
		dataSourceBuilder.username("postgres");
		dataSourceBuilder.password("2000");
		return dataSourceBuilder.build();
	}*/



	@Bean
	public TextEncryptor textEncryptor(){
		return new TextEncryptor() {
			@Override
			public String encrypt(String text) {
				return text;
			}

			@Override
			public String decrypt(String encryptedText) {
				return encryptedText;
			}
		};
	}
    @Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
				.antMatchers("/login").permitAll()
				.antMatchers("/register").permitAll()
				.antMatchers("/home/**").authenticated()
				.antMatchers("/home/me").authenticated()
				.antMatchers("/admin/**").hasAnyRole("ADMIN")
				.and()
				.formLogin()
				.loginPage("/login")
				.usernameParameter("email")

				.defaultSuccessUrl("/home")

				.permitAll()
				.and()
				.logout()
				.logoutSuccessUrl("/login?logout=true")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.permitAll()
				.and()
				.csrf()
				.disable();
	}
	@Override
	public void configure(WebSecurity web) {
		web.ignoring()
				.antMatchers("/resources/**", "/static/**","/static/css");
	}




}
