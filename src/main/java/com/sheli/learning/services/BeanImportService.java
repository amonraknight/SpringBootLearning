package com.sheli.learning.services;

import com.sheli.learning.objects.AsychUser;
import com.sheli.learning.objects.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class BeanImportService {
    private static final Logger logger = LoggerFactory.getLogger(BeanImportService.class);

    @Autowired
    private ApplicationContext configContext;

    public AsychUser getAsycUser() {
        AsychUser targetUser = configContext.getBean("AUser", AsychUser.class);

        return targetUser;
    }

    public void checkBeansInContainer() {
        logger.info("Is AsycUser in container? {}",configContext.containsBean("AUser"));
        logger.info("Is tracer in container? {}",configContext.containsBean("tracer"));
    }

    public Car getMyCar() {
        return (Car) configContext.getBean(Car.class);
    }
}
