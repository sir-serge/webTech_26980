package com.taskbuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TaskBuddyApplication — Entry point for the Spring Boot application.
 *
 * @SpringBootApplication combines:
 *   - @Configuration        → marks this class as a bean source
 *   - @EnableAutoConfiguration → auto-configures Spring based on classpath
 *   - @ComponentScan        → scans com.taskbuddy.* for Spring components
 */
@SpringBootApplication
public class TaskbuddyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskbuddyApplication.class, args);
    }
}
