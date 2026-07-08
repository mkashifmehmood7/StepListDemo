package com.example.steplistdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Main entry point for the Spring Boot application.
 *
 * [@SpringBootApplication] is a convenience annotation that combines:
 * - [@Configuration]: marks this class as a source of bean definitions
 * - [@EnableAutoConfiguration]: enables Spring Boot auto-configuration
 * - [@ComponentScan]: scans the current package and sub-packages for Spring components
 */
@SpringBootApplication
class StepListDemoApplication

/**
 * Standard JVM main method. Spring Boot starts the embedded server from here.
 *
 * [runApplication] bootstraps the Spring context, loads beans, and starts Tomcat.
 */
fun main(args: Array<String>) {
    runApplication<StepListDemoApplication>(*args)
}
