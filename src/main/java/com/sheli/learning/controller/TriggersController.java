package com.sheli.learning.controller;

import com.sheli.learning.cashing.SimpleBookRepository;
import com.sheli.learning.objects.AsychUser;
import com.sheli.learning.objects.Book;
import com.sheli.learning.objects.Car;
import com.sheli.learning.services.BeanFactoryTestService;
import com.sheli.learning.services.BeanImportService;
import com.sheli.learning.services.GitHubLookupService;
import com.sheli.learning.services.MultiThreadDemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/triggers")
public class TriggersController {

    @Autowired
    SimpleBookRepository simpleBookRepository;

    @Autowired
    MultiThreadDemoService multiThreadDemoService;

    @Autowired
    GitHubLookupService gitHubLookupService;

    @Autowired
    BeanFactoryTestService beanFactoryTestService;

    @Autowired
    BeanImportService beanImportService;

    //load ApplicationContext
    @GetMapping("/getClassesInContext")
    public String getClassesInContext() throws Exception {
        beanFactoryTestService.showContextInfo();

        beanFactoryTestService.getBeanFromFactoryBean();
        return "getClassesInContext";
    }

    @GetMapping("/concurrenttasks")
    public String triggerAJob() {
        log.debug("/concurrenttasks");
        for(int i=0;i<3;i++){
            multiThreadDemoService.executeAsyncTask();
        }
        return "concurrenttasks";
    }

    @GetMapping("/githubusertest")
    public String triggerGitHubTests() {
        log.debug("/githubusertest");

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
            log.info("Elapsed time: " + (System.currentTimeMillis() - start));
            log.info("--> " + page1.get());
            log.info("--> " + page2.get());
            log.info("--> " + page3.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return "githubusertest";
    }

    @GetMapping("/getCache")
    public String getCache(){
        log.debug("/getCache");

        log.info(".... Fetching books");
        log.info("isbn-1234 -->" + simpleBookRepository.getByIsbn("isbn-1234"));
        log.info("isbn-4567 -->" + simpleBookRepository.getByIsbn("isbn-4567"));
        log.info("isbn-1234 -->" + simpleBookRepository.getByIsbn("isbn-1234"));
        log.info("isbn-4567 -->" + simpleBookRepository.getByIsbn("isbn-4567"));
        log.info("isbn-1234 -->" + simpleBookRepository.getByIsbn("isbn-1234"));
        log.info("isbn-1234 -->" + simpleBookRepository.getByIsbn("isbn-1234"));

        return "getCache";
    }

    @GetMapping("/triggerBeanAcq")
    public String triggerBeanAcq() {
        beanImportService.checkBeansInContainer();
        return "triggerBeanAcq";
    }

    @GetMapping("/getMyCar")
    public Car getMyCar() {
        return beanImportService.getMyCar();
    }

}
