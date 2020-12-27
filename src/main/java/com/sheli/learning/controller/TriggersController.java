package com.sheli.learning.controller;

import com.sheli.learning.cashing.SimpleBookRepository;
import com.sheli.learning.objects.AsychUser;
import com.sheli.learning.services.BeanFactoryTestService;
import com.sheli.learning.services.GitHubLookupService;
import com.sheli.learning.services.MultiThreadDemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/triggers")
public class TriggersController {

    private static final Logger logger = LoggerFactory.getLogger(TriggersController.class);

    @Autowired
    SimpleBookRepository simpleBookRepository;

    @Autowired
    MultiThreadDemoService multiThreadDemoService;

    @Autowired
    GitHubLookupService gitHubLookupService;

    @Autowired
    BeanFactoryTestService beanFactoryTestService;

    //load ApplicationContext
    @GetMapping("/getClassesInContext")
    public String getClassesInContext() {
        beanFactoryTestService.showContextInfo();

        beanFactoryTestService.getBeanFromFactoryBean();
        return "getClassesInContext";
    }

    @GetMapping("/concurrenttasks")
    public String triggerAJob() {
        logger.debug("/concurrenttasks");
        for(int i=0;i<3;i++){
            multiThreadDemoService.executeAsyncTask();
        }
        return "concurrenttasks";
    }

    @GetMapping("/githubusertest")
    public String triggerGitHubTests() {
        logger.debug("/githubusertest");

        // Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        try {
            CompletableFuture<AsychUser> page1 = gitHubLookupService.findUser("PivotalSoftware");
            CompletableFuture<AsychUser> page2 = gitHubLookupService.findUser("CloudFoundry");
            CompletableFuture<AsychUser> page3 = gitHubLookupService.findUser("Spring-Projects");

            // Wait until they are all done
            CompletableFuture.allOf(page1,page2,page3).join();

            // Print results, including elapsed time
            logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
            logger.info("--> " + page1.get());
            logger.info("--> " + page2.get());
            logger.info("--> " + page3.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return "githubusertest";
    }

    @GetMapping("/getCache")
    public String getCache(){
        logger.debug("/getCache");

        logger.info(".... Fetching books");
        logger.info("isbn-1234 -->" + simpleBookRepository.getByIsbn("isbn-1234"));
        logger.info("isbn-4567 -->" + simpleBookRepository.getByIsbn("isbn-4567"));
        logger.info("isbn-1234 -->" + simpleBookRepository.getByIsbn("isbn-1234"));
        logger.info("isbn-4567 -->" + simpleBookRepository.getByIsbn("isbn-4567"));
        logger.info("isbn-1234 -->" + simpleBookRepository.getByIsbn("isbn-1234"));
        logger.info("isbn-1234 -->" + simpleBookRepository.getByIsbn("isbn-1234"));

        return "getCache";
    }
}
