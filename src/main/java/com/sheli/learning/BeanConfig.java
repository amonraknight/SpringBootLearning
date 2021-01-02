package com.sheli.learning;

import com.sheli.learning.objects.AsychUser;
import com.sheli.learning.objects.Car;
import com.sheli.learning.objects.UserTracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = true)
@EnableAutoConfiguration
@EnableConfigurationProperties(Car.class)
public class BeanConfig {

    private static final Logger logger = LoggerFactory.getLogger(BeanConfig.class);

    @Bean("AUser")
    public AsychUser getAUser() {
        AsychUser aUser = new AsychUser();

        aUser.setName("John Doe");
        aUser.setBlog("John Doe's Blog");
        logger.info(aUser.toString());

        return aUser;
    }

    @ConditionalOnBean(name = "AUser")
    @Bean
    public UserTracer tracer() {
        UserTracer tractor = new UserTracer();
        tractor.setTractorName("This is a tracer");
        tractor.setTargetUser(getAUser());
        return tractor;
    }


}
