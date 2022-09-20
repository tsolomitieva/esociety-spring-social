package com.java.Esociety.config;

import com.java.Esociety.entities.Social;
import com.java.Esociety.entities.User;
import com.java.Esociety.repositories.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.*;

import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.instagram.connect.InstagramConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.sql.DataSource;


@Configuration
@EnableSocial

public class socialConfig implements SocialConfigurer {


    @Autowired
    DataSource dataSource;



    @Autowired
    userRepository userRepository;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        connectionFactoryConfigurer.addConnectionFactory(new FacebookConnectionFactory(
            "1044551659727911",
           "c3ee51fc1b33108f554a8a9ff8415115"));

        connectionFactoryConfigurer.addConnectionFactory(new InstagramConnectionFactory("807956450244413","5fe3bbfb4e8bce50846518d56d431df6"));
        connectionFactoryConfigurer.addConnectionFactory(new TwitterConnectionFactory("JN6fhreV3vCMs9a6SVfKs36sQ", "g07NDnAipVHFwPTEDBGnUUApyE3y2uZvNS81r4MnqiMNIqBDN9"));


    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }


    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());

   }

  @Bean
  public TwitterTemplate twitterTemplate(){
      TwitterTemplate twitter= new TwitterTemplate("JN6fhreV3vCMs9a6SVfKs36sQ","g07NDnAipVHFwPTEDBGnUUApyE3y2uZvNS81r4MnqiMNIqBDN9","1568177439129444354-dmxrONmIPhDw5xRihszItHlldDXl4E","jiFTlGXP7QfxNrXEtov9SUGSEsMhHOeqtZ0J5U20U00GK");
      return twitter;
  }


  @Bean
   public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
        ConnectController controller = new ConnectController(connectionFactoryLocator, connectionRepository);
       controller.setApplicationUrl("");
        controller.setViewPath("");

   return controller ;
    }





}
