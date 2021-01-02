package com.sheli.learning.services;

import com.sheli.learning.beans.factorybeans.MyFactoryBean;
import com.sheli.learning.objects.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BeanFactoryTestService {

    @Autowired
    private ApplicationContext applicationContext;

    public void showContextInfo (){
        log.info("The context name is '{}'.", applicationContext.getClass().getName());
        log.info("The application name is {}.", applicationContext.getEnvironment().getProperty("spring.application.name"));
        log.info("The application has {} beans.", applicationContext.getBeanDefinitionCount());
        for(String name : applicationContext.getBeanDefinitionNames()){
            if(!name.contains("."))
                log.info("Bean '{}' is in context.", name);
        }
    }

    public void getBeanFromFactoryBean() throws Exception {
        Book aBook= (Book)applicationContext.getBean("BookBean");
        log.info("Get a book, name {}, ISBN {}.", aBook.getTitle(), aBook.getIsbn());
        //get the factory itself
        MyFactoryBean factoryBean= (MyFactoryBean)applicationContext.getBean("&BookBean");
        log.info("The class is {}. Get a book, name {}, ISBN {}.", factoryBean.getClass(), factoryBean.getObject().getTitle(), factoryBean.getObject().getIsbn());
    }


}
