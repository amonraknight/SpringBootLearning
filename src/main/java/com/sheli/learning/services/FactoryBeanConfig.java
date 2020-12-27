package com.sheli.learning.services;

import com.sheli.learning.beans.factorybeans.MyFactoryBean;
import com.sheli.learning.objects.Book;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
//@Import({Book.class, java.lang.String.class})
public class FactoryBeanConfig {

    @Bean
    public MyFactoryBean BookBean() {//The function name is directly the if of this bean.
        return new MyFactoryBean();
    }
}
