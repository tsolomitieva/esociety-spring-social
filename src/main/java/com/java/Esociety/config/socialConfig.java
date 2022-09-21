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
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
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
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.sql.DataSource;


@Configuration


public class socialConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    private ConnectionFactoryLocator addConnectionFactories() {
        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
        registry.addConnectionFactory(new FacebookConnectionFactory(
            "1044551659727911",
           "c3ee51fc1b33108f554a8a9ff8415115"));

        registry.addConnectionFactory(new InstagramConnectionFactory("807956450244413","5fe3bbfb4e8bce50846518d56d431df6"));
        registry.addConnectionFactory(new TwitterConnectionFactory("JN6fhreV3vCMs9a6SVfKs36sQ", "g07NDnAipVHFwPTEDBGnUUApyE3y2uZvNS81r4MnqiMNIqBDN9"));
       return registry;

    }


    @Bean
    public UsersConnectionRepository getUsersConnectionRepository() {
        return new JdbcUsersConnectionRepository(dataSource, addConnectionFactories(), Encryptors.noOpText());

     }

    @Bean
    @Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)
    public ConnectionRepository connectionRepository(){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
        throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
    }
        return getUsersConnectionRepository().createConnectionRepository(authentication.getName());
}




  @Bean
   public ConnectController connectController() {
        ConnectController controller = new ConnectController(addConnectionFactories(), connectionRepository());

        controller.setViewPath("");

   return controller ;
    }





}
