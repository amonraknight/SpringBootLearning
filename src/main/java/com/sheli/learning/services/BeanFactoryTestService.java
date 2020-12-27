package com.sheli.learning.services;

import com.sheli.learning.objects.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class BeanFactoryTestService {

    private static final Logger logger = LoggerFactory.getLogger(BeanFactoryTestService.class);

    @Autowired
    private ApplicationContext applicationContext;

    public void showContextInfo (){
        logger.info("The context name is '{}'.", applicationContext.getClass().getName());
        logger.info("The application name is {}.", applicationContext.getEnvironment().getProperty("spring.application.name"));
        logger.info("The application has {} beans.", applicationContext.getBeanDefinitionCount());
        for(String name : applicationContext.getBeanDefinitionNames()){
            if(!name.contains("."))
                logger.info("Bean '{}' is in context.", name);
        }
    }

    public void getBeanFromFactoryBean() {
        Book aBook= (Book)applicationContext.getBean("BookBean");
        logger.info("Get a book, name {}, ISBN {}.", aBook.getTitle(), aBook.getIsbn());
    }
}
