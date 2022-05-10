package com.kula.growthchannelapi;

import com.kula.growthchannelapi.config.OAuthGoogleProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@EnableConfigurationProperties({OAuthGoogleProperties.class})
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.kula.growthchannelapi.repository"})
public class GrowthchannelApiApplication  {

    public static void main(String[] args) {
        SpringApplication.run(GrowthchannelApiApplication.class, args);
    }


}
