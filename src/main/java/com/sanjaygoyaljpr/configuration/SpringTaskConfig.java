package com.sanjaygoyaljpr.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan({ "com.sanjaygoyaljpr.task" })
public class SpringTaskConfig {

}
