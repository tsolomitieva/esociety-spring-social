package com.java.Esociety.config;


import com.java.Esociety.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@Configuration
@ComponentScan("com.java.Esociety")

public class config extends WebSecurityConfigurerAdapter {


    @Autowired
    com.java.Esociety.services.userService userService;


    @Bean
    PasswordEncoder bcryptPasswordEncoder(){
        return new BCryptPasswordEncoder();

    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(bcryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userService);
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(daoAuthenticationProvider());

    }

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