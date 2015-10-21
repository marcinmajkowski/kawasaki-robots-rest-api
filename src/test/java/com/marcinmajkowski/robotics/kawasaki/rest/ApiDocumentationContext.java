package com.marcinmajkowski.robotics.kawasaki.rest;

import com.marcinmajkowski.robotics.kawasaki.rest.service.LocationService;
import com.marcinmajkowski.robotics.kawasaki.rest.service.RealService;
import com.marcinmajkowski.robotics.kawasaki.rest.service.RobotService;
import com.marcinmajkowski.robotics.kawasaki.rest.service.StringService;
import com.marcinmajkowski.robotics.kawasaki.rest.web.RobotController;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = RobotController.class)
public class ApiDocumentationContext {
    @Bean
    public LocationService locationService() {
        return Mockito.mock(LocationService.class);
    }

    @Bean
    public RealService realService() {
        return Mockito.mock(RealService.class);
    }

    @Bean
    public RobotService robotService() {
        return Mockito.mock(RobotService.class);
    }

    @Bean
    public StringService stringService() {
        return Mockito.mock(StringService.class);
    }
}
